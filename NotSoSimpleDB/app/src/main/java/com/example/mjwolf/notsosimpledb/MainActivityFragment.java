package com.example.mjwolf.notsosimpledb;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static String TAG = "MainActivityFragment";
    private String name;
    private String email;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

        @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        Bundle args = getArguments();

        if (args != null) { //this is not a new Fragment
            Log.d(TAG, "extracting Bundle contents");
            name = args.getString("NAME");
            email = args.getString("EMAIL");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");

        EditText nameText = (EditText) getActivity().findViewById(R.id.name);
        EditText emailText = (EditText) getActivity().findViewById(R.id.email);

        if (nameText != null && emailText != null) {
            //we are in portrait mode, update the bits we need

            nameText.setText(name);
            emailText.setText(email);

        }
    }

        @Override
    public void onDetach() {
        super.onDetach();

    }


}
