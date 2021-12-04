package com.healthcare.billing.repository.jdbc;

import com.healthcare.billing.exception.ConnectionException;
import com.healthcare.billing.model.*;
import com.healthcare.billing.model.Currency;
import com.healthcare.billing.repository.json.JSONCodeManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.chrono.ThaiBuddhistEra;
import java.util.*;

public class JDBCRepositoryImpl {

    private final Map<String, Currency> currencies;
    private final JSONCodeManager manager;
    private final Random rnd;

    public JDBCRepositoryImpl() {
        manager = JSONCodeManager.getInstance();
        rnd = new Random();
        currencies = new HashMap<>();
        currencies.put("USD", new Currency("USD", (char) 36));
    }

    public Currency getCurrency(String identifier) {
        return currencies.get(identifier);
    }

    public List<CPTCodeRate> getCPTRates() {
        System.out.println("getCPTRates starts");
        List<CPTCodeRate> codes = new LinkedList<>();
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, CPTCODE_ID, CURRENCY, RATE FROM CPT_RATES");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                codes.add(getCPTRateCode(rs));
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        System.out.println("getCPTRates ends");
        return codes;
    }

    public List<CPTCodeRate> getCPTRates(String cptCode) {
        System.out.println("getCPTRates starts");
        List<CPTCodeRate> codes = new LinkedList<>();
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            StringBuilder sql = new StringBuilder("SELECT ID, CPTCODE_ID, CURRENCY, RATE FROM CPT_RATES");
            Map<Integer, Object> parameters = new HashMap<>();
            CPT cpt = manager.getCPT(cptCode);
            CPTCodeRate rate = new CPTCodeRate();
            rate.setCptCode(cpt);
            String condition = buildConditionCPTRate(rate, parameters);
            if (condition != null) {
                sql.append(" WHERE ");
                sql.append(condition);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            for (Integer key : parameters.keySet()) {
                Object o = parameters.get(key);
                if (o instanceof Integer) {
                    preparedStatement.setInt(key, (Integer) o);
                } else if (o instanceof String) {
                    preparedStatement.setString(key, (String) o);
                }
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                codes.add(getCPTRateCode(rs));
            }
        } catch (ConnectionException e) {

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        System.out.println("getCPTRates ends");
        return codes;
    }

    private String buildConditionCPTRate(CPTCodeRate code, Map<Integer, Object> parameters) {
        if (code == null) return null;
        if (code.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();
        int parametersCount = 1;
        if (code.getId() != null) {
            sb.append(" ID = ?");
            parameters.put(parametersCount++, Integer.valueOf(code.getId()));
        }
        if (code.getCptCode() != null) {
            if (parametersCount == 2) sb.append(" AND");
            sb.append(" CPTCODE_ID = ?");
            parameters.put(parametersCount++, code.getCptCode().getCode());
        }
        if (code.getCurrency() != null) {
            if (parametersCount == 3) sb.append(" AND");
            sb.append(" CURRENCY = ?");
            parameters.put(parametersCount++, code.getCurrency().getIdentifier());
        }
        if (code.getRate() != 0) {
            if (parametersCount == 4) sb.append(" AND");
            sb.append(" RATE = ?");
            parameters.put(parametersCount++, Integer.valueOf(code.getRate()));
        }
        return sb.toString();
    }

    public String createClaim(Claim claim) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            int amount = calculateAmount(claim.getCptCodes());
            String idempotencyKey = UUID.randomUUID().toString();
            String sql = "INSERT INTO CLAIM (ID, PATIENT_ID, STATUS, CURRENCY, AMOUNT, ADDITIONAL_INFO, TIME_CREATED) VALUES (?, ?, ?, ?, ?, ?, NOW())";
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, idempotencyKey);
            ps.setString(index++, claim.getPatientId());
            ps.setInt(index++, 1);
            ps.setString(index++, "USD");
            ps.setInt(index++, amount);
            ps.setString(index++, claim.getAdditionalInfo());
            ps.executeUpdate();
            addClaimCPT(claim.getCptCodes(), idempotencyKey, connection);
            addClaimICD(claim.getIcd10Codes(), idempotencyKey, connection);
            connection.commit();
            return idempotencyKey;
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception", e);
        } finally {
            close(connection);
        }
    }

    public List<Claim> getClaim(Claim claim) {
        StringBuilder sql = new StringBuilder("SELECT ID, ADDITIONAL_INFO, AMOUNT, CURRENCY, PATIENT_ID, STATUS, TIME_CREATED, TIME_UPDATED from CLAIM ");
        Connection conn = null;
        List<Claim> claims = new LinkedList<>();
        try {
            conn = JDBCConnection.getInstance().getConnection();
            Map<Integer, Object> parameters = new HashMap<>();
            String condition = buildConditionClaim(claim, parameters);
            if (condition != null && !condition.equals("")) {
                sql.append(" WHERE ");
                sql.append(condition);
            }
            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());
            for (Integer key : parameters.keySet()) {
                Object o = parameters.get(key);
                if (o instanceof Integer) {
                    preparedStatement.setInt(key, (Integer) o);
                } else if (o instanceof String) {
                    preparedStatement.setString(key, (String) o);
                }
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                claims.add(getClaim(rs));
                updateCPTs(claim, conn);
                updateICDs(claim, conn);
            }
            return claims;
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } finally {
            close(conn);
        }
    }

    private String buildConditionClaim(Claim claim, Map<Integer, Object> parameters) {
        if (claim == null) return null;
        StringBuilder sb = new StringBuilder();
        int parametersCount = 1;
        if (claim.getId() != null) {
            sb.append(" ID = ?");
            parameters.put(parametersCount++, Integer.valueOf(claim.getId()));
        }
        if (claim.getPatientId() != null) {
            if (parametersCount == 2) sb.append(" AND");
            sb.append(" PATIENT_ID = ?");
            parameters.put(parametersCount++, claim.getPatientId());
        }
        if (claim.getStatus() != null) {
            if (parametersCount == 3) sb.append(" AND");
            sb.append(" STATUS = ?");
            parameters.put(parametersCount++, Integer.valueOf(claim.getStatus().getStatus()));
        }
        return sb.toString();
    }

    public Claim getClaim(String id) {
        String sql = "SELECT ID, ADDITIONAL_INFO, AMOUNT, CURRENCY, PATIENT_ID, STATUS, TIME_CREATED, TIME_UPDATED from CLAIM WHERE ID = ?";
        Connection conn = null;
        try {
            conn = JDBCConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Claim claim = null;
            while (rs.next()) {
                claim = getClaim(rs);
            }
            updateCPTs(claim, conn);
            updateICDs(claim, conn);
            return claim;
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } finally {
            close(conn);
        }
    }

    public void addTransaction(Transaction transaction) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            String sql = "INSERT INTO TRANSACTION (CLAIM_ID, AMOUNT, COMMENTS, PAYER_ID, PAYEE_TYPE, TIME_CREATED, TRANSACTION_TYPE, CURRENCY)" +
                    " VALUES (?, ?, ?, ?, ?, NOW(), ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, transaction.getClaimId());
            ps.setInt(index++, transaction.getAmount());
            ps.setString(index++, transaction.getComments());
            ps.setString(index++, transaction.getPayerId());
            ps.setInt(index++, transaction.getPayeeType());
            ps.setInt(index++, transaction.getTransactionType());
            ps.setString(index++, transaction.getCurrency().getIdentifier());
            ps.executeUpdate();
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("UnexpectedError", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("UnexpectedError", e);
        } finally {
            close(connection);
        }
    }

    public void updateClaim(Claim claim) {
        StringBuilder sql = new StringBuilder("UPDATE CLAIM SET ");
        Map<Integer, Object> parameters = new HashMap<>();
        String condition = buildUpdateCondition(claim, parameters);
        if (condition != null) {
            sql.append(condition);
            sql.append(" WHERE ID = ?");
            Connection connection = null;
            try {
                connection = JDBCConnection.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql.toString());
                for (int key : parameters.keySet()) {
                    Object o = parameters.get(key);
                    if (o instanceof Integer) {
                        ps.setInt(key, (Integer) o);
                    } else if (o instanceof String) {
                        ps.setString(key, (String) o);
                    }
                }
                ps.setString(parameters.size() + 1, claim.getId());
                ps.executeUpdate();
            } catch (ConnectionException e) {
                e.printStackTrace();
                throw new RuntimeException("Unexpected error", e);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Unexpected error", e);
            }
        }
    }

    public List<Transaction> getTransactions(Claim claim) {
        Connection connection = null;
        List<Transaction> transactions = new LinkedList<>();
        try {
            connection = JDBCConnection.getInstance().getConnection();
            String sql = "SELECT ID, CLAIM_ID, AMOUNT, PAYER_ID, TIME_CREATED, CURRENCY FROM TRANSACTION WHERE CLAIM_ID = ? AND PAYEE_TYPE = 2 AND TRANSACTION_TYPE = 1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, claim.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = getTransactions(rs);
                transaction.setPayeeType(2);
                transaction.setTransactionType(1);
                transactions.add(transaction);
            }
            return transactions;
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } finally {
            close(connection);
        }
    }

    private Transaction getTransactions(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        int index = 1;
        transaction.setId(String.valueOf(rs.getInt(index++)));
        transaction.setClaimId(rs.getString(index++));
        transaction.setAmount(rs.getInt(index++));
        transaction.setPayerId(rs.getString(index++));
        transaction.setTimeCreated(rs.getTimestamp(index++));
        transaction.setCurrency(currencies.get(rs.getString(index++)));
        return transaction;
    }

    private String buildUpdateCondition(Claim claim, Map<Integer, Object> parameter) {
        StringBuilder sb = new StringBuilder();
        int parameterIndex = 1;
        if (claim.getStatus() != null) {
            sb.append(" STATUS = ? ");
            parameter.put(parameterIndex++, Integer.valueOf(claim.getStatus().getStatus()));
        }
        if (parameterIndex == 2) {
            sb.append(", TIME_UPDATED = NOW()");
        }
        return sb.toString();
    }

    private Claim getClaim(ResultSet rs) throws SQLException {
        Claim claim = new Claim();
        int index = 1;
        claim.setId(rs.getString(index++));
        claim.setAdditionalInfo(rs.getString(index++));
        claim.setTotalAmount(rs.getInt(index++));
        claim.setCurrency(currencies.get(rs.getString(index++)));
        claim.setPatientId(rs.getString(index++));
        claim.setStatus(Status.getStatus(rs.getInt(index++)));
        claim.setTimeCreated(rs.getTimestamp(index++));
        claim.setTimeUpdated(rs.getTimestamp(index++));
        return claim;
    }

    private void updateCPTs(Claim claim, Connection connection) throws SQLException {
        String sql = "SELECT CPTCODE_ID FROM CLAIM_CPT WHERE CLAIM_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, claim.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String code = rs.getString(1);
            CPTCodeRate codeRate = getCPTRates(code).get(0);
            if (claim.getCptCodes() == null) claim.setCptCodes(new LinkedList<>());
            claim.getCptCodes().add(codeRate);
        }
    }

    private void updateICDs(Claim claim, Connection connection) throws SQLException {
        String sql = "SELECT ICD10CODE_ID FROM CLAIM_ICD WHERE CLAIM_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, claim.getId());
        ResultSet rs = ps.executeQuery();
        Map<String, ICD10> codes = new HashMap<>();
        while (rs.next()) {
            String code = rs.getString(1);
            if (code.contains(".")) {
                code = code.substring(0, code.indexOf("."));
            }
            ICD10 icd10 = manager.getICDCodes(code).get(0);
            Queue<ICD10> queue = new LinkedList<>();
            queue.offer(icd10);
            while (!queue.isEmpty()) {
                ICD10 _icd10 = queue.poll();
                codes.put(_icd10.getCode(), _icd10);
                if (_icd10.getSubCodes() != null) {
                    for (ICD10 __icd10 : _icd10.getSubCodes())
                        queue.offer(__icd10);
                }
            }
            if (claim.getIcd10Codes() == null) claim.setIcd10Codes(new LinkedList<>());
            claim.getIcd10Codes().add(codes.get(rs.getString(1)));
        }
    }

    private void addClaimCPT(List<CPTCodeRate> CPTs, String claimID, Connection connection) throws SQLException {
        String sql = "INSERT INTO CLAIM_CPT (CLAIM_ID, CPTCODE_ID) VALUES (?, ?)";
        PreparedStatement ps = null;
        for (CPTCodeRate cpt : CPTs) {
            ps = connection.prepareStatement(sql);
            ps.setString(1, claimID);
            ps.setString(2, cpt.getCptCode().getCode());
            ps.executeUpdate();
        }
    }

    private void addClaimICD(List<ICD10> icd10s, String claimID, Connection connection) throws SQLException {
        String sql = "INSERT INTO CLAIM_ICD (CLAIM_ID, ICD10CODE_ID) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        Queue<ICD10> queue = new LinkedList<>();
        for (ICD10 icd10 : icd10s)
            if (icd10.getSubCodes() != null)
                for (ICD10 _icd10 : icd10.getSubCodes())
                    queue.offer(_icd10);
        while (!queue.isEmpty()) {
            ICD10 code = queue.poll();
            ps.setString(1, claimID);
            ps.setString(2, code.getCode());
            ps.executeUpdate();
            if (code.getSubCodes() != null) {
                for (ICD10 _code : code.getSubCodes())
                    queue.offer(_code);
            }
        }
    }

    private int calculateAmount(List<CPTCodeRate> CPTs) {
        int amount = 0;
        for (CPTCodeRate cpt : CPTs) {
            CPTCodeRate rate = getCPTRates(cpt.getCptCode().getCode()).get(0);
            amount += rate.getRate();
        }
        return amount;
    }

    private CPTCodeRate getCPTRateCode(ResultSet rs) throws SQLException {
        CPTCodeRate code = new CPTCodeRate();
        code.setId(String.valueOf(rs.getInt(1)));
        CPT cpt = manager.getCPT(rs.getString(2));
        code.setCptCode(cpt);
        code.setCurrency(currencies.get(rs.getString(3)));
        code.setRate(rs.getInt(4));
        return code;
    }

    public int addRates(int rate, Currency currency, CPT code, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CPT_RATES (CURRENCY, CPTCODE_ID, RATE) VALUES (?, ?, ?)");
            ps.setString(1, currency.getIdentifier());
            ps.setString(2, code.getCode());
            ps.setInt(3, rate);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addCPTCodes(List<CPT> codes) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            for (CPT code : codes) {
                addRates(getRandomInt(), currencies.get("USD"), code, connection);
            }
        } catch (ConnectionException e) {

        } finally {
            close(connection);
        }
    }

    public int getRandomInt() {
        return rnd.nextInt(4000);
    }

//    public void addRates() {
//        List<CPTGroup> groups = JSONCodeManager.getInstance().getCPTCodes();
//        for (CPTGroup group : groups) {
//            addCPTCodes(group.getCodes());
//        }
//    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
