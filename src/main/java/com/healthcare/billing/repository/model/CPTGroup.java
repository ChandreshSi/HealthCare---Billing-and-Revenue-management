package com.healthcare.billing.repository.model;

public class CPTGroup {
    private int id;
    private String type;
    private String description;
    private String additionalInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public boolean isEmpty() {
        return this.id == 0 && this.additionalInfo == null && this.description == null && this.type == null;
    }
}
