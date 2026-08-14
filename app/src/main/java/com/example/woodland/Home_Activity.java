package com.example.woodland;

import static androidx.core.app.ActivityCompat.finishAffinity;
import static com.example.woodland.R.id.homeBottomNaVigationPage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.woodland.Fragement.Categories_Fragement;
import com.example.woodland.Fragement.Chart_Fragment;
import com.example.woodland.Fragement.Entertenment_Fragement;
import com.example.woodland.Fragement.home_fragement;
import com.example.woodland.GoogeMap.my_location;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    boolean doubleTap = false;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    BottomNavigationView homeBottomNaVigationPage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeBottomNaVigationPage=findViewById(R.id.homeBottomNaVigationPage);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFrameLayout, new home_fragement())
                .commit();
        homeBottomNaVigationPage.setOnNavigationItemSelectedListener(this);
        homeBottomNaVigationPage.setSelectedItemId(R.id.homeBottomNaVigationPage);
        preferences = PreferenceManager.getDefaultSharedPreferences(Home_Activity.this);
        editor =preferences.edit();
        boolean isFirstTime=preferences.getBoolean("isFirstTime",true);
        if (isFirstTime){
            welcome();
        }
    }
    private void welcome() {
        AlertDialog.Builder ab =new AlertDialog.Builder(Home_Activity.this);
        ab.setTitle("WOODLAND");
        ab.setMessage("Welcome To WOODLAND");
        ab.setPositiveButton("Thank You", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("isFirstTime",false).commit();
                dialog.cancel();
            }
        }).show().create();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuHomeMyProfile) {
            Intent i = new Intent(Home_Activity.this, my_profile.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.menuHomeSetting) {
            Toast.makeText(Home_Activity.this,
                    "Setting Click",
                    Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.menuHomeContact) {
            Intent i = new Intent(Home_Activity.this, contact_us.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.menuHomeAbout) {
            Intent i = new Intent(Home_Activity.this, about_us.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.menuHomeLogOut) {
            logout();
        } else if (item.getItemId()==R.id.menuHomeMap) {
            Intent i = new Intent(Home_Activity.this, my_location.class);
            startActivity(i);
        }
    else if (item.getItemId()==R.id.menuHomeScanME) {
        Intent i = new Intent(Home_Activity.this, QRCodeScannerActivity.class);
        startActivity(i);
    }
        return true;
    }
    @SuppressLint("GestureBackNavigation")
    private void logout(){
        AlertDialog.Builder ad = new AlertDialog.Builder(Home_Activity.this);
        ad.setTitle("LogOut");
        ad.setMessage("Are you want to LogOut?");
        ad.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setNegativeButton("LOGOUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("isLogin",false).commit() ;
                Intent i = new Intent(Home_Activity.this, Login_activity.class);
                startActivity(i);
                finishAffinity();
            }
        }).show().create();
    }
    @SuppressLint({"GestureBackNavigation", "MissingSuperCall"})
    @Override
    public void onBackPressed() {

        if (doubleTap) {
            finishAffinity();
        } else {
            doubleTap = true;

            Toast.makeText(this,
                    "Press again to exit",
                    Toast.LENGTH_SHORT).show();

            new Handler(getMainLooper()).postDelayed(() ->
                    doubleTap = false, 2000);
        }
    }

    home_fragement home_fragement =new home_fragement();
    Categories_Fragement Categories_Fragement =new Categories_Fragement();
    Entertenment_Fragement Entertenment_Fragement =new Entertenment_Fragement();
    Chart_Fragment Chart_Fragment = new Chart_Fragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.bottomHomeNavigation) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayout, home_fragement)
                    .commit();

        } else if (menuItem.getItemId() == R.id.bottomaCategoriesNavigation) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayout, Categories_Fragement)
                    .commit();

        } else if (menuItem.getItemId() == R.id.bottomEntertenmentNavigation) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayout, Entertenment_Fragement)
                    .commit();

        } else if (menuItem.getItemId() == R.id.bottomaCartNavigation) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayout, Chart_Fragment)
                    .commit();
        }
        return true;
    }
}
