package com.example.lostnexus;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lostnexus.databinding.LostAddFragmentBinding;
import com.example.lostnexus.models.FoundItem;
import com.example.lostnexus.viewmodels.FoundItemViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;


public class ItemAddFragment extends AppCompatActivity {

LostAddFragmentBinding lostAddFragmentBinding;
FoundItemViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     lostAddFragmentBinding = LostAddFragmentBinding.inflate(getLayoutInflater());
        setContentView(lostAddFragmentBinding.getRoot());
        mainViewModel = new ViewModelProvider(this).get(FoundItemViewModel.class);
//        setStyle(DialogFragment.STYLE_NORMAL , R.style.AppTheme_FullScreenDialog);
setFousChangeListner();

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        lostAddFragmentBinding = DataBindingUtil.inflate(inflater , R.layout.lost_add_fragment ,container , false);
//
//        return lostAddFragmentBinding.getRoot();
//    }

    @Override
    public void onStart() {
        super.onStart();
        mainViewModel.getLostItemLiveData().observe(this, new Observer<FoundItem>() {
            @Override
            public void onChanged(FoundItem lostItem) {
                lostAddFragmentBinding.setLostItem(lostItem);
            }
        });

        List<String> items = Arrays.asList(getResources().getStringArray(R.array.items));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext() ,android.R.layout.simple_list_item_1 , items);
        lostAddFragmentBinding.itemTypeAutoComplete.setAdapter(arrayAdapter);
        lostAddFragmentBinding.itemTypeAutoComplete.setThreshold(1);

    }
    public void gotomap(View view)
    {
        Intent intent = new Intent(ItemAddFragment.this , mapFragment.class);

        startActivityIntent.launch(intent);

    }


    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
<<<<<<< HEAD
                    if(result==null) return;
=======
                    if(result.getResultCode()!=200) return;
>>>>>>> testHomeFragment
                    String st = result.getData().getStringExtra("address");
                    double latt = result.getData().getDoubleExtra("lat" , -1);
                    double longt = result.getData().getDoubleExtra("longt" , -1);
lostAddFragmentBinding.lattitude.setText(String.valueOf(latt));
lostAddFragmentBinding.longtitude.setText(String.valueOf(longt));
                }
            });

    public void addData(View view){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setTitle("Uploading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        mainViewModel.addLostItem(progressDialog);
        mainViewModel.shouldClose.observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
               if(aBoolean) finish();
            }
        });

    }
public void addImage(View view){
    Intent intent  = new Intent();
    intent.setType("image/");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent , "Select Picture"),200);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null) return ;
        Uri selectedImageUri = data.getData();
if(requestCode == 200){
    lostAddFragmentBinding.itemImage.setImageURI(selectedImageUri);

    mainViewModel.getLostItemLiveData().getValue().setImage(String.valueOf(selectedImageUri));
}
        mainViewModel.validate();
        validateData();
    }



    void setFousChangeListner(){

        lostAddFragmentBinding.detail.addTextChangedListener(new TextChangeListner());
        lostAddFragmentBinding.itemTypeAutoComplete.addTextChangedListener(new TextChangeListner());
        lostAddFragmentBinding.location.addTextChangedListener(new TextChangeListner());
        lostAddFragmentBinding.nearby.addTextChangedListener(new TextChangeListner());
        lostAddFragmentBinding.time.addTextChangedListener(new TextChangeListner());
        lostAddFragmentBinding.date.addTextChangedListener(new TextChangeListner());


lostAddFragmentBinding.detail.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.time.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.itemTypeAutoComplete.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.location.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.nearby.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.editimage.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.date.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTime();
            }
        });
        lostAddFragmentBinding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate();
            }
        });
    }

public class TextChangeListner implements  TextWatcher{

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        validateData();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
validateData();
System.out.println("yess");
    }

    @Override
    public void afterTextChanged(Editable s) {
validateData();
    }
}

    public class FocusChangeListner implements View.OnFocusChangeListener{


        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(!hasFocus){
                TextView textView = (TextView) v;
                String val = textView.getText().toString();
                System.out.println(v);
                if (val.equals("")) {
                    ((TextView) v).setError("required");
                }
                validateData();
            }
            if (hasFocus) {


                if (v == lostAddFragmentBinding.date) {
                    addDate();
                }

                else if(v==lostAddFragmentBinding.time) addTime();


                validateData();

            }
        }



    }


    public void addDate(){
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").
                build();

        datePicker.show(getSupportFragmentManager()  , "TAG");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                System.out.println("Date is selected " + datePicker.getHeaderText());
                lostAddFragmentBinding.date.setText(datePicker.getHeaderText());
            }
        });

    }

    public void addTime(){
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hour,
                                          int minute) {
                        String format,min="";
                        if (hour == 0){
                            hour += 12;

                            format = "AM";
                        } else if (hour == 12) {
                            format = "PM";
                        } else if (hour > 12) {
                            hour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }

                        if(minute >=0 && minute<10)
                        {
                            min =  "0"+minute;
                        }
                        else
                        {
                            min = String.valueOf(minute);
                        }
lostAddFragmentBinding.time.setText(hour + ":" + min+" "+format);
                    }
                }, 0,0, false);
        timePickerDialog.show();


    }


    void validateData(){
        if (mainViewModel.validate()) {
            lostAddFragmentBinding.addItem.setEnabled(true);
        }
        else {
            lostAddFragmentBinding.addItem.setEnabled(false);

        }
    }

}