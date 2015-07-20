package com.cybercom.farzonelabs.cybercom20;

/**
 * Created by mofar1 on 2015-07-13.
 */

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private Cursor mSongsInfo;
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
        Context context = mRecyclerView.getContext();

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
        mSongsInfo = db.getSongsInfo();

        mAdapter = new SongbookAdapter(mSongsInfo,mRecyclerView.getContext());
        mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));

        /*
        // Set the card elevation.
        mCardView = (CardView) getActivity().findViewById(R.layout.song_item);
        mCardView.setCardElevation(CARD_ELEVATION);
    */
        return mRecyclerView;
    }
}

