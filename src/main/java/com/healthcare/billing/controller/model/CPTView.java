package com.healthcare.billing.controller.model;

import com.healthcare.billing.model.CPT;

public class CPTView {

    private String id;
    private String code;
    private String description;
    private String groupId;
    private int rate;

    public CPTView() {

    }

    public CPTView(CPT cpt) {
        this.id = cpt.getId();
        this.code = cpt.getCode();
        this.description = cpt.getDescription();
        this.groupId = cpt.getGroupId();
        this.rate = cpt.getRate();
    }

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
