package com.example.lostnexus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnexus.databinding.FragmentHomeScreenBinding;
import com.example.lostnexus.databinding.ItemCardBinding;
import com.example.lostnexus.models.LostItem;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

//    FragmentHomeScreenBinding homeScreenBinding;
public static List<LostItem> lostItemList;
static Context context;
ItemCardBinding itemCardBinding;
public ItemListAdapter(Context context , List<LostItem> lostItemList){
    this.context =context;
    this.lostItemList =  lostItemList;
    System.out.println(context);

}

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_card , parent , false);
System.out.println(itemCardBinding+"what is this");
        return new ItemViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//     LostItem lostItem  = lostItemList.get(position);
//        holder.itemCardBinding.setItemdata(lostItem);
    }

    @Override
    public int getItemCount() {
        if(lostItemList.size()>6) return 6;
        else return lostItemList.size();
//        return 100;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

    private ItemCardBinding itemCardBinding;
        public ItemViewHolder(@NonNull ItemCardBinding itemView) {
            super(itemView.getRoot());
            System.out.println(itemView);
            itemCardBinding  = itemView;
        }
    }

}
