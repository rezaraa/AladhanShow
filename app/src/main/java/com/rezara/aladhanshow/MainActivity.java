package com.rezara.aladhanshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    TextView txtFajrValue;
    ImageButton btn_search;
    EditText edt_azan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtFajrValue = findViewById(R.id.txtFajrValue);
        btn_search = findViewById(R.id.btn_search);
        edt_azan = findViewById(R.id.edt_azan);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                String address = "https://api.aladhan.com/v1/timingsByCity?city=" + edt_azan.getText().toString() + "&country=Iran&method=8";
                client.get(address, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Gson gson= new Gson();
                        PrayClass pray = gson.fromJson(response.toString(),PrayClass.class);
                        txtFajrValue.setText(pray.getData().getTimings().getFajr());

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        });

    }
}
