package com.healthcare.billing.controller.model;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.ICD10;

import java.util.List;

public class CreateClaimRequest {

    private List<String> icd10Codes;
    private List<String> cpts;
    private String patientId;
    private String additionalInfo;

    public List<String> getIcd10Codes() {
        return icd10Codes;
    }

    public void setIcd10Codes(List<String> icd10Codes) {
        this.icd10Codes = icd10Codes;
    }

    public List<String> getCpts() {
        return cpts;
    }

    public void setCpts(List<String> cpts) {
        this.cpts = cpts;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "CreateClaimRequest{" +
                "icd10Codes=" + icd10Codes +
                ", cpts=" + cpts +
                ", patientId='" + patientId + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
