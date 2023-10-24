package com.example.lostnexus.repository;

import android.app.ProgressDialog;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lostnexus.Notification;
import com.example.lostnexus.authenticate.validators.LostItemValidator;
import com.example.lostnexus.models.FoundItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class FoundItemRepo {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("LostItems");
    MutableLiveData<FoundItem> lostItemMutableLiveData;
    MutableLiveData<List<FoundItem>> allitems;
  public  MutableLiveData<Boolean> shouldClose;
    public MutableLiveData<List<Notification>>  notificationlist;


    public FoundItemRepo() {
        lostItemMutableLiveData = new MutableLiveData<>(new FoundItem());
        allitems =  new MutableLiveData<>(new ArrayList<>());
        shouldClose = new MutableLiveData<>(new Boolean(false));
        notificationlist =  new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<FoundItem> getLostItemMutableLiveData() {
        return lostItemMutableLiveData;
    }

    public void setLostItemMutableLiveData(MutableLiveData<FoundItem> lostItemMutableLiveData) {
        this.lostItemMutableLiveData = lostItemMutableLiveData;
    }

    public FoundItem getLostItem() {
        FoundItem lostItem = lostItemMutableLiveData.getValue();

        return lostItem;
    }


    public void addLostItem(ProgressDialog progressDialog) {
        FoundItem lostItem = getLostItem();
        lostItem.setUploadedBy(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String id  = UUID.randomUUID().toString();
        lostItem.setId(id);
//        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("LostItems");
        reference.child(id).setValue(lostItem).addOnSuccessListener(unused -> {
            System.out.println("successsfully added");
            uploadItemImage(progressDialog);

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("failed to addded");
            }
        });

    }

    public void uploadItemImage(ProgressDialog progressDialog) {
        String url = lostItemMutableLiveData.getValue().getImage();
        Uri uri = Uri.parse(url);
        StorageReference ref
                = FirebaseStorage.getInstance().getReference()
                .child(
                        "itemImages/"
                                + UUID.randomUUID().toString() + url.substring(url.length() - 5));
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        reference.child(getLostItem().getId()).child("image").setValue(String.valueOf(uri));
                        System.out.println("uploadaed image");
                        progressDialog.cancel();
                        shouldClose.setValue(new Boolean(true));
                    }
                });
//

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println(taskSnapshot.getBytesTransferred());
                double progress
                        = (100.0
                        * taskSnapshot.getBytesTransferred()
                        / taskSnapshot.getTotalByteCount());
//                progressDialog.setMessage(
//                        "Uploaded "
//                                + (int)progress + "%");
                progressDialog.incrementProgressBy((int) progress);
            }
        });


    }

    public  MutableLiveData<List<FoundItem>> getAllItems(){
List<FoundItem> itemlist = new ArrayList<>();

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                 FoundItem lostItem = postSnapshot.getValue(FoundItem.class);
                   itemlist.add(lostItem);

               }
               System.out.println("after the values");
               allitems.setValue(itemlist);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        return allitems;
    }

    public void addNotification(FoundItem itemdetail){
        if(itemdetail.getUploadedBy().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return ;
        }
        DatabaseReference temprefre = FirebaseDatabase.getInstance().getReference("Notifications");
itemdetail.setIsclaimed(true);
        Notification notification = new Notification();
        notification.detail = "this item is claimed";
        notification.date =  new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        notification.time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


     temprefre.child(itemdetail.getId()).setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("updated successfully");
            }
        });

//        temprefre.push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//            }
//        });
    }

    public MutableLiveData<List<Notification>> getAllNotifications(){
        List<Notification> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    list.add(postSnapshot.getValue(Notification.class));
                }
                notificationlist.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return notificationlist;
    }


}

