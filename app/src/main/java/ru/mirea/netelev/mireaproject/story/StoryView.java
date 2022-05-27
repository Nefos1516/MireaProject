package ru.mirea.netelev.mireaproject.story;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.netelev.mireaproject.MainActivity;
import ru.mirea.netelev.mireaproject.R;

public class StoryView extends AppCompatActivity {
    private EditText number;
    private EditText nameAdd;
    private EditText ageAdd;
    private StoryDao storyDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_view);
        storyDao = App.getInstance().getDatabase().storyDao();

        number = findViewById(R.id.numberStoryEdit);
        nameAdd = findViewById(R.id.NameEdit);
        ageAdd = findViewById(R.id.AgeEdit);

        findViewById(R.id.btnSave).setOnClickListener(this::saveBtn);
    }

    public void saveBtn(View view) {

        Story story = new Story();
        story.number = number.getText().toString();
        story.name = nameAdd.getText().toString();
        story.age = ageAdd.getText().toString();

        storyDao.insert(story);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}