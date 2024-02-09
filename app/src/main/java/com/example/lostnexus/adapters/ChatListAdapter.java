package com.example.lostnexus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnexus.ItemClickListener;
import com.example.lostnexus.R;
import com.example.lostnexus.databinding.LayoutMsglistBinding;
import com.example.lostnexus.models.UserProfile;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MViewHolder> {
//FragmentChatBinding binding;
LayoutMsglistBinding binding;
List<UserProfile> users;
ItemClickListener itemClickListener;
public ChatListAdapter(List<UserProfile> users){
    this.users  = users;
}

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.layout_msglist , parent , false);
return new MViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.MViewHolder holder, int position) {
    System.out.println(users.get(position).getEmail());
    binding.setUserProfile(users.get(position));
//holder.binding.username.setText(users.get(position).getName());
    }

    @Override
    public int getItemCount() {

    System.out.println(users.size());
    return users.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
       LayoutMsglistBinding binding;

        public MViewHolder(LayoutMsglistBinding binding){
            super(binding.getRoot());
            this.binding =  binding;
binding.card.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
          itemClickListener.onClick(view , users.get(getAdapterPosition()));
        }
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
