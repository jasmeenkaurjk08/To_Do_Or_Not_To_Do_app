package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemHolder> {
    private MainActivity mainActivity;
    private ArrayList<List> listOfLists;


    public ListAdapter(MainActivity mainActivity, ArrayList<List> listOfLists) {
        this.mainActivity = mainActivity;
        this.listOfLists = listOfLists;

    }


    @NonNull
    @Override
    public ListAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from (parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new ListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListItemHolder holder, int position) {
        List thisList = listOfLists.get(position);
        holder.textViewListItem.setText(thisList.getName());
        Log.i("info", "onBindViewHolder setText list name: " + thisList.getName());
    }

    @Override
    public int getItemCount() {
        return listOfLists.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewListItem;

        public ListItemHolder (View itemView) {
            super(itemView);
            textViewListItem = itemView.findViewById(R.id.textViewListItem);
            textViewListItem.setClickable(true);
            textViewListItem.setOnClickListener(this);
        }

        public void onClick (View view) {
            // Action to perform when user click on this view
            // Want to view all of the tasks in the list (switch from activity_main to list_view)
            mainActivity.showList(getAdapterPosition());
        }

    }
}