package com.healthcare.billing.service;

import com.healthcare.billing.model.*;
import com.healthcare.billing.repository.BillingRepository;
import com.healthcare.billing.repository.BillingRepositoryImpl;

import javax.websocket.OnError;
import javax.xml.crypto.dsig.TransformService;
import java.util.*;

public class BillingServiceImpl implements BillingService {

    private BillingRepository repository = new BillingRepositoryImpl();

    @Override
    public List<ICD10> getBaseSearchICDCodes() {
        return repository.getBaseSearchICDCodes();
    }

    @Override
    public List<ICD10> getICDCodes(String search) {
        return repository.getICDCodes(search);
    }

    @Override
    public List<CPTGroup> getCPTCodes() {
        return repository.getCPTCodes();
    }

    @Override
    public List<CPT> getCPTCodes(String groupId) {
        return repository.getCPTCodes(groupId);
    }

    @Override
    public List<CPTCodeRate> getCPTCodeRates() {
        return repository.getCPTCodeRates();
    }

    @Override
    public CPTCodeRate getCPTCodeRate(String code) {
        return repository.getCPTCodeRate(code);
    }

    @Override
    public List<CPTGroup> getCptGroupCodesWithRate() {
        List<CPTGroup> cptGroup = getCPTCodes();
        List<CPTCodeRate> cptRate = getCPTCodeRates();
        Map<String, CPTCodeRate> map = new HashMap<>();
        for (CPTCodeRate rate : cptRate) {
            map.put(rate.getCptCode().getCode(), rate);
        }
        for (CPTGroup group : cptGroup) {
            for (CPT cpt : group.getCodes()) {
                if (group.getCptCodeRates() == null) group.setCptCodeRates(new LinkedList<>());
                group.getCptCodeRates().add(map.get(cpt.getCode()));
            }
            group.setCodes(null);
        }
        return cptGroup;
    }

    @Override
    public String createClaim(Claim claim) {
        return repository.createClaim(claim);
    }

    @Override
    public Claim getClaim(String id) {
        return repository.getClaim(id);
    }

    @Override
    public Statement getStatement(String claimId) {
        Claim claim = repository.getClaim(claimId);
        if (claim == null || claim.getStatus() == Status.PROCESSED) {
            throw new IllegalArgumentException("No statement is present for the claim id: " + claimId);
        }
        List<Transaction> transactions = repository.getTransactions(claim);
        Statement statement = new Statement();
        statement.setClaim(claim);
        statement.setTransactions(transactions);
        statement.setRemainingAmount(calculateRemainingAmount(claim, transactions));
        return statement;
    }

    @Override
    public void settleClaim(String claimId) {
        Claim claim = repository.getClaim(claimId);
        claim.setStatus(Status.PROCESSED);
        repository.updateClaim(claim);
    }

    private int calculateRemainingAmount(Claim claim, List<Transaction> transactions) {
        int amount = claim.getTotalAmount();
        for (Transaction transaction : transactions) {
            amount -= transaction.getAmount();
        }
        return amount;
    }

    public void addFromInsurerTransaction(String claimId, String insurerId, int amount) {
        Transaction transaction = new Transaction();
        transaction.setClaimId(claimId);
        transaction.setPayerId(insurerId);
        transaction.setTransactionType(1); // received
        transaction.setComments("Payment received from insurer");
        transaction.setAmount(amount);
        transaction.setPayeeType(2); // insurer
        transaction.setCurrency(repository.getCurrency("USD"));
        repository.addTransaction(transaction);
    }

    @Override
    public void processClaim(String claimId) {
        Claim claim = getClaim(claimId);
        List<String> insurers = getInsuranceDetails(claim.getPatientId());
        for (String insurerId : insurers) {
            processClaim(claim, insurerId);
        }
    }

    public List<String> getInsuranceDetails(String patientId) {
        // mock
        List<String> insurer = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            insurer.add(UUID.randomUUID().toString());
        }
        return insurer;
    }

    public void processClaim(Claim claim, String insurerId) {
        // send claim to insurer
        Status status = sendToInsurer(claim, insurerId);
        // update status to SEND_FOR_ADJUDICATION
//        updateClaimStatus(Status.SENT_FOR_ADJUDICATION, claim);
        int remainingAmount = claim.getTotalAmount() - calculateRemainingAmount(claim);
        if (remainingAmount <= 0) return;
        switch (status) {
            // if claim accepted:
            case ACCEPTED:
                //  insert a transaction for received payment
                addFromInsurerTransaction(claim.getId(), insurerId, new Random().nextInt(remainingAmount));
                //  update claim status
                updateClaimStatus(Status.ACCEPTED, claim);
                //  send notification
                sendNotification(claim);
                break;
            // if claim denied, or rejected:
            case DENIED:
            case REJECTED:
                //  update claim status
                updateClaimStatus(status, claim);
                //  send notification
                sendNotification(claim);
        }

    }

    public void updateClaimStatus(Status status, Claim claim) {
        claim.setStatus(status);
        repository.updateClaim(claim);
    }

    private Status sendToInsurer(Claim claim, String insurerId) {
        // mocked
        if (claim.hashCode() % 15 == 0) return Status.DENIED;
        if (claim.hashCode() % 25 == 0) return Status.REJECTED;
        return Status.ACCEPTED;
    }

    private void sendNotification(Claim claim) {
        // mocked
    }

    private int calculateRemainingAmount(Claim claim) {
        List<Transaction> transactions = repository.getTransactions(claim);
        int amount = 0;
        for (Transaction transaction : transactions) {
            amount += transaction.getAmount();
        }
        return amount;
    }

}
