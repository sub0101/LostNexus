package com.example.lostnexus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnexus.databinding.FragmentInitialItemBinding;
import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.viewmodels.FoundItemViewModel;

import java.util.List;


public class InitialItemFragment extends Fragment {


    RecyclerView recyclerView1 , recyclerView2;
    List<FoundItem> lostItemList;
    ItemListAdapter itemListAdapter;
    FoundItemViewModel foundItemViewModel;
FragmentInitialItemBinding initialItemBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initialItemBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_initial_item, container,false);
       foundItemViewModel = new ViewModelProvider(this).get(FoundItemViewModel.class);
        return initialItemBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        foundItemViewModel.getAllItems().observe(this, new Observer<List<FoundItem>>() {
            @Override
            public void onChanged(List<FoundItem> lostItems) {
                showItems(lostItems);
            }
        });




    }



    void showItems(List<FoundItem> items){
        recyclerView1=  initialItemBinding.itemviewWeek;
        itemListAdapter  = new ItemListAdapter(getContext() ,items);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setNestedScrollingEnabled(true);
        recyclerView1.setAdapter(itemListAdapter);
        recyclerView2 = initialItemBinding.recentLostview;
        itemListAdapter = new ItemListAdapter(getContext() , items);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext() , 2));
        recyclerView2.setNestedScrollingEnabled(true);
        recyclerView2.setAdapter( itemListAdapter);
    }
}