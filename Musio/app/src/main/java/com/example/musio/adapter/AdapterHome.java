package com.example.musio.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.models.VideoYT;
import com.example.musio.utility.ChangeTo;
import com.squareup.picasso.Picasso;
import static android.content.ContentValues.TAG;
import com.squareup.picasso.Callback;
import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<VideoYT> videoList;

    public AdapterHome(Context context, List<VideoYT> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    static class YoutubeHolder extends RecyclerView.ViewHolder {

        ImageView imageMusic;
        TextView musicName, musicTime;

        public YoutubeHolder(@NonNull View itemView) {
            super(itemView);
            imageMusic = itemView.findViewById(R.id.musicImage);
            musicName = itemView.findViewById(R.id.musicName);
            musicTime = itemView.findViewById(R.id.musicTime);
        }

        public void setData(final VideoYT data) {
            final String getImage = data.getSnippet().getTitle();
            String getNameОfTrack = data.getSnippet().getPublishedAt();
            String getThumb  = data.getSnippet().getThumbnails().getMedium().getUrl();

            itemView.setOnClickListener(v -> {
                /*Intent i = new Intent(context, YTPlayerActivity.class);
                i.putExtra("video_id", data.getId().getVideoId());
                i.putExtra("video_title", getJudul);
                context.startActivity(i);*/
            });

            musicName.setText(getImage);
            musicTime.setText(ChangeTo.getTimeAgo(getNameОfTrack));
            Picasso.get()
                    .load(getThumb)
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(imageMusic, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Thumbnail berhasil ditampilkan");
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
        View view = inflater.inflate(R.layout.music_item, parent, false);
        return new YoutubeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoYT videoYT = videoList.get(position);
        YoutubeHolder yth = (YoutubeHolder) holder;
        yth.setData(videoYT);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
