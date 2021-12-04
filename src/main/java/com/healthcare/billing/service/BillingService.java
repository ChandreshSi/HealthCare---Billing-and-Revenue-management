package com.healthcare.billing.service;

import com.healthcare.billing.model.*;

import java.util.List;
import java.util.Map;

public interface BillingService {

    public List<ICD10> getBaseSearchICDCodes();

    public List<ICD10> getICDCodes(String search);

    public List<CPTGroup> getCPTCodes();

    public List<CPT> getCPTCodes(String groupId);

    public List<CPTCodeRate> getCPTCodeRates();

    public CPTCodeRate getCPTCodeRate(String code);

    public List<CPTGroup> getCptGroupCodesWithRate();

    public String createClaim(Claim claim);

    public Claim getClaim(String id);

    public void processClaim(String claimId);

    public Statement getStatement(String claimId);

    public void settleClaim(String claimId);

}
