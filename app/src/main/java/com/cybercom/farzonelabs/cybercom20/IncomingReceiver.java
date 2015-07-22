package com.cybercom.farzonelabs.cybercom20;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by empet1 on 2015-07-21.
 */
public class IncomingReceiver extends BroadcastReceiver {

    private static final String TAG = "IncomingReceiver";
    private Context mContext;
    private static final int NOTIFICATION_ID = 1;
    public static int numMessages = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "@onReceive");
        try {
            if (intent == null) {
                Log.i(TAG, "intent == null");
                return;
            } else {
                mContext = context;
                final String songTitle = getSongTitle(intent);
                generateNotification(context, songTitle, null, songTitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateNotification(Context context, String title, String json, String contenttext) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(context.getString(R.string.EXTRA_SONG_TITLE), title);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_action_cybercom_symbol).setContentTitle(title).setContentText(contenttext).setNumber(++numMessages).setAutoCancel(true);
        mBuilder.setContentIntent(contentIntent);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private String getSongTitle(Intent intent) {
        Log.i(TAG, "@getSongTitle");
        String jsonDataStr = intent.getExtras().getString(mContext.getString(R.string.PUSH_DATA));

        try {
            JSONObject jsonObject = new JSONObject(jsonDataStr);
            return jsonObject.getString(mContext.getString(R.string.song));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
