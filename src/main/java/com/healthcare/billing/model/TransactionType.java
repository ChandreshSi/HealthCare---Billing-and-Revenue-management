package com.healthcare.billing.model;

public enum TransactionType {

    CREDIT(1), DEBIT(2), UNKNOWN(10);

    private int type;

    TransactionType(int type) {
        this.type = type;
    }

    public int getTransactionType() {
        return this.type;
    }

    public static TransactionType getTransactionType(int type) {
        switch (type) {
            case 1:
                return CREDIT;
            case 2:
                return DEBIT;
            default:
                return UNKNOWN;
        }
    }

}
