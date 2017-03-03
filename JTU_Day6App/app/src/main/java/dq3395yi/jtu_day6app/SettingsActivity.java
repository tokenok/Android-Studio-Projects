package dq3395yi.jtu_day6app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    private static String CLASS_NAME;

    private CheckBox switchA;
    private CheckBox switchB;
    private Settings settings;

    public SettingsActivity() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d(CLASS_NAME, "onCreate");

        switchA = (CheckBox) findViewById(R.id.checkBox2);
        switchB = (CheckBox) findViewById(R.id.checkBox);

        settings = ((App) getApplication()).getSettings();

        // if there are settings, set them up
        switchA.setChecked(settings.isAOn());
        switchB.setChecked(settings.isBOn());
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(CLASS_NAME, "onPause");
        //save the setting here

        settings.setAOn(switchA.isChecked());
        settings.setBOn(switchB.isChecked());
    }
}
