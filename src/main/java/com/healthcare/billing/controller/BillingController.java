package com.healthcare.billing.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthcare.billing.controller.model.*;
import com.healthcare.billing.model.*;
import com.healthcare.billing.service.BillingService;
import com.healthcare.billing.service.BillingServiceImpl;
import com.healthcare.billing.util.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private BillingService billingService = new BillingServiceImpl();
    private final static String SUCCESS_MESSAGE = "{\"message:\": \"Success\"}";

    @RequestMapping("/icd10")
    public String getICD10Codes() {
        System.out.println("getICD10Codes starts");
        List<ICD10> codes = billingService.getBaseSearchICD10s();
        return new Gson().toJson(codes);
    }

    @RequestMapping("/icd10/{icd10Code}")
    public String getICD10Code(@PathVariable String icd10Code) {
        List<ICD10> codes = billingService.getSearchICD10s(icd10Code);
        if (codes == null) {
            codes = new LinkedList<>();
            codes.add(billingService.getICD10s(icd10Code));
        }
        return new Gson().toJson(codes);
    }

    @RequestMapping("/cptGroups")
    public String getCPTGroups() {
        List<CPTGroup> codes = billingService.getCPTGroups(null);
        List<CPTGroupView> view = new LinkedList<>();
        for (CPTGroup group : codes) {
            view.add(new CPTGroupView(group));
        }
        return new Gson().toJson(view);
    }

    @RequestMapping("/cptGroups/{groupId}")
    public String getCPTGroup(@PathVariable String groupId) {
        CPTGroup search = new CPTGroup();
        search.setId(groupId);
        List<CPTGroup> codes = billingService.getCPTGroups(search);
        List<CPTGroupView> view = new LinkedList<>();
        for (CPTGroup group : codes) {
            view.add(new CPTGroupView(group));
        }
        // TODO: check for single
        return new Gson().toJson(view);
    }

    @RequestMapping("/cpts")
    public String getCPTs() {
        List<CPT> codes = billingService.getCPTs(null);
        List<CPTView> view = new LinkedList<>();
        for (CPT cpt : codes) {
            view.add(new CPTView(cpt));
        }
        return new Gson().toJson(view);
    }

    @RequestMapping("/cpts/{cptId}")
    public String getCPT(@PathVariable String cptId) {
        CPT search = new CPT();
        search.setId(cptId);
        List<CPT> codes = billingService.getCPTs(search);
        List<CPTView> view = new LinkedList<>();
        for (CPT cpt : codes) {
            view.add(new CPTView(cpt));
        }
        // TODO: check for single
        return new Gson().toJson(view);
    }

    @RequestMapping(value = "/cpts/{cptId}", method = RequestMethod.PATCH)
    public ResponseEntity<String> patchCPT(@PathVariable String cptId, @RequestBody String body) {
        System.out.println("patchCPT starts...");
        try {
            Gson gson = new Gson();
            CPT cpt = gson.fromJson(body, CPT.class);
            billingService.patchCPT(cptId, cpt);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"success\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errorMessage\" : \" " + e.getCause().toString() + " \"}");
        }
    }

    @RequestMapping(value = "/claims", method = RequestMethod.GET)
    public ResponseEntity<String> getClaims(@RequestParam(value = "status", required = false) String status) {
        System.out.println("getClaims starts...");
        try {
            Gson gson = new Gson();
            Status _status = gson.fromJson(status, Status.class);
            Claim search = new Claim();
            search.setStatus(_status);
            List<Claim> claims = billingService.getClaim(search);
            List<ClaimView> view = new LinkedList<>();
            for (Claim claim : claims) {
                view.add(new ClaimView(claim));
            }
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(view));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errorMessage\" : \" " + e.getCause().toString() + " \"}");
        }
    }

    @RequestMapping("/claims/{claimId}")
    public ResponseEntity<String> getClaim(@PathVariable String claimId) {
        System.out.println("getClaim starts...");
        try {
            Claim search = new Claim();
            search.setId(claimId);
            List<Claim> claims = billingService.getClaim(search);
            List<ClaimView> view = new LinkedList<>();
            for (Claim claim : claims) {
                view.add(new ClaimView(claim));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(view));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errorMessage\" : \" " + e.getCause().toString() + " \"}");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/transactions", method = RequestMethod.POST)
    public ResponseEntity<String> addTransaction(@PathVariable String claimId, @RequestBody String body) {
        System.out.println("getClaim starts...");
        try {
            Gson gson = new Gson();
            Transaction transaction = gson.fromJson(body, Transaction.class);
            transaction.setClaimId(claimId);
            billingService.addTransaction(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(SUCCESS_MESSAGE));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errorMessage\" : \" " + e.getCause().toString() + " \"}");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/transactions")
    public ResponseEntity<String> getTransaction(@PathVariable String claimId) {
        System.out.println("getClaim starts...");
        try {
            Gson gson = new Gson();
            List<Transaction> transactions = billingService.getTransaction(claimId);
            List<TransactionView> view = new LinkedList<>();
            for (Transaction transaction : transactions) {
                view.add(new TransactionView(transaction));
            }
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(view));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"errorMessage\" : \" " + e.getCause().toString() + " \"}");
        }
    }

    @RequestMapping(value = "/claims", method = RequestMethod.POST)
    public ResponseEntity<String> createClaim(@RequestBody String body) {
        System.out.println("createClaim starts...");
        Gson gson = new Gson();
        CreateClaimRequest request = gson.fromJson(body, CreateClaimRequest.class);
        try {
            Claim claim = createClaimFromRequest(request);
            String id = billingService.createClaim(claim);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"claimId:\":\"" + id + "\"}");
        } catch (Exception e) {
            String message = getMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message:\": \"" + message + "\"");
        }
    }

    @RequestMapping(value = "/claims/{claimId}", method = RequestMethod.PATCH)
    public ResponseEntity<String> patchClaim(@PathVariable String claimId, @RequestBody String body) {
        System.out.println("patchClaim starts...");
        Gson gson = new Gson();
        try {
            Claim request = gson.fromJson(body, Claim.class);
            Claim claim = new Claim();
            claim.setStatus(request.getStatus());
            claim.setId(claimId);
            billingService.updateClaim(claim);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            String message = getMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message:\": \"" + message + "\"");
        }
    }

    @RequestMapping(value = "/claims", method = RequestMethod.PUT)
    public ResponseEntity<String> updateClaim(@RequestBody String body) {
        System.out.println("patchClaim starts...");
        Gson gson = new Gson();
        try {
            Claim claim = gson.fromJson(body, Claim.class);
            billingService.updateClaim(claim);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            String message = getMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message:\": \"" + message + "\"");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/icd10s", method = RequestMethod.PUT)
    public ResponseEntity<String> updateClaimICD(@RequestBody String body) {
        System.out.println("patchClaim starts...");
        Gson gson = new Gson();
        try {
            Claim claim = gson.fromJson(body, Claim.class);
            billingService.updateClaim(claim);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            String message = getMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message:\": \"" + message + "\"");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/cpts", method = RequestMethod.PUT)
    public ResponseEntity<String> updateClaimCPT(@RequestBody String body) {
        System.out.println("patchClaim starts...");
        Gson gson = new Gson();
        try {
            Claim claim = gson.fromJson(body, Claim.class);
            billingService.updateClaim(claim);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            String message = getMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message:\": \"" + message + "\"");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/actions/process", method = RequestMethod.POST)
    public ResponseEntity<String> processClaim(@PathVariable String claimId) {
        System.out.println("processClaim starts...");
        try {
            billingService.processClaim(claimId);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            String message = getMessage(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message:\": \"" + message + "\"");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/actions/generateStatement", method = RequestMethod.POST)
    public ResponseEntity<String> getStatement(@PathVariable String claimId) {
        System.out.println("processClaim starts...");
        try {
            Statement statement = billingService.getStatement(claimId);
            StatementView view = new StatementView(statement);
            Mock.updateMockDetailsForPatients(view, claimId.hashCode());
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(view));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message\":\"" + e.getMessage() + "\"");
        }
    }

    @RequestMapping(value = "/claims/{claimId}/actions/settle", method = RequestMethod.POST)
    public ResponseEntity<String> settleClaim(@PathVariable String claimId) {
        System.out.println("processClaim starts...");
        try {
            billingService.settleClaim(claimId);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().toString());
        }
    }

    @RequestMapping(value = "/claims/{claimId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateClaim(@PathVariable String claimId, @RequestBody String body) {
        try {
            Gson gson = new Gson();
            Claim claim = gson.fromJson(body, Claim.class);
            claim.setId(claimId);
            billingService.updateClaim(claim);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().toString());
        }
    }

    @RequestMapping(value = "/claims/{claimId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteClaim(@PathVariable String claimId) {
        try {
            billingService.deleteClaim(claimId);
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().toString());
        }
    }

    @RequestMapping(value = "/configurations")
    public ResponseEntity<String> getConfigurations() {
        try {
            Map<String, Object> configuration = billingService.getConfigurations();
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(configuration));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause().toString());
        }
    }

    private String getMessage(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null)
            message = e.getCause().getMessage();
        return message;
    }

    private Claim createClaimFromRequest(CreateClaimRequest request) {
        Claim claim = new Claim();
        List<ICD10> icd10s = new LinkedList<>();
        for (String code : request.getIcd10Codes()) {
            ICD10 icd10 = new ICD10();
            icd10.setCode(code);
            icd10s.add(icd10);
        }
        claim.setIcd10s(icd10s);
        List<CPT> CPTs = new LinkedList<>();
        for (String id : request.getCpts()) {
            CPT cpt = new CPT();
            cpt.setCode(id);
            cpt.setId(id);
            CPTs.add(cpt);
        }
        claim.setCPTs(CPTs);
        claim.setPatientId(request.getPatientId());
        claim.setAdditionalInfo(request.getAdditionalInfo());
        return claim;
    }

}
