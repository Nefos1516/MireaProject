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

import ru.mirea.netelev.mireaproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoryFragment extends Fragment {

    static int num =0;
    private List<Story> stories;
    private RecyclerView recyclerView;
    private Button newStory;
    private StoryDao storyDao;
    private AppDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoryFragment newInstance(String param1, String param2) {
        StoryFragment fragment = new StoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        db = App.getInstance().getDatabase();

        storyDao = db.storyDao();
        stories = storyDao.getAll();

        Log.d("xyq", String.valueOf(stories.size()));
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        for (Story it: stories){
            Log.d("StoriesFragment", it.number);
        }
        newStory = view.findViewById(R.id.button);
        newStory.setOnClickListener(this::onClick);

        recyclerView = view.findViewById(R.id.recyclerView);
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