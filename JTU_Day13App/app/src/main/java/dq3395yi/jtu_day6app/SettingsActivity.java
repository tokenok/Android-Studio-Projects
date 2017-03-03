package dq3395yi.jtu_day6app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String CLASS_NAME;

    SharedPreferences myPrefs;

    protected Spinner spn_meats_color;
    protected Spinner spn_veggy_color;
    protected Button btn_done;

    public SettingsActivity() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d(CLASS_NAME, "onCreate");

        myPrefs = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE);

        spn_meats_color = (Spinner)findViewById(R.id.SPN_MEATS_COLORS);
        spn_veggy_color = (Spinner)findViewById(R.id.SPN_VEGGY_COLORS);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_meats_color.setAdapter(adapter);
        spn_veggy_color.setAdapter(adapter);

        spn_meats_color.setOnItemSelectedListener(this);
        spn_veggy_color.setOnItemSelectedListener(this);

        List<String> Colors = Arrays.asList(getResources().getStringArray(R.array.colors));
        String mcolor = myPrefs.getString("meats_color", "None");
        String vcolor = myPrefs.getString("veggy_color", "None");

        spn_meats_color.setSelection(Colors.indexOf(mcolor));
        spn_veggy_color.setSelection(Colors.indexOf(vcolor));

        btn_done = (Button)findViewById(R.id.BTN_DONE);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        String color = parentView.getItemAtPosition(position).toString();
        String owner = "";
        switch (parentView.getId()){
            case R.id.SPN_MEATS_COLORS:
                owner = "meats_color";
                break;
            case R.id.SPN_VEGGY_COLORS:
                owner = "veggy_color";
                break;
        }

        if (!owner.equals("")) {
            SharedPreferences.Editor spEditor = myPrefs.edit();
            spEditor.putString(owner, color);
            spEditor.apply();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        SharedPreferences.Editor spEditor = myPrefs.edit();
        spEditor.putString("meats_color", "None");
        spEditor.apply();
    }
}
