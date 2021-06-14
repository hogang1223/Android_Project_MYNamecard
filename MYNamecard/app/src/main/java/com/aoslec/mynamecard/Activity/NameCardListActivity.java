package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_card_list);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        userid = intent.getStringExtra("userid");
        urlAddr = "http://" + macIP + ":8080/first/namecard_query_all.jsp?userid=" + userid;

        btnInsert = findViewById(R.id.list_btn_insert);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnInsert.setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    public void connectGetData() {
        try {
            NetworkTask networkTask = new NetworkTask(NameCardListActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            nameCards = (ArrayList<NameCard>) obj;

            adapter = new NameCardAdapter(NameCardListActivity.this, nameCards, macIP);
            recyclerView.setAdapter(adapter);

            helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
            helper.attachToRecyclerView(recyclerView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            // insert namecard
            intent = new Intent(NameCardListActivity.this, InsertNameCardActivity.class);
            intent.putExtra("macIP", macIP);
            intent.putExtra("userid", userid);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_namelist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_01:
                Toast.makeText(this, "전체보기", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_02:
                intent = new Intent(NameCardListActivity.this, FavoritNameCardActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
            case R.id.menu_03:
                intent = new Intent(NameCardListActivity.this, TrashCanActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
        return true;
    }
}