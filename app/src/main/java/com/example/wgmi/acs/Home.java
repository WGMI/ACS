package com.example.wgmi.acs;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    DBHandler handler;
    List<RequestItem> requestItems;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        */
        handler = new DBHandler(context);
        requestItems = new ArrayList<>();
        message = "All your items are on time";

        sendNotifications();
        openScreen(R.id.nav_home);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openScreen(int id){
        Fragment fragment = null;

        switch(id){
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_history:
                fragment = new HistoryFragment();
                break;
            case R.id.nav_scan:
                fragment = new ScanFragment();
                break;
            case R.id.items:
                startActivity(new Intent(Home.this,Categories.class));
                break;
            case R.id.logout:
                fragment = new LogoutFragment();
                break;
        }

        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        openScreen(id);

        return true;
    }

    public void sendNotifications(){
        handler = new DBHandler(this);
        String urlString = handler.getItem("URL").getName();
        String url = "http://"+urlString+"/acs/android/notify.php?id=" + handler.getItem("USR").getName();

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray requestsArray = object.getJSONArray("requests");

                            for(int i=0;i<requestsArray.length();i++){
                                JSONObject requestItemObject = requestsArray.getJSONObject(i);
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
                                int num = requestItems.size() - 1;
                                message = (requestItems.size() > 1) ? requestItems.get(0).getName() + " and " + num + " other are late":requestItems.get(0).getName() + " is late";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ic_menu_manage)
                                .setContentTitle("ACS - Late Items")
                                .setContentText(message);
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(1,builder.build());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Internet Connection Error\n" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);
    }
}
