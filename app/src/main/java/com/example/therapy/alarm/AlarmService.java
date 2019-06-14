package com.example.therapy.alarm;

import android.app.IntentService;
import android.content.Intent;

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int id = intent.getIntExtra("id", 0);
        Intent dialogIntent = new Intent(this, Alarm.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialogIntent.putExtra("id", id);
        startActivity(dialogIntent);
    }

}
