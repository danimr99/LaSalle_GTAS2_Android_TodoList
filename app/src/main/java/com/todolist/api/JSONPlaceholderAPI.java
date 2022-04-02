package com.todolist.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceholderAPI {
    @GET("todos")
    Call<List<TodoAPI>> getTodos();

    @POST("todos")
    Call<TodoAPI> addTodo(@Body TodoAPI todo);
}
