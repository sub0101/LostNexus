package com.example.lostnexus;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lostnexus.databinding.FragmentNotificationBinding;
import com.example.lostnexus.viewmodels.FoundItemViewModel;

import java.util.List;


public class NotificationFragment extends Fragment {

FragmentNotificationBinding binding;
List<Notification> notificationList;
FoundItemViewModel viewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       viewModel = new ViewModelProvider(this).get(FoundItemViewModel.class);
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_notification, container,false);
return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getAllNotifications().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> list) {
                notificationList = list;
                addList();
            }
        });


    }


    private void addList(){
        ListView listView = binding.listItem;
        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationList , getContext());
        listView.setAdapter(notificationAdapter);
    }

}