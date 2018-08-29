package de.cdvost.jibjib.repository.web;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.cdvost.jibjib.BuildConfig;
import de.cdvost.jibjib.repository.converter.WikiTextCleaner;

public class BirdWebServiceImpl implements IBirdWebService {

    private static final int TIMEOUT = 3000;

    @Override
    public String match(Object audio) throws Exception{
        String response = requestMatch(audio);
//        String response = requestDummy(audio);
        return response;
    }

    @Override
    public String getMatchBird(int id) throws  Exception{
        String response = requestBirdDetails(id);
        return response;

    }

    private String requestBirdDetails(int id) throws Exception{

        String uriString = BuildConfig.Base_URL+"/birds/"+id;

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

    }

    public String requestDummy(Object audio) throws  IOException{

        String uriString = BuildConfig.Base_URL+"/birds/dummy";

        URL url = new URL(uriString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(TIMEOUT);
        conn.setReadTimeout(TIMEOUT);
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

    }

    public String requestMatch(Object audio) throws Exception {

        String uriString = BuildConfig.Base_URL+"/detect/binaryo";

        URL url = new URL(uriString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(TIMEOUT);
        conn.setReadTimeout(TIMEOUT);

        conn.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());

        File audioFile = new File(audio.toString());
        byte[] fileData = new byte[(int) audioFile.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(audioFile));
        dis.readFully(fileData);
        dis.close();

        out.write(fileData);
        out.flush();
        out.close();

        if (conn.getResponseCode() != 200 && conn.getResponseCode()!= 202) {
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
    }
}
