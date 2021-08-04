package com.schneewittchen.rosandroid.ui.general;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.home.HomeFragment;
import com.schneewittchen.rosandroid.ui.fragments.map.MapxusFragment;
import com.schneewittchen.rosandroid.ui.fragments.master.MasterFragment;
import com.schneewittchen.rosandroid.ui.fragments.settings.SettingsFragment;

import java.lang.String;

public class TabButton {

    public final static String TAG = "TabButton";

    private Button btn;

    private NavController nav;

    public TabButton(NavController navController, Button initButton) {
        btn = initButton;
        nav = navController;
    }

    public Button get() {return btn;}
    public void set(Button setButton) {btn = setButton;}

    public void linkToFragment(final int FragmentType) {
        /*
        FragmentType : FragmentName
        0 : HomeFragment
        1 : MapxusFragment
        2 : SettingsFragment
        3 : MasterFragment
         */

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (FragmentType) {
                    case 0:
                        nav.navigate(R.id.action_to_homeFragment);
                        break;
                    case 1:
                        nav.navigate(R.id.action_to_mapxusFragment);
                        break;
                    case 2:
                        nav.navigate(R.id.action_to_settingsFragment);
                        break;
                    case 3:
                        nav.navigate(R.id.action_to_masterFragment);
                        break;
                    default:
                        Log.e(TAG, "onClick: Fragment type invalid.");
                }
            }
        });
    }

}
