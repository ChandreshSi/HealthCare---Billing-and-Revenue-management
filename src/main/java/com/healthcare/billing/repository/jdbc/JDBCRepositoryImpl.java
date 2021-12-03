package com.healthcare.billing.repository.jdbc;

import com.healthcare.billing.exception.ConnectionException;
import com.healthcare.billing.model.*;
import com.healthcare.billing.model.Currency;
import com.healthcare.billing.repository.json.JSONCodeManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCRepositoryImpl {

    private Map<String, Currency> currencies;
    private JSONCodeManager manager;

    public JDBCRepositoryImpl() {
        manager = JSONCodeManager.getInstance();
        currencies = new HashMap<>();
        currencies.put("USD", new Currency("USD", (char) 36));
    }

    private Random rnd = new Random();

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
        if (code.getId() != 0) {
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

    private CPTCodeRate getCPTRateCode(ResultSet rs) throws SQLException {
        CPTCodeRate code = new CPTCodeRate();
        code.setId(rs.getInt(1));
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
