package com.healthcare.billing.util;

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
    private final String url;
    private final Map<String, String> data;

    CallClient(OkHttpClient okHttpClient, String url, Map<String, String> data) {
        this.url = url;
        this.data = data;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void run() {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : this.data.keySet()) {
            builder.add(key, this.data.get(key));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(this.url)
                .post(formBody)
                .build();
        try {
            this.okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
