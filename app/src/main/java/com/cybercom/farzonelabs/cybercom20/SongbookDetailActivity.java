package com.cybercom.farzonelabs.cybercom20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * When pressing the card in the Songbook fragment, this activity is started,
 * providing the details such as:
 * categoryImage (from categoryNumber, songText, favorite etc.)
 * Created by mofar1 on 2015-07-20.
 */
public class SongbookDetailActivity extends AppCompatActivity {

    private static final String TAG = SongbookDetailActivity.class.getSimpleName();
    private SnapsSong mSnapsSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songbook_detail);

        Log.i(TAG, "@onCreate");

        mSnapsSong = getIntent().getParcelableExtra(getString(R.string.EXTRA_SONG_OBJECT));

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mSnapsSong.getTitle());

        // Extract the category of the chosen song for the backdrop
//        final int songCategory = intent.getIntExtra(SONG_CATEGORY, 1);
        loadBackdrop(mSnapsSong.getCategory());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "@onNewIntent");
        super.onNewIntent(intent);

        if (intent == null) {
            Log.i(TAG, "intent == null");
            return;
        } else {
            mSnapsSong = intent.getParcelableExtra(getString(R.string.EXTRA_SONG_OBJECT));
            Log.i(TAG, "mSnapsSong.toString(): " + mSnapsSong.toString());
            loadBackdrop(mSnapsSong.getCategory());
        }
    }

    private void loadBackdrop(int category) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

        // Retrieve the id of the drawable based on the category
        int categoryDrawableId = SnapsSong.getCategoryDrawable(category);

        // Apply it to the backdrop using Glide
        Glide.with(this).load(categoryDrawableId).centerCrop().into(imageView);
    }
}
