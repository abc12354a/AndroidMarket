package com.example.abc12.navigationview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewUtils;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    private List<item_net> itemList;
    private LRecyclerView lRecyclerView;
    private LuRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        lRecyclerView = (LRecyclerView)findViewById(R.id.test_list);
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        adapter = new LuRecyclerViewAdapter(itemAdapter);
        lRecyclerView.setAdapter(adapter);

    }

}
