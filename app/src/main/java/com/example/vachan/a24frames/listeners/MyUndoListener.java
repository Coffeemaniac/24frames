package com.example.vachan.a24frames.listeners;

import android.util.Log;
import android.view.View;

public class MyUndoListener implements View.OnClickListener {

    public static String LOG_TAG = MyUndoListener.class.getSimpleName();

    public void onClick(View v){
        Log.v("LOG_TAG", "Snackbar action nigga!!");
    }
}
