package dq3395yi.jtu_654app;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class PlayerListActivity extends AppCompatActivity implements PlayerInterface, GameInterface {
    private static final String LOG_TAG = "PlayerListActivity";

    private int turn;
    private String players_list_text;
    private String current_player_name;
    private int roll_count = 0;
    private int player_score = 0;
    private int high_score = 0;
    private String high_score_player = "";
    private ArrayList<Integer> rolls = new ArrayList<>();
    private ArrayList<Integer> held = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_menu);

        Intent intent = getIntent();
        turn = intent.getIntExtra("turn", 0);
        players_list_text = intent.getStringExtra("players_list_text");
        current_player_name = intent.getStringExtra("current_player_name");
        high_score = intent.getIntExtra("high_score", 0);
        high_score_player = intent.getStringExtra("high_score_player");
        roll_count = intent.getIntExtra("roll_count", 0);
        player_score = intent.getIntExtra("player_score", 0);

        if (savedInstanceState != null){
            turn = savedInstanceState.getInt("turn");
            players_list_text = savedInstanceState.getString("players_list_text");
            current_player_name = savedInstanceState.getString("current_player_name");
            roll_count = savedInstanceState.getInt("roll_count");
            player_score = savedInstanceState.getInt("player_score");
            high_score = savedInstanceState.getInt("high_score");
            high_score_player = savedInstanceState.getString("high_score_player");
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            String[] names = players_list_text.split("\n");
            setCurrentPlayerName(names[turn % names.length]);

            startGame();
        }
    }

    public void startGame() {
        Intent myIntent = new Intent(PlayerListActivity.this, GameActivity.class);
        myIntent.putExtra("turn", turn);
        myIntent.putExtra("players_list_text", players_list_text);
        myIntent.putExtra("current_player_name", current_player_name);
        myIntent.putExtra("high_score", high_score);
        myIntent.putExtra("high_score_player", high_score_player);

        PlayerListActivity.this.startActivity(myIntent);

        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putInt("turn", turn);
        bundle.putString("players_list_text", players_list_text);
        bundle.putString("current_player_name", current_player_name);
        bundle.putInt("roll_count", roll_count);
        bundle.putInt("player_score", player_score);
        bundle.putInt("high_score", high_score);
        bundle.putString("high_score_player", high_score_player);
    }

    public String getPlayers(){
        return players_list_text;
    }

    public int getTurn(){
        return turn;
    }
    public void setTurn(int t) {
        turn = t;
    }

    public String getCurrentPlayerName(){
        return current_player_name;
    }
    public void setCurrentPlayerName(String name){
        current_player_name = name;
    }

    public int getHighScore(){
        return high_score;
    }
    public void setHighScore(int score){
        high_score = score;
    }

    public String getHighScoreName(){
        return high_score_player;
    }
    public void setHighScoreName(String name){
        high_score_player = name;
    }

    public int getScore(){
        return player_score;
    }
    public void setScore(int score){
        player_score = score;
    }

    public int getRollCount(){
        return roll_count;
    }
    public void setRollCount(int count){
        roll_count = count;
    }

    public ArrayList<Integer> getRolls(){
        return rolls;
    }
    public void setRolls(ArrayList<Integer> r){
        rolls = r;
    }

    public ArrayList<Integer> getHeld(){
        return held;
    }
    public void setHeld(ArrayList<Integer> h){
        held = h;
    }
}
