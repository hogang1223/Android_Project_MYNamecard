package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aoslec.mynamecard.Adapter.NameCardAdapter;
import com.aoslec.mynamecard.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout linear_main, linear_login;
    Button btn_main_login, login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListener();
    }

    private void addListener(){

        linear_main = findViewById(R.id.linear_main);
        linear_login = findViewById(R.id.linear_login);
        btn_main_login = findViewById(R.id.btn_main_login);
        login_btn = findViewById(R.id.login_btn);


        btn_main_login.setOnClickListener(onClickListener);
        login_btn.setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String tempIp = "172.30.1.19";
            String userid = "user01";
            Intent intent = null;

            switch (v.getId()){
                case R.id.btn_main_login:
                    linear_main.setVisibility(View.INVISIBLE);
                    linear_login.setVisibility(View.VISIBLE);
                    break;
                case R.id.login_btn:
                    intent = new Intent(MainActivity.this, NameCardListActivity.class);
                    intent.putExtra("macIP", tempIp);
                    intent.putExtra("userid", userid);
                    startActivity(intent);
                    break;
            }

        }
    };
}