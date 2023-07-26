package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.ListViewBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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

        taskAdapter = new TaskAdapter(this, listOfTasks);
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
        binding.buttonAddTaskLv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("info", "Showing color wheel background list settings");
                        Intent AddTaskIntent = new Intent(ViewListActivity.this, AddTaskActivity.class);
                        startActivity(AddTaskIntent);
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
        String listTaskSort = displayList.getTaskSort();
        // get that list's tasks
        Cursor cursor = dm.selectListTasks(listId, listTaskSort);
        int taskCount = cursor.getCount();
        listOfTasks.clear();
        if (taskCount > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String dueDate = cursor.getString(2);
                String dueTime = cursor.getString(3);
                String priority = cursor.getString(4);
                String completed = cursor.getString(5);
                String notes = cursor.getString(6);
                String listID = cursor.getString(7);
                Task task = new Task(id, name, dueDate, dueTime, priority, completed, notes, listID);
                listOfTasks.add(task);
            }
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void addTask(Task task){
        dm.insertTask(task.getName(), task.getDueTime(), task.getDueDate(), task.getPriority(),
                task.getNotes(), task.getListID());
        loadData();
    }

    public void openNewTask(){
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra("List ID", displayList.getId());
        startActivity(intent);
    }

    public void onResume () {
        super.onResume();
    }
}
