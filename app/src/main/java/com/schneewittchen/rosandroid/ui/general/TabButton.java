package com.schneewittchen.rosandroid.ui.general;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.home.HomeFragment;
import com.schneewittchen.rosandroid.ui.fragments.map.MapxusFragment;
import com.schneewittchen.rosandroid.ui.fragments.master.MasterFragment;
import com.schneewittchen.rosandroid.ui.fragments.settings.SettingsFragment;

import java.lang.String;

public class TabButton {

    public final static String TAG = "TabButton";

    private Button btn;

    public TabButton(Button initButton) {btn = initButton;}

    public Button get() {return btn;}
    public void set(Button setButton) {btn = setButton;}

    public void linkToFragment(final int FragmentType, final FragmentTransaction ft) {
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
                        ft.replace(R.id.main_container, new HomeFragment());
                        break;
                    case 1:
                        ft.replace(R.id.main_container, new MapxusFragment());
                        break;
                    case 2:
                        ft.replace(R.id.main_container, new SettingsFragment());
                        break;
                    case 3:
                        ft.replace(R.id.main_container, new MasterFragment());
                        break;
                    default:
                        Log.e(TAG, "onClick: Fragment type invalid.");
                }
                ft.commit();
            }
        });
    }

}