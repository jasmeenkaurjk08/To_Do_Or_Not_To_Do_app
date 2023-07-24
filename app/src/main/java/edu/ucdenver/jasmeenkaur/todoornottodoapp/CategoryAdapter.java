package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryListItemHolder> {
    private MainActivity mainActivity;
    private ArrayList<Category> listOfCategories;


    public CategoryAdapter(MainActivity mainActivity, ArrayList<Category> listOfCategories) {
        this.mainActivity = mainActivity;
        this.listOfCategories = listOfCategories;

    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from (parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new CategoryAdapter.CategoryListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryListItemHolder holder, int position) {
        Category thisCategory = listOfCategories.get(position);
        holder.textViewListName.setText(thisCategory.getName());

    }

    @Override
    public int getItemCount() {
        return listOfCategories.size();
    }

    public class CategoryListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewListName;

        public CategoryListItemHolder (View itemView) {
            super(itemView);
            textViewListName = itemView.findViewById(R.id.textViewListItem);
            textViewListName.setClickable(true);
            textViewListName.setOnClickListener(this);


        }

        public void onClick (View view) {
            // Action to perform when user click on this view
            // Want to view all of the tasks in the list (switch from activity_main to list_view)
            //mainActivity.showContact(getAdapterPosition());
        }

    }
}
