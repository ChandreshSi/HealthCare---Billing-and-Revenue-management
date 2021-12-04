package com.healthcare.billing.model;

import java.util.List;

public class CPTGroup {
    private String id;
    private String type;
    private String description;
    private String additionalInfo;
    private List<CPT> codes;
    private List<CPTCodeRate> cptCodeRates;

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

    public List<CPT> getCodes() {
        return codes;
    }

    public void setCodes(List<CPT> codes) {
        this.codes = codes;
    }

    public List<CPTCodeRate> getCptCodeRates() {
        return cptCodeRates;
    }

    public void setCptCodeRates(List<CPTCodeRate> cptCodeRates) {
        this.cptCodeRates = cptCodeRates;
    }
}
