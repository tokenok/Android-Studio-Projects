package dq3395yi.finalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class DeerDataActivity extends Activity {
    private DeerData deerData;

    protected EditText edc_number_of_deer_sighted;
    protected EditText edc_time_of_sighting;
    protected EditText edc_distance_from_deer;
    protected CheckBox cb_doe;
    protected CheckBox cb_buck;
    protected CheckBox cb_fawn;
    protected CheckBox cb_antlerless;
    protected EditText edc_number_of_points;
    protected EditText edc_size_of_buck;
    protected EditText edc_age_of_buck;

    protected Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deer_data);

        deerData = new DeerData();

        btn_submit = (Button)findViewById(R.id.BTN_SUBMIT);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edc_number_of_deer_sighted = (EditText)findViewById(R.id.EDC_NUMBER_OF_DEER_SIGHTED);
                edc_time_of_sighting = (EditText)findViewById(R.id.EDC_TIME_OF_SIGHTING);
                edc_distance_from_deer = (EditText)findViewById(R.id.EDC_DISTANCE_FROM_DEER);
                cb_doe = (CheckBox)findViewById(R.id.CB_DOE);
                cb_buck = (CheckBox)findViewById(R.id.CB_BUCK);
                cb_fawn = (CheckBox)findViewById(R.id.CB_FAWN);
                cb_antlerless = (CheckBox)findViewById(R.id.CB_ANTLERLESS);
                edc_number_of_points = (EditText)findViewById(R.id.EDC_NUMBER_OF_POINTS);
                edc_size_of_buck = (EditText)findViewById(R.id.EDC_SIZE_OF_BUCK);
                edc_age_of_buck = (EditText)findViewById(R.id.EDC_AGE_OF_BUCK);

                int numDeer = Integer.parseInt(edc_number_of_deer_sighted.getText().toString());
                String timeOfSighting = edc_time_of_sighting.getText().toString();
                double distance = Double.parseDouble(edc_distance_from_deer.getText().toString());
                int deerTypes = 0;
                deerTypes += cb_doe.isChecked() ? DeerData.DOE : 0;
                deerTypes += cb_buck.isChecked() ? DeerData.BUCK : 0;
                deerTypes += cb_fawn.isChecked() ? DeerData.FAWN : 0;
                deerTypes += cb_antlerless.isChecked() ? DeerData.ANTLERLESS : 0;
                int numPoints = Integer.parseInt(edc_number_of_points.getText().toString());
                double buckSize = Double.parseDouble(edc_size_of_buck.getText().toString());
                double buckAge = Double.parseDouble(edc_age_of_buck.getText().toString());

                deerData.setNumDeer(numDeer);
                deerData.setTimeOfSighting(timeOfSighting);
                deerData.setDistance(distance);
                deerData.setDeerTypes(deerTypes);
                deerData.setNumPoints(numPoints);
                deerData.setBuckSize(buckSize);
                deerData.setBuckAge(buckAge);

                deerData.saveToFile(DeerDataActivity.this, "file1.ser");

                finish();
            }
        });
    }
}
