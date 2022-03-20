package com.todolist.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.todolist.R;
import com.todolist.commons.Constants;
import com.todolist.model.Task;
import com.todolist.model.TaskAdapter;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView tasks;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;

    private ArrayList<Task> tasksList = new ArrayList<>(Arrays.asList(
            new Task("Sacar el perro"),
            new Task("Comprar el pan"),
            new Task("Revisar el correo de La Salle"),
            new Task("Preparar reuniones del día"),
            new Task("Hacer ejercicio")
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up SharedPreferences
        this.sharedPreferences = getSharedPreferences(Constants.TODO_LIST_PREFERENCES,
                MODE_PRIVATE);

        // Check if exists a saved list of tasks
        if(!this.sharedPreferences.getString(Constants.TASKS_KEY, "").isEmpty() ){
            String json = this.sharedPreferences.getString(Constants.TASKS_KEY, "");
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();

            // Set recovered tasks list as the list of tasks to display
            this.tasksList.clear();
            this.tasksList = new Gson().fromJson(json, listType);
        }

        // Get elements from MainActivity
        this.fab = findViewById(R.id.fab);
        this.fab.setOnClickListener(view -> this.showCreateTaskDialog());

        this.tasks = findViewById(R.id.tasksRecyclerView);
        this.layoutManager = new LinearLayoutManager(this);
        this.tasks.setLayoutManager(layoutManager);

        this.adapter = new TaskAdapter(this.tasksList);
        this.tasks.setAdapter(this.adapter);
    }

    private void showCreateTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.create_task_input);

        // Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.button_save_task_label, (dialog, which) -> {
            if(!input.getText().toString().isEmpty()) {
                this.tasksList.add(new Task(input.getText().toString()));
            }
        });

        builder.setNegativeButton(R.string.button_cancel_label, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Set up SharedPreferences to save tasks list
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        // Convert tasks list to JSON string
        String json = new Gson().toJson(this.tasksList);

        // Save tasks list to SharedPreferences
        editor.remove(Constants.TASKS_KEY);
        editor.putString(Constants.TASKS_KEY, json);
        editor.apply();
    }
}