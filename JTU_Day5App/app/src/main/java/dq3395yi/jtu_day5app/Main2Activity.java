package dq3395yi.jtu_day5app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private static String LOG_TAG = "MainActivity2";

    public static String targetWord = "";

    protected EditText edc_guessed_word;
    protected Button btn_guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edc_guessed_word = (EditText)findViewById(R.id.EDC_GUESSWORD);
        btn_guess = (Button)findViewById(R.id.BTN_GUESS);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
            targetWord = null;
        else
            targetWord = extras.getString("TARGETWORD");
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }

    public void BTN_GUESSonClick(View view){
        if (edc_guessed_word.getText().toString().equals(targetWord)){
            Toast toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Guess. The input word was: " + targetWord, Toast.LENGTH_LONG);
            toast.show();
        }

        btn_guess.setEnabled(false);
    }
}
