package com.example.mjwolf.sharedprefs;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    public static final String PREFERENCE_FILENAME = "AppPrefs";
    SharedPreferences appPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appPrefs = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); //Visible to all activities in the app
    }

    @Override
    protected void onResume(){
        super.onResume();
        String color = appPrefs.getString("color", "black");

        if (!color.equals("black")){
            setColor(color);
        }

    }

    private void setColor(String color){
        RelativeLayout r = (RelativeLayout) findViewById(R.id.content_main2);
        if(color.equals("Yellow")){
            r.setBackgroundColor(Color.YELLOW);
        }

        if(color.equals("White")){
            r.setBackgroundColor(Color.WHITE);
        }

    }
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        Button b = (Button) selectedItemView;
        String s = b.getText().toString();
        SharedPreferences.Editor spEditor = appPrefs.edit();
        spEditor.putString("color", s);
        spEditor.apply();

        setColor(s);

    }
}
