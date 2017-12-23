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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by abc12 on 2017/12/11.
 */

public class HomeFragment extends Fragment {
    private List<item_net> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    progressDialog.cancel();
                    itemAdapter.notifyDataSetChanged();
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
                progressDialog = ProgressDialog.show(view.getContext(),"alert","refresh",true,true,null);
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
        progressDialog = ProgressDialog.show(getContext(),"更新数据中","请稍等。。。",true,true,null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobQuery<item_net> query = new BmobQuery<item_net>();
                query.addWhereGreaterThan("price",0);
                query.setLimit(10);
                query.findObjects(new FindListener<item_net>() {
                    @Override
                    public void done(List<item_net> list, BmobException e) {
                        if(e == null){
                            
                        }
                    }
                });
            }
        }, 100);
        return true;
    }
}
