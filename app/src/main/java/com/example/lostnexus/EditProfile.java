package com.example.lostnexus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lostnexus.databinding.EditProfileBinding;
import com.example.lostnexus.models.UserProfile;
import com.example.lostnexus.viewmodels.MainViewModel;
import com.google.android.material.chip.Chip;

import java.io.IOException;

public class EditProfile extends AppCompatActivity {

    MainViewModel mainViewModel;
    EditProfileBinding editProfileBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        mainViewModel =  new ViewModelProvider(this).get(MainViewModel.class);
        editProfileBinding = DataBindingUtil.setContentView(this , R.layout.edit_profile);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mainViewModel.getUserProfileMutableLiveData().observe(this, new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {

                editProfileBinding.setUserProfile(userProfile);
            }
        });
        editProfileBinding.genderChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
       Chip chip = group.findViewById(checkedIds.get(0));
       mainViewModel.getUserProfileMutableLiveData().getValue().setGender(chip.getText().toString());
        });

    }

    public void updateData(View view) {

        mainViewModel.updateUserProfile();
     }

    public void upload_image(View view){

        Intent intent  = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent , "Select Picture"),200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            if(requestCode == 200){
                ProgressDialog progressDialog
                        = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                Uri selectedImageUri = data.getData();

                if (selectedImageUri !=null) {
System.out.println(selectedImageUri);
mainViewModel.getUserProfileMutableLiveData().getValue().setImage(String.valueOf(selectedImageUri));
                   editProfileBinding.imageviewAccountProfile.setImageURI(selectedImageUri);
                    Bitmap selectedImageBitmap = null;
                    try {
                        selectedImageBitmap
                                = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(),
                                selectedImageUri);
                        editProfileBinding.imageviewAccountProfile.setImageBitmap(selectedImageBitmap);
mainViewModel.uploadImage(progressDialog);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
