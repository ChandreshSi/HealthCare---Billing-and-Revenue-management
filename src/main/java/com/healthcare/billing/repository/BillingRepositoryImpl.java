package com.healthcare.billing.repository;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.ICD10;
import com.healthcare.billing.repository.json.JSONCodeManagement;

import java.util.List;
import java.util.Map;

public class BillingRepositoryImpl implements BillingRepository {

    @Override
    public List<ICD10> getBaseSearchICDCodes() {
        return JSONCodeManagement.getInstance().getBaseSearchICDCodes();
    }

    @Override
    public List<ICD10> getICDCodes(String search) {
        return JSONCodeManagement.getInstance().getICDCodes(search);
    }

    @Override
    public Map<String, List<CPT>> getCPTCodes() {
        return JSONCodeManagement.getInstance().getCPTCodes();
    }

    @Override
    public List<CPT> getCPTCodes(String search) {
        return JSONCodeManagement.getInstance().getCPTCodes(search);
    }
}
