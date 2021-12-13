package com.healthcare.billing.controller.model;

import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.CPTGroup;

import java.util.LinkedList;
import java.util.List;

public class CPTGroupView {
    private String id;
    private String type;
    private String description;
    private String additionalInfo;
    private List<CPTView> cpts;

    public CPTGroupView() {

    }

    public CPTGroupView(CPTGroup group) {
        this.id = group.getId();
        this.type = group.getType();
        this.description = group.getDescription();
        this.additionalInfo = group.getAdditionalInfo();
        if (group.getCpts() != null) {
            this.cpts = new LinkedList<>();
            for (CPT cpt : group.getCpts()) {
                this.cpts.add(new CPTView(cpt));
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<CPTView> getCpts() {
        return cpts;
    }

    public void setCpts(List<CPTView> cpts) {
        this.cpts = cpts;
    }
}
