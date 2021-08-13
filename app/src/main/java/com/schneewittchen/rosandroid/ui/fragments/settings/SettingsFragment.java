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

import com.schneewittchen.rosandroid.ui.general.TabButton;

public class SettingsFragment extends Fragment {

    private Button languageButton;
    private boolean chinese = false;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        TabButton homeButton = new TabButton(v.findViewById(R.id.home_button));
        homeButton.linkToFragment(0, getParentFragmentManager().beginTransaction());

        TabButton masterButton = new TabButton(v.findViewById(R.id.master_button));
        masterButton.linkToFragment(3, getParentFragmentManager().beginTransaction());

        TabButton sshButton = new TabButton(v .findViewById(R.id.ssh_button));
        sshButton.linkToFragment(4, getParentFragmentManager().beginTransaction());

        languageButton = v.findViewById(R.id.language_button);
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chinese = !chinese;

                if (chinese) {
                    // Switch language to Chinese
                } else {
                    // Switch language to English
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

