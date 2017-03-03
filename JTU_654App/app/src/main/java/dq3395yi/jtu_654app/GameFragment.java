package dq3395yi.jtu_654app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment implements View.OnClickListener{
    private static final String LOG_TAG = "GameFragment";

    private static final long seed = 127; //System.currentTimeMillis();
    private static final Random rng = new Random(seed);

    protected ImageButton btn_roll1;
    protected ImageButton btn_roll2;
    protected ImageButton btn_roll3;
    protected ImageButton btn_roll4;
    protected ImageButton btn_roll5;
    protected ImageButton btn_held1;
    protected ImageButton btn_held2;
    protected ImageButton btn_held3;
    protected ImageButton btn_held4;
    protected ImageButton btn_held5;
    protected Button btn_roll;
    protected Button btn_submit_score;

    private ArrayList<Integer> rolls = new ArrayList<>();
    private ArrayList<Integer> held = new ArrayList<>();

    public GameFragment() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GameInterface gi = (GameInterface) getActivity();

        rolls = gi.getRolls();
        held = gi.getHeld();
        held.removeAll(Collections.singletonList(0));

        Log.d(LOG_TAG, "onActivityCreated " + gi.getRollCount());

        btn_roll1 = (ImageButton)getActivity().findViewById(R.id.roll1);
        btn_roll2 = (ImageButton)getActivity().findViewById(R.id.roll2);
        btn_roll3 = (ImageButton)getActivity().findViewById(R.id.roll3);
        btn_roll4 = (ImageButton)getActivity().findViewById(R.id.roll4);
        btn_roll5 = (ImageButton)getActivity().findViewById(R.id.roll5);
        btn_roll1.setOnClickListener(this);
        btn_roll2.setOnClickListener(this);
        btn_roll3.setOnClickListener(this);
        btn_roll4.setOnClickListener(this);
        btn_roll5.setOnClickListener(this);

        btn_held1 = (ImageButton)getActivity().findViewById(R.id.held1);
        btn_held2 = (ImageButton)getActivity().findViewById(R.id.held2);
        btn_held3 = (ImageButton)getActivity().findViewById(R.id.held3);
        btn_held4 = (ImageButton)getActivity().findViewById(R.id.held4);
        btn_held5 = (ImageButton)getActivity().findViewById(R.id.held5);
        btn_held1.setOnClickListener(this);
        btn_held2.setOnClickListener(this);
        btn_held3.setOnClickListener(this);
        btn_held4.setOnClickListener(this);
        btn_held5.setOnClickListener(this);

        btn_roll = (Button)getActivity().findViewById(R.id.BTN_ROLL);
        btn_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameInterface gi = (GameInterface) getActivity();
                gi.setRollCount(gi.getRollCount() + 1);
                if (gi.getRollCount() >= 3) {
                    btn_roll.setEnabled(false);
                }

                rolls.clear();
                for (int i = 0; i < 5 - held.size(); i++){
                    rolls.add(rollDie());
                }

                updateScreen();
            }
        });

        btn_submit_score = (Button)getActivity().findViewById(R.id.BTN_SUBMIT_SCORE);
        btn_submit_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameInterface gi = (GameInterface) getActivity();
                gi.setScore(calcScore());

                if (gi.getScore() > gi.getHighScore()) {
                    gi.setHighScore(gi.getScore());
                    gi.setHighScoreName(gi.getCurrentPlayerName());
                }

                gi.setTurn(gi.getTurn() + 1);
                ((GameActivity)getActivity()).endTurn();
            }
        });

        updateScreen();
    }

    @Override
    public void onClick(View v) {
        GameInterface gi = (GameInterface) getActivity();
        if (gi.getRollCount() > 0) {
            switch (v.getId()) {
                case R.id.roll1:
                case R.id.roll2:
                case R.id.roll3:
                case R.id.roll4:
                case R.id.roll5: {
                    int index = getIntFromImageID(v.getId());
                    if (rolls.size() > index && rolls.get(index) > 0) {
                        if (held.size() >= 3){
                            held.add(rolls.get(index));
                            rolls.set(index, 0);
                        }
                        else {
                            for (int i = 0; i < 3; i++) {
                                if (held.size() == i && rolls.get(index) == 6 - i) {
                                    held.add(rolls.get(index));
                                    rolls.set(index, 0);
                                }
                            }
                        }
                    }
                    break;
                }
                case R.id.held1:
                case R.id.held2:
                case R.id.held3:
                case R.id.held4:
                case R.id.held5: {
                    int index = getIntFromImageID(v.getId());
                    if (index < 3) {
                        for (int i = index; i < held.size(); i++){
                            if (held.size() > i && held.get(i) > 0) {
                                boolean found = false;
                                for (int j = 0; j < rolls.size(); j++){
                                    if (rolls.get(j) == 0){
                                        rolls.set(j, held.get(i));
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found && rolls.size() < 5){
                                    rolls.add(rolls.size(), held.get(i));
                                }

                                held.set(i, 0);
                            }
                        }
                        held.removeAll(Collections.singletonList(0));
                    }
                    else {
                        if (held.size() > index && held.get(index) > 0) {
                            boolean found = false;
                            for (int i = 0; i < rolls.size(); i++) {
                                if (rolls.get(i) == 0) {
                                    rolls.set(i, held.get(index));
                                    found = true;
                                    break;
                                }
                            }
                            if (!found && rolls.size() < 5){
                                rolls.add(rolls.size(), held.get(index));
                            }
                            held.remove(index);
                        }
                    }
                    break;
                }
            }

            updateScreen();
        }
    }

    public int rollDie() {
        return rng.nextInt(6) + 1;
    }

    public int getDiceImageID(int roll){
        switch (roll){
            case 1: return R.drawable.die1;
            case 2: return R.drawable.die2;
            case 3: return R.drawable.die3;
            case 4: return R.drawable.die4;
            case 5: return R.drawable.die5;
            case 6: return R.drawable.die6;
            default: return 0;
        }
    }

    public int getIntFromImageID(int ID){
        switch (ID) {
            case R.id.roll1:case R.id.held1:return 0;
            case R.id.roll2:case R.id.held2:return 1;
            case R.id.roll3:case R.id.held3:return 2;
            case R.id.roll4:case R.id.held4:return 3;
            case R.id.roll5:case R.id.held5:return 4;
            default: return -1;
        }
    }

    public void drawDice(){
        if (held.size() > 0) btn_held1.setBackgroundResource(getDiceImageID(held.get(0)));
        else btn_held1.setBackgroundColor(Color.TRANSPARENT);
        if (held.size() > 1) btn_held2.setBackgroundResource(getDiceImageID(held.get(1)));
        else btn_held2.setBackgroundColor(Color.TRANSPARENT);
        if (held.size() > 2) btn_held3.setBackgroundResource(getDiceImageID(held.get(2)));
        else btn_held3.setBackgroundColor(Color.TRANSPARENT);
        if (held.size() > 3) btn_held4.setBackgroundResource(getDiceImageID(held.get(3)));
        else btn_held4.setBackgroundColor(Color.TRANSPARENT);
        if (held.size() > 4) btn_held5.setBackgroundResource(getDiceImageID(held.get(4)));
        else btn_held5.setBackgroundColor(Color.TRANSPARENT);

        if (rolls.size() > 0 && rolls.get(0) > 0) btn_roll1.setBackgroundResource(getDiceImageID(rolls.get(0)));
        else btn_roll1.setBackgroundColor(Color.TRANSPARENT);
        if (rolls.size() > 1 && rolls.get(1) > 0) btn_roll2.setBackgroundResource(getDiceImageID(rolls.get(1)));
        else btn_roll2.setBackgroundColor(Color.TRANSPARENT);
        if (rolls.size() > 2 && rolls.get(2) > 0) btn_roll3.setBackgroundResource(getDiceImageID(rolls.get(2)));
        else btn_roll3.setBackgroundColor(Color.TRANSPARENT);
        if (rolls.size() > 3 && rolls.get(3) > 0) btn_roll4.setBackgroundResource(getDiceImageID(rolls.get(3)));
        else btn_roll4.setBackgroundColor(Color.TRANSPARENT);
        if (rolls.size() > 4 && rolls.get(4) > 0) btn_roll5.setBackgroundResource(getDiceImageID(rolls.get(4)));
        else btn_roll5.setBackgroundColor(Color.TRANSPARENT);
    }

    public int calcScore(){
        int score = 0;
        if (held.size() > 3) score += held.get(3);
        if (held.size() > 4) score += held.get(4);
        String scoretext = "Score: " + score;
        GameInterface gi = (GameInterface) getActivity();
        String highscoretext = "High Score: " + gi.getHighScoreName() + " (" + gi.getHighScore() + ")";
        ((TextView)getActivity().findViewById(R.id.TV_SCORE)).setText(scoretext);
        ((TextView)getActivity().findViewById(R.id.TV_HIGH_SCORE)).setText(highscoretext);
        return score;
    }

    public void updateScreen(){
        GameInterface gi = (GameInterface) getActivity();

        drawDice();
        calcScore();

        if (held.size() == 5 || gi.getRollCount() >= 3)
            btn_roll.setEnabled(false);
        else if (gi.getRollCount() < 3)
            btn_roll.setEnabled(true);
    }
}
