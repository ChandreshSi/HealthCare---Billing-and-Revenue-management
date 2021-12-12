package com.healthcare.billing.util;

import com.healthcare.billing.controller.model.StatementView;
import com.healthcare.billing.model.Claim;
import com.healthcare.billing.model.Transaction;

import java.net.InetAddress;
import java.util.*;

public class Mock {
    private static final String[] patientsName = new String[]{"John B. Smith", "Franklin T. Wong", "Alicia J. Zelava",
            "Jennifer S. Wallace", "Ramesh K. Narayan", "Joyce A. English", "Ahmad V. Jabbar", "James E. Borg"};
    private static final String[] patientsAddress = new String[]{"731 Fondren, Houston, TX", "638 Voss Houston, TX",
            "3321 Castle Spring, TX", "291 Berry Bellaire, TX",
            "975 Fire Oak Humble, TX", "5631 Rice Houston, TX", "980 Dallas Houston, TX", "450 Stone, Houston, TX"};
    private static final String[] dob = new String[]{"1965-01-09", "1955-12-08", "1968-01-19", "1941-06-20", "1962-09-15", "1972-07-31", "1969-03-29", "1937-11-10"};
    private static final List<Map<String, String>> insuranceDetails = new LinkedList<>();

    static {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "Public Health Insurance");
        map.put("id", "ICDX-SA-SASA-ASA");
        insuranceDetails.add(map);
    }

    public static void updateMockDetailsForPatients(StatementView view, int index) {
        Random random = new Random();
        index = index < 0 ? index * -1 : index;
        index = index % patientsAddress.length;
        view.setPatientName(patientsName[index]);
        view.setAddress(patientsAddress[index]);
        view.setDob(dob[index]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(9));
        }
        view.setPatientSSN(sb.toString());
        view.setInsuranceDetails(insuranceDetails);
        List<String> dates = new LinkedList<>();
        dates.add(new Date().toString());
        view.setEncounterDates(dates);
    }

    public static void sendNotificationInsurer(String clientURL, Claim claim) throws Exception {
        Map<String, String> message = new LinkedHashMap<>();
        message.put("message", "A claim is generated for patient: " + claim.getPatientId());
        message.put("amount", String.valueOf(claim.getAmount()));
        String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/billing/claims/" + claim.getId() + "/actions/generateStatement";
        message.put("comment", "A complete statement can be downloaded from: POST " + url);
        Client.getInstance().makeRequest("http://" + InetAddress.getLocalHost().getHostName() + clientURL, message);
    }

    public static void sendNotification(String clientURL, Claim claim) throws Exception {
        Map<String, String> message = new LinkedHashMap<>();
        message.put("message", "A claim is update/created for you - patientID: " + claim.getPatientId());
        message.put("amount", String.valueOf(claim.getAmount()));
        message.put("status", String.valueOf(claim.getStatus().toString()));
        String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/billing/claims/" + claim.getId() + "/actions/generateStatement";
        message.put("comment", "Please see your complete statement at: POST " + url);
        Client.getInstance().makeRequest("http://" + InetAddress.getLocalHost().getHostName() + clientURL, message);
    }

    public static void sendNotification(String url, Transaction transaction) throws Exception {
        Map<String, String> message = new HashMap<>();
        message.put("message", "A new payment is made against your claim with id: " + transaction.getClaimId());
        message.put("amount", String.valueOf(transaction.getAmount()));
        message.put("type", String.valueOf(transaction.getTransactionType().toString()));
        String hostname = "http://" + InetAddress.getLocalHost().getHostName() + ":8080";
        String claimUrl = hostname + "/billing/claims/" + transaction.getId() + "/actions/generateStatement";
        String transactionUrl = hostname + "/claims/" + transaction.getId() + "/transactions";
        message.put("comment", "Please see your complete statement at: POST " + claimUrl + " and look at all transactions at: " + transactionUrl);
        Client.getInstance().makeRequest("http://" + InetAddress.getLocalHost().getHostName() + url, message);
    }

}
