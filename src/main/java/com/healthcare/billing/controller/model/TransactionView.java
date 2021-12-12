package com.healthcare.billing.controller.model;

import com.healthcare.billing.constants.BillingConstant;
import com.healthcare.billing.model.Transaction;
import com.healthcare.billing.model.TransactionType;
import com.healthcare.billing.util.Configuration;

import java.util.Date;

public class TransactionView {

    private String id;
    private String claimId;
    private int amount;
    private String comments;
    private String payerId;
    private Date timeCreated;
    private TransactionType transactionType;

    public TransactionView() {

    }

    public TransactionView(Transaction transaction) {
        this.id = transaction.getId();
        this.claimId = transaction.getClaimId();
        this.amount = transaction.getAmount();
        this.comments = transaction.getComments();
        this.payerId = transaction.getPayerId();
        this.timeCreated = transaction.getTimeCreated();
        this.transactionType = transaction.getTransactionType();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
