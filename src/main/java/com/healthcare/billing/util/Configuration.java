package com.healthcare.billing.util;

import com.healthcare.billing.repository.jdbc.JDBCRepositoryImpl;

import java.util.Map;

public class Configuration {

    private static Configuration configuration;
    private Map<String, Object> configurations;

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (configuration == null)
            configuration = new Configuration();
        return configuration;
    }

    public Map<String, Object> getConfigurations() {
        if (configurations == null)
            initialize();
        return this.configurations;
    }

    public Object getConfigurationValue(String key) {
        if (configurations == null)
            initialize();
        return this.configurations.get(key);
    }

    private void initialize() {
        JDBCRepositoryImpl impl = new JDBCRepositoryImpl();
        this.configurations = impl.getConfigurationVariables();
    }

}
