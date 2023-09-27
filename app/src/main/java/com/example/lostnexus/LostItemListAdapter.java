package com.example.lostnexus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class LostItemListAdapter extends RecyclerView.Adapter<LostItemListAdapter.MviewHolder> {

Context context;
public LostItemListAdapter(Context context){
    this.context =context;
}

    @NonNull
    @Override
    public MviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemcard2 , parent,false);
   return new MviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostItemListAdapter.MviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
    class MviewHolder extends RecyclerView.ViewHolder{
        public MviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
