package com.example.mjwolf.simpledbs;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class DataBaseListActivity extends AppCompatActivity {
    private DBAdapter db;

    protected ListView lv_database;

    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> id_names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_list);

        db = new DBAdapter(this);

        lv_database = (ListView)findViewById(R.id.LV_DATABASE);

        setTitle("Select entry to delete");

        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            do {
                id_names.add("id: " + c.getString(0) + " Name: " + c.getString(1));
                emails.add(c.getString(2));
            } while (c.moveToNext());
        }
        db.close();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, emails) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(emails.get(position));
                text2.setText(id_names.get(position));

                return view;
            }
        };
        lv_database.setAdapter(adapter);

        lv_database.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parentView, View childView, int position, long id) {
                lv_database.removeViewAt(position);
                lv_database.getAdapter().
            }
            public void onNothingSelected(AdapterView parentView) {  }
        });
    }
}
