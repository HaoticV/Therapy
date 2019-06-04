package com.example.therapy.add;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

                Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drugImage.getDrawable()).getBitmap(), 300, 300, true);
                drug.setDrugImagePath(saveToInternalStorage(bitmap, drug.getDrugName()));

                drug.setTime(outputTime.getText().toString());
                drugsDatabase.daoAccess().insertOnlySingleDrug(drug);

                Intent backIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("newDrug", drug);
                backIntent.putExtras(bundle);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory, name + ".jpg");

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myPath.getAbsolutePath();
    }

    protected Dialog createdDialog(int id) {
        if (id == TIME_DIALOG_ID) {
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
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

