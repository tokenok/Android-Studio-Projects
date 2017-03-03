package dq3395yi.jtu_654app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josh_2 on 2/13/2017.
 */

public interface GameInterface {
    int getTurn();
    void setTurn(int t);

    String getCurrentPlayerName();
    void setCurrentPlayerName(String name);

    int getHighScore();
    void setHighScore(int score);

    String getHighScoreName();
    void setHighScoreName(String name);

    int getScore();
    void setScore(int score);

    int getRollCount();
    void setRollCount(int count);

    ArrayList<Integer> getRolls();
    void setRolls(ArrayList<Integer> rolls);

    ArrayList<Integer> getHeld();
    void setHeld(ArrayList<Integer> held);
}
