package com.example.wgmi.acs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class Categories extends AppCompatActivity {

    DBHandler handler;
    ListView listView;
    List<String> categories;
    List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        handler = new DBHandler(this);
        String urlString = handler.getItem("URL").getName();
        listView = (ListView) findViewById(R.id.list);
        categories = new ArrayList<>();
        categoryList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+urlString+"/acs/android/get_categories.php";

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray catArray = object.getJSONArray("categories");

                    for (int i = 0; i < catArray.length(); i++) {
                        JSONObject catObject = catArray.getJSONObject(i);
                        Category category = new Category(Integer.parseInt(catObject.getString("category_id")), catObject.getString("cat_name"));
                        categoryList.add(category);
                        categories.add(catObject.getString("cat_name"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Categories.this,
                            android.R.layout.simple_list_item_1,
                            categories
                    );

                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Please check internet connection. " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int cat_id = categoryList.get(position).getCategory_id();
                String name = categoryList.get(position).getCategory_name();
                //Toast.makeText(Categories.this,name + "",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Categories.this,Items.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",cat_id + "");
                bundle.putString("name",name + "");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

