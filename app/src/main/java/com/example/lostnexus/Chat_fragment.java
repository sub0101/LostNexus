package com.example.lostnexus;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostnexus.adapters.ChatListAdapter;
import com.example.lostnexus.databinding.FragmentChatBinding;
import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.models.UserProfile;
import java.util.ArrayList;
import java.util.List;

import com.example.lostnexus.kotlin.ChatViewModel;
public class Chat_fragment extends Fragment implements  ItemClickListener{

    ChatListAdapter adapter;
    FragmentChatBinding binding;
   List<UserProfile> users ;
    ChatViewModel viewModel =  new ChatViewModel();
//    MainViewModel viewModel;
    public Chat_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        users =  new ArrayList<>();
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_chat, container,false);
        viewModel =  new ViewModelProvider(this).get(ChatViewModel.class);
        return binding.getRoot();
    }

    public void setAdapter(){
        adapter =  new ChatListAdapter(users);
        RecyclerView recyclerView =  binding.recyclerview;
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void getChattingList(){


        viewModel.getchatUsers().observe(this, new Observer<List<UserProfile>>() {
            @Override
            public void onChanged(List<UserProfile> userProfiles) {
                Log.e("error" , String.valueOf(userProfiles.size()));
                for(UserProfile profile:userProfiles){

UserProfile profile1 = profile;
users.add(profile1);
                }
                adapter.notifyDataSetChanged();
            }
        });



         }

    @Override
    public void onStart() {
        super.onStart();
        getChattingList();
        setAdapter();

    }

    @Override
    public void onClick(View view, FoundItem item) {

    }

    @Override
    public void onClick(View view, UserProfile userProfile) {
Intent intent =  new Intent(getContext(), ConversationActivity.class);
        intent.putExtra("userProfile" , userProfile);
        System.out.println(userProfile.getName());
startActivity(intent);
    }

    @Override
    public void onButtonClick(int possition) {

    }
}