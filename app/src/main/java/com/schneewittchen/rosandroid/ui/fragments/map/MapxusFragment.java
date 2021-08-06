package com.schneewittchen.rosandroid.ui.fragments.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapxus.map.mapxusmap.api.map.MapViewProvider;
import com.mapxus.map.mapxusmap.api.map.MapxusMap;
import com.mapxus.map.mapxusmap.api.map.interfaces.OnMapxusMapReadyCallback;
import com.mapxus.map.mapxusmap.impl.MapboxMapViewProvider;
import com.schneewittchen.rosandroid.R;

import org.jetbrains.annotations.NotNull;

import com.schneewittchen.rosandroid.ui.fragments.home.HomeFragment;
import com.schneewittchen.rosandroid.ui.general.TabButton;

public class MapxusFragment extends Fragment implements OnMapReadyCallback, OnMapxusMapReadyCallback {

    MapView mapView;
    MapViewProvider mapViewProvider;
    MapxusMap mapxusMap;
    MapboxMap mapboxMap;

    private static TabButton homeButton;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapxus_map, container, false);

        mapView = v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mapViewProvider = new MapboxMapViewProvider(requireActivity(), mapView);
        mapViewProvider.getMapxusMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(@NonNull @NotNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.33446, 114.263551), 18), 500);

        mapboxMap.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                // show camera coordinate in developer mode

                LatLng latLng = mapboxMap.getCameraPosition().target;

                String latString = String.format("%.6f", latLng.getLatitude());
                String lngString = String.format("%.6f", latLng.getLongitude());

                double bearing = mapboxMap.getCameraPosition().bearing;
                double zoomLevel = mapboxMap.getCameraPosition().zoom;

                String message = String.format("Camera: %s,%s\nBearing: %f Zoom lv: %f",
                        latString, lngString, bearing, zoomLevel);

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeButton = new TabButton(view.findViewById(R.id.home_button_mapxus));
        homeButton.linkToFragment(0, getParentFragmentManager().beginTransaction());
    }

    @Override
    public void onMapxusMapReady(MapxusMap mapxusMap) {
        this.mapxusMap = mapxusMap;
    }
}

