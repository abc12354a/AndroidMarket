package com.example.abc12.navigationview.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abc12.navigationview.R;
import com.example.abc12.navigationview.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abc12 on 2017/12/14.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder>{
    private List<item> mItemList;
    private Context mContext;
    private Map<Integer,Boolean> map = new HashMap<>();
    private void InitMap(){
        for(int i  = 0;i<mItemList.size();i++){
            map.put(i,false);
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView ItemImg;
        TextView ItemPrice;
        TextView ItemName;
        CheckBox ItemCheck;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            ItemImg = (ImageView)view.findViewById(R.id.cart_item_img);
            ItemName = (TextView)view.findViewById(R.id.cart_item_name);
            ItemPrice = (TextView)view.findViewById(R.id.cart_item_price);
            ItemCheck = (CheckBox)view.findViewById(R.id.cart_item_check);
        }
    }
    public CartItemAdapter(List<item> itemslist){
        mItemList = itemslist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(),"long clicked",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        item items = mItemList.get(position);
        holder.ItemName.setText(items.getName());
        holder.ItemImg.setImageResource(items.getImageid());
        holder.ItemPrice.setText(items.getPrice());
        holder.ItemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    map.put(position, true);
                }
                else{
                    map.put(position,false);
                }
            }
        });
        if(map.get(position)==null){
            map.put(position,false);
        }
        holder.ItemCheck.setChecked(map.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
    public Map<Integer,Boolean>getMap(){
        return map;
    }
}
