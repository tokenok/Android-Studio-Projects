package com.example.mjwolf.pizzaapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DistanceActivity extends AppCompatActivity implements ToppingsInterface {

    private int meatCount;
    private int vegCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the values from the Intent to initialize the counts
        meatCount = getIntent().getExtras().getInt("meats", 0);
        vegCount = getIntent().getExtras().getInt("veg", 0);
        //However, if the app is restarting, get the saved values
        if (savedInstanceState != null){
            meatCount = savedInstanceState.getInt("meats");
            vegCount = savedInstanceState.getInt("veg");
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line so we don't need this activity.
            finish();
            return;
        }

//        if (savedInstanceState == null) {
//            setContentView(R.layout.activity_distance);
//
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            }
//
//            // During initial setup, plug in the veggie fragment.
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            DistanceFragment details = new DistanceFragment();
//            details.setArguments(getIntent().getExtras());
//
//            ft.replace(R.id.list, details);
//            ft.commit();
//        }
//

        setContentView(R.layout.activity_distance);




    }
    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("meats", meatCount);
        bundle.putInt("veg", vegCount);
    }

    public int getMeatCount(){
        return meatCount;
    }
    public int getVegCount(){
        return vegCount;
    }

}
