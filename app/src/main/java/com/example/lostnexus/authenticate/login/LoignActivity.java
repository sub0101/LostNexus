package com.example.lostnexus.authenticate.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.lostnexus.MainActivity;
import com.example.lostnexus.R;
import com.example.lostnexus.authenticate.ClickHandler;
import com.example.lostnexus.databinding.LayoutLoginBinding;
import com.example.lostnexus.databinding.LayoutRegisterBinding;

public class LoignActivity extends AppCompatActivity {

    LayoutLoginBinding loginBinding;
    FragmentManager fragmentManager;
    LoginViewModel mainViewModel;
    LayoutRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mainViewModel = new LoginViewModel();
        loginBinding = DataBindingUtil.setContentView(this, R.layout.layout_login);
        loginBinding.setLoginViewModel(mainViewModel);
        loginBinding.setClickHandler(new ClickHandler(this));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewModel.getIsLogIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {

                if (bool) {
                    Intent intent = new Intent(LoignActivity.this , MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}



