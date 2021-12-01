package com.healthcare.billing.repository.model;

import java.util.Map;

public class ICD10Code {
    private String code;
    private String information;
    private Map<String, ICD10Code> subCode;

    public ICD10Code() {

    }

    public ICD10Code(String code, String information) {
        this.code = code;
        this.information = information;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Map<String, ICD10Code> getSubCode() {
        return subCode;
    }

    public void setSubCode(Map<String, ICD10Code> subCode) {
        this.subCode = subCode;
    }

    @Override
    public String toString() {
        return "ICD10Code{" +
                "code='" + code + '\'' +
                ", information='" + information + '\'' +
                ", subCode=" + subCode +
                '}';
    }
}
