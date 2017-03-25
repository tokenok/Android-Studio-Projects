package com.example.mjwolf.notificationdemonstration;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class CreateNotificationActivity extends ActionBarActivity {

    public static String CLASS_NAME;

    private static int notificationID = 1;

    public CreateNotificationActivity(){
        CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);
    }
    public void onClick(View view) {
        displayNotification();
    }

    protected void displayNotification()
    {
        Log.d(CLASS_NAME,"creating notification");

        //---PendingIntent to launch activity if the user selects
        // this notification---
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("notificationID", notificationID);  //makes it somewhat interesting
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK); // the activity will be started outside of the context of an existing activity,
                                                    // and you don't want it put on the task stack
                                                   // so you must use the Intent.FLAG_ACTIVITY_NEW_TASK launch flag in the Intent.
        Log.d(CLASS_NAME,"creating notificationk with ID "+ notificationID);

        // Now wrap the intent and this activity in a PendingIntent
        PendingIntent pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Instantiate a Builder object.
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Build the notification view
        builder.setSmallIcon(R.drawable.ic_launcher)
               .setContentTitle("Reminder: Meeting starts in 5 minutes")
               .setContentText("This is notification " + Integer.toString(notificationID) + ".\nYou better show up.")
               .setWhen(System.currentTimeMillis());

        //add the PendingIntent
        builder.setContentIntent(pendingIntent);

        //build the notification
        Notification notif = builder.build();

        //Grab a hold of the NotificationManager
        final NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        //Issue the notification
        nm.notify(notificationID, notif);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_notification, menu);
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