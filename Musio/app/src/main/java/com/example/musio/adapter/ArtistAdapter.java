package com.example.musio.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.models.deezerData.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private static final String TAG = "AdapterArtist";

    private List<Artist> listArtist;
    OnArtistClickListener mListener;

    public Context context;

    public interface OnArtistClickListener {
        void onTextClick(String artist);
    }

    // set the listener. Must be called from the fragment
    public void setListener(OnArtistClickListener listener) {
        this.mListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArtistAdapter(List<Artist> listArtist) {
        this.listArtist = listArtist;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textArtistName;
        private ImageView imageView;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            //find view's
            textArtistName = itemView.findViewById(R.id.textArtistName);
            imageView = itemView.findViewById(R.id.imageArtist);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_artist, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = listArtist.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textArtistName.setText(artist.getName());


        Picasso.get().load(artist.getPictureMedium()).into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            // get the name based on the position and tell the fragment via listener
            mListener.onTextClick(artist.getName());
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listArtist.size();
    }
}
