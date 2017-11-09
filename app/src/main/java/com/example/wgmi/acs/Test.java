package com.example.wgmi.acs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity {

    DBHandler handler;
    ListView listView;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        handler = new DBHandler(this);
        items = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);

        items = handler.getAllItems();

        ItemListAdapter adapter = new ItemListAdapter(this,R.layout.custom_list,items);
        listView.setAdapter(adapter);
    }
}
