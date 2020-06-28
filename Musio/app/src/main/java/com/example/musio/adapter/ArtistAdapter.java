package com.example.musio.adapter;

import android.content.Context;
import android.content.Intent;
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

    public Context context;

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
            //c'est ici que l'on fait nos findView
            textArtistName = (TextView) itemView.findViewById(R.id.textArtistName);
            imageView = (ImageView) itemView.findViewById(R.id.imageArtist);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArtistAdapter(List<Artist> listArtist) {
        this.listArtist = listArtist;
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
        final Artist artist = listArtist.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textArtistName.setText(artist.getName());

//        Picasso.with(holder.itemView.getContext())
//                .load(artist.getPictureMedium())
//                .centerCrop().fit().into(holder.imageView);
        Picasso.get().load(artist.getPictureMedium()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "click on <" + artist.getName()+ ">");
                /*Intent intent = new Intent(view.getContext(), ListAlbumActivity.class);
                intent.putExtra("artist", artist.getName());
                view.getContext().startActivity(intent);*/
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listArtist.size();
    }
}
