package dq3395yi.jtu_654app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    protected Button btn_add_player;
    protected EditText edc_new_player;
    protected TextView tv_player_list;
    protected Button btn_submit_players;

    private String players_list_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edc_new_player = (EditText)findViewById(R.id.EDC_PLAYER_NAME);

        tv_player_list = (TextView)findViewById(R.id.TV_PLAYER_LIST);
        tv_player_list.setMovementMethod(new ScrollingMovementMethod());

        btn_add_player = (Button)findViewById(R.id.BTN_ADD_PLAYER);
        btn_add_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edc_new_player.getText().length() > 0) {
                    tv_player_list.append(edc_new_player.getText() + "\n");
                    edc_new_player.setText("");
                }
            }
        });

        btn_submit_players = (Button)findViewById(R.id.BTN_SUBMIT_PLAYERS);
        btn_submit_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_player_list.getText().toString().length() > 0){
                    Intent myIntent = new Intent(MainActivity.this, PlayerListActivity.class);
                    myIntent.putExtra("turn", 0);
                    myIntent.putExtra("players_list_text", tv_player_list.getText().toString());
                    String[] players = tv_player_list.getText().toString().split("\n");
                    myIntent.putExtra("current_player_name", players[0]);
                    myIntent.putExtra("high_score", 0);
                    myIntent.putExtra("high_score_player", "");
                    myIntent.putExtra("roll_count", 0);
                    myIntent.putExtra("player_score", 0);

                    MainActivity.this.startActivity(myIntent);
                    finish();
                }
            }
        });

        if (savedInstanceState != null){
            players_list_text = savedInstanceState.getString("players_list_text");
            tv_player_list.setText(players_list_text);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putString("players_list_text", tv_player_list.getText().toString());
    }
}
