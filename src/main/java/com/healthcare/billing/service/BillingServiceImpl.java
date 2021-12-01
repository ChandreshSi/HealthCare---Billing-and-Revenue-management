package com.healthcare.billing.service;

import com.healthcare.billing.repository.BillingRepository;
import com.healthcare.billing.repository.BillingRepositoryImpl;
import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.ICD10;

import java.util.*;

public class BillingServiceImpl implements BillingService {

    private BillingRepository repository = new BillingRepositoryImpl();

    @Override
    public List<ICD10> getBaseSearchICDCodes() {
        return repository.getBaseSearchICDCodes();
    }

    @Override
    public List<ICD10> getICDCodes(String search) {
        return repository.getICDCodes(search);
    }

    @Override
    public Map<String, List<CPT>> getCPTCodes() {
        return repository.getCPTCodes();
    }

    @Override
    public List<CPT> getCPTCodes(String search) {
        return repository.getCPTCodes(search);
    }
}
