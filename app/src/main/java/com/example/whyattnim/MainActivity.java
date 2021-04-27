package com.example.whyattnim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.widget.Button;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int orientation = 0;
    public static String opponent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeScreen( View v ) {
        Button value = (Button)v;
        TextView changeView = (TextView)findViewById(R.id.stickView);

        opponent = value.getText().toString();

        orientation = this.getResources().getConfiguration().orientation;
        Intent myIntent = new Intent(this,Game.class);
        this.startActivity(myIntent);

    }
}