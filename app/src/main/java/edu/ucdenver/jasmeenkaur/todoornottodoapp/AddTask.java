package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.AddTaskBinding;

public class AddTask extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private AddTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = AddTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolbarAddTask.inflateMenu(R.menu.menu_back);

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
                        //clear scroll bar
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
                        //figure out scroll bar, parentTask,completed, listId
                        String notes=binding.textInputNotesEt.getText().toString();
                        //Task task = new Task(name,dueDate,dueTime,priority,notes);

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.addTask(task);
                        dismiss();
                    }
                }
        );



    }
}
