package com.example.lostnexus.models;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class UserProfile extends BaseObservable {
 public   String name,email,contact,address;
   public String  gender , image,city,state;

    public UserProfile() {

    }

    public UserProfile(String name, String email,String image, String contact, String address, String gender, String city, String state) {
        this.name = name;
        this.email = email;

        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.image = image;
        this.city = city;
        this.state = state;
    }

    @Bindable
    public String getName() {
        System.out.println(name);
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println(name);



    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String city) {
        this.address = city;
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        System.out.println(gender);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @BindingAdapter({"image"})
    public static void setProfilePicture(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }

    @BindingAdapter("gender")
    public static void setSelectedChip(ChipGroup chipGroup, String selectedChip) {
System.out.println("inside gender");
        int n = chipGroup.getChildCount();
        for(int i=0;i<n;i++){
            Chip chip = (Chip) chipGroup.getChildAt(i);
            System.out.println(chip.getText() + selectedChip);
            if(chip.getText().toString().equals(selectedChip))
            {
                System.out.println(chip.getText()+"selected");
                chip.setChecked(true);
                break;
            }
        }
    }
}



