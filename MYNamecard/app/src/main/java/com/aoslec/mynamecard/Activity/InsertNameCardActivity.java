package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;

import java.util.ArrayList;

public class InsertNameCardActivity extends AppCompatActivity {

    String urlAddr, macIP, userid, urlAddr1, urlAddr2;
    String eNamecardFilePath, eName, eCompany, eDept, eJobPosition, eMobile, eTel;
    String eFax, eEmail, eAddress, eMemo, eGroupName;
    Button btnBack, btnInsert, btnInsertImage;
    ArrayList<NameCard> nameCards = null;
    WebView wvFilePath;
    EditText edtName, edtJobPosition, edtCompany, edtDept, edtMobile, edtTel, edtFax, edtEmail;
    EditText edtAddress, edtMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_name_card);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        userid = intent.getStringExtra("userid");
        urlAddr = "http://" + macIP + ":8080/first/";
        addListener();

    }

    private void addListener(){

        // button
        btnBack = findViewById(R.id.insert_btn_back);
        btnInsert = findViewById(R.id.insert_btn_insert);
        btnInsertImage = findViewById(R.id.insert_btn_namecardFilePath);

        edtName = findViewById(R.id.insert_edt_Name);
        edtJobPosition = findViewById(R.id.insert_edt_jobPosition);
        edtCompany = findViewById(R.id.insert_edt_company);
        edtDept = findViewById(R.id.insert_edt_dept);
        edtMobile = findViewById(R.id.insert_edt_mobile);
        edtTel = findViewById(R.id.insert_edt_tel);
        edtFax = findViewById(R.id.insert_edt_fax);
        edtEmail = findViewById(R.id.insert_edt_email);
        edtAddress = findViewById(R.id.insert_edt_address);
        edtMemo = findViewById(R.id.insert_edt_memo);

        btnBack.setOnClickListener(onClickListener);
        btnInsert.setOnClickListener(onClickListener);
        btnInsertImage.setOnClickListener(onClickListener);
    } // End - addListener

    // button Click Event
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.insert_btn_back:
                    finish();
                    break;
                case R.id.insert_btn_insert:

                    urlAddr1 = urlAddr + "namecardInsertReturn1.jsp?userid="+userid;
                    String result1 = connectInsertData1();

                    if(result1.equals("1")){

                        eName = edtName.getText().toString();
                        eJobPosition = edtJobPosition.getText().toString();
                        eCompany = edtCompany.getText().toString();
                        eDept = edtDept.getText().toString();
                        eMobile = edtMobile.getText().toString();
                        eTel = edtTel.getText().toString();
                        eFax = edtFax.getText().toString();
                        eEmail = edtEmail.getText().toString();
                        eAddress = edtAddress.getText().toString();
                        eMemo = edtMemo.getText().toString();

                        urlAddr2 = urlAddr + "namecardInsertReturn2.jsp?name=" + eName + "&jobPosition=" + eJobPosition + "&company=" + eCompany + "&dept=" + eDept +
                                "&mobile=" + eMobile + "&tel=" + eTel + "&fax=" + eFax + "&email=" + eEmail + "&address=" + eAddress + "&memo=" + eMemo ;

                        String result2 = connectInsertData2();

                        if(result2.equals("1")){
                            Toast.makeText(InsertNameCardActivity.this, eName + "님의 정보가 등록되었습니다.", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(InsertNameCardActivity.this, "등록에 실패했습니다.", Toast.LENGTH_LONG).show();
                            Log.v("insert", "inser2");
                        }
                        finish();
                    }else{
                        Toast.makeText(InsertNameCardActivity.this, "등록에 실패했습니다.", Toast.LENGTH_LONG).show();
                        Log.v("insert", "inser1");
                    }
                    finish();
                    break;

                case R.id.insert_btn_namecardFilePath:
                    Intent intent = null;
                    intent = new Intent(InsertNameCardActivity.this, imgTest.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private String connectInsertData1(){
        String result = null;
        try{
            NetworkTask networkTask = new NetworkTask(InsertNameCardActivity.this, urlAddr1, "insert");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private String connectInsertData2(){
        String result = null;
        try{
            NetworkTask networkTask = new NetworkTask(InsertNameCardActivity.this, urlAddr2, "insert");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}