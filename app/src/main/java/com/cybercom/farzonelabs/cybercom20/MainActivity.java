package com.cybercom.farzonelabs.cybercom20;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pushwoosh.fragment.PushEventListener;
import com.pushwoosh.fragment.PushFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, PushEventListener {

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private static final String TAG = MainActivity.class.getSimpleName();


    private final ScheduleFragment mScheduleFragment = new ScheduleFragment();
    private final SongbookCardFragment mSongbookFragment = new SongbookCardFragment();
    private final FlowFragment mFlowFragment = new FlowFragment();

    private Handler mDrawerActionHandler = new Handler();
    public static DrawerLayout mDrawerLayout;
    public static Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "@onCreate");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        setContentView(R.layout.activity_main);

        PushFragment.init(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.drawer_item_1;
            AnimationClass.PREV_BACKGROUND = getWindow().getDecorView().getBackground();
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        // listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // Set correct font for mTitle. and drawer header
        TextView mToolbarTextView = getActionBarTextView();
        mToolbarTextView.setTypeface(Typeface.createFromAsset(getAssets(), getString(R.string.font_caecilia)));

        TextView mDrawerHeader = (TextView) findViewById(R.id.drawer_header_textview);
        mDrawerHeader.setTypeface(Typeface.createFromAsset(getAssets(), getString(R.string.font_caecilia)));

        navigate(mNavItemId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "@onNewIntent");
        PushFragment.onNewIntent(this, intent);

        /*if (intent == null) {
            Log.i(TAG, "intent == null");
            return;
        } else {
            final String songName = intent.getStringExtra(getString(R.string.EXTRA_SONG_TITLE));
            Log.i(TAG, "songName: " + songName);
        }*/
    }

    private TextView getActionBarTextView() {
        TextView titleTextView = null;

        try {
            Field f = mToolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(mToolbar);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return titleTextView;
    }

    /*
        Set custom font for the a given string
    */

    private String customFont(String text, int fontRes, Resources resources) {

        SpannableString spannableString = new SpannableString(text);

        // Create the Typeface you want to apply to certain text
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(
                TypefaceUtils.load(resources.getAssets(), getString(fontRes)));
        // Apply typeface to the Spannable 0 - 6 "Hello!" This can of course by dynamic.
        int len = spannableString.length();
        spannableString.setSpan(typefaceSpan, 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString.toString();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Performs the actual navigation logic, updating the main content fragment.
     */
    private void navigate(final int itemId) {

        switch (itemId) {
            case R.id.drawer_item_1:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mScheduleFragment)
                        .commit();
                break;
            case R.id.drawer_item_2:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mSongbookFragment)
                        .commit();
                break;
            case R.id.drawer_item_3:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mFlowFragment)
                        .commit();
            default:
                // ignore
                break;
        }
    }


    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);

        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    @Override
    public void doOnUnregisteredError(String s) {
        Log.i(TAG, "@doOnUnregisteredError");
        Log.i(TAG, s);
    }

    @Override
    public void doOnRegisteredError(String s) {
        Log.i(TAG, "@doOnRegisteredError");
        Log.i(TAG, s);
    }

    @Override
    public void doOnRegistered(String s) {
        Log.i(TAG, "@doOnRegistered");
        Log.i(TAG, s);
    }

    @Override
    public void doOnMessageReceive(String s) {
        Log.i(TAG, "@doOnMessageReceive");
        Log.i(TAG, s);

        if (s != null) {
            String songId = getSongId(s);
            if (songId != null) {
                try {
                    SnapsSong song = getSongByIdFromDb(Integer.parseInt(songId));
                    if (song != null) {
                        Intent intent = new Intent(this, SongbookDetailActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        intent.putExtra(getString(R.string.EXTRA_SONG_OBJECT), song);

                        startActivity(intent);
                    } else {
                        Log.i(TAG, "song == null");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doOnUnregistered(String s) {
        Log.i(TAG, "@doOnUnregistered");
        Log.i(TAG, s);
    }

    private String getSongId(String data) {
        Log.i(TAG, "@getSongId");

        try {
            JSONObject jsonObjectAll = new JSONObject(data);
            JSONObject jsonObjectU = new JSONObject(jsonObjectAll.getString("u"));
            return jsonObjectU.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SnapsSong getSongByIdFromDb(int id) {
        SongbookDatabase db = new SongbookDatabase(this);
        return db.getSnapsSongBySongId(id);
    }
}