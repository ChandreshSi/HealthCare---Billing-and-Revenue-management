package com.healthcare.billing.model;

import java.util.List;

public class CPTGroup {
    private String id;
    private String type;
    private String description;
    private String additionalInfo;
    private List<CPT> cpts;

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

    public List<CPT> getCpts() {
        return cpts;
    }

    public void setCpts(List<CPT> cpts) {
        this.cpts = cpts;
    }

    @Override
    public String toString() {
        return "CPTGroup{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", cpts=" + cpts +
                '}';
    }
}
