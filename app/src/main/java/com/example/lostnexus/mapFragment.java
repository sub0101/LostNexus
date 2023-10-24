package com.example.lostnexus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.lostnexus.databinding.FragmentMapBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;


public class mapFragment extends AppCompatActivity implements OnMapReadyCallback {


    @SuppressLint("MissingPermission")

    private void detectAndShowDeviceLocaionMap() {
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnSuccessListener(location -> {
                        mLastLocation = location;
                        lat = location.getLatitude();
                        longt = location.getLongitude();
                        LatLng latLng = new LatLng(lat, longt);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));
                        addressFromlatLng(latLng);
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private boolean isGPSEnabled() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {

        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        return !(!gpsEnabled && !networkEnabled);
    }

    private static final String TAG = "LOCATION_PICKER_TAG";
    private static final int DEFAULT_ZOOM = 15;

    FragmentMapBinding mapBinding;
    GoogleMap mMap = null;
    Double lat = null;
    Double longt = null;
    String address = null;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public Location mLastLocation = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapBinding = FragmentMapBinding.inflate(getLayoutInflater());

        setContentView(mapBinding.getRoot());
        mapBinding.saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("lat", lat);
                intent.putExtra("longt", longt);
                intent.putExtra("address", address);
                setResult(200, intent);
                finish();
            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_api_key));
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        String apikey = getString(R.string.google_api_key).trim();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapBinding.currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGPSEnabled()) {
                    requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    Toast.makeText(getApplicationContext(), "get", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mapBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }
        detectAndShowDeviceLocaionMap();
    }

    private void addressFromlatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            Address address1 = addressList.get(0);

            address = address1.getAddressLine(0);
            System.out.println(addressList.size());
            String address_line = address1.getAddressLine(0);
            String coutnryname = address1.getCountryName();
            String sublocal = address1.getSubLocality();
            addMarker(latLng, "" + sublocal, address1.getAddressLine(0));
        } catch (Exception e) {

        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        System.out.println("inside the mapready");
        mMap = googleMap;
        requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                System.out.println("clicking on map");
                lat = latLng.latitude;
                longt = latLng.longitude;
                addressFromlatLng(latLng);

            }
        });

    }

    private ActivityResultLauncher<String> requestLocationPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {

            if (result) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMap.setMyLocationEnabled(true);
                pickCurrentPlace();

            } else {
            }
        }


    });

    // Set the map's camera position to the current location of the device.


    public void addMarker(LatLng latLng, String name, String address) {

        mMap.clear();
        System.out.println(name + " " + address);
        try {

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(name);
            markerOptions.snippet(address);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
            mapBinding.location.setText(address);
        } catch (Exception e) {
            Log.e(TAG, address);
        }
    }

    public void searchLocation(String string){

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(string,5);

            double latitude = (int) addressList.get(0).getLatitude();
            double lont = (int) addressList.get(0).getLongitude();
            LatLng latLng = new LatLng(latitude , lont);
String address = addressList.get(0).getAddressLine(0);
            addMarker(latLng  , addressList.get(0).getSubLocality(),  address );
        } catch (Exception e) {

        }
    }

}

