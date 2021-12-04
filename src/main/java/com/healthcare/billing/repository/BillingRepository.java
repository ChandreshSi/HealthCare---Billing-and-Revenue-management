package com.healthcare.billing.repository;

import com.healthcare.billing.model.*;

import java.util.List;
import java.util.Map;

public interface BillingRepository {

    public List<ICD10> getBaseSearchICDCodes();

    public List<ICD10> getICDCodes(String search);

    public List<CPTGroup> getCPTCodes();

    public List<CPT> getCPTCodes(String groupId);

    public List<CPTCodeRate> getCPTCodeRates();

    public CPTCodeRate getCPTCodeRate(String code);

    public String createClaim(Claim claim);

    public Claim getClaim(String id);

    public void addTransaction(Transaction transaction);

    public void updateClaim(Claim claim);

    public List<Transaction> getTransactions(Claim claim);

    public List<Claim> getClaim(Claim claim);

    public Currency getCurrency(String identifier);

}
