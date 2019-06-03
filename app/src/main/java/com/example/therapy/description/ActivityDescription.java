package com.example.therapy.description;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.therapy.R;

public class ActivityDescription extends AppCompatActivity {
    TextView nazwa, opis;
    ImageView obraz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        nazwa = findViewById(R.id.nazwa);
        opis = findViewById(R.id.opis);
        obraz = findViewById(R.id.obraz);

        int id = getIntent().getIntExtra("id", 0);

        //nazwa.setText(MyApp.NAME[id]);
        //opis.setText(MyApp.DESRIPTION[id]);
        //obraz.setImageResource(MyApp.IMAGE.getResourceId(id, 0));
    }
}
