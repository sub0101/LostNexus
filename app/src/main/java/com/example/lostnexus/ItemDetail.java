package com.example.lostnexus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
}