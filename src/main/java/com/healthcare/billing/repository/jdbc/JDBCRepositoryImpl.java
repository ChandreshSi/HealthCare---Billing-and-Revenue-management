package com.healthcare.billing.repository.jdbc;

import com.healthcare.billing.exception.ConnectionException;
import com.healthcare.billing.model.*;
import com.healthcare.billing.repository.json.JSONCodeManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCRepositoryImpl {

    private final String tenantId;
    private final Map<String, ICD10> icd10Map;

    public JDBCRepositoryImpl() {
        tenantId = "c209d08b-0c02-4461-a0ce-7789934aed56";
        icd10Map = new HashMap<>();
    }

    public void patchCPT(String cptId, CPT cpt) {
        Connection conn = null;
        try {
            conn = JDBCConnection.getInstance().getConnection();
            Map<Integer, Object> parameters = new HashMap<>();
            StringBuilder sql = new StringBuilder("UPDATE CPT_RATE SET ");
            String condition = buildUpdateConditionCPT(cpt, parameters);
            if (condition != null && !condition.equals("")) {
                sql.append(condition);
                sql.append(" WHERE CPT_ID = ?");
                parameters.put(parameters.size() + 1, cptId);
            } else return;
            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());
            for (Integer key : parameters.keySet()) {
                Object o = parameters.get(key);
                if (o instanceof Integer) {
                    preparedStatement.setInt(key, (Integer) o);
                } else if (o instanceof String) {
                    preparedStatement.setString(key, (String) o);
                }
            }
            preparedStatement.executeUpdate();
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception", e);
        } finally {
            close(conn);
        }
    }

    private String buildUpdateConditionCPT(CPT cpt, Map<Integer, Object> parameters) {
        if (cpt == null) return null;
        StringBuilder sb = new StringBuilder();
        int parametersCount = 1;
        if (cpt.getRate() != -1) {
            sb.append(" RATE = ?");
            parameters.put(parametersCount++, Integer.valueOf(cpt.getRate()));
        }
        return sb.toString();
    }

    public List<CPTGroup> getCPTGroups(CPTGroup search) {
        Connection connection = null;
        List<CPTGroup> groups = new LinkedList<>();
        try {
            connection = JDBCConnection.getInstance().getConnection();
            StringBuilder sql = new StringBuilder("SELECT ID, TYPE, DESCRIPTION, ADDITIONAL_INFO FROM CPT_GROUP");
            Map<Integer, Object> parameters = new HashMap<>();
            String condition = buildSelectConditionCPTGroup(search, parameters);
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
                CPTGroup group = getCPTGroup(rs);
                CPT cptSearch = new CPT();
                cptSearch.setGroupId(group.getId());
                List<CPT> CPTs = getCPT(cptSearch, connection);
                group.setCpts(CPTs);
                groups.add(group);
            }
            return groups;
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

    private String buildSelectConditionCPTGroup(CPTGroup cptGroup, Map<Integer, Object> parameters) {
        if (cptGroup == null) return null;
        StringBuilder sb = new StringBuilder();
        int parametersCount = 1;
        if (cptGroup.getId() != null) {
            sb.append(" ID = ?");
            parameters.put(parametersCount++, cptGroup.getId());
        }
        if (cptGroup.getType() != null) {
            if (parametersCount == 2) sb.append(" AND");
            sb.append(" TYPE = ?");
            parameters.put(parametersCount++, cptGroup.getType());
        }
        if (cptGroup.getDescription() != null) {
            if (parametersCount == 3) sb.append(" AND");
            sb.append(" DESCRIPTION = ?");
            parameters.put(parametersCount++, cptGroup.getDescription());
        }
        return sb.toString();
    }

    private CPTGroup getCPTGroup(ResultSet rs) throws SQLException {
        CPTGroup group = new CPTGroup();
        group.setId(rs.getString(1));
        group.setType(rs.getString(2));
        group.setDescription(rs.getString(3));
        group.setAdditionalInfo(rs.getString(4));
        return group;
    }

    private String buildSelectConditionCPT(CPT cpt, Map<Integer, Object> parameters) {
        if (cpt == null) return null;
        StringBuilder sb = new StringBuilder();
        int parametersCount = 1;
        if (cpt.getId() != null) {
            sb.append(" CPT.ID = ?");
            parameters.put(parametersCount++, cpt.getId());
        }
        if (cpt.getCode() != null) {
            if (parametersCount == 2) sb.append(" AND");
            sb.append(" CPT.CODE = ?");
            parameters.put(parametersCount++, cpt.getCode());
        }
        if (cpt.getDescription() != null) {
            if (parametersCount == 3) sb.append(" AND");
            sb.append(" CPT.DESCRIPTION = ?");
            parameters.put(parametersCount++, cpt.getDescription());
        }
        if (cpt.getGroupId() != null) {
            if (parametersCount == 4) sb.append(" AND");
            sb.append(" CPT.GROUP_ID = ?");
            parameters.put(parametersCount++, cpt.getGroupId());
        }
        return sb.toString();
    }

    public String addClaim(Claim claim) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            List<CPT> CPTs = getCpts(claim.getCPTs(), connection);
            List<ICD10> icd10s = getIcd10s(claim.getIcd10s());
            int amount = calculateAmount(CPTs);
            String id = UUID.randomUUID().toString();
            String sql = "INSERT INTO CLAIM (ID, PATIENT_ID, STATUS, AMOUNT, ADDITIONAL_INFO, TIME_CREATED) VALUES (?, ?, ?, ?, ?, NOW())";
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, id);
            ps.setString(index++, claim.getPatientId());
            ps.setInt(index++, Status.INITIATED.getStatus());
            ps.setInt(index++, amount);
            ps.setString(index++, claim.getAdditionalInfo());
            ps.executeUpdate();
            addClaimCPT(CPTs, id, connection);
            addClaimICD(icd10s, id, connection);
            connection.commit();
            return id;
        } catch (ConnectionException e) {
            e.printStackTrace();
            rollback(connection);
            throw new RuntimeException("Unexpected exception", e);
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception", e);
        } finally {
            close(connection);
        }
    }

    private List<ICD10> getIcd10s(List<ICD10> _icd10s) {
        List<ICD10> icd10s = new LinkedList<>();
        for (ICD10 icd10 : _icd10s) {
            icd10s.add(getICD10(icd10.getCode()));
        }
        return icd10s;
    }

    private ICD10 getICD10(String _code) {
        String code = _code;
        if (code.contains(".")) {
            code = code.substring(0, code.indexOf("."));
        }
        if (!icd10Map.containsKey(_code)) {
            ICD10 dbICD10 = JSONCodeManager.getInstance().getSearchICD10s(code).get(0);
            Queue<ICD10> queue = new LinkedList<>();
            queue.offer(dbICD10);
            while (!queue.isEmpty()) {
                ICD10 _icd10 = queue.poll();
                if (_icd10.getSubCodes() != null) {
                    for (ICD10 subCode : _icd10.getSubCodes()) {
                        icd10Map.put(subCode.getCode(), subCode);
                        queue.offer(subCode);
                    }
                }
            }
        }
        return icd10Map.get(_code);
    }

    private List<CPT> getCpts(List<CPT> cpts, Connection connection) throws SQLException {
        List<CPT> CPTs = new LinkedList<>();
        for (CPT cpt : cpts) {
            CPT search = new CPT();
            search.setCode(cpt.getCode());
            CPT dbCPT = getCPT(search, connection).get(0);
            CPTs.add(dbCPT);
        }
        return CPTs;
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Claim> getClaim(Claim search) {
        Connection conn = null;
        try {
            conn = JDBCConnection.getInstance().getConnection();
            return getClaim(search, conn);
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } finally {
            close(conn);
        }
    }

    public List<Claim> getClaim(Claim search, Connection conn) {
        StringBuilder sql = new StringBuilder("SELECT ID, ADDITIONAL_INFO, AMOUNT, PATIENT_ID, STATUS, TIME_CREATED, TIME_UPDATED from CLAIM ");
        List<Claim> claims = new LinkedList<>();
        try {
            Map<Integer, Object> parameters = new HashMap<>();
            String condition = buildSelectConditionClaim(search, parameters);
            String append = " WHERE ";
            if (search.getStatus() != Status.DELETED) {
                sql.append(" WHERE STATUS <> 7");
                append = " AND ";
            }
            if (condition != null && !condition.equals("")) {
                sql.append(append);
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
                Claim claim = getClaim(rs);
                updateCPTs(claim, conn);
                updateICDs(claim, conn);
                claims.add(claim);
            }
            return claims;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private Claim getClaim(ResultSet rs) throws SQLException {
        Claim claim = new Claim();
        int index = 1;
        claim.setId(rs.getString(index++));
        claim.setAdditionalInfo(rs.getString(index++));
        claim.setAmount(rs.getInt(index++));
        claim.setPatientId(rs.getString(index++));
        claim.setStatus(Status.getStatus(rs.getInt(index++)));
        claim.setTimeCreated(rs.getTimestamp(index++));
        claim.setTimeUpdated(rs.getTimestamp(index++));
        return claim;
    }

    private String buildSelectConditionClaim(Claim claim, Map<Integer, Object> parameters) {
        if (claim == null) return null;
        StringBuilder sb = new StringBuilder();
        int parametersCount = 1;
        if (claim.getId() != null) {
            sb.append(" ID = ?");
            parameters.put(parametersCount++, claim.getId());
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

    public void addTransaction(Transaction transaction) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            addTransaction(transaction, connection);
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("UnexpectedError", e);
        } finally {
            close(connection);
        }
    }

    public void addTransaction(Transaction transaction, Connection connection) {
        try {
            String sql = "INSERT INTO TRANSACTION (CLAIM_ID, AMOUNT, COMMENTS, PAYER_ID, TIME_CREATED, TRANSACTION_TYPE)" +
                    " VALUES (?, ?, ?, ?, NOW(), ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, transaction.getClaimId());
            ps.setInt(index++, transaction.getAmount());
            ps.setString(index++, transaction.getComments());
            ps.setString(index++, transaction.getPayerId());
            ps.setInt(index++, transaction.getTransactionType().getTransactionType());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("UnexpectedError", e);
        }
    }

    public void updateClaim(Claim claim) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            updateClaim(claim, connection);
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } finally {
            close(connection);
        }
    }

    public void updateClaim(Claim claim, Connection connection) {
        StringBuilder sql = new StringBuilder("UPDATE CLAIM SET ");
        Map<Integer, Object> parameters = new HashMap<>();
        String condition = buildUpdateConditionClaim(claim, parameters);
        if (condition != null) {
            sql.append(condition);
            sql.append(" WHERE ID = ?");
            try {
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
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Unexpected error", e);
            }
        }
    }

    public void deleteClaim(String claimId) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement("DELETE FROM CLAIM_ICD WHERE CLAIM_ID = ?");
            ps.setString(1, claimId);
            ps.executeUpdate();
            ps = connection.prepareStatement("DELETE FROM CLAIM_CPT WHERE CLAIM_ID = ?");
            ps.setString(1, claimId);
            ps.executeUpdate();
            ps = connection.prepareStatement("DELETE FROM CLAIM WHERE ID = ?");
            ps.setString(1, claimId);
            ps.executeUpdate();
            connection.commit();
        } catch (ConnectionException e) {
            e.printStackTrace();
            rollback(connection);
            throw new RuntimeException("Unexpected error", e);
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
            throw new RuntimeException("Unexpected error", e);
        } finally {
            close(connection);
        }
    }

    public Map<String, Object> getConfigurationVariables() {
        Map<String, Object> configurations = new HashMap<>();
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            String sql = "SELECT DATA_TYPE, VARIABLE_KEY, VARIABLE_VALUE FROM CONFIGURATION_VARIABLES WHERE TENANT_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tenantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String dataType = rs.getString(1);
                String variableKey = rs.getString(2);
                String variableValue = rs.getString(3);
                switch (dataType) {
                    case "BOOLEAN":
                        configurations.put(variableKey, Boolean.valueOf(variableValue));
                        break;
                    case "INTEGER":
                        configurations.put(variableKey, Integer.valueOf(variableValue));
                        break;
                    default:
                        configurations.put(variableKey, variableValue);
                }
            }
            return configurations;
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

    public List<Transaction> getTransactions(String claimId) {
        Connection connection = null;
        List<Transaction> transactions = new LinkedList<>();
        try {
            connection = JDBCConnection.getInstance().getConnection();
            String sql = "SELECT ID, CLAIM_ID, AMOUNT, COMMENTS, PAYER_ID, TIME_CREATED, TRANSACTION_TYPE FROM TRANSACTION WHERE CLAIM_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, claimId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = getTransactions(rs);
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
        transaction.setComments(rs.getString(index++));
        transaction.setPayerId(rs.getString(index++));
        transaction.setTimeCreated(rs.getTimestamp(index++));
        transaction.setTransactionType(TransactionType.getTransactionType(rs.getInt(index++)));
        return transaction;
    }

    private String buildUpdateConditionClaim(Claim claim, Map<Integer, Object> parameter) {
        StringBuilder sb = new StringBuilder();
        int parameterIndex = 1;
        if (claim.getStatus() != null) {
            sb.append(" STATUS = ? ");
            parameter.put(parameterIndex++, Integer.valueOf(claim.getStatus().getStatus()));
        }
        if (claim.getAmount() != -1) {
            if (parameterIndex > 1) sb.append(", ");
            sb.append(" AMOUNT = ? ");
            parameter.put(parameterIndex++, Integer.valueOf(claim.getAmount()));
        }
        if (parameterIndex > 1) sb.append(", ");
        sb.append("TIME_UPDATED = NOW()");
        return sb.toString();
    }

    private void updateCPTs(Claim claim, Connection connection) throws SQLException {
        String sql = "SELECT CPT_ID FROM CLAIM_CPT WHERE CLAIM_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, claim.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CPT search = new CPT();
            search.setId(rs.getString(1));
            CPT cpt = getCPT(search).get(0);
            if (claim.getCPTs() == null) claim.setCPTs(new LinkedList<>());
            claim.getCPTs().add(cpt);
        }
    }

    private void updateICDs(Claim claim, Connection connection) throws SQLException {
        String sql = "SELECT ICD_CODE FROM CLAIM_ICD WHERE CLAIM_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, claim.getId());
        ResultSet rs = ps.executeQuery();
        Map<String, ICD10> codes = new HashMap<>();
        while (rs.next()) {
            String code = rs.getString(1);
            if (icd10Map.containsKey(code)) {
                ICD10 icd10 = icd10Map.get(code);
                if (claim.getIcd10s() == null) claim.setIcd10s(new LinkedList<>());
                claim.getIcd10s().add(icd10);
            } else {
                ICD10 icd10 = getICD10(code);
                if (claim.getIcd10s() == null) claim.setIcd10s(new LinkedList<>());
                claim.getIcd10s().add(icd10);
                icd10Map.put(code, icd10);
            }
        }
    }

    void addClaimICD(List<ICD10> icd10s, String claimID, Connection connection) throws SQLException {
        String sql = "INSERT INTO CLAIM_ICD (CLAIM_ID, ICD_CODE) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        for (ICD10 icd10 : icd10s) {
            ps = connection.prepareStatement(sql);
            ps.setString(1, claimID);
            ps.setString(2, icd10.getCode());
            ps.executeUpdate();
        }
    }

    void addClaimCPT(List<CPT> CPTs, String claimID, Connection connection) throws SQLException {
        String sql = "INSERT INTO CLAIM_CPT (CLAIM_ID, CPT_ID) VALUES (?, ?)";
        PreparedStatement ps = null;
        for (CPT cpt : CPTs) {
            ps = connection.prepareStatement(sql);
            ps.setString(1, claimID);
            if (cpt.getId() != null)
                ps.setString(2, cpt.getId());
            else {
                CPT search = new CPT();
                search.setCode(cpt.getCode());
                CPT dbCPT = getCPT(search).get(0);
                ps.setString(2, dbCPT.getId());
            }
            ps.executeUpdate();
        }
    }

    private int calculateAmount(List<CPT> CPTs) {
        int amount = 0;
        for (CPT cpt : CPTs) {
            amount += cpt.getRate();
        }
        return amount;
    }

    public List<CPT> getCPT(CPT search) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            return getCPT(search, connection);
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

    public List<CPT> getCPT(CPT search, Connection connection) throws SQLException {
        List<CPT> CPTs = new LinkedList<>();
        StringBuilder sql = new StringBuilder("SELECT CPT.ID, CPT.CODE, CPT.DESCRIPTION, CPT.GROUP_ID, CPT_RATE.RATE FROM CPT, CPT_RATE where CPT.ID = CPT_RATE.CPT_ID");
        Map<Integer, Object> parameters = new HashMap<>();
        String condition = buildSelectConditionCPT(search, parameters);
        if (condition != null) {
            sql.append(" AND ");
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
            CPT cpt = getCPT(rs);
            CPTs.add(cpt);
        }
        return CPTs;
    }

    private CPT getCPT(ResultSet rs) throws SQLException {
        CPT cpt = new CPT();
        cpt.setId(rs.getString(1));
        cpt.setCode(rs.getString(2));
        cpt.setDescription(rs.getString(3));
        cpt.setGroupId(rs.getString(4));
        cpt.setRate(rs.getInt(5));
        return cpt;
    }

    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
