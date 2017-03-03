package com.example.mjwolf.notsosimpledb;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener{
    private static String TAG = "MainActivity";
    private DBAdapter db;
    private static String UPDATE_FRAG = "UPDATE_FRAG";
    private static String ITEM_FRAG = "ITEM_FRAG";
    long activeID; //use to keep track of whether an item has been selected and if so, its id in the DB
    private ItemFragment itemFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DBAdapter(this);
        activeID = -1;
        Log.d(TAG, "onCreate: dbAdadpter init");

        if ( findViewById(R.id.fragment_container) != null){
            // in portrait mode, Note that in landscape mode the fragments are declared statically
            Log.d(TAG, "frag container found");
            View v = findViewById(R.id.showAll);
            if(v != null) v.setVisibility(View.VISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            if( savedInstanceState == null){
                //starting Activity for the first time
                Log.d(TAG, "starting fresh");
                MainActivityFragment maf = new MainActivityFragment();
                ft.replace(R.id.fragment_container, maf, UPDATE_FRAG); //UPDATE_FRAG is a a tag
            }
            else{
                Log.d(TAG, "restarting");
                //Since we are restarting, there may be one or more fragments laying around.
                //Let's try to find them
                MainActivityFragment maFrag = (MainActivityFragment) fm.findFragmentByTag(UPDATE_FRAG);
                if (maFrag == null){
                    //we maybe started in landscape mode
                    maFrag = (MainActivityFragment) fm.findFragmentById(R.id.updateFrag);
                }
                itemFrag = (ItemFragment) fm.findFragmentByTag(ITEM_FRAG);
                if (itemFrag == null){
                    //we maybe started in landscape mode
                    itemFrag = (ItemFragment) fm.findFragmentById(R.id.listFrag);
                }
                if( maFrag != null && itemFrag != null){
                    Log.d(TAG, "both frags present");
                    //forces the update frag to the foreground
                    fm.popBackStack();
                }
            }
            ft.commit();
        }
        else { //don't show the Update button in landscape mode
            findViewById(R.id.showAll).setVisibility(View.INVISIBLE);
        }
    }

    public void DisplayContact(Cursor c) //displays a single contact
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Name: " + c.getString(1) + "\n" +
                        "Email:  " + c.getString(2),
                Toast.LENGTH_SHORT).show();
    }

    //This method handles callbacks from the ItemFragment list.
    public void onFragmentInteraction(long id){
        Log.d(TAG, "onFragmentInteraction");

        activeID = id;
        db.open();
        Cursor c = db.getContact(activeID);
        db.close();
        String name = c.getString(c.getColumnIndex(db.KEY_NAME));
        String email = c.getString(c.getColumnIndex(db.KEY_EMAIL));

        MainActivityFragment updateFrag = (MainActivityFragment) getSupportFragmentManager().findFragmentByTag(UPDATE_FRAG);
        EditText nameText = (EditText) findViewById(R.id.name);
        EditText emailText = (EditText) findViewById(R.id.email);

        if (updateFrag != null && nameText != null && emailText != null) {
            //we are in landscape mode, just look for the bits we need

            nameText.setText(name);
            emailText.setText(email);

        } else{  // Set up the new Fragment and put the bits in that we want
            Log.d(TAG, "setting up new UpdateFrag with name ");
            Bundle args = new Bundle();
            args.putString("NAME",name );
            args.putString("EMAIL", email);
            updateFrag.setArguments(args);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.fragment_container, updateFrag);
            ft.commit();
        }
    }

    public void addUser(View view){

        Log.d(TAG, "Adding to DB");

        EditText nameText = (EditText) findViewById(R.id.name);
        EditText emailText = (EditText) findViewById(R.id.email);

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();

        //do some sanitizing and error checking before actually adding anything to a DB
        db.open();
        long id = db.insertContact(name,email);
        db.close();

        Toast.makeText(this,name + " added with id " + id, Toast.LENGTH_SHORT).show();

        if (findViewById(R.id.listFrag) != null) {
            ItemFragment itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);
            itemFragment.notifyAdapter(true, id); // is there a better way to signify at the end?
        }

        //reset the text boxes to take in the next values
        nameText.setText("");
        emailText.setText("");

        activeID = -1; //make sure that if the fields were pre-populated, that that entry is not
                       //removed from the DB
    }

    public void showAll(View view){
        Log.d(TAG, "showAll");

        if (findViewById(R.id.listFrag)== null) {
            Log.d("MainActivity", "Doing Frag replacement");
            itemFrag = ItemFragment.newInstance();
            FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, itemFrag, ITEM_FRAG);
            ft.addToBackStack(null);
            ft.commit();
        }
        activeID = -1;
    }

    public void deleteEntry(View view) {
        Log.d(TAG, "Delete Entry");
        db.open();
        if (db.deleteContact(activeID)){
            ItemFragment itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentByTag(ITEM_FRAG);
            if (itemFragment != null) {
                Log.d("MainActivity", "Notifying Adapter");
                itemFragment.notifyAdapter(false, activeID);
            }
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close();
        activeID = -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
