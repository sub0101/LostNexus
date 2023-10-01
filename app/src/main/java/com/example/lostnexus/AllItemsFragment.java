package com.example.lostnexus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.viewmodels.FoundItemViewModel;

import java.util.ArrayList;
import java.util.List;


public class AllItemsFragment extends AppCompatActivity {


    RecyclerView recyclerView=null;
 FoundItemListAdapter listAdapter;
 List<FoundItem> list;
 FoundItemViewModel foundItemViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_items);
        foundItemViewModel = new ViewModelProvider(this).get(FoundItemViewModel.class);

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
            }
        });

    }

    void setRecyclerView(List<FoundItem> lostItemList){
         recyclerView = findViewById(R.id.lostitemlsist);
    listAdapter = new FoundItemListAdapter(this ,lostItemList );
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
}
