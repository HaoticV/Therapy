package com.example.therapy.description;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    int drugId;
    Drug drug;

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
        drugId = drug.getDrugId();

        nazwa.setText(drug.getDrugName());
        obraz.setImageURI(Uri.parse(drug.getDrugImagePath()));
        opis.setText(drug.getDrugDescription());
        czas.setText(drug.getTime());

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionEdit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDescription.this, EditActivity.class);
                intent.putExtra("id", drugId);
                startActivityForResult(intent, 11);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 11) {
            Bundle bundle = data.getExtras();
            drug = (Drug) bundle.getSerializable("newDrug");
            nazwa.setText(drug.getDrugName());
            obraz.setImageURI(Uri.parse(drug.getDrugImagePath()));
            opis.setText(drug.getDrugDescription());
            czas.setText(drug.getTime());
        }
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("newDrug", drug);
        backIntent.putExtras(bundle);
        setResult(RESULT_OK, backIntent);
        finish();
    }
}
