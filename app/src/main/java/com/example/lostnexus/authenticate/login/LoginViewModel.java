package com.example.lostnexus.authenticate.login;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lostnexus.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends BaseObservable {

String email,password;
MutableLiveData<Boolean> isLogIn = new MutableLiveData<>(false);
FirebaseAuth auth = FirebaseAuth.getInstance();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public MutableLiveData<Boolean> getIsLogIn() {
        return isLogIn;
    }

    public void setIsLogIn(MutableLiveData<Boolean> isLogIn) {
        this.isLogIn = isLogIn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void onLoginClicked(View view)
    {

        System.out.println(email +   " "+password);
auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            if(!auth.getCurrentUser().isEmailVerified()){
                Toast.makeText(view.getContext()  , "Email is not varified " , Toast.LENGTH_SHORT).show();
                auth.signOut();
                return;
            }
            auth.getCurrentUser().getUid();
            isLogIn.setValue(true);
//           isLogIn = new MutableLiveData<>();

            Toast.makeText(view.getContext()  , "Login Successfully " , Toast.LENGTH_SHORT).show();

        }
        else {
//            System.out.println(task.getException().toString());

            Toast.makeText(view.getContext() ,"No User Found" , Toast.LENGTH_SHORT).show();
        }
    }
});
    }

}
