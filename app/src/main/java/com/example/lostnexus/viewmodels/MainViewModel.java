package com.example.lostnexus.viewmodels;

import android.app.Application;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.lostnexus.adapters.FoundItemListAdapter;
import com.example.lostnexus.repository.Repository;
import com.example.lostnexus.models.UserProfile;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {

    MutableLiveData<UserProfile> userProfileMutableLiveData;
    MutableLiveData<ArrayList<UserProfile>> alluserProfile ;
    MutableLiveData<FoundItemListAdapter> lostItemLiveData;
    Repository  repository;
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public MutableLiveData<UserProfile> getUserProfileMutableLiveData() {
        userProfileMutableLiveData = repository.getUserProfileMutableLiveData();
        return userProfileMutableLiveData;
    }



    public MutableLiveData<ArrayList<UserProfile>> getAllUserProfileMutableLiveData() {
        alluserProfile = repository.getAlluserProfile();
        return  alluserProfile;
    }
    public void setUserProfileMutableLiveData(MutableLiveData<UserProfile> userProfileMutableLiveData) {
        this.userProfileMutableLiveData = userProfileMutableLiveData;
    }

    public void updateUserProfile(){

        System.out.println(repository.getUserProfileMutableLiveData().getValue().getImage());
repository.updateUserProfile();
    }
    public void uploadImage(ProgressDialog progressDialog)
    {
        repository.uploadImage(progressDialog);
    }

}
