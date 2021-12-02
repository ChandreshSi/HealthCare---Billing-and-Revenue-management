package com.healthcare.billing.controller;

import com.google.gson.Gson;
import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.CPTGroup;
import com.healthcare.billing.model.ICD10;
import com.healthcare.billing.service.BillingService;
import com.healthcare.billing.service.BillingServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private BillingService billingService = new BillingServiceImpl();

    @RequestMapping("/procedures/{procedureId}/rates")
    public String getRate(@PathVariable String procedureId) {
        return "{\"message\" : \"This API is under construction.\"}";
    }

    @RequestMapping("/icd10codes")
    public String getICD10Codes() {
        List<ICD10> codes = billingService.getBaseSearchICDCodes();
        return new Gson().toJson(codes);
    }

    @RequestMapping("/icd10codes/{code}")
    public String getICD10CodesSearch(@PathVariable String code) {
        List<ICD10> codes = billingService.getICDCodes(code);
        return new Gson().toJson(codes);
    }

    @RequestMapping("/cptCodes")
    public String getCPTCodes() {
        List<CPTGroup> codes = billingService.getCPTCodes();
        return new Gson().toJson(codes);
    }

    @RequestMapping("/cptCodes/{groupId}")
    public String getCPTCodes(@PathVariable String groupId) {
        List<CPT> codes = billingService.getCPTCodes(groupId);
        return new Gson().toJson(codes);
    }

    @RequestMapping("/claims")
    public String getClaims() {
        return "{\"message\" : \"This API is under construction.\"}";
    }

    @RequestMapping("/claims/{claimId}")
    public String getClaim(@PathVariable String claimId) {
        return "{\"message\" : \"This API is under construction.\"}";
    }

    @RequestMapping(value = "/claims/{claimId}", method = RequestMethod.POST)
    public String createClaim(@PathVariable String claimId) {
        return "{\"message\" : \"This API is under construction.\"}";
    }

    @RequestMapping(value = "/claims/{claimId}", method = RequestMethod.PUT)
    public String updateClaim(@PathVariable String claimId) {
        return "{\"message\" : \"This API is under construction.\"}";
    }

    @RequestMapping(value = "/claims/{claimId}", method = RequestMethod.DELETE)
    public String deleteClaim(@PathVariable String claimId) {
        return "{\"message\" : \"This API is under construction.\"}";
    }


}
