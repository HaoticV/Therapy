package com.example.therapy.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.therapy.R;
import com.example.therapy.add.AddActivity;
import com.example.therapy.database.Drug;
import com.example.therapy.database.DrugsDatabase;
import com.example.therapy.description.ActivityDescription;

import java.util.List;

public class MainActivity extends AppCompatActivity implements goToActivityable {
    static final int ADD_INTENT = 1;
    Drug drug = new Drug();
    private List<Drug> contentList;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private DrugsDatabase drugsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        drugsDatabase = DrugsDatabase.getInMemoryDatabase(this);
        contentList = drugsDatabase.daoAccess().findAllDrugs();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(contentList, this);
        recyclerView.setAdapter(mAdapter);

        enableSwipeToDeleteAndUndo();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_INTENT);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_INTENT && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Drug drug = (Drug) bundle.getSerializable("newDrug");
            contentList.add(drug);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Dodano lek", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void goToWithId(int id) {
        Intent intent = new Intent(this, ActivityDescription.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDelete swipeToDeleteCallback = new SwipeToDelete(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Drug item = mAdapter.getContentList().get(position);

                mAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(recyclerView, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void remove(int id) {
        drug = drugsDatabase.daoAccess().fetchOneDrugbyDrugId(id + 1);
        drugsDatabase.daoAccess().deleteDrug(drug);
    }

    @Override
    public void undo(int id) {
        drugsDatabase.daoAccess().insertOnlySingleDrug(drug);
    }


}

