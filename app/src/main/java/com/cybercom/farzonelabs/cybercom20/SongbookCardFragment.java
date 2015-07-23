package com.cybercom.farzonelabs.cybercom20;

/**
 * Created by mofar1 on 2015-07-13.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class SongbookCardFragment extends Fragment {

    /**
     * Card elevation in pixels
     */
    private final static float CARD_ELEVATION = 8;

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

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_songbook, container, false);
        final Context context = mRecyclerView.getContext();

        //Init the color animation if there is one and assign the new PREV_BACKGROUND
        if(AnimationClass.PREV_BACKGROUND != null){
            AnimationClass.switchColor(2,mRecyclerView);
        }
        AnimationClass.PREV_BACKGROUND = mRecyclerView.getBackground();

        // Assign layout manager for recycler view
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Assign adapter
        db = new SongbookDatabase(context);
        mSongsInfo = db.getSongsInfoArrayList();

        mAdapter = new SongbookAdapter(mSongsInfo,mRecyclerView.getContext());
        mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));

        // Add listeners
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                // Extract clicked SnapsSong object
                SnapsSong clickedSong = mSongsInfo.get(position);

                // Query the song text from db. Since it is now requested for the detailed view
                // and set it to the clicked song object.
                int id = position + 1;
                String clickedSongText = db.getSongTextBySongId(id);
                clickedSong.setSongText(clickedSongText);

                // Create the intent of detailed song and start the new activity
                Intent intent = new Intent(context, SongbookDetailActivity.class);
                intent = intent.putExtra(getActivity().getString(R.string.EXTRA_SONG_OBJECT), clickedSong);
                startActivity(intent);

                Log.d("RecyclerView", "onItemClick: Position " + position);
                Log.d("RecyclerView", "Title: " + clickedSong.getTitle());

            }

            @Override
            public void onItemLongClick(View view, int position)
            {
                SnapsSong clickedSong = mSongsInfo.get(position);
                Log.d("RecyclerView", "onItemLongClick: Position " + position);
                Log.d("RecyclerView", "Title: " + clickedSong.getTitle());
            }
        }));

        return mRecyclerView;
    }
}

