package com.todolist.model;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.todolist.R;
import com.todolist.api.APIClient;
import com.todolist.api.TodoAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter  extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private APIClient apiClient;
    private ArrayList<TodoAPI> tasks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CheckBox checkBox;
        public ImageView crossImage, editImage;

        public ViewHolder(View view) {
            super(view);

            // Get elements of the view for each item of the RecyclerView
            this.title = view.findViewById(R.id.textView);
            this.checkBox = view.findViewById(R.id.checkbox);
            this.crossImage = view.findViewById(R.id.crossImage);
            this.editImage = view.findViewById(R.id.editImage);
        }
    }

    public TaskAdapter(ArrayList<TodoAPI> tasks, APIClient apiClient) {
        this.tasks = tasks;
        this.apiClient = apiClient;
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
        final TodoAPI item = this.tasks.get(position);

        holder.checkBox.setChecked(item.isCompleted());
        holder.title.setText(item.getTitle());

        if(item.isCompleted()) {
            holder.title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.checkBox.setOnCheckedChangeListener((checkbox, value) -> {
            if(checkbox.isChecked()) {
                // Strike through text
                holder.title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                // Clear strike through text
                holder.title.setPaintFlags(holder.title.getPaintFlags() &
                        (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }

            item.setCompleted(value);

            this.apiClient.updateTodo(item, new Callback<TodoAPI>() {
                @Override
                public void onResponse(Call<TodoAPI> call, Response<TodoAPI> response) {
                    Toast.makeText(holder.itemView.getContext(), "Todo updated to API",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<TodoAPI> call, Throwable t) {
                    Toast.makeText(holder.itemView.getContext(), "ERROR: Cannot update TODO to API",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.crossImage.setOnClickListener(view -> {
            this.tasks.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, this.tasks.size());

            this.apiClient.deleteTodo(item, new Callback<TodoAPI>() {
                @Override
                public void onResponse(Call<TodoAPI> call, Response<TodoAPI> response) {
                    Toast.makeText(holder.itemView.getContext(), "Todo deleted from API",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<TodoAPI> call, Throwable t) {
                    Toast.makeText(holder.itemView.getContext(), "ERROR: Cannot delete TODO from API",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.editImage.setOnClickListener(view -> {
            this.showEditDialog(view, position);
        });
    }

    private void showEditDialog(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.create_task_input);

        // Set up the input
        final EditText input = new EditText(view.getContext());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.button_edit_task_label, (dialog, which) -> {
            if(!input.getText().toString().isEmpty()) {
                TodoAPI task = this.tasks.get(position);
                task.setTitle(input.getText().toString());
                notifyDataSetChanged();

                this.apiClient.updateTodo(task, new Callback<TodoAPI>() {
                    @Override
                    public void onResponse(Call<TodoAPI> call, Response<TodoAPI> response) {
                        Toast.makeText(view.getContext(), "Todo updated to API",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TodoAPI> call, Throwable t) {
                        Toast.makeText(view.getContext(), "ERROR: Cannot update TODO to API",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(R.string.button_cancel_label, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }
}
