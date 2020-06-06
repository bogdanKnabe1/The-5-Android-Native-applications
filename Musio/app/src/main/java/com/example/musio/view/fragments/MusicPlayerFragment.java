package com.example.musio.view.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.view.activity.MainActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicPlayerFragment extends Fragment {

    SeekBar mSeekBar;
    TextView songTitle;
    ArrayList<File> allSongs;
    static MediaPlayer mMediaPlayer;
    int position;
    TextView curTime;
    TextView totTime;
    ImageView playIcon;
    ImageView prevIcon;
    ImageView nextIcon;
    Intent playerData;
    Bundle bundle;
    ImageView repeatIcon;
    ImageView suffleIcon;
    ImageView curListIcon;

    public MusicPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_player, container, false); // Inflate the layout for this fragment

        //init
        mSeekBar = v.findViewById(R.id.seekbar);
        songTitle = v.findViewById(R.id.musicTitle1);
        curTime = v.findViewById(R.id.current_time);
        totTime = v.findViewById(R.id.total_time);

        playIcon = v.findViewById(R.id.play_pause_btn);
        prevIcon = v.findViewById(R.id.prev_button);
        nextIcon = v.findViewById(R.id.next_button);

        repeatIcon = v.findViewById(R.id.repeat);
        suffleIcon = v.findViewById(R.id.mix);
        curListIcon = v.findViewById(R.id.curListIcon);

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }

        playerData = requireActivity().getIntent();
        bundle = playerData.getExtras();

        allSongs = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("position", 0);
        initPlayer(position);

        /*curListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(getActivity(),CurrentList.class);
                list.putExtra("songsList",allSongs);
                startActivity(list);
            }
        });*/


        playIcon.setOnClickListener(v1 -> play());
        prevIcon.setOnClickListener(v12 -> {

            if (position <= 0) {
                position = allSongs.size() - 1;
            } else {
                position--;
            }

            initPlayer(position);

        });

        nextIcon.setOnClickListener(v13 -> {
            if (position < allSongs.size() - 1) {
                position++;
            } else {
                position = 0;

            }
            initPlayer(position);
        });


        return v;
    }

    private void initPlayer(final int position) {

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
        }

        String sname = allSongs.get(position).getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
        songTitle.setText(sname);
        Uri songResourceUri = Uri.parse(allSongs.get(position).toString());

        mMediaPlayer = MediaPlayer.create(getActivity(), songResourceUri); // create and load mediaplayer with song resources
        mMediaPlayer.setOnPreparedListener(mp -> {
            String totalTime = createTimeLabel(mMediaPlayer.getDuration());
            totTime.setText(totalTime);
            mSeekBar.setMax(mMediaPlayer.getDuration());
            mMediaPlayer.start();
            playIcon.setImageResource(R.drawable.ic_pause_circle);

        });

        mMediaPlayer.setOnCompletionListener(mp -> {
            int curSongPoition = position;
            // code to repeat songs until the
            if (curSongPoition < allSongs.size() - 1) {
                curSongPoition++;
                initPlayer(curSongPoition);
            } else {
                curSongPoition = 0;
                initPlayer(curSongPoition);
            }

            //playIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                    mSeekBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        new Thread(() -> {
            while (mMediaPlayer != null) {
                try {
//                        Log.i("Thread ", "Thread Called");
                    // create new message to send to handler
                    if (mMediaPlayer.isPlaying()) {
                        Message msg = new Message();
                        msg.what = mMediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //Log.i("handler ", "handler called");
            int current_position = msg.what;
            mSeekBar.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            curTime.setText(cTime);
        }
    };


    private void play() {

        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            playIcon.setImageResource(R.drawable.ic_pause_circle);
        } else {
            pause();
        }

    }

    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playIcon.setImageResource(R.drawable.ic_play_circle);

        }

    }

    private String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;


    }
}
