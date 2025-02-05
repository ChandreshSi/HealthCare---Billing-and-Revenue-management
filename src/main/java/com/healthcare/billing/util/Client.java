package com.healthcare.billing.util;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class Client {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private Client() {
    }

    private static Client client;

    public static Client getInstance() {
        if (client == null)
            client = new Client();
        return client;
    }

    public void makeRequest(String url, Map<String, String> map) {
        Thread thread = new Thread(new CallClient(okHttpClient, url, map));
        thread.start();
    }
}

class CallClient implements Runnable {

    private final OkHttpClient okHttpClient;
    private String url;
    private final Map<String, String> data;
    int tries = 1;

    CallClient(OkHttpClient okHttpClient, String url, Map<String, String> data) {
        this.url = url;
        this.data = data;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void run() {
        String json = new Gson().toJson(this.data);
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(this.url)
                .post(body)
                .build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            if (tries > 1) {
                e.printStackTrace();
                return;
            }
            String local = this.url.substring(this.url.lastIndexOf(":") + 1);
            String host = "http://ec2-18-223-151-191.us-east-2.compute.amazonaws.com:" + local;
            this.url = host;
            tries++;
            this.run();
        }
    }
}
