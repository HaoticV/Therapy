package com.example.therapy;

import android.app.Application;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class MyApp extends Application {
    public static String[] NAME;
    public static TypedArray IMAGE;
    public static String[] DESRIPTION;
    public static int SIZE;

    @Override
    public void onCreate() {
        super.onCreate();

        Resources resources=getResources();
        NAME = resources.getStringArray(R.array.names);
        IMAGE = resources.obtainTypedArray(R.array.pictures);
        DESRIPTION = resources.getStringArray(R.array.descriptions);

        int size0 = NAME.length;
        int size1 = IMAGE.length();
        int size2 = DESRIPTION.length;

        if(size0 == size1 && size1 == size2)
            SIZE = size0;
        else
            SIZE = 0;
    }
}
