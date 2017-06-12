package com.rugged.application.hestia.backend.frontend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import hestia.backend.NetworkHandler;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.RequiredInfo;
import hestia.backend.models.deserializers.RequiredInfoDeserializer;

/**
 * Created by lars on 12-6-17.
 */

public class MockServerCollectionsInteractor extends ServerCollectionsInteractor{

    public MockServerCollectionsInteractor(NetworkHandler handler) {
        super(handler);
    }

    public ArrayList<String> getCollections() throws IOException, ComFaultException {
        ArrayList<String> collections = new ArrayList<>();
        collections.add("CollectionOne");
        collections.add("CollectionTwo");
        return collections;
    }

    public ArrayList<String> getPlugins(String collection) throws IOException, ComFaultException {
        ArrayList<String> plugins = new ArrayList<>();
        plugins.add("PluginOne");
        plugins.add("PluginTwo");
        return plugins;
    }

    public RequiredInfo getRequiredInfo(String collection, String plugin) throws IOException, ComFaultException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("ip", "The ip should be entered here");
        hashMap.put("username", "The username as registerd");
        return new RequiredInfo("Collection", "Plugin", hashMap);
    }

}
