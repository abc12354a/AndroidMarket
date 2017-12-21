package com.example.abc12.navigationview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class TestActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if(progressBar.getVisibility() == ProgressBar.VISIBLE){
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }else {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final Button handle = (Button)findViewById(R.id.test_handle);
        progressBar = (ProgressBar)findViewById(R.id.test_progressbar);
        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

}
