package com.example.wgmi.acs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

public class Test extends AppCompatActivity {

    /*DBHandler handler;
    ListView listView;
    List<Item> items;*/

    Context context;
    DBHandler handler;
    ListView listView;
    List<RequestItem> requestItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*handler = new DBHandler(this);
        items = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);

        items = handler.getAllItems();

        ItemListAdapter adapter = new ItemListAdapter(this,R.layout.custom_list,items);
        listView.setAdapter(adapter);
        */

        context = Test.this;
        listView = (ListView) findViewById(R.id.list);
        handler = new DBHandler(context);
        requestItems = new ArrayList<>();

        String urlString = handler.getItem("URL").getName();
        String user = handler.getItem("USR").getName();

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://"+urlString+"/acs/android/history.php?id=" + user;

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        requestItems.add(new RequestItem("qwerty","qwerty","qwerty","qwerty","qwerty","qwerty","qwerty","qwerty"));
        requestItems.add(new RequestItem("qwerty","qwerty","qwerty","qwerty","qwerty","qwerty","qwerty","qwerty"));
        progressBar.setVisibility(View.INVISIBLE);

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray requestItemArray = object.getJSONArray("requests");

                            for(int i=0;i<requestItemArray.length();i++){
                                JSONObject requestItemObject = requestItemArray.getJSONObject(i);
                                Log.d(TAG, "onResponse: "+ i);
                                String name = requestItemObject.getString("item_name");
                                String item_id = requestItemObject.getString("request_id");
                                String user_id = requestItemObject.getString("user_id");
                                String date = requestItemObject.getString("date");
                                String date_from = requestItemObject.getString("date_from");
                                String date_to = requestItemObject.getString("date_to");
                                String permit = requestItemObject.getString("permit");
                                String status = requestItemObject.getString("status");

                                RequestItem requestItem = new RequestItem(name,item_id,user_id,date,date_from,date_to,permit,status);
                                requestItems.add(requestItem);
                            }

                            RequestListAdapter adapter = new RequestListAdapter(context,R.layout.requests,requestItems);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Please check internet connection. " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);

        RequestListAdapter adapter = new RequestListAdapter(context,R.layout.requests,requestItems);
        listView.setAdapter(adapter);
    }
}
