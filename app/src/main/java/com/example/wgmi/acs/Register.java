package com.example.wgmi.acs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {

    EditText et_name,et_username,et_surname,et_pass_again;
    Button register;
    String name,username,surname,pass_again;
    String urlString,url;
    Context context;
    DBHandler handler;

    HashMap params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;
        handler = new DBHandler(context);

        et_name = (EditText) findViewById(R.id.name);
        et_username = (EditText) findViewById(R.id.username);
        et_surname = (EditText) findViewById(R.id.surname);
        et_pass_again = (EditText) findViewById(R.id.password);

        urlString = handler.getItem("URL").getName();
        params = new HashMap<>();
        handler.deleteItem("USR");

        url = "http://"+urlString+"/acs/android/android_register.php";

        register = (Button) findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                username = et_username.getText().toString();
                surname = et_surname.getText().toString();
                pass_again = et_pass_again.getText().toString();

                if(name.trim().length() == 0 || username.trim().length() == 0 || surname.trim().length() == 0){
                    Toast.makeText(context,"All fields are required",Toast.LENGTH_LONG).show();
                } else if(pass_again.length() < 6){
                    Toast.makeText(context,"The password should be at least 6 characters long",Toast.LENGTH_LONG).show();
                } else{
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
                                        if(success){
                                            startActivity(new Intent(Register.this,Login.class));
                                        } else{
                                            Toast.makeText(Register.this,"Registration failed. Please try again.",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Internet Connection Error\nConnected to: " + handler.getItem("URL").getName(),Toast.LENGTH_LONG).show();
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            params.put("name",name);
                            params.put("username",username);
                            params.put("password",pass_again);
                            params.put("surname",surname);
                            return params;
                        }
                    };
                    queue.add(request);
                }
            }
        });
    }
}
