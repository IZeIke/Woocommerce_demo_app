package com.jwplayer.opensourcedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button shopBtn;
    Button videoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shopBtn = (Button) findViewById(R.id.shopBtn);
        videoBtn = (Button) findViewById(R.id.videoBtn);

        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,JWPlayerViewExample.class);

                startActivity(intent);

            }
        });

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FeedActivity.class);

                startActivity(intent);

            }
        });
    }
}
