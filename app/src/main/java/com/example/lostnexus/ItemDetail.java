package com.example.lostnexus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lostnexus.databinding.ActivityItemDetailBinding;
import com.example.lostnexus.databinding.ActivityItemDetailBindingImpl;
import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.viewmodels.FoundItemViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemDetail extends AppCompatActivity {

    ActivityItemDetailBinding binding;
    FoundItemViewModel viewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_item_detail );
        FoundItem item  =  getIntent().getParcelableExtra("item" );

//        if(item.isclaimed) binding.claim.setEnabled(false);
        viewModel =  new ViewModelProvider(this).get(FoundItemViewModel.class);
        binding.setItemdetail(item);

        System.out.println(item.getDetail());
        binding.claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

viewModel.addNotification(item);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.observeShowDialogEvent(this, new Observer<String>() {
            @Override
            public void onChanged(String st) {
                Toast.makeText(getBaseContext() , st , Toast.LENGTH_SHORT).show();
            }
        });    }
}