package com.cybercom.farzonelabs.cybercom20;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Loads the precreated database from the assets folder
 * Created by mofar1 on 2015-07-17.
 */
public class SongbookDatabase extends SQLiteAssetHelper {

    /**
     * The name of the database file in the asset folder
     */
    private static final String DATABASE_NAME = "songbook.db";

    /**
     * The file version
     */
    private static final int DATABASE_VERSION = 1;

    private Context mContext;

    public interface TABLES {
        String SONGS = "SONGS";
    }

    public interface COLUMNS {
        String id = "ID";
        String title = "TITLE";
        String author = "AUTHOR";
        String melody = "MELODY";
        String songtext = "SONGTEXT";
        String category = "CATEGORY";
    }

    public SongbookDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }


    public Cursor getSongsInfo() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {COLUMNS.id, COLUMNS.title, COLUMNS.author, COLUMNS.melody, COLUMNS.category};
        String sqlTables = TABLES.SONGS;

        queryBuilder.setTables(sqlTables);
        Cursor c = queryBuilder.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        db.close();

        return c;
    }

    public SnapsSong getSnapsSongBySongId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] SELECT = {COLUMNS.id, COLUMNS.title, COLUMNS.author, COLUMNS.melody, COLUMNS.category};
        String FROM = TABLES.SONGS;
        String WHERE = "ID=" + id;

        queryBuilder.setTables(FROM);
        Cursor c = queryBuilder.query(db, SELECT, WHERE, null,
                null, null, null);

        c.moveToFirst();

        SnapsSong snapsSong = convertCursorToArraylist(c).get(0);

        return snapsSong;
    }

    public ArrayList<SnapsSong> getSongsInfoArrayList() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {COLUMNS.id, COLUMNS.title, COLUMNS.author, COLUMNS.melody, COLUMNS.category};
        String sqlTables = TABLES.SONGS;

        queryBuilder.setTables(sqlTables);
        Cursor c = queryBuilder.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        db.close();

        return convertCursorToArraylist(c);
    }

    public String getSongTextBySongId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {COLUMNS.songtext};
        String sqlWhere = "ID=" + id;
        String sqlTables = TABLES.SONGS;

        queryBuilder.setTables(sqlTables);
        Cursor c = queryBuilder.query(db, sqlSelect, sqlWhere, null, null, null, null);

        c.moveToFirst();
        String songText = c.getString(c.getColumnIndex(COLUMNS.songtext));

        return songText;
    }

    private ArrayList<SnapsSong> convertCursorToArraylist(Cursor dbSongInfo) {
        ArrayList<SnapsSong> snapsSongs = new ArrayList<>();

        dbSongInfo.moveToFirst();
        while (!dbSongInfo.isAfterLast()) {
            SnapsSong snapsSong = new SnapsSong();
            String title = dbSongInfo.getString(dbSongInfo.getColumnIndex(COLUMNS.title));
            String author = dbSongInfo.getString(dbSongInfo.getColumnIndex(COLUMNS.author));
            String melody = dbSongInfo.getString(dbSongInfo.getColumnIndex(COLUMNS.melody));
            snapsSong.setSongInfo(title, melody, author);

            snapsSongs.add(snapsSong); //add the item
            dbSongInfo.moveToNext();
        }
        return snapsSongs;
    }
}
