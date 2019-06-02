package com.example.therapy.add;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.therapy.R;
import com.example.therapy.database.Drug;
import com.example.therapy.database.DrugsDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int TIME_DIALOG_ID = 1111;

    private DrugsDatabase drugsDatabase;
    private ImageView drugImage;
    private TextView drugName;
    private TextView outputTime;
    private int hr;
    private int min;
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hr = hourOfDay;
            min = minutes;
            updateTime(hr, min);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        drugsDatabase = DrugsDatabase.getInMemoryDatabase(this);
        drugImage = findViewById(R.id.imageView);
        drugName = findViewById(R.id.editText);
        outputTime = findViewById(R.id.output);

        final Calendar c = Calendar.getInstance();
        hr = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        updateTime(hr, min);

        //wybierz zdjęcia z galerii
        Button choosePicture = findViewById(R.id.addPhoto);
        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        //zrób zdjęcie aparetem
        Button takePicture = findViewById(R.id.takePhoto);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        //ustaw godzinę
        Button setTime = findViewById(R.id.setTime);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(1111).show();
            }
        });

        //dodaj do bazy danych
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drug drug = new Drug();
                drug.setDrugName(drugName.getText().toString());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable) drugImage.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                drug.setDrugImage(stream.toByteArray());

                drug.setTime(outputTime.getText().toString());
                drugsDatabase.daoAccess().insertOnlySingleDrug(drug);
            }
        });
    }

    protected Dialog createdDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hr, min, true);
        }
        return null;
    }

    private void updateTime(int hours, int mins) {
        String minutes;
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String aTime = String.valueOf(hours) + ':' + minutes;
        outputTime.setText(aTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
                drugImage.setImageURI(data.getData());
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                assert extras != null;
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                drugImage.setImageBitmap(imageBitmap);
            }
        }
    }


}
