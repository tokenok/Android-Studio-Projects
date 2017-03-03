package com.example.mjwolf.pizzaapp;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ToppingsFragment extends ListFragment {

    private static final String DEBUG_TAG = "ToppingsFragment";
    boolean mShowTwoFragments;
    int meatCount = 0;
    int vegCount = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ToppingsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //allow a multiple items to be selected. There are other options.
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Populate our ListView control within the Fragment
        String[] veggies = getResources().getStringArray(
                R.array.toppings_array);
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, veggies));

//         Check which state we're in
        View detailsFrame = getActivity().findViewById(R.id.distanceEntry);
        mShowTwoFragments = detailsFrame != null
                && detailsFrame.getVisibility() == View.VISIBLE; //The frame may exist, but be hidden
        Log.d(DEBUG_TAG, "Show 2 Frags: "+ mShowTwoFragments);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mShowTwoFragments ) {
            showOrderInfo();
        }
    }

    void showOrderInfo() {
        Log.d(DEBUG_TAG, "Show 2 Frags: "+ mShowTwoFragments);
        meatCount = 0;
        vegCount = 0;
        ListView l = getListView();

        SparseBooleanArray checked = l.getCheckedItemPositions();

//        Log.d(DEBUG_TAG, "  count "+ Integer.toString(l.getCount()));

        for (int count = 0; count < l.getCount() / 2; count++) {  //Meats are first
            if (checked.get(count)) {
                meatCount++;
            }
        }
        for (int count = l.getCount() / 2; count < l.getCount() - 1; count++) { //Veg is last
            if (checked.get(count)) {
                vegCount++;
            }
        }
        if (mShowTwoFragments) {

            MainActivity mainActivity = (MainActivity)    getActivity();
            mainActivity.setMeatCount(meatCount);
            mainActivity.setVegCount(vegCount);
//            Log.d(DEBUG_TAG, "Show 2 Frags: about to call updateToppings in other Frag");
            FragmentManager fm = getFragmentManager();
            DistanceFragment distanceFragment = (DistanceFragment) fm.findFragmentById(R.id.distanceEntry);
            distanceFragment.updateToppings();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        showOrderInfo();

        TextView textView = (TextView) v;
        if (textView.getText().equals("Submit")) {
            v.setActivated(false);

            if (!mShowTwoFragments) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), DistanceActivity.class);
                intent.putExtra("meats", meatCount);
                intent.putExtra("veg", vegCount);

                startActivity(intent);
            }
        }
    }

}
