package com.example.lostnexus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.lostnexus.Notification;
import com.example.lostnexus.R;
import com.example.lostnexus.databinding.FragmentNotificationBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Notification> {
    List<Notification> notificationList;
    Context context;
    FragmentNotificationBinding  binding;
    public NotificationAdapter( List<Notification> list,  @NonNull Context context) {
        super(context , 0 , list);

//        super(context, R.layout.notification_layout);
        notificationList = list;
     this.context  = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.notification_layout, null);
        }
        Notification item = getItem(position);

        TextView textView1 = v.findViewById(R.id.notidetail);

        textView1.setText(item.detail);

        return v;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
