package com.example.musio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.utility.Player;

import java.util.ArrayList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> musicList;


    public SongListAdapter(Context context, ArrayList<String> musicList) {
        this.context = context;
        this.musicList = musicList;
        //this.itemListener = itemListener;
    }

    private Context getContext(){
        return context;
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView musicImg;
        TextView musicName;
        TextView musicDuration;
        //public ImageView setting;

        MyViewHolder(View view) {
            super(view);
            musicImg = view.findViewById(R.id.musicImage);
            musicName = view.findViewById(R.id.musicName);
            musicDuration = view.findViewById(R.id.musicTime);

            // on item click
            itemView.setOnClickListener(v -> {
                // get position
                int position = getAdapterPosition();

                // check if item still exists
                if(position != RecyclerView.NO_POSITION){

                    //get position of track
                    String songName = musicList.get(position);

                    //Intent to music player
                    Intent play = new Intent(getContext(), Player.class);

                    //Convert to array
                    String[] allSongs = new String[musicList.size()];
                    allSongs = musicList.toArray(allSongs);

                    //put into intent data
                    play.putExtra("songs", allSongs).putExtra("songName",songName).putExtra("position", position);
                    getContext().startActivity(play);
                }
            });
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.musicImg.setImageResource(R.drawable.musicimg);
        holder.musicName.setText(musicList.get(position));
        holder.musicDuration.setText("4:20");

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

}
