package com.example.therapy.main;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.therapy.R;
import com.example.therapy.add.AddActivity;
import com.example.therapy.database.Drug;
import com.example.therapy.database.DrugsDatabase;
import com.example.therapy.description.ActivityDescription;

import java.util.List;

public class MainActivity extends AppCompatActivity implements goToActivityable {
    public static final String DATABASE_NAME = "drugs_db";
    public DrugsDatabase drugsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        drugsDatabase = DrugsDatabase.getInMemoryDatabase(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        List<Drug> arrayList = drugsDatabase.daoAccess().findAllDrugs();

        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new MyAdapter(arrayList, this);
        recyclerView.setAdapter(mAdapter);

        drugsDatabase = Room.databaseBuilder(getApplicationContext(), DrugsDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void goToWithId(int id) {
        Intent intent = new Intent(this, ActivityDescription.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
