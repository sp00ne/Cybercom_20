package com.cybercom.farzonelabs.cybercom20;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Song class used to create the song objects for use with the card view.
 * Created by mofar1 on 2015-07-15.
 */
public class SnapsSong implements Serializable {

    String mTitle;
        String mAuthor;
        String mMelody;
        String mSongText;
        int mCategory;

    public void setSongInfo(String title, String melody, String author){
        this.mTitle = title;
        this.mMelody = melody;
        this.mAuthor = author;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getMelody() {
        return mMelody;
    }

    public void setMelody(String mMelody) {
        this.mMelody = mMelody;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSongText() {
        return mSongText;
    }

    public void setSongText(String songText) {
        this.mSongText = songText;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        this.mCategory = category;
    }

    @Override
    public String toString() {
        return "SnapsSong{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mMelody='" + mMelody + '\'' +
                ", mSongText='" + mSongText + '\'' +
                ", mCategory=" + mCategory +
                '}';
    }
}
