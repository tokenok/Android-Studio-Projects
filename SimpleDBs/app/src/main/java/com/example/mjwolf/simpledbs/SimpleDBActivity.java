package com.example.mjwolf.simpledbs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleDBActivity extends AppCompatActivity {
    private static String TAG= "SimpleDBActivity";

    private DBAdapter db;
    long activeID; //used to keep track of whether an item has been selected and if so, its id in the DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_db);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DBAdapter(this);
        activeID = -1;

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        /*Button btn_remove = (Button)findViewById(R.id.BTN_REMOVE);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(SimpleDBActivity.this, DataBaseListActivity.class);
               // startActivity(intent);
            }
        });*/
    }

    public void addUser(View view){

        Log.d(TAG, "Adding to DB");

        EditText nameText = (EditText) findViewById(R.id.name);
        EditText emailText = (EditText) findViewById(R.id.email);

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();

        //do some sanitizing and error checking before actually adding anything to a DB
        db.open();

        long id = db.insertContact(name, email);
        db.close();

        Toast.makeText(this,name + " added with id " + id, Toast.LENGTH_SHORT).show();

        //reset the text boxes to take in the next values
        nameText.setText("");
        emailText.setText("");

        activeID = -1; //make sure that if the fields were pre-populated, that that entry is not
        //removed from the DB

    }

    public void deleteContact(View view){
        EditText nameText = (EditText) findViewById(R.id.name);
        EditText emailText = (EditText) findViewById(R.id.email);

        findContact(emailText.getText().toString());

        if (activeID > -1) {
            db.open();
            db.deleteContact(activeID);
            db.close();
        }

        nameText.setText("");
        emailText.setText("");

        activeID = -1;
    }

    private void findContact(String email){
        activeID = -1;
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            do {
                if (c.getString(2).equals(email)){
                    activeID = Integer.parseInt(c.getString(0));
                }
            } while (c.moveToNext());
        }
        db.close();
    }

    private void DisplayContact(Cursor c) {
        Toast.makeText(this,
        "id: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "Email:  " + c.getString(2),
        Toast.LENGTH_SHORT).show();

    }

    public void showAll(View view){
        Log.d(TAG, "showAll");
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
                DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_db, menu);
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
