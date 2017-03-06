package dq3395yi.jtu_day6app;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    protected Spinner spn_pizzaSize;
    protected TextView tv_totalPrice;
    protected RadioGroup radiogroup_meats;
    protected CheckBox cb_mushroom;
    protected CheckBox cb_blackOlive;
    protected CheckBox cb_tomato;
    protected CheckBox cb_squash;
    protected RadioButton rbtn_none;
    protected RadioButton rbtn_sausage;
    protected RadioButton rbtn_pepperoni;
    protected RadioButton rbtn_ham;
    protected RadioButton rbtn_duck;
  //  protected TextView tv_pizzaSize;
  //  protected TextView tv_meat;
  //  protected TextView tv_vegetable;
    protected EditText edc_miles;
    protected Button btn_purchase;

    protected Drawable rbtnCol;

    final double meatCost10 = 1.5;
    final double meatCost13 = 2.0;
    final double meatCost16 = 3.0;
    final double vegetableCost10 = 1.0;
    final double vegetableCost13 = 1.5;
    final double vegetableCost16 = 2.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("buttonsActivity", "setting landscape portrait");
            setContentView(R.layout.activity_main);
        }
        else {
            Log.d("buttonsActivity", "setting landscape layout");
            setContentView(R.layout.activity_main_landscape);
        }

        Log.d(LOG_TAG, "onCreate()");

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      //  tv_pizzaSize = (TextView)findViewById(R.id.tv_pizzaSize);
      //  tv_meat = (TextView)findViewById(R.id.tv_meat);
      //  tv_vegetable = (TextView)findViewById(R.id.tv_vegetable);

        spn_pizzaSize = (Spinner)findViewById(R.id.SPN_PIZZA_SIZE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pizza_sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_pizzaSize.setAdapter(adapter);
        spn_pizzaSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                calcPrice();
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                calcPrice();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                calcPrice();
            }
        };

        radiogroup_meats = (RadioGroup)findViewById(R.id.RADIOGROUP_MEAT);
        rbtn_none = (RadioButton)findViewById(R.id.RBTN_NONE);
        rbtn_sausage = (RadioButton)findViewById(R.id.RBTN_SAUSAGE);
        rbtn_pepperoni = (RadioButton)findViewById(R.id.RBTN_PEPPERONI);
        rbtn_ham = (RadioButton)findViewById(R.id.RBTN_HAM);
        rbtn_duck = (RadioButton)findViewById(R.id.RBTN_DUCK);
        rbtn_none.setChecked(true);
        rbtn_none.setOnClickListener(onClickListener);
        rbtn_sausage.setOnClickListener(onClickListener);
        rbtn_pepperoni.setOnClickListener(onClickListener);
        rbtn_ham.setOnClickListener(onClickListener);
        rbtn_duck.setOnClickListener(onClickListener);

        rbtnCol = rbtn_duck.getBackground();

        cb_mushroom = (CheckBox)findViewById(R.id.CB_MUSHROOM);
        cb_blackOlive = (CheckBox)findViewById(R.id.CB_BLACK_OLIVE);
        cb_tomato = (CheckBox)findViewById(R.id.CB_TOMATO);
        cb_squash = (CheckBox)findViewById(R.id.CB_SQUASH);
        cb_mushroom.setOnClickListener(onClickListener);
        cb_blackOlive.setOnClickListener(onClickListener);
        cb_tomato.setOnClickListener(onClickListener);
        cb_squash.setOnClickListener(onClickListener);

        edc_miles = (EditText)findViewById(R.id.edc_miles);
        edc_miles.setText("");
        edc_miles.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                calcPrice();
            }
        });

        tv_totalPrice = (TextView)findViewById(R.id.TV_TOTAL_PRICE);

        btn_purchase = (Button)findViewById(R.id.BTN_PURCHASE);
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                StringBuilder s = new StringBuilder("Your Order:");

                PizzaSize pizzaSize = PizzaSize.values()[(int)spn_pizzaSize.getSelectedItemId()];
                s.append((pizzaSize == PizzaSize.LARGE ? " 16\""
                        : pizzaSize == PizzaSize.MEDIUM ? " 13\""
                        : pizzaSize == PizzaSize.SMALL ? " 10\""
                        : ""));

                int meat = radiogroup_meats.indexOfChild(radiogroup_meats.findViewById(radiogroup_meats.getCheckedRadioButtonId()));
                if (meat >= 0 && Meat.values()[meat] != Meat.NONE) {
                    s.append(" "
                            + (Meat.values()[meat] == Meat.SAUSAGE ? "Sausage"
                            : Meat.values()[meat] == Meat.PEPPERONI ? "Pepperoni"
                            : Meat.values()[meat] == Meat.HAM ? "Ham"
                            : Meat.values()[meat] == Meat.DUCK ? "Duck"
                            : ""));
                }
                s.append(" pizza");

                String[] veggies = new String[4];
                veggies[0] = cb_mushroom.isChecked() ? "Mushrooms" : "";
                veggies[1] = cb_blackOlive.isChecked() ? "Black Olives" : "";
                veggies[2] = cb_tomato.isChecked() ? "Tomatoes" : "";
                veggies[3] = cb_squash.isChecked() ? "Squash" : "";
                int c = (veggies[0].length() > 0 ? 1 : 0)
                        + (veggies[1].length() > 0 ? 1 : 0)
                        + (veggies[2].length() > 0 ? 1 : 0)
                        + (veggies[3].length() > 0 ? 1 : 0);
                boolean first = true;

                for (int i = 0; i < veggies.length; i++){
                    if (veggies[i].length() > 0) {
                        s.append((first ? " with " : (c == 1 && !first) ? " and " : ", ") + veggies[i]);
                        c--;
                        first = false;
                    }
                }

                double totalCost = calcPrice();
                s.append( "\nTotal Cost: $" + String.format("%.2f", totalCost));

                Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
            }
        });

        spn_pizzaSize.requestFocus();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart()");

        Settings settings = ( (App) getApplication()).getSettings();

        if (settings.isAOn()) {
            for (int i = 0; i < radiogroup_meats.getChildCount(); i++) {
                ((RadioButton) radiogroup_meats.getChildAt(i)).setBackgroundColor(Color.parseColor("Blue"));
            }
        }
        else {
            for (int i = 0; i < radiogroup_meats.getChildCount(); i++) {
                ((RadioButton) radiogroup_meats.getChildAt(i)).setBackgroundDrawable(rbtnCol);
            }
        }

        if (settings.isBOn()) {
            cb_mushroom.setBackgroundColor(Color.parseColor("Green"));
            cb_blackOlive.setBackgroundColor(Color.parseColor("Green"));
            cb_tomato.setBackgroundColor(Color.parseColor("Green"));
            cb_squash.setBackgroundColor(Color.parseColor("Green"));
        }
        else {
            cb_mushroom.setBackgroundDrawable(rbtnCol);
            cb_blackOlive.setBackgroundDrawable(rbtnCol);
            cb_tomato.setBackgroundDrawable(rbtnCol);
            cb_squash.setBackgroundDrawable(rbtnCol);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "clickedSettings");
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public enum PizzaSize {
        SMALL, MEDIUM, LARGE;
    }

    public enum Meat {
        NONE, SAUSAGE, PEPPERONI, HAM, DUCK;
    }

    public enum Vegetable {
        MUSHROOM, BLACK_OLIVE, TOMATO, SQUASH;
    }

    protected void updatePrices(double pizzaCost, double deliveryCost, PizzaSize pizzaSize) {
        tv_totalPrice.setText("Price: $" + String.format("%.2f", pizzaCost) + " + $" + String.format("%.2f", deliveryCost) + " (Delivery Fee)");

        String s2 = rbtn_sausage.getText().toString();
        String s3 = rbtn_pepperoni.getText().toString();
        String s4 = rbtn_ham.getText().toString();
        String s5 = rbtn_duck.getText().toString();
        s2 = s2.substring(0, s2.lastIndexOf('(') - 1);
        s3 = s3.substring(0, s3.lastIndexOf('(') - 1);
        s4 = s4.substring(0, s4.lastIndexOf('(') - 1);
        s5 = s5.substring(0, s5.lastIndexOf('(') - 1);
        String meatCost = " ($" + String.format("%.2f", (pizzaSize == PizzaSize.SMALL ? meatCost10 : pizzaSize == PizzaSize.MEDIUM ? meatCost13 : meatCost16)) + ")";
        rbtn_none.setText("None ($0)");
        rbtn_sausage.setText(s2 + meatCost);
        rbtn_pepperoni.setText(s3 + meatCost);
        rbtn_ham.setText(s4 + meatCost);
        rbtn_duck.setText(s5 + meatCost);

        s2 = cb_mushroom.getText().toString();
        s3 = cb_blackOlive.getText().toString();
        s4 = cb_tomato.getText().toString();
        s5 = cb_squash.getText().toString();
        s2 = s2.substring(0, s2.lastIndexOf('(') - 1);
        s3 = s3.substring(0, s3.lastIndexOf('(') - 1);
        s4 = s4.substring(0, s4.lastIndexOf('(') - 1);
        s5 = s5.substring(0, s5.lastIndexOf('(') - 1);
        String vegetableCost = " ($" + String.format("%.2f", (pizzaSize == PizzaSize.SMALL ? vegetableCost10 : pizzaSize == PizzaSize.MEDIUM ? vegetableCost13 : vegetableCost16)) + ")";
        cb_mushroom.setText(s2 + vegetableCost);
        cb_blackOlive.setText(s3 + vegetableCost);
        cb_tomato.setText(s4 + vegetableCost);
        cb_squash.setText(s5 + vegetableCost);
    }

    protected double calcPrice() {
        double pizzaCost = 0.0;

        int meat = radiogroup_meats.indexOfChild(radiogroup_meats.findViewById(radiogroup_meats.getCheckedRadioButtonId()));

        PizzaSize pizzaSize = PizzaSize.values()[(int)spn_pizzaSize.getSelectedItemId()];

        switch (pizzaSize){
            case SMALL: {
                pizzaCost += 7.0;

                if (meat >= 0 && Meat.values()[meat] != Meat.NONE)
                    pizzaCost += meatCost10;

                pizzaCost += cb_mushroom.isChecked() ? vegetableCost10 : 0;
                pizzaCost += cb_blackOlive.isChecked() ? vegetableCost10 : 0;
                pizzaCost += cb_tomato.isChecked() ? vegetableCost10 : 0;
                pizzaCost += cb_squash.isChecked() ? vegetableCost10 : 0;

                break;
            }
            case MEDIUM: {
                pizzaCost += 9.0;

                if (meat >= 0 && Meat.values()[meat] != Meat.NONE)
                    pizzaCost += meatCost13;

                pizzaCost += cb_mushroom.isChecked() ? vegetableCost13 : 0;
                pizzaCost += cb_blackOlive.isChecked() ? vegetableCost13 : 0;
                pizzaCost += cb_tomato.isChecked() ? vegetableCost13 : 0;
                pizzaCost += cb_squash.isChecked() ? vegetableCost13 : 0;

                break;
            }
            case LARGE:{
                pizzaCost += 11.0;

                if (meat >= 0 && Meat.values()[meat] != Meat.NONE)
                    pizzaCost += meatCost16;

                pizzaCost += cb_mushroom.isChecked() ? vegetableCost16 : 0;
                pizzaCost += cb_blackOlive.isChecked() ? vegetableCost16 : 0;
                pizzaCost += cb_tomato.isChecked() ? vegetableCost16 : 0;
                pizzaCost += cb_squash.isChecked() ? vegetableCost16 : 0;

                break;
            }
        }

        double deliveryCost = 0.0;
        String smiles = edc_miles.getText().toString();
        if (smiles.length() > 0)
            deliveryCost = Double.parseDouble(smiles) * 0.5;

        updatePrices(pizzaCost, deliveryCost, pizzaSize);

        return pizzaCost + deliveryCost;
    }
}
