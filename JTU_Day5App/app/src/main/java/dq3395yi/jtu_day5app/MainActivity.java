package dq3395yi.jtu_day5app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "MainActivity";

    protected EditText edc_target_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edc_target_word = (EditText)findViewById(R.id.EDC_TARGETWORD);
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
       super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }

    public void BTN_SUBMITonClick(View view){
        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
        i.putExtra("TARGETWORD", edc_target_word.getText().toString());
        startActivity(i);
    }

}
