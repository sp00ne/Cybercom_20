package com.cybercom.farzonelabs.cybercom20;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by empet1 on 2015-07-21.
 */
public class ParseApp extends Application {

    private static final String TAG = "ParseApp";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(getApplicationContext(), getString(R.string.APP_ID), getString(R.string.CLIENT_KEY));

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i(TAG, "success");
                } else {
                    Log.i(TAG, "error. e.getMessage(): " + e.getMessage());
                }
            }
        });

    }
}
