package com.example.lostnexus.repository;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lostnexus.Notification;
import com.example.lostnexus.models.Message;

import com.example.lostnexus.models.FoundItem;
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
        reference.child(id).setValue(lostItem).addOnSuccessListener(unused -> {
            uploadItemImage(progressDialog);

        }).addOnFailureListener(e -> System.out.println("failed to addded"));

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
               allitems.setValue(itemlist);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        return allitems;
    }

    public void addNotification(FoundItem itemdetail){

        DatabaseReference temprefre = FirebaseDatabase.getInstance().getReference("Notifications");
itemdetail.setIsclaimed(true);
        Notification notification = new Notification();
        notification.id = itemdetail.getId();
        notification.detail = "A item is claimed By a User";
        notification.date =  new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        notification.time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

addinchatList(itemdetail);
        temprefre.child(itemdetail.getUploadedBy()).child(itemdetail.getId()).setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("Notifications" ,"notifcation added");
                itemdetail.setIsclaimed(true);
                reference.child(itemdetail.getId()).setValue(itemdetail).addOnSuccessListener(unused1 -> {});
            }
        });
    }
    private void addinchatList(FoundItem item){
        DatabaseReference  listref =  FirebaseDatabase.getInstance().getReference("ChatUsers");
        DatabaseReference chatting = FirebaseDatabase.getInstance().getReference("Chatting");
        Message message =  new Message();
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessage("Hello this is my item");
        message.setSenderId(FirebaseAuth.getInstance().getUid());
        Date date =  new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat =  new SimpleDateFormat("dd-MM-yyyy '\n' HH:mm:ss ");

        String str = dateFormat.format(new Date());
        message.setTimestamp(str);

        listref.child(FirebaseAuth.getInstance().getUid()).child("uuid").setValue(item.getUploadedBy()).addOnSuccessListener(unused -> {});
       listref.child(item.getUploadedBy()).child("uuid").setValue(FirebaseAuth.getInstance().getUid()).addOnSuccessListener(unused -> {});
        String randomKey = FirebaseDatabase.getInstance().getReference().push().getKey();

        chatting.child(FirebaseAuth.getInstance().getUid()+item.getUploadedBy()).child(randomKey).setValue(message).addOnSuccessListener(unused -> {});
        chatting.child(item.getUploadedBy()+FirebaseAuth.getInstance().getUid()).child(randomKey).setValue(message).addOnSuccessListener(unused -> {});


    }


    public MutableLiveData<List<Notification>> getAllNotifications(){
        List<Notification> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Notifications").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
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

