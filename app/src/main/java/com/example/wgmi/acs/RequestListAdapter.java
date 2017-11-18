package com.example.wgmi.acs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by WGMI on 14/11/2017.
 */

public class RequestListAdapter extends ArrayAdapter {

    List<RequestItem> requestsList;
    Context context;
    int resource;

    public RequestListAdapter(Context context, int resource, List<RequestItem> requestsList){
        super(context,resource,requestsList);
        this.context = context;
        this.resource = resource;
        this.requestsList = requestsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource,null,false);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView date = (TextView) view.findViewById(R.id.date);
        /*
        TextView from = (TextView) view.findViewById(R.id.from);
        TextView to = (TextView) view.findViewById(R.id.to);
        */
        TextView status = (TextView) view.findViewById(R.id.status);
        TextView permit = (TextView) view.findViewById(R.id.permit);

        RequestItem request = requestsList.get(position);
        name.setText(request.getName());
        date.setText(request.getDate());
        /*
        from.setText(request.getDate_from());
        to.setText(request.getDate_to());
        */
        status.setText(request.getStatus());
        permit.setText(request.getPermit());

        return view;
    }
}
