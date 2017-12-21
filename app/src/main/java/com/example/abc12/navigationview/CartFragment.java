package com.example.abc12.navigationview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abc12.navigationview.Adapter.CartItemAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abc12 on 2017/12/11.
 */

public class CartFragment extends Fragment {
    public final static int PROGRESS_ON = 1;
    private ProgressBar progressBar;
    private List<item> itemList = new ArrayList<>();
    private CartItemAdapter adapter;
    private RecyclerView ItemListView;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PROGRESS_ON:
                    Log.d("progress:","on");
                    if(initItemList()){
                        Log.d("progress:","ok");
                        progressDialog.cancel();
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        adapter.notifyDataSetChanged();}
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment,container,false);
        initItemList();
        ItemListView = (RecyclerView)view.findViewById(R.id.cart_item_list);
        progressBar = (ProgressBar)view.findViewById(R.id.cart_progressbar);
        FloatingActionButton pay = (FloatingActionButton)view.findViewById(R.id.cart_pay);
        Button Pickall = (Button)view.findViewById(R.id.cart_pickall);
        Button UnPickall = (Button)view.findViewById(R.id.cart_unpickall);
        Button Refresh = (Button)view.findViewById(R.id.refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new CartItemAdapter(itemList);
        ItemListView.setAdapter(adapter);
        ItemListView.setLayoutManager(layoutManager);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar)view.findViewById(R.id.toolbar));
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getContext(),"alert","refresh",true,true,null);
                Log.d("refresh: ","refresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = PROGRESS_ON;
                        handler.sendMessage(msg);
                    }},1000);
            }
        });
        pay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Map<Integer,Boolean>map = adapter.getMap();
                for(int i = 0;i<map.size();i++){
                    if(map.get(i)){
                        Toast.makeText(view.getContext(),"you choose"+i+"item",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        UnPickall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Map<Integer,Boolean>map = adapter.getMap();
                for(int i = 0;i<itemList.size();i++){
                    map.put(i,false);
                }
                adapter.notifyDataSetChanged();
            }
        });
        Pickall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Map<Integer,Boolean>map = adapter.getMap();
                for(int i = 0;i<itemList.size();i++){
                    map.put(i,true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
    private boolean initItemList(){
        itemList.clear();
        List<item> itemList2 = DataSupport.findAll(item.class);
        for (item items2:itemList2){
            itemList.add(items2);
        }
        return true;
//        for (int i = 0;i<2;i++){
//            item item1 = new item("apple","$12",R.drawable.airplane);
//            itemList.add(item1);
//            item item2 = new item("air","$13",R.drawable.cart);
//            itemList.add(item2);
//            item item3 = new item("menu","$15",R.drawable.airplane);
//            itemList.add(item3);
//            item item4 = new item("apple","$11",R.drawable.airplane);
//            itemList.add(item4);
//            item item5 = new item("222","$17",R.drawable.cart);
//            itemList.add(item5);
//            item item6 = new item("RPG","$20",R.drawable.airplane);
//            itemList.add(item6);
//        }
    }
}
