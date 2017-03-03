package dq3395yi.jtu_654app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlayerListFragment extends ListFragment {
    private static final String LOG_TAG = "PlayerListFragment";

    public PlayerListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(LOG_TAG, "onActivityCreated");


        ListView lv_players = getListView();
        lv_players.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        PlayerInterface pi = (PlayerInterface) getActivity();
        String[] players = pi.getPlayers().split("\n");

        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, players));

        for (int i = 0; i < lv_players.getCount(); i++) {
            lv_players.setItemChecked(i, pi.getTurn() % lv_players.getCount() == i);
        }

        lv_players.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlayerInterface pi = (PlayerInterface) getActivity();
                if (position == pi.getTurn() % getListView().getCount()) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        ((GameInterface) getActivity()).setCurrentPlayerName(getListView().getItemAtPosition(position).toString());
                        ((PlayerListActivity) getActivity()).startGame();
                    }
                }
                else {
                    getListView().setItemChecked(pi.getTurn() % getListView().getCount(), true);
                }
            }
        });

    }
}
