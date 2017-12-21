package com.example.abc12.navigationview.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abc12.navigationview.ItemDetails;
import com.example.abc12.navigationview.R;
import com.example.abc12.navigationview.item;

import java.util.List;

/**
 * Created by abc12 on 2017/12/11.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<item> mitems;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView itemname;
        TextView itemprice;
        View itemview;
        public ViewHolder(View view){
            super(view);
            itemimage = (ImageView)view.findViewById(R.id.home_item_image);
            itemname = (TextView)view.findViewById(R.id.home_item_name);
            itemprice = (TextView)view.findViewById(R.id.home_item_price);
            itemview = view;
        }
    }
    public ItemAdapter(List<item> items){
        mitems = items;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item items = mitems.get(position);
        holder.itemimage.setImageResource(items.getImageid());
        holder.itemname.setText(items.getName());
        holder.itemprice.setText(items.getPrice());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemview.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                try {
                    Toast.makeText(view.getContext(),"long clicked",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(view.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        holder.itemview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                item items = mitems.get(position);
                Intent OpenDetails = new Intent(view.getContext(),ItemDetails.class);
                String name = items.getName();
                String price = items.getPrice();
                int imageid = items.getImageid();
                OpenDetails.putExtra("name",name);
                OpenDetails.putExtra("price",price);
                OpenDetails.putExtra("imageid",imageid);
                OpenDetails.putExtra("postion",position);
                view.getContext().startActivity(OpenDetails);
            }
        });
        return holder;
    }

    @Override
    public int getItemCount() {
        return mitems.size();
    }
}
