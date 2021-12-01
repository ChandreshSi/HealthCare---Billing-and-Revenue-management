package com.healthcare.billing.model;

public class CPT {
    private String id;
    private String code;
    private String description;
    private String cptGroupId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCptGroupId() {
        return cptGroupId;
    }

    public void setCptGroupId(String cptGroupId) {
        this.cptGroupId = cptGroupId;
    }

    public boolean isEmpty() {
        return this.id == null && this.code == null && this.cptGroupId == null && this.description == null;
    }
}
