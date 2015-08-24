package com.cybercom.farzonelabs.cybercom20;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by empet1 on 2015-07-22.
 */
public class Utils {

    public static void printIntentData(final String TAG, Bundle bundle) {
        Log.i(TAG, "TAG: " + TAG);
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.d(TAG, String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }
    }

    public static String getUuid(Context context) {
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getDeviceId();
    }
}
