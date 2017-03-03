package dq3395yi.jtu_day8app;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class MainActivity extends FragmentActivity implements ToppingsFragment.OnFragmentInteractionListener, SubmitFragment.OnFragmentInteractionListener {
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
   //     setTitle("Select your toppings");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    public void onFragmentInteraction(SubmitFragment sf, ListView lv) {
        boolean b_portrait = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);

        if (b_portrait) {
            if (lv.getCheckedItemPositions().get(lv.getCount() - 1)){
                setContentView(R.layout.submit_fragment);
/*
                lv.setItemChecked(lv.getCount() - 1, false);

                FragmentManager fm = getSupportFragmentManager();

                SubmitFragment nsf = SubmitFragment.newInstance(lv);

                fm.beginTransaction().add(R.id.activity_main, nsf).commit();

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.activity_main, nsf);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                ft = fm.beginTransaction();
                ToppingsFragment myFrag = (ToppingsFragment)fm.findFragmentById(R.id.FRAG_TOPPINGS);
                ft.hide(myFrag);
                ft.commit();*/
            }
        }
        else {
            if (sf != null && sf.isAdded()) {
                sf.calcPrice(lv);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
