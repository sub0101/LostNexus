package com.example.lostnexus.repository;

import android.app.ProgressDialog;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lostnexus.models.UserProfile;
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

import java.util.UUID;

public class Repository {

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("UserProfile");

  private  MutableLiveData<UserProfile> userProfileMutableLiveData;
    public Repository() {
        userProfileMutableLiveData= new MutableLiveData<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
UserProfile userProfile = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(UserProfile.class);

                System.out.println("data changed called");
                userProfileMutableLiveData.postValue(userProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println("inside repo  constructor");


    }
    public MutableLiveData<UserProfile> getUserProfileMutableLiveData() {
        System.out.println(userProfileMutableLiveData + "get repo");
        return userProfileMutableLiveData;
    }

    public void setUserProfileMutableLiveData(MutableLiveData<UserProfile> userProfileMutableLiveData) {
        this.userProfileMutableLiveData = userProfileMutableLiveData;
    }
    public void updateUserProfile(){
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userProfileMutableLiveData.getValue());
    }


    public void uploadImage(ProgressDialog progressDialog)
    {
        String url =userProfileMutableLiveData.getValue().getImage();
        Uri uri = Uri.parse(url);
        StorageReference ref
                = FirebaseStorage.getInstance().getReference()
                .child(
                        "images/"
                                + UUID.randomUUID().toString()+url.substring(url.length()-5));
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println("yes uploaded");
                        userProfileMutableLiveData.getValue().setImage(uri.toString());
                        System.out.println(uri);
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("image").setValue(userProfileMutableLiveData.getValue().getImage());

                        progressDialog.cancel();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("error");
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress
                        = (100.0
                        * taskSnapshot.getBytesTransferred()
                        / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage(
                        "Uploaded "
                                + (int)progress + "%");
            }
        });
        System.out.println("uploading");
    }




}

