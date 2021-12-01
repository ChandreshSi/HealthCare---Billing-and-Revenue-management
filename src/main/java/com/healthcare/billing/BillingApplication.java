package com.healthcare.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BillingApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BillingApplication.class, args);
    }

}
