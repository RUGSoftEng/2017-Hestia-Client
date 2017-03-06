package com.rugged.application.hestia;

import java.util.UUID;

public class Peripheral {
    private String type;
    private UUID uuId;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getuuId() {
        return uuId;
    }

    public void setuuId(UUID uuId) {
        this.uuId = uuId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }






}
