package com.todolist.model;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.todolist.R;

import java.util.List;

public class TaskAdapter  extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> tasks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CheckBox checkBox;
        public ImageView crossImage;

        public ViewHolder(View view) {
            super(view);

            // Get elements of the view for each item of the RecyclerView
            this.title = view.findViewById(R.id.textView);
            this.checkBox = view.findViewById(R.id.checkbox);
            this.crossImage = view.findViewById(R.id.crossImage);
        }
    }

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        final Task item = this.tasks.get(position);

        holder.checkBox.setChecked(item.isDone());
        holder.checkBox.setOnCheckedChangeListener((checkbox, value) -> {
            if(checkbox.isChecked()) {
                // Strike through text
                holder.title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                // Clear strike through text
                holder.title.setPaintFlags(holder.title.getPaintFlags() &
                        (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }

            item.setDone(value);
        });
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }
}
