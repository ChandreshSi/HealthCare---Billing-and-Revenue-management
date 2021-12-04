package com.healthcare.billing.model;

public class CPTCodeRate {
    private String id;
    private CPT cptCode;
    private int rate;
    private Currency currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CPT getCptCode() {
        return cptCode;
    }

    public void setCptCode(CPT cptCode) {
        this.cptCode = cptCode;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isEmpty() {
        return this.id == null && this.cptCode == null && this.currency == null && this.rate == 0;
    }

    @Override
    public String toString() {
        return "CPTCodeRate{" +
                "id=" + id +
                ", cptCode='" + cptCode + '\'' +
                ", rate=" + rate +
                ", currency=" + currency +
                '}';
    }
}
