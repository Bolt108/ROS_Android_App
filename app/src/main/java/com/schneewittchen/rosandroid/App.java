package com.schneewittchen.rosandroid;

import com.mapxus.map.mapxusmap.api.map.MapxusMapContext;

public class App extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MapxusMapContext.init(getApplicationContext());//, "com.liphy.light.android.app", "/I3eV0lc");

    }
}
