package com.example.lostnexus.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnexus.R;
import com.example.lostnexus.models.Message;
import com.example.lostnexus.databinding.ItemRecieveBinding;
import com.example.lostnexus.databinding.ItemSentBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ConversationAdapter extends RecyclerView.Adapter {

ItemRecieveBinding recieveBinding;
ArrayList<Message> messageArrayList;
ItemSentBinding sentBinding;

public ConversationAdapter(ArrayList<Message> messageArrayList ){
System.out.println(messageArrayList.size()+"hai");
    this.messageArrayList = messageArrayList;
}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    System.out.println("this is created view holder");
        if(viewType == 1){
            sentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_sent , parent , false);
        return  new SentViewHolder(sentBinding);
        }
        else {
            recieveBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_recieve , parent , false);
            return  new RecieverViewHolder(recieveBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


if(holder.getClass() == SentViewHolder.class){
    sentBinding.setMeessage(messageArrayList.get(position));
//    sentBinding.messageSent.setText(messageArrayList.get(position).getMessage());

}
else     recieveBinding.setMeessage(messageArrayList.get(position));

// else       {
//
//        }
    }

    @Override
    public int getItemCount() {
    System.out.println(messageArrayList.size());
        return messageArrayList.size();
    }

    class SentViewHolder extends  RecyclerView.ViewHolder
    {
        ItemSentBinding binding;
        public SentViewHolder(@NonNull ItemSentBinding binding) {
            super(binding.getRoot());
            this.binding =  binding;
        }
    }
    class RecieverViewHolder extends  RecyclerView.ViewHolder
    {
        ItemRecieveBinding binding;
        public RecieverViewHolder(@NonNull ItemRecieveBinding binding) {
            super(binding.getRoot());
            this.binding =  binding;

        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message =  messageArrayList.get(position);
        if(message.getSenderId().equals(FirebaseAuth.getInstance().getUid())) return 1;
        else return  2;
//        return super.getItemViewType(position);
    }
}
