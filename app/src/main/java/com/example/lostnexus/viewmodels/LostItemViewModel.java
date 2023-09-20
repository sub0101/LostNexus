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
import com.example.lostnexus.models.LostItem;
import com.example.lostnexus.repository.LostItemRepo;
import com.example.lostnexus.repository.Repository;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LostItemViewModel extends AndroidViewModel {
    LostItemRepo lostItemRepo;
    MutableLiveData<LostItem> lostItemLiveData;
    public MutableLiveData<Boolean> shouldClose;
    public MutableLiveData<List<LostItem>> allitems;

    public LostItemViewModel(@NonNull Application application) {
        super(application);
lostItemRepo = new LostItemRepo();
shouldClose  = lostItemRepo.shouldClose;
    }

    public LiveData<LostItem> getLostItemLiveData() {
        lostItemLiveData = lostItemRepo.getLostItemMutableLiveData();
        return lostItemLiveData;
    }
    public LiveData<List<LostItem>> getAllItems(){
        return allitems;
    }

//

    public void setLostItemLiveData(MutableLiveData<LostItem> lostItemLiveData) {
        this.lostItemLiveData = lostItemLiveData;
    }


    public void addLostItem(ProgressDialog progressDialog){

       lostItemRepo.addLostItem(progressDialog);

    }


    @BindingAdapter({"bind:imgUrl"})
    public static void setProfilePicture(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }

    public boolean validate(){

        LostItem item = lostItemLiveData.getValue();
//        System.out.println(item.getDetail()+"inside the validate");
        boolean b = !item.getDetail().equals("") && !item.getImage().equals("") && !item.getNearby().equals("")
                && !item.getLocation().equals("") && !item.getDate().equals("") && !item.getTime().equals("");
System.out.println(b + "hai");
        return b;
    }


}
