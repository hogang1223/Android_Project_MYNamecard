package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.aoslec.mynamecard.Adapter.NameCardAdapter;
import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;
import com.aoslec.mynamecard.common.ItemTouchHelperCallback;

import java.util.ArrayList;

public class NameCardListActivity extends AppCompatActivity {

    String urlAddr = null;
    String macIP;
    String userid;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NameCard> nameCards;
    NameCardAdapter adapter;
    ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_card_list);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        userid = intent.getStringExtra("userid");
        urlAddr = "http://" + macIP + ":8080/first/namecard_query_all.jsp?userid="+userid;


        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    public void connectGetData(){
        try {
                NetworkTask networkTask = new NetworkTask(NameCardListActivity.this, urlAddr, "select");
                Object obj = networkTask.execute().get();
                nameCards = (ArrayList<NameCard>) obj;

                adapter = new NameCardAdapter(nameCards, macIP);
                recyclerView.setAdapter(adapter);

                helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
                helper.attachToRecyclerView(recyclerView);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}