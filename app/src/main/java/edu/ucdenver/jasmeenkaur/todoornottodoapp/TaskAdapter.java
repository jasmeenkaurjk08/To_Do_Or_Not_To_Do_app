package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskListItemHolder> {
    private ViewListActivity viewListActivity;
    private ArrayList<Task> listOfTasks;


    public TaskAdapter(ViewListActivity viewListActivity, ArrayList<Task> listOfTasks) {
        this.viewListActivity = viewListActivity;
        this.listOfTasks = listOfTasks;

    }

    @NonNull
    @Override
    public TaskAdapter.TaskListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from (parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new TaskAdapter.TaskListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskListItemHolder holder, int position) {
        Task thisTask = listOfTasks.get(position);
        holder.textViewListName.setText(thisTask.getName());

    }

    @Override
    public int getItemCount() {
        return listOfTasks.size();
    }

    public class TaskListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewListName;

        public TaskListItemHolder (View itemView) {
            super(itemView);
            textViewListName = itemView.findViewById(R.id.textViewListItem);
            textViewListName.setClickable(true);
            textViewListName.setOnClickListener(this);


        }

        public void onClick (View view) {
            // Action to perform when user click on this view
            // Want to view a specific task from the list (switch from list_view to task_view)
            viewListActivity.showTask(getAdapterPosition());
        }

    }
}
