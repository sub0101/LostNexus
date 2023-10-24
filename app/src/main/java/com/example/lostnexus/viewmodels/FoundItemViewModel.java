package com.example.lostnexus.viewmodels;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.ProgressDialog;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.lostnexus.Notification;
import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.repository.FoundItemRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import kotlin.Unit;

public class FoundItemViewModel extends AndroidViewModel {
   FoundItemRepo lostItemRepo;

    MutableLiveData<FoundItem> lostItemLiveData;
    public MutableLiveData<Boolean> shouldClose;
    public MutableLiveData<List<FoundItem>> allitems;
    public MutableLiveData<List<Notification>>  notificationlsit;

    public FoundItemViewModel(@NonNull Application application) {
        super(application);
lostItemRepo = new FoundItemRepo();
shouldClose  = lostItemRepo.shouldClose;
    }

    public LiveData<FoundItem> getLostItemLiveData() {
        lostItemLiveData = lostItemRepo.getLostItemMutableLiveData();
        return lostItemLiveData;
    }
    public MutableLiveData<List<FoundItem>> getAllItems(){
allitems =  lostItemRepo.getAllItems();
        return allitems;
    }

//

    public void setLostItemLiveData(MutableLiveData<FoundItem> lostItemLiveData) {
        this.lostItemLiveData = lostItemLiveData;
    }


    public void addLostItem(ProgressDialog progressDialog){

       lostItemRepo.addLostItem(progressDialog);

    }


//    @BindingAdapter({"bind:imgUrl"})
//    public static void setProfilePicture(ImageView imageView, String imgUrl) {
//        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
//    }

    public boolean validate(){

        FoundItem item = lostItemLiveData.getValue();
//        System.out.println(item.getDetail()+"inside the validate");
        boolean b = !item.getDetail().equals("") && !item.getImage().equals("") && !item.getNearby().equals("")
                && !item.getLocation().equals("") && !item.getDate().equals("") && !item.getTime().equals("");
System.out.println(b + "hai");
        return b;
    }

    public void addNotification(FoundItem foundItem){
        if(foundItem.getUploadedBy().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

        }
lostItemRepo.addNotification(foundItem);

    }

    public MutableLiveData<List<Notification>> getAllNotifications(){
return lostItemRepo.getAllNotifications();
    }





}
