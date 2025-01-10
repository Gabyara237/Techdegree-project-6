package com.teamtreehouse.techdegrees.model;

public class Todo {

    private int id;
    private String name;
    private boolean isCompleted;

    public Todo(boolean isCompleted, String name) {
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
