package com.nerdsure.appractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class InputPatternActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;
    String password;
    Button reset_pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pattern);

        reset_pattern = findViewById(R.id.reset_pattern);
        reset_pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InputPatternActivity.this,CreatePatternActivity.class));
            }
        });

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREF", 0);
        password = sharedPreferences.getString("password",null);

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if(password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {

                    Intent intent = new Intent(InputPatternActivity.this, Passbook.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(InputPatternActivity.this,"incorrect password",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }
}