package com.example.lostnexus.models;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;

public class LostItem extends BaseObservable {
    public String detail="" , type="" , date="" , time="" , location="" , nearby="" ,image="",state,  city,uploadedBy="";
  public  String lattitude="" , longtitude="";

    public LostItem() {
    }

    public LostItem(String detail, String type, String date, String time, String location, String nearby, String image, String state, String city, String lattitude, String longtitude,String uploadedBy) {
        this.detail = detail;
        this.type = type;
        this.date = date;
        this.time = time;
        this.location = location;
        this.nearby = nearby;
        this.image = image;
        this.state = state;
        this.city = city;
        this.lattitude = lattitude;
        this.longtitude = longtitude;
        this.uploadedBy=uploadedBy;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @Bindable
    public String getDetail() {
        System.out.println(detail);
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
        notifyChange();

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNearby() {
        return nearby;
    }

    public void setNearby(String nearby) {
        this.nearby = nearby;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @BindingAdapter({"image"})
    public static void setItemImage(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }
}
