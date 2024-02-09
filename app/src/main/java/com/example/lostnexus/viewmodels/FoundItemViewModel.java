package com.example.lostnexus.viewmodels;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.ProgressDialog;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.lostnexus.Event;
import com.example.lostnexus.Notification;
import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.repository.FoundItemRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import kotlin.Unit;

public class FoundItemViewModel extends AndroidViewModel {
   FoundItemRepo lostItemRepo;
    private Event showDialogEvent = new Event();

    public void showDialog(String str) {
        showDialogEvent.setValue(str);
    }

    public void observeShowDialogEvent(LifecycleOwner owner, Observer<String> observer) {
        showDialogEvent.observe(owner, observer);
    }
    MutableLiveData<FoundItem> lostItemLiveData;
    public MutableLiveData<Boolean> shouldClose;
    public MutableLiveData<List<FoundItem>> allitems;
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


    public boolean validate(){

        FoundItem item = lostItemLiveData.getValue();
        boolean b = !item.getDetail().equals("") && !item.getImage().equals("") && !item.getNearby().equals("")
                && !item.getLocation().equals("") && !item.getDate().equals("") && !item.getTime().equals("");
        return b;
    }

    public void addNotification(FoundItem foundItem){
        if(foundItem.getUploadedBy().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
showDialog("Same User Can not claim Same item");
return ;
        }

lostItemRepo.addNotification(foundItem);
        showDialog("Item is Claimed");

    }

    public MutableLiveData<List<Notification>> getAllNotifications(){
return lostItemRepo.getAllNotifications();
    }





}
