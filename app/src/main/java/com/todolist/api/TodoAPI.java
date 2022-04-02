package com.todolist.api;

import java.io.Serializable;

public class TodoAPI implements Serializable {
    private static int todoAPICounter = 1;
    private Integer userID;
    private Integer id;
    private String title;
    private Boolean completed;

    public TodoAPI(Integer userID, Integer id, String title, Boolean completed) {
        this.userID = userID;
        this.id = id;
        todoAPICounter = id;

        this.title = title;
        this.completed = completed;
    }

    public TodoAPI(String title) {
        this.userID = 1;
        todoAPICounter++;
        this.id = todoAPICounter;
        this.title = title;
        this.completed = false;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
