package com.nerdsure.appractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Passbook extends AppCompatActivity {

    private TextView mTextViewBalance;
    private RequestQueue mQueue;
    int total_balance=10000;

    ArrayList<String> from = new ArrayList<String>();
    ArrayList<String> to = new ArrayList<String>();
    ArrayList<String> amount = new ArrayList<String>();

    String[] From ;//,"234","345","456","567","678","789","890","901"};
    String[] To ;//,"@#$","#$%","#$%","$^&","%$#","&^%","(*&","(&^"};
    String[] Amount ;//,"j","h","g","f","a","s","d","f"};

    Boolean timeRefreshPressed = true;
    String Account_number;// = "0x6364Aab7199eF17c03E334231C54405023653392";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook);

        final ListView ListView = findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter();

        SharedPreferences prefs = getSharedPreferences("MyPref", 0);
        Account_number = prefs.getString("User", null);

        //ListView.setAdapter(customAdapter);

        mTextViewBalance = findViewById(R.id.tv_balance);
        Button buttonRefresh = findViewById(R.id.button_refresh);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonParse();
                From = GetStringArray(from);
                To = GetStringArray(to);
                Amount = GetStringArray(amount);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        ListView.setAdapter(customAdapter);

                    }
                }, 1000);
                //ListView.setAdapter(customAdapter);
            }
        });
    }

    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return From.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            TextView textView_from = view.findViewById(R.id.tv_From);
            TextView textView_to = view.findViewById(R.id.tv_To);
            TextView textView_amount = view.findViewById(R.id.tv_amount);

            textView_from.setText("From: " + From[i]);
            textView_to.setText("To: " + To[i]);
            textView_amount.setText("Amount: " + Amount[i]);
            return view;
        }
    }

    public void jsonParse() {
        if(timeRefreshPressed) {
            timeRefreshPressed = false;
            String url = "http://api-ropsten.etherscan.io/api?module=account&action=tokentx&address=" + Account_number + "&startblock=0&endblock=999999999&sort=asc&apikey=HK5DCN4RBUX8KA5UBWA54TF649QJ9BGDIR";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("result");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject result = jsonArray.getJSONObject(i);

                                    String From = result.getString("from");
                                    from.add(From);
                                    String To = result.getString("to");
                                    to.add(To);
                                    int value = result.getInt("value");
                                    amount.add(String.valueOf(value));
                                    if (From.substring(37,42) == Account_number.substring(37,42)) {
                                        //Toast.makeText(getApplicationContext(),"If",Toast.LENGTH_SHORT).show();
                                        total_balance += value;
                                    }else{
                                        //Toast.makeText(getApplicationContext(),"Else",Toast.LENGTH_SHORT).show();
                                        total_balance -=  value;
                                    }
                                    //mTextViewTotalBalance.append("------------------------\nFrom : " + From.substring(37) + "\nTo : " + To.substring(37) + "\nValue : " + value + "\n");
                                }
                                mTextViewBalance.setText(String.valueOf(total_balance));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            mQueue.add(request);
        }else{
            Toast.makeText(Passbook.this,"Refreshed.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeScreen.class));
    }
}

