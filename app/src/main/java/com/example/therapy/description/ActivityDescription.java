package com.example.therapy.description;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.therapy.R;
import com.example.therapy.database.Drug;
import com.example.therapy.database.DrugsDatabase;

import java.util.List;

public class ActivityDescription extends AppCompatActivity {
    TextView nazwa, opis, czas;
    ImageView obraz;
    DrugsDatabase drugsDatabase;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        nazwa = findViewById(R.id.nazwa);
        obraz = findViewById(R.id.obraz);
        opis = findViewById(R.id.opis);
        czas = findViewById(R.id.time_description);

        drugsDatabase = DrugsDatabase.getInMemoryDatabase(this);

        int id = getIntent().getIntExtra("id", 0);
        List<Drug> list = drugsDatabase.daoAccess().findAllDrugs();
        Drug drug = list.get(id);

        nazwa.setText(drug.getDrugName());
        obraz.setImageURI(Uri.parse(drug.getDrugImagePath()));
        opis.setText(drug.getDrugDescription());
        czas.setText(drug.getTime());


    }
}
