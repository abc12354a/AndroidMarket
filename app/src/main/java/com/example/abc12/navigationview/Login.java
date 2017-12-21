package com.example.abc12.navigationview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.v3.http.bean.Init;

public class Login extends AppCompatActivity {
    private String username;
    private String password;
    private EditText E_username;
    private EditText E_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitUI();
    }
    public void InitUI(){
        E_username = (EditText) findViewById(R.id.login_username);
        E_password = (EditText) findViewById(R.id.login_password);
    }
}
