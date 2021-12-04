package com.healthcare.billing.model;

public enum Status {

    INITIATED(1), SENT_FOR_ADJUDICATION(2), ACCEPTED(3), REJECTED(4), DENIED(5), DELETED(6), PROCESSED(7);

    private int status;

    private Status(int status) {
        this.status = status;
    }

    public static Status getStatus(int status) {
        switch (status) {
            case 1:
                return INITIATED;
            case 2:
                return SENT_FOR_ADJUDICATION;
            case 3:
                return ACCEPTED;
            case 4:
                return REJECTED;
            case 5:
                return DENIED;
            case 7:
                return PROCESSED;
            default:
                return DELETED;
        }
    }

    public int getStatus() {
        return this.status;
    }
}
