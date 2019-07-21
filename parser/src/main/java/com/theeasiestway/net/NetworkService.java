package com.theeasiestway.net;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkService {

    private HttpURLConnection connection;
    private int connTimeout = 0;
    private int readTimeout = 0;

    public NetworkService setConnTimeout(int timeout) {
        if (timeout < 0) timeout = 0;
        connTimeout = timeout;
        return this;
    }

    public NetworkService setReadTimeout(int timeout) {
        if (timeout < 0) timeout = 0;
        readTimeout = timeout;
        return this;
    }

    public BufferedInputStream request(String urlString) throws Exception {
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(connTimeout);
            connection.setReadTimeout(readTimeout);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) return new BufferedInputStream(connection.getInputStream());
            else throw new ResponseServerException("Response code: " + responseCode);
        } catch (Exception e) {
            disconnect();
            throw e;
        }
    }

    public void disconnect() {
        if (connection != null) connection.disconnect();
    }
}