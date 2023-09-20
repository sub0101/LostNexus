package com.example.lostnexus.authenticate.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lostnexus.R;
import com.example.lostnexus.authenticate.ClickHandler;
import com.example.lostnexus.authenticate.login.LoginViewModel;
import com.example.lostnexus.authenticate.login.LoignActivity;
import com.example.lostnexus.databinding.LayoutRegisterBinding;


public class SignUpActivity extends AppCompatActivity {
    LayoutRegisterBinding registerBinding;

RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
System.out.println("layout register");
registerBinding = DataBindingUtil.setContentView(this , R.layout.layout_register);
registerViewModel  = new RegisterViewModel();
registerBinding.setRegisterViewModel(registerViewModel);
registerBinding.setClickHandler(new ClickHandler(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
       registerViewModel.getIsRegistered().observe(this, new Observer<Boolean>() {
           @Override
           public void onChanged(Boolean aBoolean) {
               System.out.println("outside on change");


               if(aBoolean){
                   System.out.println("inside on change");
                   Toast.makeText(getBaseContext(), "Verification Mail Sent Successfully" , Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(SignUpActivity.this , LoignActivity.class);
                   startActivity(intent);
               }

           }
       });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




}
