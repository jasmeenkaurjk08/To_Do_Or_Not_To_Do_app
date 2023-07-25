package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.AddTaskBinding;

public class AddTaskActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private AddTaskBinding binding;
    private ViewListActivity viewListActivity;
    private String listID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = AddTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
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
