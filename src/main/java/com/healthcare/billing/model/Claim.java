package com.healthcare.billing.model;

import java.util.Date;
import java.util.List;

public class Claim {

    private String id;
    private List<ICD10> icd10s;
    private List<CPT> CPTs;
    private String patientId;
    private String additionalInfo;
    private Status status;
    private int amount = -1;
    private Date timeCreated;
    private Date timeUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ICD10> getIcd10s() {
        return icd10s;
    }

    public void setIcd10s(List<ICD10> icd10s) {
        this.icd10s = icd10s;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public List<CPT> getCPTs() {
        return CPTs;
    }

    public void setCPTs(List<CPT> CPTs) {
        this.CPTs = CPTs;
    }
}
