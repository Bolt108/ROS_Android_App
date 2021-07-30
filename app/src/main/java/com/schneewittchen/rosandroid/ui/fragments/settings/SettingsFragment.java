package com.schneewittchen.rosandroid.ui.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.map.MapxusFragment;

import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment {

    Button auto_nav;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        auto_nav = v.findViewById(R.id.btn1);
        auto_nav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MapxusFragment mapxusFrag = new MapxusFragment();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.main_container, mapxusFrag);
                ft.commit();

            }
        }

        );
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

