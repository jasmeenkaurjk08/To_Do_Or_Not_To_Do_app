package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import edu.ucdenver.jasmeenkaur.todoornottodoapp.databinding.AddListBinding;

public class AddList extends DialogFragment {
    private AddListBinding binding;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        binding = AddListBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());
        binding.toolbarAddList.inflateMenu(R.menu.menu_back);

        binding.toolbarAddList.setOnMenuItemClickListener(
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

        binding.buttonClearAd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.textInputListTitleAd.setText("");
                        binding.textInputListTitleAd.requestFocus();
                    }
                }
        );

        binding.buttonSaveAd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = binding.textInputListTitleAd.getText().toString();

                        //List list = new List(name);

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.addList(name);
                        dismiss();
                    }
                }
        );


        return builder.create();

    }
}
