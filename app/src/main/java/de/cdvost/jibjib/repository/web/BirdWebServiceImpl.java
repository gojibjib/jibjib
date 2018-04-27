package de.cdvost.jibjib.repository.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BirdWebServiceImpl implements IBirdWebService {
    @Override
    public String match(Object audio) {
        //access web service (some seconds delay)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "chirp chirp says the sparrow";
    }

    public String requestMatch(Object audio) {

        try {
            String uriString = "https://jibjib.cdvost.de/match";

            URL url = new URL(uriString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("format", "json");
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

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
