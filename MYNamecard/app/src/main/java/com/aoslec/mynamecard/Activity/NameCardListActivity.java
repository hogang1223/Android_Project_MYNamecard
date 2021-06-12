package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
        urlAddr = "http://" + macIP + ":8080/first/namecard_query_all.jsp";

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            NetworkTask networkTask = new NetworkTask(MainActivity.this, urlAddr);
            Object obj = networkTask.execute().get();
            nameCards = (ArrayList<NameCard>) obj;

            adapter = new NameCardAdapter(nameCards);
            recyclerView.setAdapter(adapter);

            helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
            helper.attachToRecyclerView(recyclerView);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}