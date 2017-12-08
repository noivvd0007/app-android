package com.vuvannoi.ndapptracnghiem.Main_App.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vuvannoi.ndapptracnghiem.R;

import java.util.List;

/**
 * Created by Admin on 9/6/2017.
 */

public class custom_list_topbxh extends ArrayAdapter<topbxh> {

    public custom_list_topbxh(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public custom_list_topbxh(Context context, int resource, List<topbxh> items) {
        super(context, resource, items);


    }



    @Override
    public View getView(int postion , View view , ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_lisview_top, null);

        }
        topbxh item = getItem(postion);
        if (item != null) {




            // set Text View
            TextView tv_name = (TextView) v.findViewById(R.id.tv_item_name);
            TextView tv_email = (TextView) v.findViewById(R.id.tv_item_email);
            TextView tv_score = (TextView) v.findViewById(R.id.tv_item_score);
            // set text
            tv_name.setText(item.getName());
            tv_email.setText(item.getEmail());
            tv_score.setText(item.getScore());


        }
        return v;
    }

}
