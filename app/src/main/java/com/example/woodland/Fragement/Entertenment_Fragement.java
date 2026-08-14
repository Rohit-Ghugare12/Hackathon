package com.example.woodland.Fragement;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woodland.R;


public class Entertenment_Fragement extends Fragment {



    TextView tvEntertenmentSongName,tvStartTime,tvEndTime;
    ImageView ivEntertenmentForward,ivEntertenmentBackward,ivEntertenmentnext,ivEntertenmentPrevious,ivEntertenmentSongCoverPage,ivEntertenmentPause;

    SeekBar sbEntertenmentSeekBar;

    MediaPlayer mediaPlayer;
    private static int songprogressIndex=0;
    private final int[] songs={
            R.raw.give_me_some_sunshine,
            R.raw.dil_meri_na_sune,
            R.raw.gav_sutena,
            R.raw.mere_samne_wali_khidki_me
    };

    private final String[] songNames={
            "Give me Some Sunshine",
            "Dil meri na sune",
            "Gav sutana",
            "Mere samne wali khidki me"
    };
    private final int[] songImages={
            R.drawable.give_me_sunshine,
            R.drawable.dil_meri_na_sune,
            R.drawable.gav_sutana,
            R.drawable.samne_wale_khidki_me
    };
    Handler handler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_entertenment, container, false);
        Toast.makeText(getActivity(),"Entertenment Fragement",Toast.LENGTH_SHORT).show();

        tvStartTime = view.findViewById(R.id.tvStartTime);
        tvEntertenmentSongName=view.findViewById(R.id.tvEntertenmentSongName);
        tvEndTime = view.findViewById(R.id.tvEndTime);
        ivEntertenmentForward = view.findViewById(R.id.ivEntertenmentForward);
        ivEntertenmentBackward= view.findViewById(R.id.ivEntertenmentBackward);
        ivEntertenmentnext = view.findViewById(R.id.iEntertenmentNext);
        ivEntertenmentPrevious=view.findViewById(R.id.ivEntertenmentPrevious);
        ivEntertenmentPause = view.findViewById(R.id.ivEntertenmentPause);
        sbEntertenmentSeekBar=view.findViewById(R.id.sbEntertenmentSeekBar);
        ivEntertenmentSongCoverPage=view.findViewById(R.id.ivEntertenmentSongCoverPage);

        loadSongs();
        ivEntertenmentPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    ivEntertenmentPause.setImageResource(R.drawable.play_circle_24px);
                }
                else
                {
                    mediaPlayer.start();
                    ivEntertenmentPause.setImageResource(R.drawable.pause_icon);
                    handler.post(updateProgress);
                }
            }
        });

        ivEntertenmentnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songprogressIndex++;
                if (songprogressIndex>=songs.length)
                {
                    songprogressIndex=0;
                }
                loadSongs();
            }
        });

        ivEntertenmentPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songprogressIndex--;
                if (songprogressIndex<=0)
                {
                    songprogressIndex=songs.length-1;
                }
                loadSongs();
            }
        });
        ivEntertenmentForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = mediaPlayer.getCurrentPosition()+5000;
                if (positon<mediaPlayer.getDuration())
                {
                    mediaPlayer.seekTo(positon);
                }
                else
                {
                    Toast.makeText(getActivity(),"Can't be Fprward",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivEntertenmentBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = mediaPlayer.getCurrentPosition()-5000;
                if (positon>0)
                {
                    mediaPlayer.seekTo(positon);
                }
                else
                {
                    Toast.makeText(getActivity(),"Can't be Backward",Toast.LENGTH_SHORT);
                }

            }
        });
        sbEntertenmentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;

    }

    private void loadSongs()
    {
        if (mediaPlayer!=null)
        {
            mediaPlayer.release();
        }
        mediaPlayer=MediaPlayer.create(getActivity(),songs[songprogressIndex]);
        tvEntertenmentSongName.setText(songNames[songprogressIndex]);
        ivEntertenmentSongCoverPage.setImageResource(songImages[songprogressIndex]);

        sbEntertenmentSeekBar.setMax(mediaPlayer.getDuration());

        tvStartTime.setText("0:00");
        tvEndTime.setText(formatTime(mediaPlayer.getDuration()));

        ivEntertenmentPause.setImageResource(R.drawable.play_circle_24px);

    }

    private String formatTime(int duration)
    {
        int minutes = (duration / 1000) / 60;
        int seconds = (duration / 1000) % 60;

        return String.format("%d:%02d", minutes, seconds);
    }
    private final Runnable updateProgress=new Runnable() {
        @Override
        public void run() {

            int current= mediaPlayer.getCurrentPosition();
            sbEntertenmentSeekBar.setProgress(current);
            tvStartTime.setText(formatTime(current));

            handler.postDelayed(this,1000);
        }
    };

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mediaPlayer!=null)
        {
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}