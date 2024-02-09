package com.example.lostnexus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.lostnexus.adapters.FoundItemListAdapter;
import com.example.lostnexus.databinding.FragmentAllItemsBinding;
import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.models.UserProfile;
import com.example.lostnexus.viewmodels.FoundItemViewModel;

import java.util.ArrayList;
import java.util.List;


public class AllItemsFragment extends AppCompatActivity implements ItemClickListener{


    RecyclerView recyclerView=null;
 FoundItemListAdapter listAdapter;
 List<FoundItem> list;
 FoundItemViewModel foundItemViewModel;

FragmentAllItemsBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_items);
        binding = DataBindingUtil.setContentView(this , R.layout.fragment_all_items);
        foundItemViewModel = new ViewModelProvider(this).get(FoundItemViewModel.class);
binding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBar(findViewById(R.id.toolbar2));
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        foundItemViewModel.getAllItems().observe(this, new Observer<List<FoundItem>>() {
            @Override
            public void onChanged(List<FoundItem> lostItemList) {
             list = lostItemList;
                setRecyclerView(list);
                binding.shimmerViewContainer.setVisibility(View.GONE);

            }
        });

    }

    void setRecyclerView(List<FoundItem> lostItemList){
        System.out.println("set recycler view");
         recyclerView = findViewById(R.id.lostitemlsist);
    listAdapter = new FoundItemListAdapter(this ,lostItemList );
    listAdapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionSearch);
        final androidx.appcompat.widget.SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void filter(String text) {

List<FoundItem> filteredlist =  new ArrayList<>();
        for (FoundItem item :  list) {
            if (item.getDetail().toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {

            listAdapter.getFilter(filteredlist);
        }
    }


    @Override
    public void onClick(View view, FoundItem item) {
//        System.out.println(position);
        Intent intent = new Intent(AllItemsFragment.this , ItemDetail.class);

//        intent.putExtra("possition",position);
        intent.putExtra("item" , item);
        startActivity(intent);

    }

    @Override
    public void onClick(View view, UserProfile userProfile) {

    }

    @Override
    public void onButtonClick(int possition) {
        gotolocation(possition);
    }

    public  void gotolocation(int p){
        String lat = list.get(p).getLattitude();
        String longt = list.get(p).getLongtitude();
        String address = list.get(p).getLocation();
        System.out.println(lat + longt);
        if(!lat.equals("") && !longt.equals(""))
        {
            Uri mapUri = Uri.parse("geo:0,0?q=" +lat+","+ longt+"(label)");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
           startActivity(mapIntent);
        }
        else if(!address.equals("")){

            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));


            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else {
            System.out.println("uessfsfsfsd");
            Toast.makeText(this ,"please select location or add address of the location" ,Toast.LENGTH_SHORT ).show();
        }

    }

}
