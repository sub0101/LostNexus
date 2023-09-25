package com.example.lostnexus.repository;

import android.app.Application;
import android.app.ProgressDialog;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lostnexus.models.LostItem;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LostItemRepo {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("LostItems");

    MutableLiveData<LostItem> lostItemMutableLiveData;
    MutableLiveData<List<LostItem>> allitems;
  public  MutableLiveData<Boolean> shouldClose;

    public LostItemRepo() {
        lostItemMutableLiveData = new MutableLiveData<>(new LostItem());
        allitems =  new MutableLiveData<>(new ArrayList<>());
        shouldClose = new MutableLiveData<>(new Boolean(false));
    }

    public MutableLiveData<LostItem> getLostItemMutableLiveData() {
        return lostItemMutableLiveData;
    }

    public void setLostItemMutableLiveData(MutableLiveData<LostItem> lostItemMutableLiveData) {
        this.lostItemMutableLiveData = lostItemMutableLiveData;
    }

    public LostItem getLostItem() {
        LostItem lostItem = lostItemMutableLiveData.getValue();

        return lostItem;
    }


    public void addLostItem(ProgressDialog progressDialog) {
        LostItem lostItem = getLostItem();
        lostItem.setUploadedBy(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("LostItems");
        reference.child(lostItem.getDetail() + lostItem.getTime()).setValue(lostItem).addOnSuccessListener(unused -> {
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
                        reference.child(getLostItem().getDetail() + getLostItem().getTime()).child("image").setValue(String.valueOf(uri));
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

    public  MutableLiveData<List<LostItem>> getAllItems(){
List<LostItem> itemlist = new ArrayList<>();

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                 LostItem lostItem = postSnapshot.getValue(LostItem.class);
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


}

