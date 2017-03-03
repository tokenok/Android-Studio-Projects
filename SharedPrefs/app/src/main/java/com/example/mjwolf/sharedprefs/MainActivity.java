package com.example.mjwolf.sharedprefs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String PREFERENCE_FILENAME = "AppPrefs";
    SharedPreferences activityPrefs;
    SharedPreferences appPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String s = null;

        activityPrefs = getPreferences(MODE_PRIVATE); // Visible only in this activity

        s = activityPrefs.getString("name", "enemy"); //check to see if the name is already set

        if (!s.equals("enemy")){
            TextView t = (TextView) findViewById(R.id.text);
            t.setText("Hello " + s + "!");
        }

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
    public void onClick(View view){
        Button b = (Button) view;
        String s = b.getText().toString();
        SharedPreferences.Editor spEditor = activityPrefs.edit();
        spEditor.putString("name", s);
        spEditor.apply();

        TextView t = (TextView) findViewById(R.id.text);
        t.setText("Hello " + s + "!");

    }
    private void setColor(String color) {
        RelativeLayout r = (RelativeLayout) findViewById(R.id.content_main);
        if (color.equals("Yellow")) {
            r.setBackgroundColor(Color.YELLOW);
        }

        if (color.equals("White")) {
            r.setBackgroundColor(Color.WHITE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
