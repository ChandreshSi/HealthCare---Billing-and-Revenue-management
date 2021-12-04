package com.healthcare.billing.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthcare.billing.controller.model.CreateClaimRequest;
import com.healthcare.billing.model.*;
import com.healthcare.billing.service.BillingService;
import com.healthcare.billing.service.BillingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private BillingService billingService = new BillingServiceImpl();

    @RequestMapping("/cptCodes/rates")
    public String getRates() {
        System.out.println("getRates starts");
        List<CPTCodeRate> codes = billingService.getCPTCodeRates();
        return new Gson().toJson(codes);
    }

    @RequestMapping("/cptCodes/rates/{cptCodesId}")
    public String getRate(@PathVariable String cptCodesId) {
        System.out.println("getRate starts");
        CPTCodeRate code = billingService.getCPTCodeRate(cptCodesId);
        return new Gson().toJson(code);
    }

    @RequestMapping("/icd10codes")
    public String getICD10Codes() {
        System.out.println("getICD10Codes starts");
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
//        List<CPTGroup> codes = billingService.getCPTCodes();
        List<CPTGroup> codes = billingService.getCptGroupCodesWithRate();
        return new Gson().toJson(codes);
    }

    @RequestMapping("/cptCodes/{groupId}")
    public String getCPTCodes(@PathVariable String groupId) {
        List<CPT> codes = billingService.getCPTCodes(groupId);
        return new Gson().toJson(codes);
    }

    @RequestMapping("/claims")
    public ResponseEntity<String> getClaims() {
        System.out.println("getClaims starts...");
        try {
            List<Claim> claims = billingService.getClaim(new Claim());
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(claims));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errorMessage\" : \" " + e.getCause().toString() + " \"}");
        }
    }

    @RequestMapping("/claims/{claimId}")
    public ResponseEntity<String> getClaim(@PathVariable String claimId) {
        System.out.println("getClaim starts...");
        try {
            Claim claim = billingService.getClaim(claimId);
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(claim));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errorMessage\" : \" " + e.getCause().toString() + " \"}");
        }
    }

    @RequestMapping(value = "/claims", method = RequestMethod.POST)
    public ResponseEntity<String> createClaim(@RequestBody String body) {
        System.out.println("createClaim starts...");
        Gson gson = new Gson();
        Claim claim = gson.fromJson(body, Claim.class);
        try {
            String id = billingService.createClaim(claim);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().toString());
        }
    }

    @RequestMapping(value = "/claims/{claimId}/actions/process", method = RequestMethod.POST)
    public ResponseEntity<String> processClaim(@PathVariable String claimId) {
        System.out.println("processClaim starts...");
        try {
            billingService.processClaim(claimId);
            return ResponseEntity.status(HttpStatus.OK).body("\"message:\": \"Success\"");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().toString());
        }
    }

    @RequestMapping(value = "/claims/{claimId}/actions/generateStatement", method = RequestMethod.POST)
    public ResponseEntity<String> getStatement(@PathVariable String claimId) {
        System.out.println("processClaim starts...");
        try {
            Statement statement = billingService.getStatement(claimId);
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(statement));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message\":\"" + e.getMessage() + "\"");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/actions/settle", method = RequestMethod.POST)
    public ResponseEntity<String> settleClaim(@PathVariable String claimId) {
        System.out.println("processClaim starts...");
        try {
            billingService.settleClaim(claimId);
            return ResponseEntity.status(HttpStatus.OK).body("\"message\": \"success\"");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().toString());
        }
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
