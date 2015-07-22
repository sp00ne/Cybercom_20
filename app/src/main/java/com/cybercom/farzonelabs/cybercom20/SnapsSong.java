package com.cybercom.farzonelabs.cybercom20;

/**
 * Song class used to create the song objects for use with the card view.
 * Created by mofar1 on 2015-07-15.
 */
public class SnapsSong {
        String mTitle;
        String mAuthor;
        String mMelody;
        String mSongText;
        int mSongId;
        int mCategory;

    public static int getCategoryDrawable(int category) {
        switch (category){
            default:
            case 1: return R.drawable.backdrop_klassiker;
            case 2: return R.drawable.backdrop_aa;
            case 3: return R.drawable.backdrop_tillol;
            case 4: return R.drawable.backdrop_tillvin;
            case 5: return R.drawable.backdrop_tillsnaps;
            case 6: return R.drawable.backdrop_tillpunsch;
            case 7: return R.drawable.backdrop_skanskt;
            case 8: return R.drawable.backdrop_ekivokt;
            case 9: return R.drawable.backdrop_natur;
            case 10: return R.drawable.backdrop_ovrigt;
            case 11: return R.drawable.backdrop_vedertaget;
        }
    }

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

    public int getSongId() {
        return mSongId;
    }

    public void setSongId(int mSongId) {
        this.mSongId = mSongId;
    }

}
