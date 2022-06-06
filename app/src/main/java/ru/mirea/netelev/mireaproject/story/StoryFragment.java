package ru.mirea.netelev.mireaproject.story;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.netelev.mireaproject.App;
import ru.mirea.netelev.mireaproject.AppDatabase;
import ru.mirea.netelev.mireaproject.R;

public class StoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppDatabase db = App.getInstance().getDatabase();

        StoryDao storyDao = db.storyDao();
        List<Story> stories = storyDao.getAll();

        Log.d("xyq", String.valueOf(stories.size()));
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        for (Story it: stories){
            Log.d("StoriesFragment", it.number);
        }
        Button newStory = view.findViewById(R.id.button);
        newStory.setOnClickListener(this::onClick);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        StoryAdapter adapter = new StoryAdapter(stories);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), StoryView.class);
        startActivity(intent);

    }
}