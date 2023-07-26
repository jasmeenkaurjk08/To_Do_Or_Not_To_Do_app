package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.ui.AppBarConfiguration;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.DialogAddTaskBinding;

public class AddTaskActivity extends DialogFragment {
    private AppBarConfiguration appBarConfiguration;
    private DialogAddTaskBinding binding;
    private ViewListActivity viewListActivity;
    private String listID;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        binding = DialogAddTaskBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        binding.toolbarAddTask.inflateMenu(R.menu.menu_back);
        listID = getIntent().getStringExtra("List ID");

        binding.toolbarAddTask.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.action_back) {
                            finish();
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
                        //need id and list id
                        Task task = new Task("-1",name,dueDate,dueTime,priority,completed,notes, listID);
                        //MainActivity mainActivity = (MainActivity) getActivity();
                        viewListActivity.addTask(task);
                        finish();
                    }
                }
        );
    }

    public AddTaskActivity(ViewListActivity viewListActivity) {
        this.viewListActivity = viewListActivity;

    }

}
