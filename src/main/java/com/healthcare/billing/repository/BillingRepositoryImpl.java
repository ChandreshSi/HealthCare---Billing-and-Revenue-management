package com.healthcare.billing.repository;

import com.healthcare.billing.exception.ConnectionException;
import com.healthcare.billing.model.*;
import com.healthcare.billing.repository.jdbc.JDBCConnection;
import com.healthcare.billing.repository.jdbc.JDBCRepositoryImpl;
import com.healthcare.billing.repository.json.JSONCodeManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BillingRepositoryImpl implements BillingRepository {

    JDBCRepositoryImpl repository = new JDBCRepositoryImpl();

    @Override
    public List<ICD10> getBaseSearchICD10s() {
        return JSONCodeManager.getInstance().getBaseSearchICD10s();
    }

    @Override
    public List<ICD10> getSearchICD10s(String search) {
        return JSONCodeManager.getInstance().getSearchICD10s(search);
    }

    @Override
    public ICD10 getICD10s(String search) {
        return JSONCodeManager.getInstance().getICD10Map().get(search);
    }

    @Override
    public List<CPTGroup> getCPTGroups(CPTGroup search) {
        return repository.getCPTGroups(search);
    }

    @Override
    public List<CPT> getCPTs(CPT search) {
        return repository.getCPT(search);
    }

    @Override
    public void patchCPT(String cptId, CPT cpt) {
        repository.patchCPT(cptId, cpt);
    }

    @Override
    public String createClaim(Claim claim) {
        return repository.addClaim(claim);
    }

    @Override
    public List<Claim> getClaim(Claim search) {
        return repository.getClaim(search);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            Claim search = new Claim();
            search.setId(transaction.getClaimId());
            Claim claim = repository.getClaim(search, connection).get(0);
            if (claim.getStatus() == Status.PROCESSED || claim.getStatus() == Status.DELETED || claim.getStatus() == Status.INITIATED)
                throw new RuntimeException("Cannot receive transaction to this claim with id: " + claim.getId() + " with status: " + claim.getStatus());
            repository.addTransaction(transaction, connection);
            if (transaction.getTransactionType() == TransactionType.CREDIT) {
                claim.setStatus(Status.PARTIALLY_PROCESSED);
                repository.updateClaim(claim, connection);
            }
            connection.commit();
        } catch (Exception e) {
            repository.rollback(connection);
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } finally {
            repository.close(connection);
        }
    }

    @Override
    public void updateClaim(Claim claim) {
        repository.updateClaim(claim);
    }

    @Override
    public List<Transaction> getTransactions(String claimId) {
        return repository.getTransactions(claimId);
    }

    @Override
    public void deleteClaim(String claimId) {
        repository.deleteClaim(claimId);
    }

    @Override
    public Map<String, Object> getConfigurations() {
        return repository.getConfigurationVariables();
    }
}
