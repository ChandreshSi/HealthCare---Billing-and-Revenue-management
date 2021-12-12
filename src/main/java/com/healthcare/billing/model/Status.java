package com.healthcare.billing.model;

public enum Status {

    INITIATED(1), SENT_FOR_ADJUDICATION(2), PARTIALLY_PROCESSED(3), ACCEPTED(4), REJECTED(5), DENIED(6), DELETED(7), PROCESSED(10);

    private int status;

    Status(int status) {
        this.status = status;
    }

    public static Status getStatus(int status) {
        switch (status) {
            case 1:
                return INITIATED;
            case 2:
                return SENT_FOR_ADJUDICATION;
            case 3:
                return PARTIALLY_PROCESSED;
            case 4:
                return ACCEPTED;
            case 5:
                return REJECTED;
            case 6:
                return DENIED;
            case 10:
                return PROCESSED;
            default:
                return DELETED;
        }
    }

    public int getStatus() {
        return this.status;
    }
}
