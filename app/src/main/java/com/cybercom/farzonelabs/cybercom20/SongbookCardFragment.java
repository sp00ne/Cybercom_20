package com.cybercom.farzonelabs.cybercom20;

/**
 * Created by mofar1 on 2015-07-13.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class SongbookCardFragment extends Fragment {

    /**
     * Card elevation in pixels
     */
    private final static float CARD_ELEVATION = 8;
    private static final String TAG = SongbookCardFragment.class.getSimpleName();

    /**
     * The recycler view that handles the cards
     */
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Cursors for data handling
     */
    private ArrayList<SnapsSong> mSongsInfo;
    private Cursor mSongsText;

    /**
     * The pre-populated database
     */
    private SongbookDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflateAndSetup(container);
    }


    private View inflateAndSetup(ViewGroup container) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_songbook, container, false);
        final Context context = mRecyclerView.getContext();

        //Init the color animation if there is one and assign the new PREV_BACKGROUND
        if (AnimationClass.PREV_BACKGROUND != null) {
            AnimationClass.switchColor(2, mRecyclerView);
        }
        AnimationClass.PREV_BACKGROUND = mRecyclerView.getBackground();

        // Assign layout manager for recycler view
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Assign adapter
        db = new SongbookDatabase(context);
        mSongsInfo = db.getSongsInfoArrayList();

        mAdapter = new SongbookAdapter(mSongsInfo, mRecyclerView.getContext());
        mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));

        // Add listeners
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SnapsSong clickedSong = mSongsInfo.get(position);
                Log.d("RecyclerView", "onItemClick: Position " + position);
                Log.d("RecyclerView", "Title: " + clickedSong.getTitle());
            }

            @Override
            public void onItemLongClick(View view, int position) {
                SnapsSong clickedSong = mSongsInfo.get(position);
                Log.d(TAG, "onItemLongClick: Position " + position);
                Log.d(TAG, "Title: " + clickedSong.getTitle());

                final String uuid = Utils.getUuid(context);

                if (uuid == null) {
                    return;
                }

//                if (!uuid.equals(getString(R.string.uuid_emil))) {
//                    return;
//                }

                pushAlertDialog(position);
            }
        }));

        return mRecyclerView;
    }

    private void sendPush(int position) {
        ParsePush push = new ParsePush();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", String.valueOf(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        push.setData(jsonObject);
        push.setChannel("");
        push.sendInBackground();
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i(TAG, "success");
                } else {
                    Log.i(TAG, "fail");
                    Log.i(TAG, "e.getMessage(): " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void pushAlertDialog(final int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Skicka push?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendPush(position);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }
}

