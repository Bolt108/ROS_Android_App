package com.schneewittchen.rosandroid.ui.fragments.home;

<<<<<<< Updated upstream
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {




=======
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.fragments.main.OnBackPressedListener;
import com.schneewittchen.rosandroid.ui.fragments.map.MapxusFragment;

import org.jetbrains.annotations.NotNull;

import com.schneewittchen.rosandroid.ui.general.TabButton;

public class HomeFragment extends Fragment {

    private static TabButton auto_nav, settings;

    private Button autoNav;

    final public static String TAG = HomeFragment.class.getSimpleName();

    private static MapxusFragment mapxusFragment;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        Log.d(TAG, "onCreateView: HOMEFRAGMENT CREATED. DICK AND BALLS INCLUDED.");
//        Activity th = requireActivity();
//        NavController nv =  Navigation.findNavController(th, R.id.fragment_container_new);        Log.d(TAG, "onCreateView: still alive with my cock.");
//        Log.d(TAG, "onCreateView: still alive with my cock.");

//        auto_nav = new TabButton(Navigation.findNavController(th, R.id.fragment_container), v.findViewById(R.id.auto_nav));
//        auto_nav.linkToFragment(1);
//        if(mapxusFragment == null) {
//            Log.d(TAG, "onCreateView: OH NO! MAPXUS FRAGMENT WAS NULL and wanted to FUCK ME IN THE ASS.");
//            mapxusFragment = new MapxusFragment();
//        }
//        settings = new TabButton(Navigation.findNavController(requireActivity(), R.id.fragment_container), v.findViewById(R.id.settings));
//        settings.linkToFragment(2);

        autoNav = v.findViewById(R.id.auto_nav);
        autoNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                Log.d(TAG, "onClick: button clicked, shitass.");
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container_new);
                NavController navCo = navHostFragment.getNavController();
                navCo.navigate(R.id.action_homeFragment_to_mapxusFragment);

            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
>>>>>>> Stashed changes
}
