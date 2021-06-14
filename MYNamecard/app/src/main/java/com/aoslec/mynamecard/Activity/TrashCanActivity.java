package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aoslec.mynamecard.Adapter.FavoriteNameCardAdapter;
import com.aoslec.mynamecard.Adapter.TrashCanAdapter;
import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;
import com.aoslec.mynamecard.common.FavoriteItemTouchHelperCallback;
import com.aoslec.mynamecard.common.ItemTouchHelperCallback;

import java.util.ArrayList;

public class TrashCanActivity extends AppCompatActivity {

    String urlAddr = null;
    String macIP;
    String userid;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NameCard> nameCards;
    TrashCanAdapter adapter;
    ItemTouchHelper helper;
    Button deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_can);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        userid = intent.getStringExtra("userid");
        urlAddr = "http://" + macIP + ":8080/first/namecard_query_trashcan.jsp?userid=" + userid;

        deleteAll = findViewById(R.id.delete_all);
        recyclerView = findViewById(R.id.trash_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        deleteAll.setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    public void connectGetData() {
        try {
            NetworkTask networkTask = new NetworkTask(TrashCanActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            nameCards = (ArrayList<NameCard>) obj;

            adapter = new TrashCanAdapter(TrashCanActivity.this, nameCards, macIP);
            recyclerView.setAdapter(adapter);

            helper = new ItemTouchHelper(new FavoriteItemTouchHelperCallback(adapter));
            helper.attachToRecyclerView(recyclerView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // delete All
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(TrashCanActivity.this)
                    .setTitle("휴지통 비우기")
                    .setMessage("휴지통을 비우시겠습니까?")
                    .setCancelable(false)
                    .setNegativeButton("취소", null)
                    .setPositiveButton("비우기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            connectDelete();
                            String result = connectDelete();
                                Toast.makeText(TrashCanActivity.this, "휴지통을 말끔히 비웠습니다.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .show();
        }
    };

    private String connectDelete(){
        urlAddr = "http://" + macIP + ":8080/first/namecardTrashCan_Delete_All.jsp?userid=" + userid;
        String result = null;
        try {
            NetworkTask networkTask = new NetworkTask(TrashCanActivity.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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
                intent = new Intent(TrashCanActivity.this, NameCardListActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
            case R.id.menu_02:
                intent = new Intent(TrashCanActivity.this, FavoritNameCardActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
            case R.id.menu_03:
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