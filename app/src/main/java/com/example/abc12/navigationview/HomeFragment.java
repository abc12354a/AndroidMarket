package com.example.abc12.navigationview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by abc12 on 2017/12/11.
 */

public class HomeFragment extends Fragment {
    private List<item_net> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private ProgressDialog progressDialog;
    private final static int REFRESH_OK = 10;
    private final static int REFRESH_FAIL = 11;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_OK:
                    progressDialog.cancel();
                    itemAdapter.notifyDataSetChanged();
                    Log.d("home refresh: ","success");
                    break;
                case REFRESH_FAIL:
                    progressDialog.cancel();
                    Toast.makeText(getContext(),"刷新失败，检查网络",Toast.LENGTH_SHORT).show();
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
                itemList.clear();
                progressDialog = ProgressDialog.show(getContext(),"更新数据中","请稍等...",true,true,null);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BmobQuery<item_net> query = new BmobQuery<item_net>();
                        query.addWhereGreaterThan("price",0);
                        query.setLimit(100);
                        query.findObjects(new FindListener<item_net>() {
                            @Override
                            public void done(List<item_net> list, BmobException e) {
                                if(e == null){
                                    for (item_net items:list){
                                        BmobFile pic_file = items.getPicFile();
                                        if(pic_file != null){
                                            File savefile = new File(Environment.getExternalStorageDirectory()+"/SHOP/",items.getName()+".jpg");
                                            pic_file.download(savefile, new DownloadFileListener() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if(e == null){
                                                        Log.d("download: success",s.toString());
                                                    }
                                                    else {
                                                        Log.d("download: fail",e.toString());
                                                    }
                                                }
                                                @Override
                                                public void onProgress(Integer integer, long l) {

                                                }
                                            });
                                        }
                                        itemList.add(items);
                                    }
                                    Message msg = new Message();
                                    msg.what = REFRESH_OK;
                                    handler.sendMessage(msg);
                                }else {
                                    Message msg = new Message();
                                    msg.what = REFRESH_FAIL;
                                    handler.sendMessage(msg);
                                    Log.d("home_refresh: ","fail "+e.getMessage());
                                }
                            }
                        });
                    }},100);
            }
        });
        return view;
    }
    private boolean initItemList(){
        itemList.clear();
        progressDialog = ProgressDialog.show(getContext(),"更新数据中","请稍等...",true,true,null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobQuery<item_net> query = new BmobQuery<item_net>();
                query.addWhereGreaterThan("price",0);
                query.setLimit(100);
                query.findObjects(new FindListener<item_net>() {
                    @Override
                    public void done(List<item_net> list, BmobException e) {
                        if(e == null){
                            for (item_net items:list){
                                itemList.add(items);
                            }
                            Message msg = new Message();
                            msg.what = REFRESH_OK;
                            handler.sendMessage(msg);
                        }else {
                            Message msg = new Message();
                            msg.what = REFRESH_FAIL;
                            handler.sendMessage(msg);
                            Log.d("home_refresh: ","fail "+e.getMessage());
                        }
                    }
                });
            }
        }, 100);
        return true;
    }
}
