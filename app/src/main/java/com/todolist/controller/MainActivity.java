package com.todolist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.todolist.R;
import com.todolist.model.Task;
import com.todolist.model.TaskAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        this.layoutManager = new LinearLayoutManager(this);
        this.tasks.setLayoutManager(layoutManager);

        this.adapter = new TaskAdapter(this.tasksList);
        this.tasks.setAdapter(this.adapter);
    }
}