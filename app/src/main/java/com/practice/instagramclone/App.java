package com.practice.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("VQ0XV9lZz4WXigSjwPYZnS3dFr3Omgsv61xNHX56")
                // if defined
                .clientKey("rdf8WlxRfIyRpAjpHnSPkVzD1qnzkCt52qTRYQNb")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }

}
