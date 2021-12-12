package com.healthcare.billing.controller.model;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.Claim;
import com.healthcare.billing.model.ICD10;
import com.healthcare.billing.model.Status;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ClaimView {

    private String id;
    private List<ICD10> icd10s;
    private List<CPTView> CPTs;
    private String patientId;
    private String additionalInfo;
    private Status status;
    private int amount;
    private Date timeCreated;
    private Date timeUpdated;

    public ClaimView() {

    }

    public ClaimView(Claim claim) {
        this.id = claim.getId();
        this.icd10s = claim.getIcd10s();
        this.CPTs = new LinkedList<>();
        for (CPT cpt : claim.getCPTs()) {
            this.CPTs.add(new CPTView(cpt));
        }
        this.patientId = claim.getPatientId();
        this.additionalInfo = claim.getAdditionalInfo();
        this.status = claim.getStatus();
        this.amount = claim.getAmount();
        this.timeCreated = claim.getTimeCreated();
        this.timeUpdated = claim.getTimeUpdated();
    }

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

    public List<CPTView> getCPTs() {
        return CPTs;
    }

    public void setCPTs(List<CPTView> CPTs) {
        this.CPTs = CPTs;
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
}
