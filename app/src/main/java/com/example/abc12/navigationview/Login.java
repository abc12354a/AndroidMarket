package com.example.abc12.navigationview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.http.bean.Init;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {
    private final static int LOGIN_SUCCESS = 1;
    private final static int LOGIN_FAILED = 2;
    private final static int REGISTER_SUCCRSS = 3;
    private final static int REGISTER_FAILED = 4;
    private String username;
    private String password;
    private EditText E_username;
    private EditText E_password;
    private Button Login;
    private Button Register;
    private ProgressDialog progressDialog;
    Handler LoginHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_SUCCESS:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"登陆成功！",Toast.LENGTH_SHORT).show();
                    Intent OpenMain = new Intent(getBaseContext(),MainActivity.class);
                    getBaseContext().startActivity(OpenMain);
                    break;
                case LOGIN_FAILED:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"登陆失败，请检查网络和账户。",Toast.LENGTH_SHORT).show();
                    break;
                case REGISTER_SUCCRSS:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"注册成功！,请记住ID并登陆",Toast.LENGTH_SHORT).show();
                    break;
                case REGISTER_FAILED:
                    progressDialog.cancel();
                    Toast.makeText(getBaseContext(),"注册失败，请检查网络和账户",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //------------------------------------------------------
        Bmob.initialize(this,"7b3fba74df74db9d68950773c6cfa6f0");
        Connector.getDatabase();
        //------------------------------------------------------
        InitUI();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Login: ","clicked");
                if(E_username.getText().toString().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
                    Log.d("Login: ","log1");
                    progressDialog = ProgressDialog.show(view.getContext(),"Loading","login...",true,true,null);
                    username = E_username.getText().toString();
                    password = E_password.getText().toString();
                    final BmobUser User_Login = new BmobUser();
                    User_Login.setUsername(username);
                    User_Login.setPassword(password);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Login: ","log2");
                           User_Login.login(new SaveListener<BmobUser>() {
                               @Override
                               public void done(BmobUser bmobUser, BmobException e) {
                                   if(e == null){
                                       Message msg_login_OK = new Message();
                                       msg_login_OK.what = LOGIN_SUCCESS;
                                       LoginHandle.sendMessage(msg_login_OK);
                                   }
                                   else {
                                       Message msg_login_Fail = new Message();
                                       msg_login_Fail.what = LOGIN_FAILED;
                                       LoginHandle.sendMessage(msg_login_Fail);
                                       Log.d("Login: ","failed"+e.toString());
                                   }
                               }
                           });
                        }
                    },100);
                }
                else {
                    Toast.makeText(getBaseContext(),"密码长度或账号有误",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((E_username.getText().toString().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"))&&(E_password.getText().length()>6)){
                    progressDialog = ProgressDialog.show(view.getContext(),"Loading","register...",true,true,null);
                    username = E_username.getText().toString();
                    password = E_password.getText().toString();
                    final BmobUser User_Register = new BmobUser();
                    User_Register.setUsername(username);
                    User_Register.setPassword(password);
                    User_Register.setEmail(username);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User_Register.signUp(new SaveListener<BmobUser>() {
                                @Override
                                public void done(BmobUser bmobUser, BmobException e) {
                                    if(e == null){
                                        Message msg_register_OK = new Message();
                                        msg_register_OK.what = REGISTER_SUCCRSS;
                                        LoginHandle.sendMessage(msg_register_OK);
                                        Log.d("Register: ","info:"+bmobUser.toString());
                                    }
                                    else {
                                        Message msg_register_Fail = new Message();
                                        msg_register_Fail.what = REGISTER_FAILED;
                                        LoginHandle.sendMessage(msg_register_Fail);
                                        Log.d("Register: ","failed"+e.toString());
                                    }
                                }
                            });
                        }
                    }, 100);
                }
                else {
                    Toast.makeText(getBaseContext(),"密码长度或账号有误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void InitUI(){
        E_username = (EditText) findViewById(R.id.login_username);
        E_password = (EditText) findViewById(R.id.login_password);
        Login = (Button)findViewById(R.id.login_login);
        Register = (Button)findViewById(R.id.login_register);
    }
}
