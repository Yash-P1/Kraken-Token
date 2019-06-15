package com.nerdsure.appractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IsPatternAvailable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_pattern_available);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("PREF", 0);
                String password = sharedPreferences.getString("password","0");
                if(password.equals("0")){
                    Intent intent = new Intent(IsPatternAvailable.this,CreatePatternActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(IsPatternAvailable.this,InputPatternActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },0);
    }
}
