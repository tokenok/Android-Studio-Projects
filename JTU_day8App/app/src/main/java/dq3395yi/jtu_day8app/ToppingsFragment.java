package dq3395yi.jtu_day8app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static dq3395yi.jtu_day8app.R.array.toppings;

public class ToppingsFragment extends ListFragment {
    private OnFragmentInteractionListener mListener;

    ListView lv_toppings;

    public ToppingsFragment() { }

    public static ToppingsFragment newInstance() {
        ToppingsFragment fragment = new ToppingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean b_portrait = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);

        String[] rtoppings = getResources().getStringArray(toppings);
        String[] toppings = new String[rtoppings.length + (b_portrait ? 1 : 0)];
        for (int i = 0; i < rtoppings.length; i++){
            String[] t = rtoppings[i].split(",");
            toppings[i] = t[0];
        }
        if (b_portrait)
            toppings[toppings.length - 1] = "Done";
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, toppings));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toppings_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Toppings Fragment", "onAttach context");
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("Toppings Fragment", "onAttach activity");
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        FragmentManager fm = getFragmentManager();
        SubmitFragment fragment = (SubmitFragment) fm.findFragmentById(R.id.FRAG_SUBMIT);
        mListener.onFragmentInteraction(fragment, lv);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(SubmitFragment sf, ListView lv);
    }
}
