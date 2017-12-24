package com.example.abc12.navigationview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.w3c.dom.Text;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ItemDetails extends AppCompatActivity {
    private item detail_item;
    private String name;
    private String price;
    private String imageid;
    private EditText E_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetails);
        Toolbar toolbar = (Toolbar)findViewById(R.id.detail_toolbar);
        Button AddToCart = (Button)findViewById(R.id.detail_buy);
        Button add_num = (Button)findViewById(R.id.detail_add);
        Button sub_num = (Button)findViewById(R.id.detail_sub);
        E_num = (EditText)findViewById(R.id.detail_num);
        E_num.setText("1");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView Detail_image = (ImageView)findViewById(R.id.detail_image);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        imageid = intent.getStringExtra("imageid");
        final int position = intent.getIntExtra("position",0);
        try{
            Bitmap bitmap = BitmapFactory.decodeFile(imageid);
            Detail_image.setImageBitmap(bitmap);
        }catch (Exception e){
            Detail_image.setImageResource(R.drawable.airplane);
        }
        TextView Detail_name = (TextView)findViewById(R.id.detail_title);
        Detail_name.setText(name);
        TextView Detail_price = (TextView)findViewById(R.id.detail_price);
        Detail_price.setText(price);
        detail_item = new item(name,price,imageid);
        add_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Integer temp_num = Integer.parseInt(E_num.getText().toString());
                    E_num.setText((temp_num+1)+"");
                }
                catch (Exception e){
                    Log.d("fatal error, trans: ",e.toString());
                }
            }
        });
        sub_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Integer temp_num = Integer.parseInt(E_num.getText().toString());
                    E_num.setText((temp_num-1)+"");
                }
                catch (Exception e){
                    Log.d("fatal error, trans: ",e.toString());
                }
            }
        });
        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                try {
                    List<item> itemList = DataSupport.where("name = ?",name).find(item.class);
                    Log.d("try: ","started");
                    if(itemList.size()!=0){
                        Log.d("try: ","in count");
                        for(item items:itemList){
                            count += items.getCount();
                        }
                        detail_item = new item(name,price,imageid);
                        detail_item.setCount(count+1);
                        DataSupport.deleteAll(item.class,"name = ?",name);
                        detail_item.save();
                        Log.d("try:","succeed");
                    }
                    else {
                        detail_item = new item(name, price, imageid);
                        detail_item.setCount(1);
                        detail_item.save();
                        Log.d("try:", "saved");
                    }
                }
                catch (Exception e){
                    Log.d("catch","failed"+e.toString());
                    detail_item.setCount(1);
                    detail_item.save();
                    Log.d("catch:","saved");
                }
//                if(detail_item.isSaved()) {
//                    Log.d("saved:","saved");
//                    List<item> itemList = DataSupport.where("name = ?",name).find(item.class);
//                    for (item items:itemList){
//                        count += items.getCount();
//                        Log.d("count",""+count);
//                    }
//                    //detail_item.updateAll("name = ?",name);
//                    Toast.makeText(view.getContext(), "update", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    detail_item.setCount(1);
//                    detail_item.save();
//                    Toast.makeText(view.getContext(), "saved", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar,menu);
        return true;
    }
}
