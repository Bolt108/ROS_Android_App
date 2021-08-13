package com.schneewittchen.rosandroid.ui.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.map.MapxusFragment;

import org.jetbrains.annotations.NotNull;

import com.schneewittchen.rosandroid.ui.general.TabButton;

public class HomeFragment extends Fragment {

    private static TabButton autoNav, settings, joystick, shc, robotArm, manualControl;

    final public static String TAG = HomeFragment.class.getSimpleName();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCreateView: HOMEFRAGMENT CREATED. DICK AND BALLS INCLUDED. ");

        (autoNav = new TabButton(v.findViewById(R.id.auto_nav))).linkToFragment(1, getParentFragmentManager().beginTransaction());

        (settings = new TabButton(v.findViewById(R.id.settings))).linkToFragment(2, getParentFragmentManager().beginTransaction());

        (shc = new TabButton(v.findViewById(R.id.shc))).linkToFragment(5, getParentFragmentManager().beginTransaction());

        (robotArm = new TabButton(v.findViewById(R.id.robotArm))).linkToFragment(6, getParentFragmentManager().beginTransaction());

        (manualControl = new TabButton(v.findViewById(R.id.manualControl))).linkToFragment(7, getParentFragmentManager().beginTransaction());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
