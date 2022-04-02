package com.todolist.api;

import com.todolist.commons.Constants;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static APIClient shared;
    private Retrofit retrofit;
    private JSONPlaceholderAPI service;

    public static APIClient getInstance() {
        if(shared == null) {
            shared = new APIClient();
        }

        return shared;
    }

    public APIClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constants.TODO_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = this.retrofit.create(JSONPlaceholderAPI.class);
    }

    public void getTodos(Callback<List<TodoAPI>> callback) {
        this.service.getTodos().enqueue(callback);
    }

    public void addTodo(TodoAPI todo, Callback<TodoAPI> callback) {
        this.service.addTodo(todo).enqueue(callback);
    }

    public void updateTodo() {

    }

    public void deleteTodo() {

    }
}
