package com.healthcare.billing.model;

import java.util.List;

public class ICD10 {

    private String id;
    private String code;
    private String description;
    private String parentId;
    private List<ICD10> subCodes;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<ICD10> getSubCodes() {
        return subCodes;
    }

    public void setSubCodes(List<ICD10> subCodes) {
        this.subCodes = subCodes;
    }

    @Override
    public String toString() {
        return "ICD10{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", parentId='" + parentId + '\'' +
                ", subCodes=" + subCodes +
                '}';
    }
}
