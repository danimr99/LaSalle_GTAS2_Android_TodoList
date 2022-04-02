package com.todolist.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONPlaceholderAPI {
    @GET("todos")
    Call<List<TodoAPI>> getTodos();

    @POST("todos")
    Call<TodoAPI> addTodo(@Body TodoAPI todo);

    @PATCH("todos/{id}")
    Call<TodoAPI> updateTodo(@Path("id") Integer id, @Body TodoAPI todo);

    @DELETE("todos/{id}")
    Call<TodoAPI> deleteTodo(@Path("id") Integer id);
}
