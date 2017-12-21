package com.example.abc12.navigationview;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.I;

public class Add_Item extends AppCompatActivity {
    private ImageView img;
    private EditText name;
    private EditText price;
    private EditText describe;
    private FloatingActionButton ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);
        InitUI();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(price.getText().toString().matches("-?[0-9]+.*[0-9]*")) {
                    String S_name = name.getText().toString();
                    String S_price = price.getText().toString();
                    try {
                        Double D_price = Double.valueOf(S_price);
                        Log.d("trans: ","passed");
                    }
                    catch (Exception e){
                        Log.d("trans: ",e.toString());
                    }
                    String S_describe = describe.getText().toString();
                    item Aitem = new item(S_name,S_price,R.drawable.airplane);
                    Aitem.save();
                    Toast.makeText(view.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(view.getContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void InitUI(){
        img = (ImageView)findViewById(R.id.add_img);
        name = (EditText)findViewById(R.id.add_name);
        price = (EditText)findViewById(R.id.add_price);
        describe = (EditText)findViewById(R.id.add_decribe);
        ok = (FloatingActionButton)findViewById(R.id.add_ok);
        Toolbar toolbar = (Toolbar)findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
