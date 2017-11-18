package com.example.wgmi.acs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by WGMI on 03/11/2017.
 */

public class HomeFragment extends Fragment {

    Context context;
    DBHandler handler;
    Bundle bundle;

    TextView welcome;
    TextView requests;
    TextView borrowed;
    TextView held;
    TextView late;

    String requestNum;
    String borrowedNum;
    String heldNum;
    String lateNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,container,false);

        context = this.getActivity();
        handler = new DBHandler(context);
        String urlString = handler.getItem("URL").getName();

        //bundle = getI

        welcome = (TextView) view.findViewById(R.id.welcome);
        requests = (TextView) view.findViewById(R.id.requests);
        borrowed = (TextView) view.findViewById(R.id.borrowed);
        held = (TextView) view.findViewById(R.id.held);
        late = (TextView) view.findViewById(R.id.late);

        String user = handler.getItem("USR").getName();

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://"+urlString+"/acs/android/home.php?id=" + user;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            requestNum = object.getString("requests");
                            borrowedNum = object.getString("borrowed");
                            heldNum = object.getString("held");
                            lateNum = object.getString("late");

                            requests.setText(requestNum + "\nRequests");
                            borrowed.setText(borrowedNum + "\nItems\nBorrowed");
                            held.setText(heldNum + "\nItems Held");
                            late.setText(requestNum + "\nItems Late");
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

        String username = handler.getItem("USR").getSpecifications();
        welcome.setText("Welcome, " + username);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }
}
