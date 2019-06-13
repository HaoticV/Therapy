package com.example.therapy.alarm;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.therapy.R;

public class Alarm extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_dialog);
    }
}
