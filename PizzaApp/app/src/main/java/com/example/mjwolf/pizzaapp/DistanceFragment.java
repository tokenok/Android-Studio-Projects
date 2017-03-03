package com.example.mjwolf.pizzaapp;


import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DistanceFragment extends Fragment {
    private static final String DEBUG_TAG = "DistanceFragment";


    public DistanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_distance, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(DEBUG_TAG, " in oAC");
        updateToppings();
    }

    public void updateToppings(){
        int meatCount = 0;
        int vegCount = 0;
        Log.d(DEBUG_TAG, " updating toppings");

        ToppingsInterface d = (ToppingsInterface) getActivity();
        meatCount  = d.getMeatCount();
        vegCount  = d.getVegCount();
        TextView v = (TextView) getActivity().findViewById(R.id.price);
        v.setText("Your total is " + Integer.toString(meatCount) + " meats and \n" + Integer.toString(vegCount) + " veggies.");

    }
}
