package com.healthcare.billing.service;

import com.healthcare.billing.model.*;
import com.healthcare.billing.repository.BillingRepository;
import com.healthcare.billing.repository.BillingRepositoryImpl;
import com.healthcare.billing.util.Mock;

import java.util.*;

public class BillingServiceImpl implements BillingService {

    final private BillingRepository repository = new BillingRepositoryImpl();

    @Override
    public List<ICD10> getBaseSearchICD10s() {
        return repository.getBaseSearchICD10s();
    }

    @Override
    public List<ICD10> getSearchICD10s(String search) {
        return repository.getSearchICD10s(search);
    }

    @Override
    public ICD10 getICD10s(String search) {
        return repository.getICD10s(search);
    }

    @Override
    public List<CPTGroup> getCPTGroups(CPTGroup search) {
        return repository.getCPTGroups(search);
    }

    @Override
    public List<CPT> getCPTs(CPT search) {
        return repository.getCPTs(search);
    }

    @Override
    public void patchCPT(String cptId, CPT cpt) {
        repository.patchCPT(cptId, cpt);
    }

    @Override
    public String createClaim(Claim claim) {
        String id = repository.createClaim(claim);
        try {
//            Claim search = new Claim();
//            search.setId(id);
//            Claim dbClaim = getClaim(search).get(0);
//            Mock.sendNotification(dbClaim);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        }
        return id;
    }

    @Override
    public Statement getStatement(String claimId) {
        Claim search = new Claim();
        search.setId(claimId);
        Claim claim = repository.getClaim(search).get(0);
        if (claim == null || claim.getStatus() == Status.PROCESSED || claim.getStatus() == Status.DELETED) {
            throw new IllegalArgumentException("No statement is present for the claim id: " + claimId);
        }
        List<Transaction> transactions = repository.getTransactions(claim.getId());
        Statement statement = new Statement();
        statement.setClaim(claim);
        statement.setTransactions(transactions);
        statement.setBalance(calculateRemainingAmount(claim, transactions));
        return statement;
    }

    @Override
    public void settleClaim(String claimId) {
        Claim update = new Claim();
        update.setId(claimId);
        update.setStatus(Status.PROCESSED);
        repository.updateClaim(update);
        try {
//            Claim claim = getClaim(update).get(0);
//            Mock.sendNotification(claim);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private int calculateRemainingAmount(Claim claim, List<Transaction> transactions) {
        int amount = claim.getAmount();
        for (Transaction transaction : transactions) {
            amount += transaction.getTransactionType() == TransactionType.CREDIT ?
                    transaction.getAmount() : -1 * transaction.getAmount();
        }
        return amount;
    }

    @Override
    public void processClaim(String claimId) {
        Claim update = new Claim();
        update.setId(claimId);
        update.setStatus(Status.SENT_FOR_ADJUDICATION);
        repository.updateClaim(update);
        try {
//            Claim claim = getClaim(update).get(0);
//            Mock.sendNotification(claim);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public List<Claim> getClaim(Claim search) {
        return repository.getClaim(search);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        repository.addTransaction(transaction);
        try {
//            Transaction dbTransaction = getTransaction(transaction.getClaimId()).get(0);
//            Mock.sendNotification(dbTransaction);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public void updateClaim(Claim claim) {
        repository.updateClaim(claim);
        try {
//            Claim search = new Claim();
//            search.setId(claim.getId());
//            Claim dbClaim = getClaim(claim).get(0);
//            Mock.sendNotification(dbClaim);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public List<Transaction> getTransaction(String claimId) {
        return repository.getTransactions(claimId);
    }

    @Override
    public void deleteClaim(String claimId) {
        Claim claim = new Claim();
        claim.setId(claimId);
        claim.setStatus(Status.DELETED);
        repository.updateClaim(claim);
    }

    @Override
    public Map<String, Object> getConfigurations() {
        return repository.getConfigurations();
    }

}
