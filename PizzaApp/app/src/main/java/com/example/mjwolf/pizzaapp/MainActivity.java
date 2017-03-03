package com.example.mjwolf.pizzaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ToppingsInterface{
    private int meatCount = 0;
    private int vegCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            meatCount = savedInstanceState.getInt("meats");
            vegCount = savedInstanceState.getInt("veg");
        }

    }
       public int getMeatCount(){
        return meatCount;
    }
    public int getVegCount(){
        return vegCount;
    }
    public void  setMeatCount(int count){
        meatCount = count;
    }
    public void setVegCount(int count){
        vegCount = count;
    }
    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("meats", meatCount);
        bundle.putInt("veg", vegCount);

    }
}
