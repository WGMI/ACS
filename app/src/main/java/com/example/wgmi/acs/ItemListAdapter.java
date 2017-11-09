package com.example.wgmi.acs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by WGMI on 03/11/2017.
 */

public class ItemListAdapter extends ArrayAdapter<Item> {

    List<Item> items;
    Context context;
    int resource;

    public ItemListAdapter(Context context, int resource, List<Item> items){
        super(context,resource,items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource,null,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewTeam = (TextView) view.findViewById(R.id.textViewTeam);
        Button viewButton = (Button) view.findViewById(R.id.view);

        Item item = items.get(position);
        imageView.setImageDrawable(context.getResources().getDrawable(item.getDefaultImage()));
        //imageView.setImageDrawable(context.getResources().getDrawable(item.getImageID()));
        textViewName.setText(item.getName());
        textViewTeam.setText(item.getSno());

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewItem(position);

            }
        });

        return view;
    }

    public void viewItem(final int position){
        Intent intent = new Intent(context,ViewItem.class);
        Bundle b = new Bundle();
        b.putString("sno",items.get(position).getSno());
        b.putString("name",items.get(position).getName());
        b.putString("specifications",items.get(position).getSpecifications());
        b.putString("image",items.get(position).getImage());
        b.putString("category",items.get(position).getCategory());
        b.putInt("status",items.get(position).getStatus());
        b.putInt("available",items.get(position).getAvailable());
        intent.putExtras(b);
        context.startActivity(intent);
        /*
        Intent intent = new Intent(context,ItemView.class);
        intent.putExtra("sno",items.get(position).getSno());
        context.startActivity(intent);
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this?");

        //if the response is positive in the alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                items.remove(position);

                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //creating and displaying the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        */
    }
}
