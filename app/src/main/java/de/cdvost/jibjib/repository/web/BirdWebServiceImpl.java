package de.cdvost.jibjib.repository.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BirdWebServiceImpl implements IBirdWebService {
    @Override
    public String match(Object audio) {
        String response = requestMatch(audio);
        return response;
    }

    @Override
    public String getMatchBird(int id) {
        String response = requestBirdDetails(id);
        return response;

    }

    public String requestBirdDetails(int id) {

        try {
            String uriString = "http://jibjib.api.f0rkd.net:8080/birds/"+id;

            URL url = new URL(uriString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            conn.disconnect();
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String requestMatch(Object audio) {

        try {
            String uriString = "http://jibjib.api.f0rkd.net:8080/dummy";

            URL url = new URL(uriString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            conn.disconnect();
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
