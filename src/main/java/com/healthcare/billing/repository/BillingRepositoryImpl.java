package com.healthcare.billing.repository;

import com.healthcare.billing.exception.ConnectionException;
import com.healthcare.billing.model.*;
import com.healthcare.billing.repository.jdbc.JDBCConnection;
import com.healthcare.billing.repository.jdbc.JDBCRepositoryImpl;
import com.healthcare.billing.repository.json.JSONCodeManager;

import java.sql.Connection;
import java.util.List;

public class BillingRepositoryImpl implements BillingRepository {

    JDBCRepositoryImpl repository = new JDBCRepositoryImpl();

    @Override
    public List<ICD10> getBaseSearchICDCodes() {
        return JSONCodeManager.getInstance().getBaseSearchICDCodes();
    }

    @Override
    public List<ICD10> getICDCodes(String search) {
        return JSONCodeManager.getInstance().getICDCodes(search);
    }

    @Override
    public List<CPTGroup> getCPTCodes() {
        return JSONCodeManager.getInstance().getCPTCodes();
    }

    @Override
    public List<CPT> getCPTCodes(String groupId) {
        return JSONCodeManager.getInstance().getCPTCodes(groupId);
    }

    @Override
    public List<CPTCodeRate> getCPTCodeRates() {
        return repository.getCPTRates();
    }

    @Override
    public CPTCodeRate getCPTCodeRate(String code) {
        List<CPTCodeRate> codes = repository.getCPTRates(code);
        return codes.get(0);
    }

    @Override
    public String createClaim(Claim claim) {
        return repository.createClaim(claim);
    }

    @Override
    public Claim getClaim(String id) {
        return repository.getClaim(id);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        repository.addTransaction(transaction);
    }

    @Override
    public void updateClaim(Claim claim) {
        repository.updateClaim(claim);
    }

    @Override
    public List<Transaction> getTransactions(Claim claim) {
        return repository.getTransactions(claim);
    }

    @Override
    public List<Claim> getClaim(Claim claim) {
        return repository.getClaim(claim);
    }

    @Override
    public Currency getCurrency(String identifier) {
        return repository.getCurrency(identifier);
    }

}
