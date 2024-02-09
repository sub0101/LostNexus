package com.example.lostnexus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnexus.ItemClickListener;
import com.example.lostnexus.R;
import com.example.lostnexus.databinding.Itemcard2Binding;
import com.example.lostnexus.models.FoundItem;

import java.util.List;


public class FoundItemListAdapter extends RecyclerView.Adapter<FoundItemListAdapter.MviewHolder> {

Context context;
List<FoundItem> lostItemList;
Itemcard2Binding itemcard2Binding;
    private ItemClickListener clickListener;


public FoundItemListAdapter(Context context , List<FoundItem> lostItemList){

    this.context =context;
    this.lostItemList = lostItemList;
}

    @NonNull
    @Override
    public MviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      itemcard2Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.itemcard2 , parent,false);
   return new MviewHolder(itemcard2Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundItemListAdapter.MviewHolder holder, int position) {

    holder.binding.setItemdata(lostItemList.get(position));

    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    @Override
    public int getItemCount() {
        return lostItemList.size();
    }
    class MviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    Itemcard2Binding binding;
        public MviewHolder(@NonNull Itemcard2Binding  itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.cardView.setOnClickListener(this);
            binding.addToCartButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == binding.addToCartButton) {
                if (clickListener != null) clickListener.onButtonClick(getAdapterPosition());

            } else {
                if (clickListener != null) clickListener.onClick(v, lostItemList.get(getAdapterPosition()));

            }
        }
    }

    public void getFilter(List<FoundItem> list)
    {
       lostItemList = list;
       notifyDataSetChanged();

    }



}
