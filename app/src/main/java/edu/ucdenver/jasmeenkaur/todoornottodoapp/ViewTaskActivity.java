package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private String taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = TaskViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbarTaskView);

        taskID = getIntent().getStringExtra("Task ID");
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

        binding.buttonDeleteTaskEt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTask(displayTask);
                        finish();
                    }
                }
        );

        binding.checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
                        if(checked){
                            // checkbox got checked
                            dm.updateTaskComplete(displayTask.getId(), "true");
                        }
                        else {
                            // checkbox got unchecked
                            dm.updateTaskComplete(displayTask.getId(), "false");
                        }
                    }
                }
        );


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
            EditTaskActivity editTask = new EditTaskActivity(this);
            editTask.show(getSupportFragmentManager(), "");
        }
        loadData();
        return super.onOptionsItemSelected(item);
    }

    public String getDisplayTaskID(){
        return displayTask.getId();
    }

    public Task taskToEdit(){
        return displayTask;
    }

    public void updateTask(@NonNull Task task){
        if(task == null){
            Log.i("info", "THE CREATED TASK IS NULL (ViewListActivity");
        }
        Log.i("info", "Display Task ID: " + displayTask.getId());
        dm.updateTask(displayTask.getId(), task.getName(), task.getDueDate(), task.getDueTime(), task.getPriority(),
                task.getNotes());
        //(String id, String name, String dueDate, String dueTime, String priority, String notes)
        loadData();
    }


    public void deleteTask(@NonNull Task task){
        if(task == null){
            Log.i("info", "THE TASK TO DELETE IS NULL (ViewListActivity");
        }
        Log.i("info", "VIEW TASK: the task to delete is " + task.getId());
        dm.deleteTask(task.getId());
    }




    public void loadData(){
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


    public void onResume () {
        loadData();
        super.onResume();
    }
}
