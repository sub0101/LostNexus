package com.example.lostnexus;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lostnexus.databinding.LostAddFragmentBinding;
import com.example.lostnexus.models.LostItem;
import com.example.lostnexus.viewmodels.LostItemViewModel;
import com.example.lostnexus.viewmodels.MainViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.IOException;
import java.util.List;


public class ItemAddFragment extends AppCompatActivity {

LostAddFragmentBinding lostAddFragmentBinding;
LostItemViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     lostAddFragmentBinding = LostAddFragmentBinding.inflate(getLayoutInflater());
        setContentView(lostAddFragmentBinding.getRoot());
        mainViewModel = new ViewModelProvider(this).get(LostItemViewModel.class);
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
        mainViewModel.getLostItemLiveData().observe(this, new Observer<LostItem>() {
            @Override
            public void onChanged(LostItem lostItem) {
                System.out.println("inside lost item mutable data oberserver");
                Toast.makeText(getApplicationContext() , "indise", Toast.LENGTH_SHORT).show();
                lostAddFragmentBinding.setLostItem(lostItem);
            }
        });

//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

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
                    String st = result.getData().getStringExtra("address");
                    double latt = result.getData().getDoubleExtra("lat" , -1);
                    double longt = result.getData().getDoubleExtra("longt" , -1);
lostAddFragmentBinding.lattitude.setText(String.valueOf(latt));
lostAddFragmentBinding.longtitude.setText(String.valueOf(longt));
System.out.println(st + "this is the address");
System.out.println(latt + " "+longt);
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
        Uri selectedImageUri = data.getData();
if(requestCode == 200){
    lostAddFragmentBinding.itemImage.setImageURI(selectedImageUri);
    mainViewModel.validate();
    mainViewModel.getLostItemLiveData().getValue().setImage(String.valueOf(selectedImageUri));
}
    }


    void setFousChangeListner(){
        lostAddFragmentBinding.detail.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.date.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.time.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.itemType.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.location.setOnFocusChangeListener(new FocusChangeListner());
        lostAddFragmentBinding.nearby.setOnFocusChangeListener(new FocusChangeListner());

    }


    public class FocusChangeListner implements View.OnFocusChangeListener{


        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (hasFocus) {


                if (v == lostAddFragmentBinding.date) {
                    addDate();
                }
                else if(v==lostAddFragmentBinding.time) addTime();

                TextView textView = (TextView) v;
                String val = textView.getText().toString();
                if (val.equals("")) {
                    ((TextView) v).setError("required");
                }

                if (mainViewModel.validate()) {
                    lostAddFragmentBinding.addItem.setEnabled(true);
                }
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
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                    }
                }, 0,0, false);
        timePickerDialog.show();

    }

   public  void gotolocation(View view){
String lat = mainViewModel.getLostItemLiveData().getValue().getLattitude();
String longt = mainViewModel.getLostItemLiveData().getValue().getLongtitude();
String address = mainViewModel.getLostItemLiveData().getValue().getLocation();

       if(lat!="" && longt!="")
       {
           Uri mapUri = Uri.parse("geo:0,0?q=" +lat+","+ longt+"(label)");
           Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
           mapIntent.setPackage("com.google.android.apps.maps");
           startActivity(mapIntent);
       }
       else if(!address.equals("")){
//       String []str  =   getLonglat(address);

           Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
//           System.out.println(str[0] + " " + str[1] + "is itherere");
//           Uri mapUri = Uri.parse("geo:0,0?q=" +str[0]+","+ str[1]+"(label)");

           Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
           mapIntent.setPackage("com.google.android.apps.maps");
           startActivity(mapIntent);
       }
       else {
           Toast.makeText(this ,"please select location or add address of the location" ,Toast.LENGTH_SHORT ).show();
       }

    }
//    private String[] getLonglat(String strAddress){
//        Geocoder coder = new Geocoder(this);
//        List<Address> address;
//        Address location = null;
//        try {
//            address = coder.getFromLocationName(strAddress, 2);
//            if (address == null) {
//                return null;
//            }
//           location = address.get(0);
//            location.getLatitude();
//            location.getLongitude();
//System.out.println(location.getLatitude() + " " +location.getLongitude() +"is real");
////            p1 = new LatLng((double) (location.getLatitude() * 1E6),
////                    (double) (location.getLongitude() * 1E6));
//
////            return p1;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String str[] = {String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())};
//   return str;
//
//    }


}