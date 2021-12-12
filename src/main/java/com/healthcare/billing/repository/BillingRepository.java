package com.healthcare.billing.repository;

import com.healthcare.billing.model.*;

import java.util.List;
import java.util.Map;

public interface BillingRepository {

    public List<ICD10> getBaseSearchICD10s();

    public List<ICD10> getSearchICD10s(String search);

    public ICD10 getICD10s(String search);

    public List<CPTGroup> getCPTGroups(CPTGroup search);

    public List<CPT> getCPTs(CPT search);

    public void patchCPT(String cptId, CPT cpt);

    public String createClaim(Claim search);

    public List<Claim> getClaim(Claim search);

    public void addTransaction(Transaction transaction);

    public void updateClaim(Claim claim);

    public void deleteClaim(String claimId);
//
//    public void updateClaim(Claim claim);
//
    public List<Transaction> getTransactions(String claimId);
//
//    public List<Claim> getClaim(Claim claim);

    public Map<String, Object> getConfigurations();

}
