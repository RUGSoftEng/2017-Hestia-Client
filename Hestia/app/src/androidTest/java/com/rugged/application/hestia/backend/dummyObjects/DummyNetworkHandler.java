package com.rugged.application.hestia.backend.dummyObjects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import hestia.backend.NetworkHandler;

public class DummyNetworkHandler extends NetworkHandler {

    /* TODO implement this class to mock the behaviour of the network handler based on the endpoint given. */

    public DummyNetworkHandler(String ip, int port) {
        super(ip, port);
    }

    public JsonElement GET(String endpoint) throws IOException {
        JsonElement payload = new JsonObject();
        return payload;
    }

    public JsonElement POST(JsonObject object, String endpoint) throws IOException {
        JsonElement payload = new JsonObject();
        return payload;
    }

    public JsonElement DELETE(String endpoint) throws IOException {
        JsonElement payload = new JsonObject();
        return payload;
    }

    public JsonElement PUT(JsonObject object, String endpoint) throws IOException {
        JsonElement payload = new JsonObject();
        return payload;
    }
}
