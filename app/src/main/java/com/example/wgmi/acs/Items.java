package com.example.wgmi.acs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Items extends AppCompatActivity {

    DBHandler handler;
    String cat_id,name;
    ListView listView;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        handler = new DBHandler(this);
        String urlString = handler.getItem("URL").getName();
        Bundle bundle = getIntent().getExtras();
        cat_id = bundle.getString("id");
        name = bundle.getString("name");

        items = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+urlString+"/acs/android/get_items.php?cat_name=" + name;

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray itemArray = object.getJSONArray("items");

                            for (int i = 0; i < itemArray.length(); i++) {
                                JSONObject itemObject = itemArray.getJSONObject(i);
                                int status = (itemObject.getString("status") == "Functional") ? 1 : 0;
                                int available = (itemObject.getString("available") == "1") ? 1 : 0;
                                Item item = new Item(itemObject.getString("serial_no"), itemObject.getString("name"), itemObject.getString("specifications"), itemObject.getString("image"), name, status, available);
                                items.add(item);
                            }

                            ItemListAdapter adapter = new ItemListAdapter(Items.this, R.layout.custom_list, items);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check internet connection. " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(request);
    }
}
