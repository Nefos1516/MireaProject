package ru.mirea.netelev.mireaproject.tmtimes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.netelev.mireaproject.App;
import ru.mirea.netelev.mireaproject.AppDatabase;

public class TmTimesViewModel extends ViewModel {
    private final LiveData<List<TmTimes>> times;
    private final AppDatabase appDatabase = App.instance.getDatabase();
    private final TmTimesDao timesDao = appDatabase.timesDao();

    public TmTimesViewModel(){
        times = timesDao.getAll();
    }

    public void addTime(TmTimes time){
        timesDao.insert(time);
    }

    public LiveData<List<TmTimes>> getTimesLiveData(){
        return times;
    }
    public List<TmTimes> getTimes(){
        return times.getValue();
    }
}
