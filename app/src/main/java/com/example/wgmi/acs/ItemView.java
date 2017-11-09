package com.example.wgmi.acs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ItemView extends AppCompatActivity {

    TextView specs,sno,name,cat;
    Dialog dialog;
    Dialog request;
    DBHandler handler;
    Context context;
    Button borrow;

    int i;

    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    TextView fromDateView,toDateView;
    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    DatePickerDialog fromDatePickerDialog;
    DatePickerDialog toDatePickerDialog;
    String dt_f = df.format(new Date());
    String dt_t = df.format(new Date());

    long fromDateSelected = date.getTime();
    long toDateSelected = date.getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        final String serial = getIntent().getExtras().getString("sno");
        i = 0;

        specs = (TextView)findViewById(R.id.specs);
        sno = (TextView)findViewById(R.id.sno);
        name = (TextView)findViewById(R.id.name);
        cat = (TextView)findViewById(R.id.cat);
        context = ItemView.this;
        handler = new DBHandler(this);
        borrow = (Button)findViewById(R.id.borrow);

        sno.setText(handler.getItem(serial).getSno());
        name.setText(handler.getItem(serial).getName());
        cat.setText(handler.getItem(serial).getCategory());

        specs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.specifications);
                dialog.show();

                TextView title = (TextView) dialog.findViewById(R.id.title);
                TextView body = (TextView) dialog.findViewById(R.id.body);

                title.setText(handler.getItem(serial).getName());
                /*body.setText(
                        "- 2.53GHz Intel Core i3-480M.\n\n" +
                                "- 15.6in (Windows 7 Home Premium 64-bit.\n\n" +
                                "- nVidia GeForce 315M with 1GB RAM\n\n" +
                                "- Gigabit ethernet\n\n"
                );*/
                body.setText(handler.getItem(serial).getSpecifications());
            }
        });

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new Dialog(context);
                request.setContentView(R.layout.request);
                request.show();

                TextView item = (TextView) request.findViewById(R.id.item);
                fromDateView = (TextView) request.findViewById(R.id.category_from);
                toDateView = (TextView) request.findViewById(R.id.category_to);
                Button makeRequest = (Button) request.findViewById(R.id.request);

                fromDateView.setText(df.format(new Date(fromDateSelected)));
                toDateView.setText(df.format(new Date(toDateSelected)));

                item.setText("ItemRequest " + handler.getItem(serial).getName());

                DatePickerDialog.OnDateSetListener fromlistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int m = month + 1;
                        String d = dayOfMonth + "/" + m + "/" + year;
                        try {
                            Date d1 = df.parse(d);
                            dt_f = df.format(d1);
                            fromDateView.setText(dt_f);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DatePickerDialog.OnDateSetListener tolistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int m = month + 1;
                        String d = dayOfMonth + "/" + m + "/" + year;
                        try {
                            Date d1 = df.parse(d);
                            dt_t = df.format(d1);
                            toDateView.setText(dt_t);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                };

                fromDatePickerDialog = new DatePickerDialog(
                        context,
                        fromlistener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                toDatePickerDialog = new DatePickerDialog(
                        context,
                        tolistener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                fromDateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fromDatePickerDialog.show();
                        DatePicker datePicker = fromDatePickerDialog.getDatePicker();
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();

                        try {
                            Date date = df.parse(day + "/" + month + "/" + year);
                            fromDateSelected = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                toDateView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDatePickerDialog.show();
                        DatePicker datePicker = toDatePickerDialog.getDatePicker();
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();

                        try {
                            Date date = df.parse(day + "/" + month + "/" + year);
                            toDateSelected = date.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                makeRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(ItemView.this);
                        String url = "http://172.16.40.105/acs/android_request.php";
                        StringRequest request = new StringRequest(
                                Request.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Nope", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            protected Map<String,String> getParams() throws AuthFailureError{
                                Map<String,String> params = new HashMap<>();
                                params.put("android","1");
                                params.put("from",String.valueOf(fromDateSelected));
                                params.put("to",String.valueOf(toDateSelected));
                                return params;
                            }
                        };
                        queue.add(request);
                    }
                });
            }
        });

        sno.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(context,new Date(dateSelected) + "\n" + new Date(dateSelected2),Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}