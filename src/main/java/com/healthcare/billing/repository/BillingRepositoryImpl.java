package com.healthcare.billing.repository;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.CPTCodeRate;
import com.healthcare.billing.model.CPTGroup;
import com.healthcare.billing.model.ICD10;
import com.healthcare.billing.repository.jdbc.JDBCRepositoryImpl;
import com.healthcare.billing.repository.json.JSONCodeManager;

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
}
