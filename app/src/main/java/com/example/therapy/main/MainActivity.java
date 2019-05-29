package com.example.therapy.main;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.therapy.MyApp;
import com.example.therapy.R;
import com.example.therapy.add.AddActivity;
import com.example.therapy.database.DrugsDatabase;
import com.example.therapy.description.ActivityDescription;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements goToActivityable {
    public static final String DATABASE_NAME = "drugs_db";
    public DrugsDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


        ArrayList<Pair<Integer, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < MyApp.SIZE; i++) {
            int id = MyApp.IMAGE.getResourceId(i, 0);
            arrayList.add(new Pair<>(id, MyApp.NAME[i]));
        }

        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new MyAdapter(arrayList, this);
        recyclerView.setAdapter(mAdapter);

        mDb = Room.databaseBuilder(getApplicationContext(), DrugsDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

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
