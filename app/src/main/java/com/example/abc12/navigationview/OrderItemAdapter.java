package com.example.abc12.navigationview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abc12 on 2017/12/21.
 */

public class OrderItemAdapter extends ArrayAdapter<order_item> {
    private int id;
    public OrderItemAdapter(Context context, int textViewRes, List<order_item> items){
        super(context,textViewRes,items);
        id = textViewRes;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        order_item orderItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(id,parent,false);
        TextView id = (TextView)view.findViewById(R.id.order_item_id);
        TextView name = (TextView)view.findViewById(R.id.order_item_info);
        TextView kuaidi = (TextView)view.findViewById(R.id.order_item_kuaidi);
        id.setText(orderItem.getId());
        name.setText(orderItem.getName());
        kuaidi.setText(orderItem.getKuaidi());
        return view;
    }
}
