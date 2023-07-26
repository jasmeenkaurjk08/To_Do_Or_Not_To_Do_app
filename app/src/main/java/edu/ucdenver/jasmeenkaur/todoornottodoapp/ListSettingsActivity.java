package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.ListSettingsBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ListSettingsActivity extends AppCompatActivity{
    private AppBarConfiguration appBarConfiguration;
    private ListSettingsBinding binding;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        activity = this;
        super.onCreate(savedInstanceState);
        binding = ListSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String listID = getIntent().getStringExtra("List ID");

        setSupportActionBar(binding.toolbarListSettings);
        binding.radioButtonDueDateLs.setChecked(true);
        binding.radioButtonShowLs.setChecked(true);
        binding.buttonInputBCLs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("info", "Showing color wheel background list settings");
                        Intent ColorWheelIntent = new Intent(ListSettingsActivity.this, ColorWheelActivity.class);
                        ColorWheelIntent.putExtra("List ID", listID);
                        startActivity(ColorWheelIntent);
                        //LinearLayout lay = (LinearLayout) findViewById(R.id.linearLIstSettings);
                        //ColorDrawable viewColor = (ColorDrawable) lay.getBackground();
                        //int colorId = viewColor.getColor();
                        //Activity viewListActivity = ViewListActivity.viewListActivity;
                        //viewListActivity.backgroundColor(colorId);
                        //LinearLayout listViewing = (LinearLayout) viewListActivity.findViewById(R.id.viewListViewLayout);
                        //listViewing.setBackgroundColor(colorId);
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
            //Intent i=new Intent(this, ViewListActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(i);
        }
        else if (id == R.id.action_save) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume () {
        super.onResume();
    }
}

