package com.healthcare.billing.repository;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.ICD10;

import java.util.List;
import java.util.Map;

public interface BillingRepository {

    public List<ICD10> getBaseSearchICDCodes();

    public List<ICD10> getICDCodes(String search);

    public Map<String, List<CPT>> getCPTCodes();

    public List<CPT> getCPTCodes(String search);

}
