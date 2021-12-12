package com.healthcare.billing.model;

import java.util.List;
import java.util.Map;

public class Statement {
    // patient details
    // encounter details
    private Claim claim;
    private List<Transaction> transactions;
    private int balance;

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
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
}
