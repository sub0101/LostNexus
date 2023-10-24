package com.example.lostnexus.viewmodels;

import android.app.Application;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.lostnexus.FoundItemListAdapter;
import com.example.lostnexus.repository.Repository;
import com.example.lostnexus.models.UserProfile;

public class MainViewModel extends AndroidViewModel {

    MutableLiveData<UserProfile> userProfileMutableLiveData;
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





//    @BindingAdapter({"bind:imgUrl"})
//    public static void setProfilePicture(ImageView imageView, String imgUrl) {
//        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
//    }
}
