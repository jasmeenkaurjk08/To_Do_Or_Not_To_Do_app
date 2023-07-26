package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.TaskViewBinding;

public class ViewTaskActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private TaskViewBinding binding;
    private DataManager dm;
    private ArrayList<Task> listOfTasks;
    private Task displayTask;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = TaskViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbarTaskView);

        String taskID = getIntent().getStringExtra("Task ID");
        Log.i("info", "Task to display ID: " + taskID);

        dm = new DataManager(this);

        if(taskID != "0"){
            Cursor cursor = dm.selectTask(taskID);
            int listCount = cursor.getCount();
            Log.i("info", "Number of lists to display: " + listCount);
            //String id, String name, String dueDate, String dueTime, String priority,
                    //String completed, String notes, String listID
            while(cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String dueDate = cursor.getString(2);
                String dueTime = cursor.getString(3);
                String priority = cursor.getString(4);
                String completed = cursor.getString(5);
                String notes = cursor.getString(6);
                String listID= cursor.getString(7);
                Log.i("info", "Current Task Info:" + id + ", " + name + ", " + dueDate
                        + ", " + dueTime + ", " + priority+ ", " + completed + ", " + notes);
                displayTask = new Task(id, name, dueDate, dueTime , priority, completed, notes, listID);
            }
        }
        else{
            String errorMessage = "ERROR: Could not load task information";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, errorMessage, duration);
            toast.show();
        }

        binding.textViewTaskNameTv.setText(displayTask.getName());
        binding.textViewTaskDueDateTv.setText(displayTask.getDueDate());
        binding.textViewTaskDueTimeTv.setText(displayTask.getDueTime());
        binding.textViewTaskPriorityTv.setText(displayTask.getPriority());
        binding.checkBox.setChecked(Boolean.valueOf(displayTask.getCompleted()));
        binding.textViewNotesTv.setText(displayTask.getNotes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // return to view all list
        if (id == R.id.action_back) {
            finish();
        }
        else if (id == R.id.action_edit_task) {
            //Intent EditTaskIntent = new Intent(this, EditTaskActivity.class);
            //startActivity(EditTaskIntent);
            EditTaskActivity editTask = new EditTaskActivity();
            editTask.show(getSupportFragmentManager(), "");
        }

        return super.onOptionsItemSelected(item);
    }

    public String getDisplayTaskID(){
        return displayTask.getId();
    }
 /*
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
*/

    public void onResume () {
        super.onResume();
    }
}
