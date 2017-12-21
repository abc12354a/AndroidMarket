package com.example.abc12.navigationview;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.http.bean.Init;

public class Orders extends AppCompatActivity {
    private List<order_item> order_itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        InitArry();
        OrderItemAdapter adapter = new OrderItemAdapter(Orders.this,R.layout.order_item,order_itemList);
        ListView listView = (ListView)findViewById(R.id.order_list);
        listView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar)findViewById(R.id.order_toolbar);
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
    public void InitArry(){
        order_item Item1 = new order_item("12345","apple","8008234");
        order_item Item2 = new order_item("54321","peach","8003123");
        order_item Item3 = new order_item("12738","banana","8004241");
        order_item Item4 = new order_item("41278","orange","8004124");
        order_itemList.add(Item1);
        order_itemList.add(Item2);
        order_itemList.add(Item3);
        order_itemList.add(Item4);
    }
}
