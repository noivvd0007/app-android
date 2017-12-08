package com.vuvannoi.ndapptracnghiem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.vuvannoi.ndapptracnghiem.Login_Activity.view.Activity.Login_Activity;

public class SplassScreen_Activity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splass_screen_);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}

