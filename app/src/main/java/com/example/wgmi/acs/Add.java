package com.example.wgmi.acs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Add extends AppCompatActivity {

    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        handler = new DBHandler(this);
        handler.addItem(new Item("MOJRR1","HP Lancer","High Ram","0","Desktops",1,1));
    }
}
