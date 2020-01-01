package com.example.asmuseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.thirdlib.ThirdLibSDK;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.btn_hand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdLibSDK.getInstance().doSubmit();
            }
        });
    }
}
