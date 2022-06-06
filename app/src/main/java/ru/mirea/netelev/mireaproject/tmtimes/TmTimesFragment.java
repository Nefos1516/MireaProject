package ru.mirea.netelev.mireaproject.tmtimes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.netelev.mireaproject.R;

public class TmTimesFragment extends Fragment {
    private final List<TmTimes> times = new ArrayList<>();
    private TmTimesViewModel tmTimesViewModel;
    private final TmTimesAdapter tmTimesAdapter = new TmTimesAdapter(times);
    private ActivityResultLauncher<Intent> launcher;
    public static final int ADD_CAT_RESULT_CODE=1;
    public static final String NAME_LABEL="name";
    public static final String NUMBER_LABEL="number";
    public static final String TIME_LABEL="time";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tm_times, container, false);

        if (getActivity() != null){
            tmTimesViewModel = new ViewModelProvider(getActivity()).
                    get(TmTimesViewModel.class);
            tmTimesViewModel.getTimesLiveData().observe(getActivity(), this::onChanged);
        }
        view.findViewById(R.id.buttonAddTime).setOnClickListener(this::onNewTimeClicked);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tmTimesAdapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == ADD_CAT_RESULT_CODE
                            && result.getData() != null){
                        TmTimes time = new TmTimes();
                        time.name = result.getData().getStringExtra(NAME_LABEL);
                        time.number = result.getData().getStringExtra(NUMBER_LABEL);
                        time.time = result.getData().getStringExtra(TIME_LABEL);
                        tmTimesViewModel.addTime(time);
                        tmTimesAdapter.notifyDataSetChanged();
                    }
                });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onChanged(List<TmTimes> timesFromDB) {
        if (!times.isEmpty()){
            times.clear();
        }
        times.addAll(timesFromDB);
        tmTimesAdapter.notifyDataSetChanged();
    }

    private void onNewTimeClicked(View view){
        Intent intent = new Intent(getActivity(), TmTimesView.class);
        launcher.launch(intent);
    }
}
