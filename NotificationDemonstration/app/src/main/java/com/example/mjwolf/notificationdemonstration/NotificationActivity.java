package com.example.mjwolf.notificationdemonstration;

import android.app.NotificationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;


public class NotificationActivity extends ActionBarActivity {
    public static String CLASS_NAME;
    private int notificationID;

    public NotificationActivity(){
        CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Log.d(CLASS_NAME, "onCreate: cancelling notification");

        notificationID = getIntent().getExtras().getInt("notificationID");
        Log.d(CLASS_NAME, "notification ID from intent " + notificationID);

                //---look up the notification manager service---
        NotificationManager nm = (NotificationManager)
            getSystemService(NOTIFICATION_SERVICE);

        //---cancel the notification that we started---
        nm.cancel(notificationID);

        TextView textView = (TextView) findViewById(R.id.message);
        textView.setText("Notification " + Integer.toString(notificationID) + " cancelled.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
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
