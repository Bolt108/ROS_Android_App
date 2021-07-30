package com.schneewittchen.rosandroid.ui.fragments.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.map.MapxusFragment;

import org.jetbrains.annotations.NotNull;

import com.schneewittchen.rosandroid.ui.general.TabButton;

public class SettingsFragment extends Fragment {

    private final static String TAG = SettingsFragment.class.getSimpleName();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        TabButton homeButton = new TabButton(v.findViewById(R.id.home_button));
        homeButton.linkToFragment(0, getParentFragmentManager().beginTransaction());

        TabButton masterButton = new TabButton(v.findViewById(R.id.master_button));
        masterButton.linkToFragment(3, getParentFragmentManager().beginTransaction());

        SwitchCompat sw = v.findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Log.d(TAG, "onCheckChanged: Johnny gets minimum wage :)");
                } else {
                    // The toggle is disabled
                    Log.d(TAG, "onCheckChanged: Johnny gets minimum wage :(");
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}

