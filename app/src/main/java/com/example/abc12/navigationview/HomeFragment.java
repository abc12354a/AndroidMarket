package com.example.abc12.navigationview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.abc12.navigationview.Adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc12 on 2017/12/11.
 */

public class HomeFragment extends Fragment {
    private List<item> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d("progress:","on");
                    if(initItemList()){
                        Log.d("progress:","ok");
                        progressDialog.cancel();
                        itemAdapter.notifyDataSetChanged();}
                    break;
                default:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        RecyclerView ItemListView = (RecyclerView)view.findViewById(R.id.home_list);
        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.home_refresh);
        initItemList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        ItemListView.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter(itemList);
        ItemListView.setAdapter(itemAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getContext(),"alert","refresh",true,true,null);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }},1000);
            }
        });
        return view;
    }
    private boolean initItemList(){
        itemList.clear();
        for (int i = 0;i<2;i++){
            item item1 = new item("apple","$12",R.drawable.airplane);
            itemList.add(item1);
            item item2 = new item("air","$13",R.drawable.cart);
            itemList.add(item2);
            item item3 = new item("menu","$15",R.drawable.airplane);
            itemList.add(item3);
            item item4 = new item("apple","$11",R.drawable.airplane);
            itemList.add(item4);
            item item5 = new item("222","$17",R.drawable.cart);
            itemList.add(item5);
            item item6 = new item("RPG","$20",R.drawable.airplane);
            itemList.add(item6);
        }
        return true;
    }
}
