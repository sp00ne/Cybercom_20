package com.cybercom.farzonelabs.cybercom20;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
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

        Parse.initialize(this, getString(R.string.APP_ID), getString(R.string.CLIENT_KEY));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "success");
                    storeUserUuid();
                } else {
                    Log.e(TAG, "failed to register", e);
                }
            }
        });
    }

    private void storeUserUuid() {
        SharedPreferences myPrefs = this.getSharedPreferences(
                getString(R.string.PREFS_UUID), MODE_PRIVATE);

        String uuid = myPrefs.getString(getString(R.string.PREFS_UUID_NUMBER), null);

        if (uuid == null) {
            uuid = Utils.getUuid(getApplicationContext());

            final SharedPreferences.Editor editor = getSharedPreferences(
                    getString(R.string.PREFS_UUID), MODE_PRIVATE)
                    .edit();

            editor.putString(getString(R.string.PREFS_UUID_NUMBER), uuid).commit();
        }
    }
}
