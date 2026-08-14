package com.example.woodland.GoogeMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;

import com.example.woodland.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.woodland.databinding.ActivityMyLocationBinding;

import java.io.IOException;
import java.util.List;

public class my_location extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMyLocationBinding binding;
    LocationManager locationManager;
    public final int SERVICE_CODE = 999;
    double latitude, longitude;
    String address;


    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMyLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission.ACCESS_FINE_LOCATION}, 999);
        } else if (ActivityCompat.checkSelfPermission(this,
                permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission.ACCESS_COARSE_LOCATION}, 999);
        }
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000,
                    10,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Geocoder geocoder = new Geocoder(my_location.this);
                            try {
                                List<Address> addresslist = geocoder.getFromLocation(latitude, longitude, 1);
                                address = addresslist.get(0).getAddressLine(0) + "," + addresslist.get(0).getLocality() + addresslist.get(0).getCountryName();
                                LatLng Location = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position((Location)).title(address));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Location, 16), 4000, null);
                                mMap.addCircle(new CircleOptions()
                                        .center(Location)
                                        .fillColor(Color.parseColor("#33007AFF"))
                                        .strokeColor(Color.parseColor("#007AFF"))
                                        .radius(150));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        }
        if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10000,
                    10,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Geocoder geocoder = new Geocoder(my_location.this);
                            try {
                                List<Address> addresslist = geocoder.getFromLocation(latitude, longitude, 1);
                                address = addresslist.get(0).getAddressLine(0) + "," + addresslist.get(0).getLocality() + addresslist.get(0).getCountryName();
                                LatLng Location = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position((Location)).title(address));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Location, 16), 4000, null);
                                mMap.addCircle(new CircleOptions()
                                        .center(Location)
                                        .fillColor(Color.parseColor("#33007AFF"))
                                        .strokeColor(Color.parseColor("#007AFF"))
                                        .radius(150));

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.mapTerrain)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        else if (item.getItemId()==R.id.mapSatellite)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if (item.getItemId()==R.id.mapHybrid)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        }
        else if (item.getItemId()==R.id.mapNormal)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

    }
}
