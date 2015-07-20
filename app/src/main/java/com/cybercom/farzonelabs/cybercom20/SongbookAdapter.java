package com.cybercom.farzonelabs.cybercom20;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Create the basic adapter extending from RecyclerView.Adapter
 * Note that we specify the custom ViewHolder which gives us access to our views
 * Created by mofar1 on 2015-07-17.
 */
public class SongbookAdapter extends RecyclerView.Adapter<SongbookAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public TextView tvMelody;
        public TextView tvAuthor;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.song_title);
            this.tvMelody = (TextView) itemView.findViewById(R.id.song_melody);
            this.tvAuthor = (TextView) itemView.findViewById(R.id.song_author);
        }
    }

    // Store the songs
    private ArrayList<SnapsSong> snapsSongs;
    // Store the context
    private Context context;

    // (CONSTRUCTOR) Pass in the context and users array into the constructor
    public SongbookAdapter(Cursor dbSongInfo, Context context) {

        this.context = context;
        convertCursorToArraylist(dbSongInfo);
    }

    private void convertCursorToArraylist(Cursor dbSongInfo) {

        snapsSongs = new ArrayList<SnapsSong>();

        dbSongInfo.moveToFirst();
        while(!dbSongInfo.isAfterLast()) {
            SnapsSong snapsSong = new SnapsSong();
            String title = dbSongInfo.getString(dbSongInfo.getColumnIndex(SongbookDatabase.COLUMNS.title));
            String author = dbSongInfo.getString(dbSongInfo.getColumnIndex(SongbookDatabase.COLUMNS.author));
            String melody = dbSongInfo.getString(dbSongInfo.getColumnIndex(SongbookDatabase.COLUMNS.melody));
            snapsSong.setSongInfo(title, melody, author);

            snapsSongs.add(snapsSong); //add the item
            dbSongInfo.moveToNext();
        }
    }

    /**
     *  Usually involves inflating a layout from XML and returning the holder
     * @param parent
     * @param viewType
     * @return Return a new holder instance
     */
    @Override
    public SongbookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.song_item, parent, false);

        // Return a new holder instance
        return new SongbookAdapter.ViewHolder(itemView);
    }

    /**
     * // Involves populating data into the item through holder
     * @param holder - The holder
     * @param position - Position in the array
     */
    @Override
    public void onBindViewHolder(SongbookAdapter.ViewHolder holder, int position) {

        SnapsSong snapsSong = snapsSongs.get(position);
        holder.tvTitle.setText(snapsSong.getTitle());
        holder.tvAuthor.setText(snapsSong.getAuthor());
        holder.tvMelody.setText(snapsSong.getMelody());
    }

    @Override
    public int getItemCount() {
        return snapsSongs.size();
    }

}
