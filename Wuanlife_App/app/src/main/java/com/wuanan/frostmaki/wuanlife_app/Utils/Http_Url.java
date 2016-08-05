package com.wuanan.frostmaki.wuanlife_app.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Frostmaki on 2016/7/20.
 */
public class Http_Url {

public static String getUrlReponse(String s) {
    String RESOURCE=s;
    String resultDatayhkk=null;

    try {
        Log.e("Http_url  Resource-->",RESOURCE);
        BufferedReader reader;
        URL url = new URL(RESOURCE);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(8000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");
        connection.setRequestMethod("POST");


        InputStream in = connection.getInputStream();

        reader = new BufferedReader(new InputStreamReader(in));


        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            String inputline = null;
            String resultData = null;

            while ((inputline = reader.readLine()) != null) {
                resultData = inputline + "\n";
            }

            return resultData;
        }
    } catch (Exception e) {
        e.printStackTrace();
        Log.e("Http-Url------>异常",e+"");
    }
    return null;
        }
    }


