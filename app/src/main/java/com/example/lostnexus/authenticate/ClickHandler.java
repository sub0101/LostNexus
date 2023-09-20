package com.example.lostnexus.authenticate;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.lostnexus.authenticate.login.LoignActivity;
import com.example.lostnexus.authenticate.register.SignUpActivity;

public class ClickHandler  {

    Context context;
    public ClickHandler(Context context)
    {
        this.context = context;
    }

    public void newAccountClicked(View view)
    {
        System.out.println("new ");
        Intent intent  = new Intent(view.getContext() , SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        view.getContext().startActivity(intent);
    }
    public void alreadyAccountClicked(View view)
    {
        Intent intent  = new Intent(view.getContext() , LoignActivity.class);

        view.getContext().startActivity(intent);

    }

}
