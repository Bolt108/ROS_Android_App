package com.schneewittchen.rosandroid.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
<<<<<<< Updated upstream
=======
import android.util.Log;
import android.view.View;
import android.widget.Button;
>>>>>>> Stashed changes

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
//import com.mapxus.map.mapxusmap.impl.SupportMapxusMapFragment;
import com.mapxus.map.mapxusmap.impl.SupportMapxusMapFragment;
import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.intro.IntroFragment;
import com.schneewittchen.rosandroid.ui.fragments.main.MainFragment;
import com.schneewittchen.rosandroid.ui.fragments.main.OnBackPressedListener;

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.1
 * @created on 16.01.20
 * @updated on 19.06.20
 * @modified by Nils Rottmann
 * @updated on 27.07.20
 * @modified by Nils Rottmann
 */
public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();
    private static final int LOCATION_PERM = 101;


    SupportMapxusMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        try {
            if (savedInstanceState == null && requiresIntro()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, IntroFragment.newInstance())
                        .commitNow();
            } else {
                Toolbar myToolbar = findViewById(R.id.toolbar);
                setSupportActionBar(myToolbar);

                if (savedInstanceState == null) {
                    
                    if(homeFragment == null) Log.d(TAG, "onCreate: goddamn balls");
                    
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, homeFragment)
                        .commitNow();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        this.requestPermissions();
<<<<<<< Updated upstream
=======

//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.main_container, homeFragment).commit();
>>>>>>> Stashed changes
    }

    @Override
    public void onBackPressed(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if(fragment instanceof OnBackPressedListener) {
            OnBackPressedListener listener = (OnBackPressedListener)fragment;

            if (listener.onBackPressed()){
                return;
            }
        }

        super.onBackPressed();

    }

    private void requestPermissions() {
        String[] permissions = new String[] {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERM);
    }

    // Check in required if update is available or onboarding has not been done yet
    private boolean requiresIntro() throws PackageManager.NameNotFoundException {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("introPrefs", MODE_PRIVATE);

        return (pref.getInt("VersionNumber", 0) != getPackageManager().getPackageInfo(getPackageName(),0).versionCode) ||
                !pref.getBoolean("CheckedIn", false);

    }
}
