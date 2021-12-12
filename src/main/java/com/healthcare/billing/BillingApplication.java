package com.healthcare.billing;

import com.healthcare.billing.repository.jdbc.JDBCRepositoryImpl;
import com.healthcare.billing.repository.json.JSONCodeManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }

}
