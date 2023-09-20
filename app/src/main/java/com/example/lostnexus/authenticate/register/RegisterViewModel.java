package com.example.lostnexus.authenticate.register;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.example.lostnexus.authenticate.validators.InputValidator;
import com.example.lostnexus.models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterViewModel extends BaseObservable {

    String name="", contact="" , email="" , password="";
    Boolean registerEnable=false;
    MutableLiveData<Boolean> isRegistered;
FirebaseAuth auth;

@Bindable
    public Boolean getRegisterEnable() {
        return registerEnable;

    }

    public void setRegisterEnable(Boolean registerEnable) {
        this.registerEnable = registerEnable;
        notifyChange();
    }

    public RegisterViewModel()
{
    auth = FirebaseAuth.getInstance();
    isRegistered =  new MutableLiveData<>(false);
}
@Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println(name);

    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;

        setRegisterEnable( isInputValid());

    }
@Bindable
    public MutableLiveData<Boolean> getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(MutableLiveData<Boolean> isRegistered) {
//        this.isRegistered = isRegistered;
        isRegistered.setValue(isRegistered.getValue());
        notifyChange();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setRegisterEnable( isInputValid());

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
       setRegisterEnable( isInputValid());

    }

    public void onSignUpClicked()
    {
        System.out.println(email + "" +password +"4");
        System.out.println("clicked");
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("completed");
                    verifyemail();

                }
            }
        }). addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            System.out.println("failed");
        }
    });
    }

    private void verifyemail()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("running ");
                        createProfile(auth.getCurrentUser().getUid());
                        auth.signOut();
                        isRegistered.setValue(true);

                    }

                });

            }
        }).start();
System.out.println("outside runnable");
    }

public boolean isInputValid()
{
    Boolean bool = InputValidator.isContactValid(contact) &&
            InputValidator.isEmailValid(email) &&
            !name.equals("")&&
            InputValidator.isPasswordValid(password);
    System.out.println(bool);
    return bool;

}
    private void createProfile(String uid)
    {
System.out.println("profile created");
DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("UserProfile");
reference.child(uid).setValue(new UserProfile(name , contact , "https://firebasestorage.googleapis.com/v0/b/lostnexus0101.appspot.com/o/peakpx.jpg?alt=media&token=2a3f538f-e8ac-48f0-9da0-d74839670f56", "d","v" , "d" ,"d" ,"s"));

    }



}
