package ru.mirea.netelev.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {

    private ImageButton playPauseButton, prevSongButton, nextSongButton;
    private ArrayList<Integer> songsIDs = new ArrayList<>();
    private String[] songsNames;
    private int currentSong = 0;
    private TextView textView;
    private MusicService musicService;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void startSong(){
        musicService = new MusicService(getContext(), songsIDs.get(currentSong));
        musicService.start();
        playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        textView.setText(songsNames[currentSong]);
    }

    private void onImageButtonClick(View view){
        if(musicService == null){
            startSong();
        } else {
            stopPlayer();
        }
    }

    private void onPrevSongButtonClick(View view){
        stopPlayer();
        currentSong--;
        if (currentSong < 0) currentSong = songsIDs.size()-1;
        startSong();
    }

    private void onNextSongButtonClick(View view){
        stopPlayer();
        currentSong++;
        if (currentSong > songsIDs.size()-1) currentSong = 0;
        startSong();
    }

    private void stopPlayer(){
        if (musicService != null){
            musicService.interrupt();
            try {
                musicService.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
            musicService = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        songsNames = getResources().getStringArray(R.array.songs_names);
        for (String name: songsNames){
            songsIDs.add(getResources().getIdentifier(
                    "ru.mirea.netelev.mireaproject:raw/"+name,
                    null, null));
        }
        textView = view.findViewById(R.id.textView4);
        playPauseButton = view.findViewById(R.id.playPauseButton);
        playPauseButton.setOnClickListener(this::onImageButtonClick);
        prevSongButton = view.findViewById(R.id.prevSongButton);
        prevSongButton.setOnClickListener(this::onPrevSongButtonClick);
        nextSongButton = view.findViewById(R.id.nextSongButton);
        nextSongButton.setOnClickListener(this::onNextSongButtonClick);
        return view;
    }

    
}