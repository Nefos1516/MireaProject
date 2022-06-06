package ru.mirea.netelev.mireaproject.tmtimes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.netelev.mireaproject.AppDatabase;
import ru.mirea.netelev.mireaproject.MainActivity;
import ru.mirea.netelev.mireaproject.R;
import ru.mirea.netelev.mireaproject.App;

public class TmTimesView extends AppCompatActivity {
    private EditText number;
    private EditText nameAdd;
    private EditText timeAdd;
    private TmTimesDao timesDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tmtimes_view);
        AppDatabase appDatabase = App.getInstance().getDatabase();
        timesDao = appDatabase.timesDao();

        number = findViewById(R.id.numberTmTimesEdit);
        nameAdd = findViewById(R.id.nameTmTimesEdit);
        timeAdd = findViewById(R.id.timeTmTimesEdit);

        findViewById(R.id.btnSaveTime).setOnClickListener(this::saveBtnTime);
    }

    public void saveBtnTime(View view) {

        TmTimes time = new TmTimes();
        time.number = number.getText().toString();
        time.name = nameAdd.getText().toString();
        time.time = timeAdd.getText().toString();

        timesDao.insert(time);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
