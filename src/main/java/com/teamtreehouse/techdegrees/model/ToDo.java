package com.teamtreehouse.techdegrees.model;

public class ToDo {

    private int id;
    private String name;
    private boolean isCompleted;

    public ToDo(boolean isCompleted, String name) {
        this.isCompleted = isCompleted;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
