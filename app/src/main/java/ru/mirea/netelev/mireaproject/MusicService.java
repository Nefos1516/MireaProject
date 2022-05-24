package ru.mirea.netelev.mireaproject;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class MusicService extends Thread {
    private MediaPlayer mediaPlayer;
    private final Context context;
    private int musicID;
    private String filePath;

    public MusicService(Context context, int musicID) {
        this.context = context;
        this.musicID = musicID;
    }
    public MusicService(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    @Override
    public void run()
    {
        MediaPlayer mediaPlayer = null;
        if (filePath != null){
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer = MediaPlayer.create(context, musicID);
            mediaPlayer.start();
        }
        mediaPlayer.setLooping(true);
        while (true){
            if (isInterrupted()) break;
        }
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
    }
}