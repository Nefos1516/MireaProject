package ru.mirea.netelev.mireaproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class AudioFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MusicService musicService;
    private ImageButton playButton;
    private Button startButton;
    private Button stopButton;
    private final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    private boolean isWork;
    private MediaRecorder mediaRecorder;
    private File audioFile;
    ActivityResultLauncher<String[]> permissionsRequest;

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_audio,
                container, false);

        playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(this::onPlayButtonClick);
        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this::onRecordStartClick);
        stopButton = view.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this::onRecordStopClick);
        stopButton.setEnabled(false);
        mediaRecorder = new MediaRecorder();
        permissionsRequest = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                    if (isGranted.containsValue(false)){
                        permissionsRequest.launch(PERMISSIONS);
                    } else {
                        isWork = true;
                    }
                });
        isWork = hasPermissions(getContext(), PERMISSIONS);
        if (!isWork) {
            permissionsRequest.launch(PERMISSIONS);
        }
        return view;
    }

    private void onPlayButtonClick(View view){
        if (musicService == null && audioFile != null){
            musicService = new MusicService(getContext(),
                    audioFile.getAbsolutePath());
            musicService.start();
            playButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            if (musicService != null) {
                stopMusicPlaying();
            }
            musicService = null;
        }
    }

    private void stopMusicPlaying(){
        musicService.interrupt();
        playButton.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (musicService != null){
            musicService.interrupt();
        }
    }

    private void onRecordStartClick(View view){
        if (musicService != null && musicService.isAlive()){
            stopMusicPlaying();
        }
        playButton.setEnabled(false);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        stopButton.requestFocus();
        try {
            startRecording();
        } catch (IOException e) {
            Log.e(TAG, "Caught io exception " + e.getMessage());
        }
    }

    private void startRecording() throws IOException {
        if (isWork){
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null && getActivity() != null){
                audioFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                        "mirea.3gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Log.e(TAG, "Start");
            Toast.makeText(getContext(), "Запись началась.", Toast.LENGTH_LONG).show();
        }
    }

    private void onRecordStopClick(View view){
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        startButton.requestFocus();
        stopRecording();
        processAudioFile();
        playButton.setEnabled(true);
    }

    private void stopRecording(){
        if(mediaRecorder != null){
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            Toast.makeText(getContext(), "Запись остановлена.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = requireActivity().getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);
        requireActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if (context != null && permissions != null){
            for (String permission: permissions){
                if (ActivityCompat.checkSelfPermission(context, permission)
                        == PackageManager.PERMISSION_DENIED){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}