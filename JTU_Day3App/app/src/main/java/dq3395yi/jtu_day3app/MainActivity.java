package dq3395yi.jtu_day3app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /*
       i clicked the run button, i selected the app from the recent stack
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    protected void onStart(){
        /*
         i clicked the run button, i selected the app from the recent stack
        */
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onRestart(){
        /*
        i selected the app from the recent stack
         */
        super.onRestart();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onResume(){
        /*
        i clicked the run button, i selected the app from the recent stack
         */
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        /*
        i clicked the home button, i clicked the recent stack button, i pressed the back button
         */
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        /*
        i clicked the home button, i clicked the recent stack button, i pressed the back button
         */
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        /*
        i closed the app from the recent stack screen, i pressed the back button
         */
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }
}
