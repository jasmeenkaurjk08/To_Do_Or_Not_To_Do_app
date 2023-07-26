package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.ListViewBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewListActivity extends AppCompatActivity{
    private AppBarConfiguration appBarConfiguration;
    private ListViewBinding binding;
    private DataManager dm;
    private ArrayList<Task> listOfTasks;
    private List displayList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ListViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbarListView);

        listOfTasks = new ArrayList<Task>();
        taskAdapter = new TaskAdapter(this, listOfTasks);

        // set up recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        binding.contentListView.recyclerViewList.setLayoutManager(layoutManager);
        binding.contentListView.recyclerViewList.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.contentListView.recyclerViewList.setAdapter(taskAdapter);

        dm = new DataManager(this);

        String listID = getIntent().getStringExtra("List ID");
        Log.i("info", "List to display ID: " + listID);

        if(listID != "0"){
            Cursor cursor = dm.selectList(listID);
            int listCount = cursor.getCount();
            Log.i("info", "Number of lists to display: " + listCount);
            while(cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String taskSort = cursor.getString(2);
                String taskCompleteHandle = cursor.getString(3);
                String backgroundColor = cursor.getString(4);
                Log.i("info", "Current List Info:" + id + ", " + name + ", " + taskSort
                        + ", " + taskCompleteHandle + ", " + backgroundColor);
                displayList = new List(id, name, taskSort, taskCompleteHandle, backgroundColor);
            }
        }
        else{
            String errorMessage = "ERROR: Could not load list information";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, errorMessage, duration);
            toast.show();
        }

        binding.textViewListTitle.setText(displayList.getName());
        //binding.viewListLayout.setBackgroundColor(Integer.parseInt(displayList.getBackgroundColor()));
        binding.buttonAddTaskLv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("info", "Showing color wheel background list settings");

                        AddTask addTaskDialog = new AddTask();
                        addTaskDialog.show(getSupportFragmentManager(), "");

                    }
                }
        );
        /*
        binding.buttonAddTaskLv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //AppCompatActivity activity= (AppCompatActivity) view.getContext();
                        //AddTaskActivity addTaskActivity= new AddTaskActivity(ViewListActivity.this);
                        //startActivity(addTaskActivity);
                        Intent addTaskIntent = new Intent(ViewListActivity.this, AddTaskActivity.class);
                        addTaskIntent.putExtra("List ID", displayList.getId());
                        //startActivity(addTaskIntent);
                        ViewListActivity.this.startActivity(addTaskIntent);
                    }
                }
        );
           */
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // return to view all list
        if (id == R.id.action_all_lists) {
            finish();
        }
        else if (id == R.id.action_list_settings) {
            Intent listSettingsIntent = new Intent(this, ListSettingsActivity.class);
            listSettingsIntent.putExtra("List ID", displayList.getId());
            startActivity(listSettingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public String getDisplayListID(){
        return displayList.getId();
    }

    public void loadData(){
        // need to know which list we're viewing the tasks of
        String listId = displayList.getId();
        // get all of that list's information
        Cursor cursorList = dm.selectList(listId);
        int listCount = cursorList.getCount();
        if(listCount > 0){
            while (cursorList.moveToNext()) {
                String name = cursorList.getString(1);
                String taskSort = cursorList.getString(2);
                String taskCompleteHandle = cursorList.getString(3);
                String backgroundColor = cursorList.getString(4);
                displayList = new List(listId, name, taskSort, taskCompleteHandle, backgroundColor);
            }
        }
        String listTaskSort = displayList.getTaskSort();
        // get that list's tasks
        Cursor cursorTasks = dm.selectListTasks(listId);
        if(cursorTasks != null) {
            Log.i("info", "cursorTasks != null");
            int taskCount = cursorTasks.getCount();
            Log.i("info", "Number of tasks to display: " + taskCount);
            listOfTasks.clear();
            if (taskCount > 0) {
                while (cursorTasks.moveToNext()) {
                    String id = cursorTasks.getString(0);
                    String name = cursorTasks.getString(1);
                    String dueDate = cursorTasks.getString(2);
                    String dueTime = cursorTasks.getString(3);
                    String priority = cursorTasks.getString(4);
                    String completed = cursorTasks.getString(5);
                    String notes = cursorTasks.getString(6);
                    String listID = cursorTasks.getString(7);
                    Log.i("info", "Task to be displayed in ViewListActivity: \n\tName: "
                            + name + "\n\tDue Date: " + dueDate + "\n\tDue Time: " + dueTime +
                            "\n\tPriority: " + priority + "\n\tCompleted: " + completed +
                            "\n\tNotes: " + notes + "\n\tList ID: " + listID);
                    Task task = new Task(id, name, dueDate, dueTime, priority, completed, notes, listID);
                    listOfTasks.add(task);
                }
                taskAdapter.notifyDataSetChanged();
            }
        }
        //binding.viewListLayout.setBackgroundColor(Integer.parseInt(displayList.getBackgroundColor()));
    }

    public void addTask(@NonNull Task task){
        if(task == null){
            Log.i("info", "THE CREATED TASK IS NULL (ViewListActivity");
        }
        Log.i("info", "Display List ID: " + displayList.getId());
        dm.insertTask(task.getName(), task.getDueTime(), task.getDueDate(), task.getPriority(),
                task.getNotes(), displayList.getId());
        loadData();
    }

    public void showTask(int position){
        String taskToDisplayID = listOfTasks.get(position).getId();
        Log.i("info", "Task to display ID (in viewListActivity): " + taskToDisplayID);
        Intent viewTaskIntent = new Intent(this, ViewTaskActivity.class);
        viewTaskIntent.putExtra("Task ID", taskToDisplayID);
        startActivity(viewTaskIntent);

    }

    public void onResume () {
        super.onResume();
    }

}
