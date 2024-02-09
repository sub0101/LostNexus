package com.example.lostnexus.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class UserProfile extends BaseObservable implements Parcelable {
 public   String name,email,contact,address,uuid;
   public String  gender , image,city,state;

   public UserProfile()
   {

   }


    public UserProfile(String name, String email,String image, String contact, String address, String uuid ,  String gender, String city, String state) {
        this.name = name;
        this.email = email;
this.uuid  = uuid;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.image = image;
        this.city = city;
        this.state = state;
    }

    protected UserProfile(Parcel in) {
        name = in.readString();
        email = in.readString();
        contact = in.readString();
        address = in.readString();
        uuid = in.readString();
        gender = in.readString();
        image = in.readString();
        city = in.readString();
        state = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(contact);
        parcel.writeString(address);
        parcel.writeString(uuid);
        parcel.writeString(gender);
        parcel.writeString(image);
        parcel.writeString(city);
        parcel.writeString(state);
    }
}



