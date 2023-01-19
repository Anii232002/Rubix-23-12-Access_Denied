package com.example.csiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PostContent extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView backgroundimg;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String desc = extras.getString("desc");
        String img = extras.getString("img");

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        description =(TextView) findViewById(R.id.description);
        description.setText(desc);

        backgroundimg=findViewById(R.id.postImage);
        Glide.with(getApplicationContext()).load(img).into(backgroundimg);

    }
}