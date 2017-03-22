package com.rugged.application.hestia;

public class Activator {
    private int id;
    private boolean value;
    private String name;
    private String type;


    public Activator(int id, boolean value, String name, String type) {
        this.id = id;
        this.value = value;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
