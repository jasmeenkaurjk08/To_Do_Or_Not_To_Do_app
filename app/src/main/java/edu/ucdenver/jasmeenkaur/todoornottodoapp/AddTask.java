package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
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
    }
}
