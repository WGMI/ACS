package com.example.wgmi.acs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    DBHandler handler;
    Context context;
    String urlString;
    String url;
    Map<String,String> params;

    EditText username,password;
    Button login;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        handler = new DBHandler(context);

        if(handler.countURL() == 1){
            urlString = handler.getItem("URL").getName();
        } else{
            handler.addItem(new Item("URL","192.168.45,5","","","",0,0));
        }

        params = new HashMap<>();
        handler.deleteItem("USR");

        url = "http://"+urlString+"/acs/android/android_login.php";

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.surname);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = username.getText().toString();
                final String pass = password.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    boolean success = object.getBoolean("success");

                                    if (success) {
                                        String id = object.getString("id");
                                        String active = object.getString("active");
                                        String fname = object.getString("firstname");
                                        String lname = object.getString("surname");

                                        if (active.equals("Active")) {
                                            handler.addItem(new Item("USR",id,fname,"","",0,0));
                                            Intent intent = new Intent(context, Home.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", id);
                                            bundle.putString("active", active);
                                            bundle.putString("fname", fname);
                                            bundle.putString("lname", lname);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(context,"Your account is not active.\nPlease contact your administrator.\n" + active,Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("Login Failed")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Internet Connection Error\nConnected to: " + handler.getItem("URL").getName(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        params.put("username",user);
                        params.put("password",pass);
                        return params;
                    }
                };
                queue.add(request);
            }
        });

        login.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Login.this,SetURL.class);
                startActivity(intent);
                return true;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
}
