package com.example.lostnexus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lostnexus.authenticate.login.LoignActivity;
import com.example.lostnexus.databinding.ActivityMainBinding;
import com.example.lostnexus.databinding.NavHeaderMainBinding;
import com.example.lostnexus.models.UserProfile;
import com.example.lostnexus.viewmodels.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    ActivityMainBinding mainBinding;
    NavHeaderMainBinding headerMainBinding;
    BottomNavigationView bottomNavigationView;

    MainViewModel viewModel;
    static int RC_SIGN_IN = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
mainBinding = DataBindingUtil.setContentView(MainActivity.this , R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setInitialView(this);
        if (firebaseAuth.getCurrentUser() == null) {
System.out.println("no user");
            goToLoginActivity();
        }
        else {
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                viewModel  = new ViewModelProvider(this).get(MainViewModel.class);
                goToDashBord();

            }
        }

    }

    private void goToLoginActivity()
        {

            Intent intent = new Intent(this , LoignActivity.class);
            startActivity(intent);
        }
    private  void goToDashBord()
    {
        Toast.makeText(this , "this is dashbord" , Toast.LENGTH_SHORT ).show();
        System.out.println(firebaseAuth.getCurrentUser().getUid());
        viewModel.getUserProfileMutableLiveData().observe(this, new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {;
                mainBinding.setUser(userProfile);
                headerMainBinding.setUser(userProfile);
            }
        });
    }

public void logout(View view)
{
    firebaseAuth.signOut();
    goToLoginActivity();
}



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_profile) {//

                  Intent intent = new Intent(MainActivity.this , EditProfile.class);
                startActivity(intent);
        }
        return false;
    }


    private class BottomNavigationItemListner implements NavigationBarView.OnItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
          if(id == R.id.home_bottomMenu) {

          }
          else if(id == R.id.search){

          }
          return true;
        }

    }



    public void add_itemFragment(View view){
//ItemAddFragment.display(getSupportFragmentManager());
        Intent intent =  new Intent(MainActivity.this , ItemAddFragment.class);
        startActivity(intent);
//        overridePendingTransition(android.R.transition.slide_bottom ,android.R.transition.slide_bottom );
//        overridePendingTransition(Animation.);

    }

    public void setInitialView(MainActivity mainActivity)
    {
        setSupportActionBar(mainBinding.mainIncludeToolbar.toolbar);
        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(this , mainBinding.drawerLayout , mainBinding.mainIncludeToolbar.toolbar,R.string.open, R.string.close);
        mainBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = mainBinding.navigation;

        View view =   navigationView.getHeaderView(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            headerMainBinding = DataBindingUtil.inflate(getLayoutInflater() , view.getSourceLayoutResId() , mainBinding.navigation , true);
        }
        getSupportFragmentManager().beginTransaction().replace(mainBinding.mainContainer.getId() , new home_fragment()).commit();

    }
}