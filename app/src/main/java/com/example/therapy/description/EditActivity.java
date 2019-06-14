package com.example.therapy.description;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import com.example.therapy.alarm.AlarmService;
import com.example.therapy.database.Drug;
import com.example.therapy.database.DrugsDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int TIME_DIALOG_ID = 1111;

    private DrugsDatabase drugsDatabase;
    private ImageView drugImage;
    private TextView drugName;
    private TextView outputTime;
    private int hr;
    private int min;
    private int id;

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

        id = getIntent().getIntExtra("id", 0);
        drugsDatabase = DrugsDatabase.getInMemoryDatabase(this);
        drugImage = findViewById(R.id.imageView);
        drugName = findViewById(R.id.editText);
        outputTime = findViewById(R.id.output);

        drugsDatabase = DrugsDatabase.getInMemoryDatabase(this);
        Drug drug = drugsDatabase.daoAccess().fetchOneDrugByDrugId(id);
        drugImage.setImageURI(Uri.parse(drug.getDrugImagePath()));
        drugName.setText(drug.getDrugName());
        outputTime.setText(drug.getTime());

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
        addButton.setText("Zmień");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drug drug = new Drug();
                drug.setDrugId(drugsDatabase.daoAccess().maxId() + 1);
                drug.setDrugName(drugName.getText().toString());

                Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drugImage.getDrawable()).getBitmap(), 300, 300, true);
                drug.setDrugImagePath(saveToInternalStorage(bitmap, String.valueOf(drug.getDrugId())));

                drug.setTime(outputTime.getText().toString());
                drugsDatabase.daoAccess().insertOnlySingleDrug(drug);

                setAlarm(drug.getDrugId());

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
        String h;
        if (hours < 10)
            h = "0" + hours;
        else
            h = String.valueOf(hours);
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String aTime = h + ':' + minutes;
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

    private void setAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getService(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 10000, pendingIntent);
    }
}

