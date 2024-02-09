package com.example.lostnexus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

//import com.example.lostnexus.chat.Message;
import com.bumptech.glide.Glide;
import com.example.lostnexus.adapters.ConversationAdapter;
import com.example.lostnexus.databinding.ToolbarChattingBinding;
import com.example.lostnexus.kotlin.ChatViewModel;
import com.example.lostnexus.models.Message;
import com.example.lostnexus.databinding.ActivityConversationBinding;
import com.example.lostnexus.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ConversationActivity extends AppCompatActivity {

    String senderRoom , recieverRoom ;
    ToolbarChattingBinding toolbarChattingBinding;
    String senderId , recieverId;
    ArrayList<Message> messageArrayList =  new ArrayList<>();
    ActivityConversationBinding binding;
    ConversationAdapter adapter;
    ChatViewModel chatViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
binding  = DataBindingUtil.setContentView( ConversationActivity.this , R.layout.activity_conversation );
       chatViewModel =  new ViewModelProvider(this).get(ChatViewModel.class);
setSupportActionBar(binding.toolbar.toolbarChat);
        toolbarChattingBinding = binding.toolbar;

getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("started again");
        UserProfile userProfile = getIntent().getParcelableExtra("userProfile" );
        binding.setProfile(userProfile);
      toolbarChattingBinding.setProfile(userProfile);
        senderId = FirebaseAuth.getInstance().getUid();
recieverId = userProfile.getUuid();
getMessages();
        setAdapter();
        binding.sendBtn.setOnClickListener(view -> {

//
            chatViewModel.sendMessage(binding.messageBox.getText().toString() , recieverId , senderId);

//            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat =  new SimpleDateFormat("dd-MM-yyyy '\n' HH:mm:ss ");
//
//            String str = dateFormat.format(new Date());
//
//            String randomkey =  FirebaseDatabase.getInstance().getReference().push().getKey();
////                Date time = Calendar.getInstance().getTime();
//////Message message =  new Message();
//            Message message = new Message(UUID.randomUUID().toString() , binding.messageBox.getText().toString() , FirebaseAuth.getInstance().getUid() ,str);
//       FirebaseDatabase.getInstance().getReference("Chatting").child(senderRoom).child(randomkey).setValue(message).addOnSuccessListener(unused -> {});
//            FirebaseDatabase.getInstance().getReference("Chatting").child(recieverRoom).child(randomkey).setValue(message).addOnSuccessListener(unused -> {});
////
////                messageArrayList.add(message);
////                adapter.notifyDataSetChanged();
binding.messageBox.setText("");
        });
    }

    public void setAdapter(){
        RecyclerView recyclerView =  binding.recyclerview;
       adapter =  new ConversationAdapter(messageArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void getMessages(){
chatViewModel.loadMessages(recieverId);
        chatViewModel.getMessageListLiveData().observe(this, messages -> {
            System.out.println("data changed of messages");
            for(Message m:messages) messageArrayList.add(m);

           adapter.notifyDataSetChanged();

        });


//        chatViewModel.getMessageListLiveData().observe(this , messages -> {

//        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}