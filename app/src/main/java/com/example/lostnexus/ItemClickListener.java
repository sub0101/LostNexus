package com.example.lostnexus;

import android.view.View;

import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.models.UserProfile;

public interface ItemClickListener {
    void onClick(View view, FoundItem item);
    void onClick(View view , UserProfile userProfile);
    void onButtonClick(int possition);

}
