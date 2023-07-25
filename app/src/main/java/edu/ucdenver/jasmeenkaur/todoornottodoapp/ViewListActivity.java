package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.ListViewBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ViewListActivity extends AppCompatActivity{
    private AppBarConfiguration appBarConfiguration;
    private ListViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ListViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

}
