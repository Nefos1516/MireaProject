package ru.mirea.netelev.mireaproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.netelev.mireaproject.databinding.FragmentMusicBinding;

public class MusicFragment extends Fragment {
    private FragmentMusicBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.playPauseButton.setOnClickListener(this::onClickPlayMusic);
        return root;
    }

    @SuppressLint("SetTextI18n")
    public void onClickPlayMusic(View view) {
        MainActivity activity = (MainActivity) requireActivity();
        Log.d("MediaPlayerFragment", "onClickPlayMusic: " + activity.getLocalClassName());
        activity.startService(
                new Intent(activity, MusicService.class));
        binding.playPauseButton.setOnClickListener(this::onClickStopMusic);
        binding.playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        binding.textView4.setText("Lagtrain");
    }

    public void onClickStopMusic(View view) {
        MainActivity activity = (MainActivity) requireActivity();
        Log.d("MediaPlayerFragment", "onClickStopMusic: ");
        activity.stopService(
                new Intent(activity, MusicService.class));
        binding.playPauseButton.setOnClickListener(this::onClickPlayMusic);
        binding.playPauseButton.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}