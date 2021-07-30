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

    private static TabButton auto_nav, settings;

    final public static String TAG = HomeFragment.class.getSimpleName();

    private static MapxusFragment mapxusFragment;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCreateView: HOMEFRAGMENT CREATED. DICK AND BALLS INCLUDED. ");
        auto_nav = new TabButton(v.findViewById(R.id.auto_nav));
        auto_nav.linkToFragment(1, getParentFragmentManager().beginTransaction());

        if(mapxusFragment == null) {
            Log.d(TAG, "onCreateView: OH NO! MAPXUS FRAGMENT WAS NULL and wanted to FUCK ME IN THE ASS.");
            mapxusFragment = new MapxusFragment();
        }
        getParentFragmentManager().beginTransaction().replace(R.id.main_container, mapxusFragment);

        settings = new TabButton(v.findViewById(R.id.settings));
        settings.linkToFragment(2, getParentFragmentManager().beginTransaction());
        
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
