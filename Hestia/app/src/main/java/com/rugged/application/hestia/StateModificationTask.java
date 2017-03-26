package com.rugged.application.hestia;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.methods.HttpPost;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


class StateModificationTask extends AsyncTask<Void,Void,Integer> {
    private String TAG = "StateModificationTask";
    Activator a;
    ActivatorState newState;

    public StateModificationTask(Activator a, ActivatorState newState) {
        this.a = a;
        this.newState = newState;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        /*
        String devicesPath = path + "devices/";
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(devicesPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.i(TAG, in.toString());

            devices = readStream(in);
            StringBuilder sb = new StringBuilder();
            for (Device d : devices) {
                sb.append(d.toString());
            }
            Log.i(TAG, sb.toString());
            urlConnection.disconnect();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

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
        return null;*/
        return 0;
    }
}
