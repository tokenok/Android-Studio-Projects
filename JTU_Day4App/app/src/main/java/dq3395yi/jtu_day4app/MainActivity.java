package dq3395yi.jtu_day4app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "GUESSING_GAME";

    protected Button btn_hide;
    protected Button btn_guess;

    protected EditText edc_hidden;
    protected EditText edc_guess;


    protected TextView tv_guess;
    protected TextView tv_hidden;
    protected TextView tv_correct;
    protected TextView tv_guess_word;
    protected TextView tv_hidden_word;
    protected TextView tv_correct_word;

    private String hidden_word = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_hide = (Button)findViewById(R.id.hide);
        btn_guess = (Button)findViewById(R.id.guess);

        edc_hidden = (EditText)findViewById(R.id.edc_hidden_word);
        edc_guess = (EditText)findViewById(R.id.edc_guessed_word);

        tv_guess = (TextView)findViewById(R.id.tv_guessed);
        tv_hidden = (TextView)findViewById(R.id.tv_original);
        tv_correct = (TextView)findViewById(R.id.tv_correct);
        tv_guess_word = (TextView)findViewById(R.id.tv_guessed_word);
        tv_hidden_word = (TextView)findViewById(R.id.tv_original_word);
        tv_correct_word = (TextView)findViewById(R.id.tv_correct_word);

        edc_guess.setEnabled(false);
        btn_guess.setEnabled(false);

        tv_guess.setVisibility(View.INVISIBLE);
        tv_hidden.setVisibility(View.INVISIBLE);
        tv_correct.setVisibility(View.INVISIBLE);
        tv_guess_word.setVisibility(View.INVISIBLE);
        tv_hidden_word.setVisibility(View.INVISIBLE);
        tv_correct_word.setVisibility(View.INVISIBLE);
    }

    public void toggle_buttons(){
        btn_guess.setEnabled(!btn_guess.isEnabled());
        btn_hide.setEnabled(!btn_hide.isEnabled());

        edc_guess.setEnabled(!edc_guess.isEnabled());
        edc_hidden.setEnabled(!edc_hidden.isEnabled());
    }

    public void btn_hide_onclick(View view) {
        Log.d(TAG, "word hidden");

        hidden_word = edc_hidden.getText().toString();
        edc_hidden.setText("");

        toggle_buttons();

        tv_guess.setVisibility(View.INVISIBLE);
        tv_hidden.setVisibility(View.INVISIBLE);
        tv_correct.setVisibility(View.INVISIBLE);
        tv_guess_word.setVisibility(View.INVISIBLE);
        tv_hidden_word.setVisibility(View.INVISIBLE);
        tv_correct_word.setVisibility(View.INVISIBLE);
    }

    public void btn_guess_onclick(View view){
        Log.d(TAG, "guess submitted");

        String guess = edc_guess.getText().toString();

        toggle_buttons();

        tv_guess.setVisibility(View.VISIBLE);
        tv_hidden.setVisibility(View.VISIBLE);
        tv_correct.setVisibility(View.VISIBLE);
        tv_guess_word.setVisibility(View.VISIBLE);
        tv_hidden_word.setVisibility(View.VISIBLE);
        tv_correct_word.setVisibility(View.VISIBLE);

        if (guess.equals(hidden_word))
            tv_correct_word.setText("true");
        else
            tv_correct_word.setText("false");

        tv_hidden_word.setText(hidden_word);
        tv_guess_word.setText(guess);

        edc_hidden.setActivated(true);
    }
}
