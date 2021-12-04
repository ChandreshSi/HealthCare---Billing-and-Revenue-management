package com.healthcare.billing.controller.model;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.ICD10;

import java.util.List;

public class CreateClaimRequest {
    private List<ICD10> icd10Codes;
    private List<CPT> cptCodes;
    private String patientId;

    public List<ICD10> getIcd10Codes() {
        return icd10Codes;
    }

    public void setIcd10Codes(List<ICD10> icd10Codes) {
        this.icd10Codes = icd10Codes;
    }

    public List<CPT> getCptCodes() {
        return cptCodes;
    }

    public void setCptCodes(List<CPT> cptCodes) {
        this.cptCodes = cptCodes;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "CreateClaimRequest{" +
                "icd10Codes=" + icd10Codes +
                ", cptCodes=" + cptCodes +
                ", patientId='" + patientId + '\'' +
                '}';
    }
}
