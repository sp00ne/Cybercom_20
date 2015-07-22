package com.cybercom.farzonelabs.cybercom20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * When pressing the card in the Songbook fragment, this activity is started,
 * providing the details such as:
 * categoryImage (from categoryNumber, songText, favorite etc.)
 * Created by mofar1 on 2015-07-20.
 */
public class SongbookDetailActivity extends AppCompatActivity{

    /**
     * (KEY) The song ID that is pushed in via the intent.
     */
    public static final String SONG_POSITION = "song_position";

    /**
     * (KEY) Title of the song picked or received from push
     */
    public static final String SONG_TITLE = "song_title";

    /**
     * (KEY) for the string category
     */
    public static final String SONG_CATEGORY = "song_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songbook_detail);

        Intent intent = getIntent();
        final String songPosition = intent.getStringExtra(SONG_POSITION);
        final String songTitle = intent.getStringExtra(SONG_TITLE);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(songTitle);

        // Extract the category of the chosen song for the backdrop
        final int songCategory = intent.getIntExtra(SONG_CATEGORY,1);
        loadBackdrop(songCategory);

    }

    private void loadBackdrop(int category) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

       // Retrieve the id of the drawable based on the category
        int categoryDrawableId = SnapsSong.getCategoryDrawable(category);

        // Apply it to the backdrop using Glide
        Glide.with(this).load(categoryDrawableId).centerCrop().into(imageView);
    }
}
