package com.example.musio.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.models.deezerData.Track;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private static final String TAG = "AdapterAlbum";

    private List<Track> listTrack;
    OnTrackClickListener mListener;
    public Context context;

    public interface OnTrackClickListener {
        void onTrackClick(Track track);
    }

    // set the listener. Must be called from the fragment
    public void setListener(OnTrackClickListener listener) {
        this.mListener = listener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTrackName;
        private TextView textArtistName;
        private TextView textTrackDuration;
        private ImageButton playButton;
        private ImageView trackImage;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            //set image view's
            //trackImage = itemView.findViewById(R.id.imageViewTrack);
            textTrackName = itemView.findViewById(R.id.text_Track_Name);
            textArtistName = itemView.findViewById(R.id.text_Artist_Name);
            textTrackDuration = itemView.findViewById(R.id.textTrackDuration);
            playButton = itemView.findViewById(R.id.playButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrackAdapter(List<Track> listAlbum) {
        this.listTrack = listAlbum;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_track, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Track track = listTrack.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textTrackName.setText(track.getTitle());
        holder.textArtistName.setText(track.getArtist().getName());

        //NO IMAGE

        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.ENGLISH);
        String str = format.format(new Date(track.getDuration()*1000));

        holder.textTrackDuration.setText(str);

        holder.playButton.setOnClickListener(view -> {
            Log.d(TAG, "click on <" + track.getTitle()+ ">");
            Log.d(TAG, "url : " + track.getLink());
            mListener.onTrackClick(track);
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listTrack.size();
    }
}
