package com.example.musio.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.models.PlaylistItems;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

import java.util.List;

import static android.content.ContentValues.TAG;

public class AdapterPlaylist extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PlaylistItems> videoList;

    public AdapterPlaylist(Context context, List<PlaylistItems> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    class YoutubeHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView playlistName, vid_count1;

        public YoutubeHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.iv_playlist_presentation);
            playlistName = itemView.findViewById(R.id.tv_playlist_title);
            vid_count1 = itemView.findViewById(R.id.tv_video_count1);
        }

        public void setData(PlaylistItems data) {
            final String getPlaylistName = data.getSnippet().getTitle();
            int getCount = data.getContentDetails().getItemCount();
            String getThumb = data.getSnippet().getThumbnails().getMedium().getUrl();

            itemView.setOnClickListener(v -> Toast.makeText(context, getPlaylistName, Toast.LENGTH_SHORT).show());

            playlistName.setText(getPlaylistName);
            vid_count1.setText(getCount + " video");
            Picasso.get()
                    .load(getThumb)
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(thumbnail, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "ОК");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Thumbnail error: ", e);
                        }
                    });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.playlist_item, parent, false);
        return new YoutubeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaylistItems videoYT = videoList.get(position);
        YoutubeHolder yth = (YoutubeHolder) holder;
        yth.setData(videoYT);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}