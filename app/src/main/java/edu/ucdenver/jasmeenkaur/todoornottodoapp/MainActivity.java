package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.content.Context;
import android.content.Intent;
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
        binding.buttonNewListMain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddList addList = new AddList();
                        addList.show(getSupportFragmentManager(), "");
                    }
                }
        );

        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentPage == "list_settings"){
            getMenuInflater().inflate(R.menu.menu_list_settings, menu);
        }
        else if(currentPage == "view_list"){
            getMenuInflater().inflate(R.menu.menu_view_list, menu);
        }
        else if (currentPage != "view_all_lists"){
            getMenuInflater().inflate(R.menu.menu_back, menu);
        }
        return true;
    }

    public void addList(List list){
        dm.insertList(list.getName());
        loadData();
    }
    public void addTask(Task task){
        dm.insertList(task.getName());
        loadData();
    }

    public void showList(int position){
        String listToDisplayID = listOfLists.get(position).getId();
        Log.i("info", "List to display ID (in main): " + listToDisplayID);
        Intent viewListIntent = new Intent(this, ViewListActivity.class);
        viewListIntent.putExtra("List ID", listToDisplayID);
        startActivity(viewListIntent);

    }

    public void loadData(){
        Cursor cursor;

        // need to know which list we're viewing the tasks of
        String currentListId;
        String currentListTaskSort;

        // determine what data to load based on what we're viewing now
        if (currentPage == "view_all_lists") {
            cursor = dm.selectAllLists();
            int listCount = cursor.getCount();
            Log.i("info", "Number of lists to display: " + listCount);
            listOfLists.clear();
            if (listCount > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String taskSort = cursor.getString(2);
                    String taskCompleteHandle = cursor.getString(3);
                    String backgroundColor = cursor.getString(4);
                    Log.i("info", "Current List Info:" + id + ", " + name + ", " + taskSort
                            + ", " + taskCompleteHandle + ", " + backgroundColor);
                    List list = new List(id, name, taskSort, taskCompleteHandle, backgroundColor);
                    listOfLists.add(list);
                }
                listAdapter.notifyDataSetChanged();
            }
            Log.i("info", "Current List Info:" + listOfLists);
        }
        else if (currentPage == "view_list") {
            // need to know which list we're viewing the tasks of
            currentListId = currentList.getId();
            currentListTaskSort = currentList.getTaskSort();
            // get that list's tasks
            cursor = dm.selectListTasks(currentListId, currentListTaskSort);
            int taskCount = cursor.getCount();
            listOfTasks.clear();
            if (taskCount > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String dueDate = cursor.getString(2);
                    String dueTime = cursor.getString(3);
                    String priority = cursor.getString(4);
                    String category = cursor.getString(5);
                    String parentTask = cursor.getString(6);
                    String completed = cursor.getString(7);
                    String notes = cursor.getString(8);
                    String listID = cursor.getString(9);
                    Task task = new Task(id, name, dueDate, dueTime, priority, category,
                            parentTask, completed, notes, listID);
                    listOfTasks.add(task);
                }
                taskAdapter.notifyDataSetChanged();
            }
        }
        else if(currentPage == "view_category_list") {
            // need to know which list we're viewing the tasks of
            currentListId = currentList.getId();
            // get that list's categories
            cursor = dm.selectListCategories(currentListId);
            int listCategoryCount = cursor.getCount();
            listOfCategories.clear();
            if (listCategoryCount > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String defaultCategory = cursor.getString(2);
                    String font = cursor.getString(3);
                    String textColor = cursor.getString(4);
                    String backgroundColor = cursor.getString(5);
                    String listID = cursor.getString(6);
                    Category category = new Category(id, name, defaultCategory, font, textColor,
                            backgroundColor, listID);
                    listOfCategories.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }
        }
        else if(currentPage == "view_category_main"){
            // get that the default categories
            cursor = dm.selectDefaultCategories();
            int mainCategoryCount = cursor.getCount();
            listOfCategories.clear();
            if(mainCategoryCount > 0){
                while(cursor.moveToNext()){
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String defaultCategory = cursor.getString(2);
                    String font = cursor.getString(3);
                    String textColor = cursor.getString(4);
                    String backgroundColor = cursor.getString(5);
                    String listID = cursor.getString(6);
                    Category category = new Category(id, name, defaultCategory, font, textColor,
                        backgroundColor, listID);
                    listOfCategories.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }
        }

    }



    public void onResume () {
        super.onResume();
        loadData();
    }
}