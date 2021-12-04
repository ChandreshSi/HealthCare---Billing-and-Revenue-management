package com.healthcare.billing.model;

import java.util.Date;
import java.util.List;

public class Claim {
    private String id;
    private List<CPTCodeRate> cptCodes;
    private List<ICD10> icd10Codes;
    private String patientId;
    private String additionalInfo;
    private Status status;
    private int totalAmount;
    private Currency currency;
    private Date timeCreated;
    private Date timeUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CPTCodeRate> getCptCodes() {
        return cptCodes;
    }

    public void setCptCodes(List<CPTCodeRate> cptCodes) {
        this.cptCodes = cptCodes;
    }

    public List<ICD10> getIcd10Codes() {
        return icd10Codes;
    }

    public void setIcd10Codes(List<ICD10> icd10Codes) {
        this.icd10Codes = icd10Codes;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(Date timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id=" + id +
                ", cptCodes=" + cptCodes +
                ", icd10Codes=" + icd10Codes +
                ", patientId='" + patientId + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", status=" + status +
                ", amount=" + totalAmount +
                ", currency=" + currency +
                ", timeCreated=" + timeCreated +
                ", timeUpdated=" + timeUpdated +
                '}';
    }
}
