package dq3395yi.jtu_day19app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    int totalScore = 0;
    int ringCount = 0;
    int maxTaps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        totalScore = intent.getExtras().getInt("totalScore", 0);
        ringCount = intent.getExtras().getInt("ringCount", 0);
        maxTaps = intent.getExtras().getInt("maxTaps", 0);

        ((TextView)findViewById(R.id.TV_SCORE)).setText("Total Score: " + totalScore + "\nMaximum possible score is: " + (ringCount * maxTaps));

        ((Button)findViewById(R.id.BTN_PLAY_AGAIN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
