package com.healthcare.billing.service;

import com.healthcare.billing.model.*;

import java.util.List;
import java.util.Map;

public interface BillingService {

    public List<ICD10> getBaseSearchICD10s();

    public List<ICD10> getSearchICD10s(String search);

    public ICD10 getICD10s(String id);

    public List<CPTGroup> getCPTGroups(CPTGroup search);

    public List<CPT> getCPTs(CPT search);

    public void patchCPT(String cptId, CPT cpt);

    public String createClaim(Claim claim);

    public void processClaim(String claimId);

    public Statement getStatement(String claimId);

    public void settleClaim(String claimId);

    public List<Claim> getClaim(Claim search);

    public void updateClaim(Claim claim);

    public void deleteClaim(String claimId);

    public void addTransaction(Transaction transaction);

    public List<Transaction> getTransaction(String claimId);

    public Map<String, Object> getConfigurations();

}
