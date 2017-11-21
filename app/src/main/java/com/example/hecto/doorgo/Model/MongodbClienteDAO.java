package com.example.hecto.doorgo.Model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Created by hecto on 26/09/2017.
 */

public class MongodbClienteDAO {
    static String stream=null;

    public MongodbClienteDAO(){
    }

    public String GetHTTPData(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            //Verificar la conexion
            if (urlConnection.getResponseCode()==200)
            {
                // Si response code = 200 -> HTTP.OK
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                // Read the BufferInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line=r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                urlConnection.disconnect();
            }else{

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }

    public void PostHTTPData(String urlString, String json){
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int lengh = out.length;

            urlConnection.setFixedLengthStreamingMode(lengh);
            urlConnection.setRequestProperty("Content-Type","application/json; charset-UTF-8");
            urlConnection.connect();
            try(OutputStream os = urlConnection.getOutputStream())
            {
                os.write(out);
            }
            InputStream response = urlConnection.getInputStream();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PutHTTPData(String urlString, String newValue){
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);

            byte[] out = newValue.getBytes(StandardCharsets.UTF_8);
            int lengh = out.length;

            urlConnection.setFixedLengthStreamingMode(lengh);
            urlConnection.setRequestProperty("Content-Type","application/json; charset-UTF-8");
            urlConnection.connect();
            try(OutputStream os = urlConnection.getOutputStream())
            {
                os.write(out);
            }
            InputStream response = urlConnection.getInputStream();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteHTTPData(String urlString, String json){
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoOutput(true);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int lengh = out.length;

            urlConnection.setFixedLengthStreamingMode(lengh);
            urlConnection.setRequestProperty("Content-Type","application/json; charset-UTF-8");
            urlConnection.connect();
            try(OutputStream os = urlConnection.getOutputStream())
            {
                os.write(out);
            }
            InputStream response = urlConnection.getInputStream();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
