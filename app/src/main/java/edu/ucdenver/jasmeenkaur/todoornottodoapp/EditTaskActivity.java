package edu.ucdenver.jasmeenkaur.todoornottodoapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.EditTaskBinding;

public class EditTaskActivity extends DialogFragment {
    //private AppBarConfiguration appBarConfiguration;
    private EditTaskBinding binding;
    private DataManager dm;
    //public static Activity activity;
    //private ViewListActivity viewListActivity;
    private String listID;
    private ViewTaskActivity viewTaskActivity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //activity = this;
        binding = EditTaskBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        binding.toolbarAddTask.inflateMenu(R.menu.menu_back);
        //listID = "-1";
        String taskID = viewTaskActivity.getDisplayTaskID();
        Log.i("info", "Task to display ID: " + taskID);
        viewTaskActivity=new ViewTaskActivity;
        dm = new DataManager (this);

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

        binding.textInputNameEt.setHint(displayTask.getName());
        binding.textInputDueDateEt.setHint(displayTask.getDueDate());
        binding.textInputDueTimeEt.setHint(displayTask.getDueTime());
        binding.textInputPriorityEt.setHint(displayTask.getPriority());
        binding.textInputNotesEt.setHint(displayTask.getNotes());

        binding.toolbarAddTask.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.action_back) {
                            dismiss();
                        }

                        return false;
                    }
                }
        );

        binding.buttonClearEt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.textInputNameEt.setText("");
                        binding.textInputDueDateEt.setText("");
                        binding.textInputDueTimeEt.setText("");
                        binding.textInputPriorityEt.setText("");
                        binding.textInputNotesEt.setText("");
                        binding.textInputNameEt.requestFocus();
                    }
                }
        );

        binding.buttonSaveEt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = binding.textInputNameEt.getText().toString();
                        String dueDate=binding.textInputDueDateEt.getText().toString();
                        String dueTime=binding.textInputDueTimeEt.getText().toString();
                        String priority=binding.textInputPriorityEt.getText().toString();
                        String completed="false";
                        String notes=binding.textInputNotesEt.getText().toString();
                        Log.i("info", "Task to add information in Add Task Dialog: \n\tName: "
                                + name + "\n\tDue Date: " + dueDate + "\n\tDue Time: " + dueTime +
                                "\n\tPriority: " + priority + "\n\tCompleted: " + completed +
                                "\n\tNotes: " + notes + "\n\tList ID: " + listID);
                        // check if any are null
                        if(name == null){
                            Log.i("info", "NAME IS NULL");
                            name = "";
                        }
                        if(dueDate == null){
                            Log.i("info", "DUE DATE IS NULL");
                            dueDate = "";
                        }
                        if(dueTime == null){
                            Log.i("info", "DUE TIME IS NULL");
                            dueTime = "";
                        }
                        if(priority == null){
                            Log.i("info", "PRIORITY IS NULL");
                            priority = "";
                        }
                        if(notes == null){
                            Log.i("info", "NOTES IS NULL");
                            notes = "";
                        }


                        //need id and list id
                        Task task = new Task(listID,name,dueDate,dueTime,priority,completed, notes, listID);
                        //MainActivity mainActivity = (MainActivity) getActivity();
                        ViewListActivity viewListActivity = (ViewListActivity) getActivity();
                        if(task == null){
                            Log.i("info", "THE CREATED TASK IS NULL");
                        }
                        viewListActivity.addTask(task);
                        dismiss();
                    }
                }
        );
        return builder.create();
    }


}



