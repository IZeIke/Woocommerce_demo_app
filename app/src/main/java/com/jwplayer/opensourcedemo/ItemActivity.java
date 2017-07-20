package com.jwplayer.opensourcedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static java.security.AccessController.getContext;

public class ItemActivity extends AppCompatActivity {

    TextView title;
    TextView decs;
    TextView price;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        String priceStr = intent.getStringExtra("price");
        String titleStr = intent.getStringExtra("title");
        String descStr = intent.getStringExtra("desc");
        String urlStr = intent.getStringExtra("url");

        title = (TextView) findViewById(R.id.title_item);
        decs = (TextView) findViewById(R.id.desc_item);
        price = (TextView) findViewById(R.id.price_item);
        image = (ImageView) findViewById(R.id.thumbnail_item);
        title.setText(titleStr);
        price.setText(priceStr);
        decs.setText(descStr);
        Glide.with(this).load(urlStr).into(image);


    }
}
