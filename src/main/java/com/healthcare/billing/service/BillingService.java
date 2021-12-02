package com.healthcare.billing.service;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.CPTGroup;
import com.healthcare.billing.model.ICD10;

import java.util.List;
import java.util.Map;

public interface BillingService {

    public List<ICD10> getBaseSearchICDCodes();

    public List<ICD10> getICDCodes(String search);

    public List<CPTGroup> getCPTCodes();

    public List<CPT> getCPTCodes(String groupId);

}
