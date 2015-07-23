package com.cybercom.farzonelabs.cybercom20;

import android.content.Context;
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
        //public final View mCardView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            //mCardView = itemView;
            this.tvTitle = (TextView) itemView.findViewById(R.id.song_title);
            this.tvMelody = (TextView) itemView.findViewById(R.id.song_melody);
            this.tvAuthor = (TextView) itemView.findViewById(R.id.song_author);
        }
    }

    // Store the songs
    public ArrayList<SnapsSong> mSnapsSongs;
    // Store the context
    private Context mContext;

    // (CONSTRUCTOR) Pass in the context and users array into the constructor
    public SongbookAdapter(ArrayList<SnapsSong> dbSongInfo, Context context) {

        this.mContext = context;
        this.mSnapsSongs = dbSongInfo;
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
        View itemView = LayoutInflater.from(mContext).
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
    public void onBindViewHolder(final SongbookAdapter.ViewHolder holder, int position) {

        SnapsSong snapsSong = mSnapsSongs.get(position);
        holder.tvTitle.setText(snapsSong.getTitle());
        holder.tvAuthor.setText(snapsSong.getAuthor());
        holder.tvMelody.setText(snapsSong.getMelody());

        /*
        //Assign click listener
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("RecyclerView","onClick");
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return mSnapsSongs.size();
    }

}
