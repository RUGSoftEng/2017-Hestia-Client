package hestia.backend;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rugged.application.hestia.R;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;


/**
 * A singleton class which handles interaction between front and back-end. It contains methods
 * for sending 4 types of requests (GET, PUT, POST AND DELETE), along with additional methods
 * for setting up the connection to the server, sending and receiving data from the server,
 * as well as getters and setters for the ip and the port number.
 */

public class NetworkHandler extends Application {
    private final String TAG = "NetworkHandler";
    private String ip;
    private Integer port;
    private SSLSocketFactory factory;

    public NetworkHandler(String ip, Integer port){
        this.ip = ip;
        this.port = port;
        registerCertificate();
    }



    public JsonElement GET(String endpoint) throws IOException {
        HttpsURLConnection connector = this.connectToSecureServer("GET", endpoint);
        JsonElement payload = this.getPayloadFromServer(connector);
        return payload;
    }

    public JsonElement POST(JsonObject object, String endpoint) throws IOException {
        HttpsURLConnection connector = this.connectToSecureServer("POST", endpoint);
        this.sendToServer(connector, object);
        JsonElement payload = this.getPayloadFromServer(connector);
        return payload;
    }

    public JsonElement DELETE(String endpoint) throws IOException {
        HttpsURLConnection connector = this.connectToSecureServer("DELETE", endpoint);
        JsonElement payload = this.getPayloadFromServer(connector);
        return payload;
    }

    public JsonElement PUT(JsonObject object, String endpoint) throws IOException {
        HttpsURLConnection connector = this.connectToSecureServer("PUT", endpoint);
        this.sendToServer(connector, object);
        JsonElement payload = this.getPayloadFromServer(connector);
        return payload;
    }

    /**
     * This method establishes the connection to the server, by setting the type of request
     * and the path.
     *
     * @param requestMethod the type of request that will be sent to the server.
     * @param endpoint      path to the server's endpoint.
     * @return the object responsible for setting up the connection to the server.
     * @throws IOException
     */
    private HttpsURLConnection connectToSecureServer(String requestMethod, String endpoint) throws IOException {

        String path = this.getDefaultPath() + endpoint;
        URL url = new URL(path);
        HttpsURLConnection connector = (HttpsURLConnection) url.openConnection();
        connector.setSSLSocketFactory(factory);
        connector.setReadTimeout(2000);
        connector.setConnectTimeout(2000);
        connector.setRequestMethod(requestMethod);
        connector.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        return connector;
    }

    /**
     * This method sends the JsonObject object to the server.
     *
     * @param connector the object responsible for setting up the connection to the server.
     * @param object    the JsonObject that will be sent to the server.
     * @throws IOException IOException
     */
    private void sendToServer(HttpsURLConnection connector, JsonObject object) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connector.getOutputStream());
        outputStreamWriter.write(object.toString());
        Log.d(TAG, object.toString());
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    /**
     * Returns the payload from the server as a JsonElement. If the response code is successful,
     * it will get the input stream from the connector. Otherwise, it will get the error stream.
     *
     * @param connector the object responsible for setting up the connection to the server.
     * @return the payload received from the server.
     * @throws IOException IOException
     */
    private JsonElement getPayloadFromServer(HttpsURLConnection connector) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Integer responseCode = connector.getResponseCode();
        Log.d(TAG, "Response code: " + responseCode);
        BufferedReader reader;
        if (this.isSuccessfulRequest(responseCode)) {
            reader = new BufferedReader(new InputStreamReader(connector.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connector.getErrorStream()));
        }

        Type returnType = new TypeToken<JsonElement>() {
        }.getType();
        JsonElement payload = gson.fromJson(gson.newJsonReader(reader), returnType);
        reader.close();

        return payload;
    }

    /**
     * Checks if the HTTPS request was successful or not.
     *
     * @param responseCode the response code of the request.
     * @return true if it succeeded, false otherwise.
     */
    public boolean isSuccessfulRequest(Integer responseCode) {
        return (responseCode != null && (200 <= responseCode && responseCode <= 299));
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDefaultPath() {
        return "https://" + ip + ":" + port + "/";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetworkHandler)) return false;

        NetworkHandler networkHandler = (NetworkHandler) o;
        if (!this.getPort().equals(networkHandler.getPort())) return false;
        return this.getIp().equals(networkHandler.getIp());
    }
}

/*
package com.mkyong.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsClient{

   public static void main(String[] args)
   {
        new HttpsClient().testIt();
   }

   private void testIt(){

      String https_url = "https://www.google.com/";
      URL url;
      try {

	     url = new URL(https_url);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

	     //dumpl all cert info
	     print_https_cert(con);

	     //dump all the content
	     print_content(con);

      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }

   }

   private void print_https_cert(HttpsURLConnection con){

    if(con!=null){

      try {

	System.out.println("Response Code : " + con.getResponseCode());
	System.out.println("Cipher Suite : " + con.getCipherSuite());
	System.out.println("\n");

	Certificate[] certs = con.getServerCertificates();
	for(Certificate cert : certs){
	   System.out.println("Cert Type : " + cert.getType());
	   System.out.println("Cert Hash Code : " + cert.hashCode());
	   System.out.println("Cert Public Key Algorithm : "
                                    + cert.getPublicKey().getAlgorithm());
	   System.out.println("Cert Public Key Format : "
                                    + cert.getPublicKey().getFormat());
	   System.out.println("\n");
	}

	} catch (SSLPeerUnverifiedException e) {
		e.printStackTrace();
	} catch (IOException e){
		e.printStackTrace();
	}

     }

   }

   private void print_content(HttpsURLConnection con){
	if(con!=null){

	try {

	   System.out.println("****** Content of the URL ********");
	   BufferedReader br =
		new BufferedReader(
			new InputStreamReader(con.getInputStream()));

	   String input;

	   while ((input = br.readLine()) != null){
	      System.out.println(input);
	   }
	   br.close();

	} catch (IOException e) {
	   e.printStackTrace();
	}

       }

   }

}
 */
