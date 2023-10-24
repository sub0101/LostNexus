package com.example.lostnexus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnexus.databinding.ItemCardBinding;
import com.example.lostnexus.models.FoundItem;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

//    FragmentHomeScreenBinding homeScreenBinding;
public static List<FoundItem> lostItemList;
static Context context;
ItemCardBinding itemCardBinding;
public ItemListAdapter(Context context , List<FoundItem> lostItemList){
    this.context =context;
    this.lostItemList =  lostItemList;
    System.out.println(context);

}

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_card , parent , false);
        return new ItemViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
     FoundItem lostItem  = lostItemList.get(position);
        holder.itemCardBinding.setItemdata(lostItem);
        holder.itemCardBinding.carView.setOnClickListener(new MonCLickListner());

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
            itemCardBinding  = itemView;


        }


    }
    public class MonCLickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            System.out.println("clicked card");
        }
    }


}
