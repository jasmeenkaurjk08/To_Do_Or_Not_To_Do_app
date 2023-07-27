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

import java.util.ArrayList;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.EditTaskBinding;

public class EditTaskActivity extends DialogFragment {
    //private AppBarConfiguration appBarConfiguration;
    private EditTaskBinding binding;
    private Task displayTask;
    //public static Activity activity;
    //private ViewListActivity viewListActivity;
    private String listID;
    private ViewTaskActivity viewTaskActivity;

    public EditTaskActivity(ViewTaskActivity viewTaskActivity) {
        this.viewTaskActivity = viewTaskActivity;

    }
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
        //dm = new DataManager (this);

        displayTask = viewTaskActivity.taskToEdit();

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
                        if(name == null || name.equals("")){
                            Log.i("info", "NAME IS NULL");
                            name = displayTask.getName();
                        }
                        if(dueDate == null || dueDate.equals("")){
                            Log.i("info", "DUE DATE IS NULL");
                            dueDate = displayTask.getDueDate();
                        }
                        if(dueTime == null || dueTime.equals("")){
                            Log.i("info", "DUE TIME IS NULL");
                            dueTime = displayTask.getDueTime();
                        }
                        if(priority == null || priority.equals("")){
                            Log.i("info", "PRIORITY IS NULL");
                            priority = displayTask.getPriority();
                        }
                        if(notes == null || notes.equals("")){
                            Log.i("info", "NOTES IS NULL");
                            notes = displayTask.getNotes();
                        }


                        //need id and list id
                        Task task = new Task(listID,name,dueDate,dueTime,priority,completed, notes, listID);
                        //MainActivity mainActivity = (MainActivity) getActivity();
                        viewTaskActivity.updateTask(task);
                        dismiss();
                    }
                }
        );

        return builder.create();
    }


}



