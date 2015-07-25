package com.cybercom.farzonelabs.cybercom20;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
                final String songId = getSongId(intent);
                getSongByIdFromDb(Integer.parseInt(songId));
                generateNotification(getSongByIdFromDb(Integer.parseInt(songId)));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void generateNotification(SnapsSong snapsSong) {
        if (snapsSong == null) {
            Log.i(TAG, "@generateNotification @snapsSong == null");
            return;
        }

        Intent intent = new Intent(mContext, SongbookDetailActivity.class);
        intent = intent.putExtra(mContext.getString(R.string.EXTRA_SONG_OBJECT), snapsSong);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext).setSmallIcon(R.drawable.ic_action_cybercom_symbol).setContentTitle("Nu ska det sjungas!").setContentText(snapsSong.getTitle()).setNumber(++numMessages).setAutoCancel(true);
        mBuilder.setContentIntent(contentIntent);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private String getSongId(Intent intent) {
        Log.i(TAG, "@getSongId");
        String jsonDataStr = intent.getExtras().getString(mContext.getString(R.string.PUSH_DATA));

//        Utils.printIntentData(TAG, intent.getExtras());


        if (jsonDataStr != null) {

            try {
                JSONObject jsonObject = new JSONObject(jsonDataStr);
                return jsonObject.getString(mContext.getString(R.string.id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "jsonDataStr == null");
        }

        return null;
    }

    private SnapsSong getSongByIdFromDb(int id) {
        SongbookDatabase db = new SongbookDatabase(mContext);
        return db.getSnapsSongBySongId(id);
    }
}
