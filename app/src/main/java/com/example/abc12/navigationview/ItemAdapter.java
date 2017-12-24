package com.example.abc12.navigationview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by abc12 on 2017/12/11.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<item_net> mitems;
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
    public ItemAdapter(List<item_net> items){
        mitems = items;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item_net items = mitems.get(position);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/SHOP/"+items.getName()+".png");
            holder.itemimage.setImageBitmap(bitmap);
        }catch (Exception e){
            holder.itemimage.setImageResource(R.drawable.ok);
            Log.d("Item_Adapter.set: ",e.toString());
        }
        holder.itemname.setText(items.getName());
        holder.itemprice.setText(items.getPrice().toString());
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
                item_net items = mitems.get(position);
                Intent OpenDetails = new Intent(view.getContext(),ItemDetails.class);
                String name = items.getName();
                String price = items.getPrice().toString();
                String path = Environment.getExternalStorageDirectory()+"/SHOP/"+items.getName()+".png";
                OpenDetails.putExtra("name",name);
                OpenDetails.putExtra("price",price);
                OpenDetails.putExtra("imageid",path);
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
