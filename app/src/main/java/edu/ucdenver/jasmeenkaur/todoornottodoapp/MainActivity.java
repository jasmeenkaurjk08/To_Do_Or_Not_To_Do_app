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
    private TaskAdapter taskAdapter;
    private ArrayList<Category> listOfCategories;
    private CategoryAdapter categoryAdapter;


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private DataManager dm;

    private String currentPage;
    private List currentList;

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
        taskAdapter = new TaskAdapter(this, listOfTasks);
        categoryAdapter = new CategoryAdapter(this, listOfCategories);


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
            view_all_lists          N/A                     activity_main.xml
            add_list                menu_back               add_list.xml
            list_settings           menu_list_settings      list_settings.xml
            view_list               menu_view_list          list_view.xml
            add_task                menu_back               add_task.xml
            view_task               menu_back               task_view.xml
            edit_task               menu_back               edit_task.xml
            add_category_main       menu_back               add_category_main.xml
            add_category_list       menu_back               add_category_list.xml
            view_category_main      menu_back               categories_view.xml
            view_category_list      menu_back               view_category.xml
            edit_category_main      menu_back               edit_category_main.xml
            edit_category_list      menu_back               edit_category_list.xml
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

    public void addList(List newList){
        dm.insertList(newList.getName());
        loadData();
    }

    public void loadData(){
        Cursor cursor;

        // need to know which list we're viewing the tasks of
        String currentListId = currentList.getId();
        String currentListTaskSort = currentList.getTaskSort();

        // determine what data to load based on what we're viewing now
        switch (currentPage){
            case("view_all_lists"):
                cursor = dm.selectAllLists();
                int listCount = cursor.getCount();
                listOfLists.clear();
                if(listCount > 0){
                    while(cursor.moveToNext()){
                        String id = cursor.getString(1);
                        String name = cursor.getString(2);
                        String taskSort = cursor.getString(3);
                        String taskCompleteHandle = cursor.getString(4);
                        String backgroundColor = cursor.getString(5);
                        List list = new List(id, name, taskSort, taskCompleteHandle, backgroundColor);
                        listOfLists.add(list);
                    }
                    listAdapter.notifyDataSetChanged();
                }
                break;
            case("view_list"):
                // get that list's tasks
                cursor = dm.selectListTasks(currentListId, currentListTaskSort);
                int taskCount = cursor.getCount();
                listOfTasks.clear();
                if(taskCount > 0){
                    while(cursor.moveToNext()){
                        String id = cursor.getString(1);
                        String name = cursor.getString(2);
                        String dueDate = cursor.getString(3);
                        String dueTime = cursor.getString(4);
                        String priority = cursor.getString(5);
                        String category = cursor.getString(6);
                        String parentTask = cursor.getString(7);
                        String completed = cursor.getString(8);
                        String notes = cursor.getString(9);
                        String listID = cursor.getString(10);
                        Task task = new Task(id, name, dueDate, dueTime, priority, category,
                                parentTask, completed, notes, listID);
                        listOfTasks.add(task);
                    }
                    taskAdapter.notifyDataSetChanged();
                }
                break;
            case("view_category_list"):
                // get that list's categories
                cursor = dm.selectListCategories(currentListId);
                int categoryCount = cursor.getCount();
                listOfCategories.clear();
                if(categoryCount > 0){
                    while(cursor.moveToNext()){
                        String id = cursor.getString(1);
                        String name = cursor.getString(2);
                        String defaultCategory = cursor.getString(3);
                        String font = cursor.getString(4);
                        String textColor = cursor.getString(5);
                        String backgroundColor = cursor.getString(6);
                        String listID = cursor.getString(7);
                        Category category = new Category(id, name, defaultCategory, font, textColor,
                                backgroundColor, listID);
                        listOfCategories.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
                break;
        }

    }



    public void onResume () {
        super.onResume();
    }
}