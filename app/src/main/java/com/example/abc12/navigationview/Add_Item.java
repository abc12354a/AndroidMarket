package com.example.abc12.navigationview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
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

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.I;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Add_Item extends AppCompatActivity {
    private ImageView img;
    private EditText name;
    private EditText price;
    private EditText describe;
    private FloatingActionButton ok;
    private String path;
    private BmobFile Picfile;
    private final static int UPLOADPIC_SUCCESS = 10;
    private final static int UPLOADPIC_FAIL = 11;
    private final static int OK_SUCCESS = 12;
    private final static int OK_FAIL = 13;
    private ProgressDialog progressDialog;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPLOADPIC_SUCCESS:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"上传成功",Toast.LENGTH_SHORT).show();
                    break;
                case UPLOADPIC_FAIL:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"上传失败",Toast.LENGTH_SHORT).show();
                    break;
                case OK_FAIL:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"添加失败",Toast.LENGTH_SHORT).show();
                    break;
                case OK_SUCCESS:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"添加成功",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    progressDialog.cancel();
                    break;
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            path = uri.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            img.setImageBitmap(bitmap);
            Picfile = new BmobFile(new File(path));
            progressDialog = ProgressDialog.show(this,"上传","上传中...",true,true,null);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picfile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Message msg = new Message();
                                msg.what = UPLOADPIC_SUCCESS;
                                handler.sendMessage(msg);
                            }
                            else {
                                Message msg = new Message();
                                msg.what = UPLOADPIC_FAIL;
                                handler.sendMessage(msg);
                            }
                        }
                    });
                }
            }, 100);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);
        InitUI();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenFile = new Intent(Intent.ACTION_GET_CONTENT);
                OpenFile.setType("image/*");
                OpenFile.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(OpenFile,1);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(price.getText().toString().matches("-?[0-9]+.*[0-9]*")&&Picfile!=null) {
                    progressDialog = ProgressDialog.show(view.getContext(),"上传","上传中...",true,true,null);
                    String S_name = name.getText().toString();
                    String S_price = price.getText().toString();
                    Double D_price = 0.0;
                    try {
                        D_price = Double.valueOf(S_price);
                        Log.d("trans: ","passed");
                    }
                    catch (Exception e){
                        Log.d("trans: ",e.toString());
                    }
                    String S_describe = describe.getText().toString();
                    final item_net Aitem = new item_net();
                    Aitem.setName(S_name);
                    Aitem.setDescribe(S_describe);
                    Aitem.setPrice(D_price);
                    Aitem.setPicFile(Picfile);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Aitem.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null){
                                        Message msg = new Message();
                                        msg.what = OK_SUCCESS;
                                        handler.sendMessage(msg);
                                    }
                                    else {
                                        Message msg = new Message();
                                        Log.d("ok: ",e.getMessage());
                                        msg.what = OK_FAIL;
                                        handler.sendMessage(msg);
                                    }
                                }
                            });
                        }
                    }, 100);
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
