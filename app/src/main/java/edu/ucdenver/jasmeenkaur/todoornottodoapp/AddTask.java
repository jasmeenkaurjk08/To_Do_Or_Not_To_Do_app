package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import androidx.navigation.ui.AppBarConfiguration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.AddTaskBinding;



public class AddTask extends DialogFragment {
    //private AppBarConfiguration appBarConfiguration;
    private AddTaskBinding binding;
    //private ViewListActivity viewListActivity;
    private String listID;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        binding = AddTaskBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        binding.toolbarAddTask.inflateMenu(R.menu.menu_back);
        listID = "-1";

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
                        binding.textInputNameAt.setText("");
                        binding.textInputDueDateEt.setText("");
                        binding.textInputDueTimeEt.setText("");
                        binding.textInputPriorityEt.setText("");
                        binding.textInputNotesEt.setText("");
                        binding.textInputNameAt.requestFocus();
                    }
                }
        );

        binding.buttonSaveEt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = binding.textInputNameAt.getText().toString();
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


