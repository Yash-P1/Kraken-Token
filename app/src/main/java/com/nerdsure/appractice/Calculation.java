package com.nerdsure.appractice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calculation extends AppCompatActivity {

    private EditText et_value;
    private EditText et_percentage;
    private float value,percentage;
    private float rwd;
    private Button btn_count, btn_send;
    private TextView tv_reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

            et_value = findViewById(R.id.value);
            et_percentage = findViewById(R.id.percentage);
            btn_count = findViewById(R.id.button);
            tv_reward = findViewById(R.id.reward_amount);
            btn_send = findViewById(R.id.btn_send);

            btn_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        value = Float.parseFloat(et_value.getText().toString());
                        percentage = Float.parseFloat(et_percentage.getText().toString());
                        rwd = (value * percentage) / 100;
                        tv_reward.setText(String.valueOf(rwd));
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(),"Value or Percentage is Empty!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://metamask.io"));
                    //"chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/home.html#initialize/welcome"
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.yandex.browser.beta");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        // Yandex browser presumably not installed so allow user to choose instead
                        intent.setPackage(null);
                        startActivity(intent);
                    }
                }
            });
    }
}

