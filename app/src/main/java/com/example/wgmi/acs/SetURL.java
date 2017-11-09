package com.example.wgmi.acs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetURL extends AppCompatActivity {

    DBHandler handler;
    String urlString;
    EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_url);

        handler = new DBHandler(this);
        handler.deleteItem("URL");

        url = (EditText) findViewById(R.id.url);
        Button set = (Button) findViewById(R.id.set);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlString = url.getText().toString();
                handler.addItem(new Item("URL",urlString,"","","",0,0));
                Intent intent = new Intent(SetURL.this,Home.class);
                startActivity(intent);
            }
        });
    }
}
