package de.cdvost.jibjib.repository.web;

import android.util.Pair;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.cdvost.jibjib.BuildConfig;
import de.cdvost.jibjib.domain.interactors.web.parser.ErrorResponseParser;

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

        checkConnection(conn);

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        conn.disconnect();
        return builder.toString();

    }

    public String requestDummy(Object audio) throws Exception{

        String uriString = BuildConfig.Base_URL+"/birds/dummy";

        URL url = new URL(uriString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(TIMEOUT);
        conn.setReadTimeout(TIMEOUT);
        //conn.setRequestProperty("Accept", "application/json");

        checkConnection(conn);

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

        String uriString = BuildConfig.Base_URL+"/detect/binary";

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

        checkConnection(conn);

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        conn.disconnect();
        return builder.toString();
    }

    private void checkConnection(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200 && conn.getResponseCode()!= 202) {
            InputStream errorStream = conn.getErrorStream();
            String message = "";
            if(errorStream!=null){
                BufferedReader br = new BufferedReader(new InputStreamReader((errorStream)));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                Pair<String, String> pair = ErrorResponseParser.parse(builder.toString());
                message = pair.second;
            }
            throw new RuntimeException("Webrequest failed\n" +
                    "HTTP error code : " + conn.getResponseCode() + "\n" +
                    "Message: "+message);
        }
    }
}
