package com.nerdsure.appractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginSignup extends AppCompatActivity {

    Button log_in, sign_up, button_bypass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        log_in = findViewById(R.id.btn_login);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSignup.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        sign_up = findViewById(R.id.btn_signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSignup.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        button_bypass = findViewById(R.id.button_bypass);
        button_bypass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignup.this,HomeScreen.class));
            }
        });

    }

}

