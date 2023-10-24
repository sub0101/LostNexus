package com.example.lostnexus;

import android.view.View;

import com.example.lostnexus.models.FoundItem;

public interface ItemClickListener {
    void onClick(View view, FoundItem item);
    void onButtonClick(int possition);

}
