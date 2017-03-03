package com.example.mjwolf.simplefragments2;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class VeggieGardenWebViewFragment extends WebViewFragment {

    private static final String DEBUG_TAG = "VGWebViewFragment";

    public static VeggieGardenWebViewFragment newInstance(int index) {
        Log.v(DEBUG_TAG, "Creating new instance: " + index);
        VeggieGardenWebViewFragment fragment = new VeggieGardenWebViewFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    public int getShownIndex() {
        int index = -1;
        Bundle args = getArguments();
        if (args != null) {
            index = args.getInt("index", -1);
        }
        if (index == -1) {
            Log.e(DEBUG_TAG, "Not an array index.");
        }

        return index;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onActivityCreated(): " + getShownIndex());
        super.onActivityCreated(savedInstanceState);

        // Load the URL after the Webview has been created in onCreateView()
        String[] veggieUrls;
        veggieUrls = getResources().getStringArray(
                R.array.veggieurls_array);
        int veggieUrlIndex = getShownIndex();

        WebView webview = getWebView();
        webview.setPadding(0, 0, 0, 0);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        if (veggieUrlIndex != -1) {
            String veggieUrl = veggieUrls[veggieUrlIndex];
            webview.loadUrl(veggieUrl);
        } else {
            webview.loadUrl("http://andys-veggie-garden.appspot.com/cherrytomatoes");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onCreate() ");
        super.onCreate(savedInstanceState);
        Log.d(DEBUG_TAG, "OnCreate(): " + getShownIndex());
    }


    @Override
    public void onStart() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onStart(): " + getShownIndex());
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onResume():" +  getShownIndex());
    }

    @Override
    public void onDetach() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onDetach()");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onPause(): " + getShownIndex());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onStop(): " + getShownIndex());
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: onDestroy()");
        super.onDestroy();
    }
}