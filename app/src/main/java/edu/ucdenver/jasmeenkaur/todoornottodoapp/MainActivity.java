package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<List> listOfLists;
    private ListAdapter listAdapter;
    private ArrayList<Task> listOfTasks;
    private ArrayList<Category> listOfCategories;


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private DataManager dm;

    private String currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // create lists and their adapters
        listOfLists = new ArrayList<List>();
        listOfTasks = new ArrayList<Task>();
        listOfCategories = new ArrayList<Category>();
        listAdapter = new ListAdapter(this, listOfLists);


        // set up recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        binding.contentMain.recyclerViewMain.setLayoutManager(layoutManager);
        binding.contentMain.recyclerViewMain.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.contentMain.recyclerViewMain.setAdapter(listAdapter);

        prefs = getSharedPreferences("toDoApp", Context.MODE_PRIVATE);
        editor = prefs.edit();


        dm = new DataManager(this);

        currentPage = "view_all_lists";
        /* current pages can be:
            view_all_lists
            list_settings           menu_list_settings
            view_list               menu_view_list
            add_task                menu_back
            view_task               menu_back
            edit_task               menu_back
            add_category_main       menu_back
            add_category_list       menu_back
            view_category_main      menu_back
            view_category_list      menu_back
            edit_category_main      menu_back
            edit_category_list      menu_back
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        switch (currentPage){
            case "view_all_lists":
                break;
            case "list_settings":
                getMenuInflater().inflate(R.menu.menu_list_settings, menu);
                break;
            case "view_list":
                getMenuInflater().inflate(R.menu.menu_view_list, menu);
                break;
            default:
                getMenuInflater().inflate(R.menu.menu_back, menu);
                break;
        }
        return true;
    }





    public void onResume () {
        super.onResume();
    }
}