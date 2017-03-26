/**
This class takes care of sending and receiving input for the phone. This class will be used after
Release 0.2.
Note that HttpClient is deprecated for android. Will be rewritten in the future such that it uses
HttpUrlConnection instead of HttpClient.
 */

package com.rugged.application.hestia;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
public class RequestHandler {public static void GETtest() throws IOException {

    CloseableHttpClient	httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet("http://127.0.0.1:5000/devices/");
    CloseableHttpResponse response1 = httpclient.execute(httpGet);
    InputStream is = response1.getEntity().getContent();
    Gson gson= new Gson();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

    Type deviceListType= new TypeToken<ArrayList<Device>>() {}.getType();
    ArrayList<Device> devices = gson.fromJson(gson.newJsonReader(reader), deviceListType);
    System.out.println(devices);
    reader.close();
    response1.close();
    httpclient.close();
}

    public static void POSTtest(String deviceId,String activatorId,String activatorType, String state) throws ClientProtocolException, IOException{
        CloseableHttpClient	httpclient = HttpClients.createDefault();
        String path=("http://127.0.0.1:5000/devices/"+deviceId+"/"+"activator/"+activatorId);
        HttpPost httpPost = new HttpPost(path);
        JsonObject json=new JsonObject();
        json.addProperty("state",state);
        System.out.println(json);
        StringEntity se = new StringEntity(json.toString());
        se.setContentType("application/json");
        httpPost.setEntity(se);
        System.out.println(httpPost.getEntity());
        ResponseHandler responseHandler = new BasicResponseHandler();
        HttpResponse response=  httpclient.execute(httpPost);
        // HttpResponse response=  httpclient.execute(httpPost, responseHandler);
        httpclient.close();
    }
}*/