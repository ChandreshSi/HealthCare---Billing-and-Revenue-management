package com.healthcare.billing.repository.jdbc;

import com.healthcare.billing.exception.ConnectionException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    
    private static JDBCConnection jdbcConnection;

    private String url;
    private String username;
    private String password;

    private JDBCConnection() {
        Properties properties = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream("/application.properties");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            properties.load(reader);
            this.url = (String) properties.get("jdbc.url");
            this.username = (String) properties.get("jdbc.username");
            this.password = (String) properties.get("jdbc.password");
            System.out.println("Connection successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JDBCConnection getInstance() {
        if (jdbcConnection == null) {
            jdbcConnection = new JDBCConnection();
        }
        return jdbcConnection;
    }

    public Connection getConnection() throws ConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(this.url, this.username, this.password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ConnectionException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(e);
        }
    }
}
