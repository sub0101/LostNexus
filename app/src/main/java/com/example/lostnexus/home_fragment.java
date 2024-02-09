package com.example.lostnexus;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostnexus.databinding.FragmentHomeScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class home_fragment extends Fragment {

FragmentHomeScreenBinding homeScreenBinding;
    BottomNavigationView bottomNavigationView;

InitialItemFragment initialItemFragment;
NotificationFragment notificationFragment;
Chat_fragment chatFragment;
AllItemsFragment allItemsFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
        // Inflate the layout for this fragment
        homeScreenBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_home_screen, container , false);
      lostItemViewModel  = new ViewModelProvider(this).get(LostItemViewModel.class);
        System.out.println("inside the fragment cretead");
=======
        homeScreenBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_home_screen, container , false);
>>>>>>> testHomeFragment

        return homeScreenBinding.getRoot();

    }

<<<<<<< HEAD
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        lostItemViewModel.getAllItems().observe(this, new Observer<List<LostItem>>() {
//            @Override
//            public void onChanged(List<LostItem> lostItemList) {
//           for(LostItem lostItem :lostItemList){
//               System.out.println(lostItem.getDetail());
//           }
//            }
//        });
    }
=======
>>>>>>> testHomeFragment

    @Override
    public void onStart() {
        super.onStart();
<<<<<<< HEAD
        recyclerView1=  homeScreenBinding.itemviewWeek;
//        lostItemList = lostItemViewModel.getAllItems().getValue();

//        itemListAdapter  = new ItemListAdapter(getContext()  ,lostItemList);
//        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView1.setAdapter(itemListAdapter);
//        Re customAdapter = new CustomAdapter(getContext() ,movies );
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerView.setAdapter(customAdapter);
=======
>>>>>>> testHomeFragment

setInitialView();
    }




    public void setInitialView()
    {

        allItemsFragment = new AllItemsFragment();
        initialItemFragment  = new InitialItemFragment();
        notificationFragment = new NotificationFragment();
        chatFragment =  new Chat_fragment();
        bottomNavigationView = homeScreenBinding.bottomNavigationView;
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new home_fragment.BottomNavigationItemListner());
        bottomNavigationView.setSelectedItemId(R.id.home_bottomMenu);


    }
    private class BottomNavigationItemListner implements NavigationBarView.OnItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.home_bottomMenu) {
setCurrentFragment(initialItemFragment);
            }
            else if(id == R.id.search){
                Intent intent = new Intent(getActivity().getApplication() , AllItemsFragment.class);
                startActivity(intent);
            }
            else if(id == R.id.notification){
                setCurrentFragment(notificationFragment);
            }
            else if(id == R.id.chat) setCurrentFragment(chatFragment);
            return true;
        }

    }
    private void setCurrentFragment(Fragment fragment){

        getFragmentManager().beginTransaction().replace(homeScreenBinding.initialContainer.getId() , fragment).commit();
    }

}