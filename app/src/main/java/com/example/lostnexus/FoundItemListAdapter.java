package com.example.lostnexus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnexus.databinding.Itemcard2Binding;
import com.example.lostnexus.models.FoundItem;

import java.util.List;


public class FoundItemListAdapter extends RecyclerView.Adapter<FoundItemListAdapter.MviewHolder> {

Context context;
List<FoundItem> lostItemList;
public FoundItemListAdapter(Context context , List<FoundItem> lostItemList){

    this.context =context;
    this.lostItemList = lostItemList;
}


    @NonNull
    @Override
    public MviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      Itemcard2Binding itemcard2Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.itemcard2 , parent,false);
   return new MviewHolder(itemcard2Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundItemListAdapter.MviewHolder holder, int position) {

    holder.binding.setItemdata(lostItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return lostItemList.size();
    }
    class MviewHolder extends RecyclerView.ViewHolder{
    Itemcard2Binding binding;
        public MviewHolder(@NonNull Itemcard2Binding  itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public void getFilter(List<FoundItem> list)
    {
       lostItemList = list;
       notifyDataSetChanged();

    }


}
