package com.example.lostnexus.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class FoundItem extends BaseObservable implements Parcelable {
    public String detail="" , type="" , date="" , time="" , location="" , nearby="" ,image="",state,  city,uploadedBy="";
  public  String lattitude="" , longtitude=""  ,description="";
 public  Boolean isclaimed = false;

 String id;

    protected FoundItem(Parcel in) {
        detail = in.readString();
        type = in.readString();
        date = in.readString();
        time = in.readString();
        location = in.readString();
        nearby = in.readString();
        image = in.readString();
        state = in.readString();
        city = in.readString();
        uploadedBy = in.readString();
        lattitude = in.readString();
        longtitude = in.readString();
        description = in.readString();
        byte tmpIsclaimed = in.readByte();
        isclaimed = tmpIsclaimed == 0 ? null : tmpIsclaimed == 1;
        id = in.readString();
    }

    public static final Creator<FoundItem> CREATOR = new Creator<FoundItem>() {
        @Override
        public FoundItem createFromParcel(Parcel in) {
            return new FoundItem(in);
        }

        @Override
        public FoundItem[] newArray(int size) {
            return new FoundItem[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FoundItem() {
    }

    public FoundItem(String detail, String type, String date, String time, String location, String nearby, String image, String state, String city, String lattitude, String longtitude,String uploadedBy) {
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
    public static void setImage(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }
    @BindingAdapter({"text"})
    public static  void setIsClaimed(TextView view ,String str){
        if(str.equals("false")) view.setText("Item is Not Claimed");
        else view.setText("Item is Claimed");
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
//dest.writeString(detail);
//        dest.writeString(date);
//        dest.writeString(time);
//        dest.writeString(type);
//        dest.writeString(location);
//        dest.writeString(lattitude);
//        dest.writeString(lo);
//        dest.writeString(detail);
//        dest.writeString(detail);

        dest.writeString(detail);
        dest.writeString(type);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(location);
        dest.writeString(nearby);
        dest.writeString(image);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(uploadedBy);
        dest.writeString(lattitude);
        dest.writeString(longtitude);
        dest.writeString(description);
        dest.writeByte((byte) (isclaimed == null ? 0 : isclaimed ? 1 : 2));
        dest.writeString(id);
    }

    public Boolean getIsclaimed() {
        return isclaimed;
    }

    public void setIsclaimed(Boolean isclaimed) {
        this.isclaimed = isclaimed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
