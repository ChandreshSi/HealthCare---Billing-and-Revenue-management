package com.healthcare.billing.controller.model;

import com.healthcare.billing.model.*;

import java.util.*;

public class StatementView {

    private String patientName;
    private String patientSSN;
    private String dob;
    private String address;
    private List<String> encounterDates;
    private List<Map<String, String>> insuranceDetails;
    // claim
    private List<Map<String, String>> icd10s;
    private List<Map<String, Object>> CPTs;
    private String additionalInfo;
    private String status;
    private int initialAmountOwed;

    private List<Transaction> transactions;

    private int balance;
    private String comments;

    public StatementView() {

    }

    public StatementView(Statement statement) {
        Claim claim = statement.getClaim();
        this.icd10s = new LinkedList<>();
        this.CPTs = new LinkedList<>();
        if (claim.getIcd10s() != null) {
            for (ICD10 icd10 : claim.getIcd10s()) {
                Map<String, String> map = new HashMap<>();
                map.put(icd10.getCode(), icd10.getDescription());
                this.icd10s.add(map);
            }
        }
        if (claim.getCPTs() != null) {
            for (CPT cpt : claim.getCPTs()) {
                Map<String, Object> map = new HashMap<>();
                map.put(cpt.getCode(), cpt.getDescription());
                map.put("rate", cpt.getRate());
                this.CPTs.add(map);
            }
        }
        this.additionalInfo = claim.getAdditionalInfo();
        this.status = claim.getStatus().toString();
        this.initialAmountOwed = claim.getAmount();
        this.transactions = statement.getTransactions();
        this.balance = statement.getBalance();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSSN() {
        return patientSSN;
    }

    public void setPatientSSN(String patientSSN) {
        this.patientSSN = patientSSN;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getEncounterDates() {
        return encounterDates;
    }

    public void setEncounterDates(List<String> encounterDates) {
        this.encounterDates = encounterDates;
    }

    public List<Map<String, String>> getInsuranceDetails() {
        return insuranceDetails;
    }

    public void setInsuranceDetails(List<Map<String, String>> insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public List<Map<String, String>> getIcd10s() {
        return icd10s;
    }

    public void setIcd10s(List<Map<String, String>> icd10s) {
        this.icd10s = icd10s;
    }

    public List<Map<String, Object>> getCPTs() {
        return CPTs;
    }

    public void setCPTs(List<Map<String, Object>> CPTs) {
        this.CPTs = CPTs;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInitialAmountOwed() {
        return initialAmountOwed;
    }

    public void setInitialAmountOwed(int initialAmountOwed) {
        this.initialAmountOwed = initialAmountOwed;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
