package dq3395yi.jtu_day6app;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    int notificationID;
    String pizzainfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationID = getIntent().getExtras().getInt("notificationID");
        pizzainfo = getIntent().getExtras().getString("pizzainfo");

        TextView tv = (TextView)findViewById(R.id.TV_NOTIFY);
        tv.setText("The driver got lost for a minute but should be there soon\n" + pizzainfo);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        nm.cancel(notificationID);
    }
}
