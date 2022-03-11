package com.todolist.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.todolist.R;
import com.todolist.model.Task;
import com.todolist.model.TaskAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView tasks;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    private List<Task> tasksList = new LinkedList<Task>() {{
        add(new Task("Sacar el perro"));
        add(new Task("Comprar el pan"));
        add(new Task("Revisar el correo de La Salle"));
        add(new Task("Preparar reuniones del día"));
        add(new Task("Hacer ejercicio"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get elements from MainActivity
        this.fab = findViewById(R.id.fab);
        this.fab.setOnClickListener(view -> this.showCreateTaskDialog(view));

        this.tasks = findViewById(R.id.tasksRecyclerView);
        this.layoutManager = new LinearLayoutManager(this);
        this.tasks.setLayoutManager(layoutManager);

        this.adapter = new TaskAdapter(this.tasksList);
        this.tasks.setAdapter(this.adapter);
    }

    private void showCreateTaskDialog(View view) {
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
}