package com.example.therapy.alarm;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.therapy.R;
import com.example.therapy.database.Drug;
import com.example.therapy.database.DrugsDatabase;

import java.util.List;

public class Alarm extends Activity {

    DrugsDatabase drugsDatabase;
    ViewFlipper flipper;
    TextView textView;
    Button turnOffAlarmButton;
    Ringtone ringtone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_dialog);
        playRingTone();
        drugsDatabase = DrugsDatabase.getInMemoryDatabase(this);
        textView = findViewById(R.id.nameAlarm);
        flipper = findViewById(R.id.myflipper);
        flipper.stopFlipping();

        int id = getIntent().getIntExtra("id", 0);
        List<Drug> drugList = drugsDatabase.daoAccess().fetchDrugsByTime(drugsDatabase.daoAccess().fetchOneDrugByDrugId(id).getTime());
        for (int i = 0; i < drugList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(Uri.parse(drugList.get(i).getDrugImagePath()));
            flipper.addView(imageView);
            textView.setText(drugList.get(i).getDrugName());
        }

        if (drugList.size() > 1) flipperInit();

        turnOffAlarmButton = findViewById(R.id.turnOffAlarmButton);
        turnOffAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringtone.stop();
                finish();
            }
        });
    }

    private void flipperInit() {
        flipper.setAutoStart(true);
        flipper.setFlipInterval(3000);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    void playRingTone() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();
    }
}
