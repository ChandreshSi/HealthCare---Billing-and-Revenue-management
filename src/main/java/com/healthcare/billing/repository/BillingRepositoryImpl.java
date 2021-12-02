package com.healthcare.billing.repository;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.CPTGroup;
import com.healthcare.billing.model.ICD10;
import com.healthcare.billing.repository.json.JSONCodeManager;

import java.util.List;

public class BillingRepositoryImpl implements BillingRepository {

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
}
