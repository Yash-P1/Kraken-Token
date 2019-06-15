package com.nerdsure.appractice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button button_send = findViewById(R.id.btn_send);
        Button button_receive = findViewById(R.id.btn_receive);
        Button button_passbook = findViewById(R.id.btn_passbook);
        Button button_signout = findViewById(R.id.btn_signout);
        TextView textView2 = findViewById(R.id.textView2);

        SharedPreferences prefs = getSharedPreferences("MyPref", 0);
        String restoredText = prefs.getString("User", null);
        textView2.setText(restoredText);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeScreen.this,Calculation.class));

                /*Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://metamask.io"));
                //"chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/home.html#initialize/welcome"
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.yandex.browser");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Yandex browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }*/
            }
        });

        button_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Receive_QR.class));
            }
        });

        button_passbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),IsPatternAvailable.class));
            }
        });

        button_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                startActivity(new Intent(HomeScreen.this,LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(HomeScreen.this,HomeScreen.class));
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
