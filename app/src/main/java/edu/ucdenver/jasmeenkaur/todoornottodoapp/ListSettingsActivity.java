package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.ListSettingsBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ListSettingsActivity extends AppCompatActivity{
    private AppBarConfiguration appBarConfiguration;
    private ListSettingsBinding binding;
    private DataManager dm;
    public static Activity activity;
    private List displayList;
    private String listID;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        dm = new DataManager(this);
        activity = this;
        super.onCreate(savedInstanceState);
        binding = ListSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        listID = getIntent().getStringExtra("List ID");
        if(listID != "0"){
            Cursor cursor = dm.selectList(listID);
            int listCount = cursor.getCount();
            Log.i("info", "Number of lists to display: " + listCount);
            while(cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String taskSort = cursor.getString(2);
                String taskCompleteHandle = cursor.getString(3);
                String backgroundColor = cursor.getString(4);
                Log.i("info", "Current List Info:" + id + ", " + name + ", " + taskSort
                        + ", " + taskCompleteHandle + ", " + backgroundColor);
                displayList = new List(id, name, taskSort, taskCompleteHandle, backgroundColor);
            }
        }
        else{
            String errorMessage = "ERROR: Could not load list information";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, errorMessage, duration);
            toast.show();
        }
        setSupportActionBar(binding.toolbarListSettings);
        binding.buttonInputBCLs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("info", "Showing color wheel background list settings");
                        Intent ColorWheelIntent = new Intent(ListSettingsActivity.this, ColorWheelActivity.class);
                        ColorWheelIntent.putExtra("List ID", listID);
                        startActivity(ColorWheelIntent);
                    }
                }
        );
        binding.buttonDeleteListLS.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteList(listID);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_settings, menu);
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
        else if (id == R.id.action_save) {
            String name = binding.textInputNameLs.getText().toString();
            Log.i("info", "list to edit information in edit  list: \n\tName: " + name);
            if(name == null || name.equals("")){
                Log.i("info", "NAME IS NULL");
                name = displayList.getName();
            }
            dm.updateListName(listID, name);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void deleteList(@NonNull String listId){
        if(listId== null){
            Log.i("info", "THE LIST TO DELETE IS NULL (ViewListActivity");
        }
        Log.i("info", "VIEW TASK: the list to delete is " + listId);
        dm.deleteList(listId);
        finish();
    }

    public void onResume () {

        super.onResume();
    }
}

