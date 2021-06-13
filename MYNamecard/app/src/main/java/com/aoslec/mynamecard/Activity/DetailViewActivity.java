package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aoslec.mynamecard.Adapter.NameCardAdapter;
import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;
import com.aoslec.mynamecard.common.ItemTouchHelperCallback;

import java.util.ArrayList;

public class DetailViewActivity extends AppCompatActivity {

    String urlAddr;
    String macIP;
    String namecardNo, groupNo;
    String eNamecardFilePath, eName, eCompany, eDept, eJobPosition, eMobile, eTel;
    String eFax, eEmail, eAddress, eMemo, eGroupName;
    ArrayList<NameCard> nameCards = null;

    Button btnBack, btnDelete, btnUpdate;
    WebView wvFilePath;
    EditText edtName, edtJobPosition, edtCompany, edtDept, edtMobile, edtTel, edtFax, edtEmail;
    EditText edtAddress, edtMemo;
    TextView tvNamecardNo, tvGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        namecardNo = intent.getStringExtra("namecardNo");
        groupNo = intent.getStringExtra("groupNo");
        eNamecardFilePath = intent.getStringExtra("namecardFilePath");
        eName = intent.getStringExtra("name");
        eCompany = intent.getStringExtra("company");
        eDept = intent.getStringExtra("dept");
        eJobPosition = intent.getStringExtra("jobPosition");
        eMobile = intent.getStringExtra("mobile");
        eTel = intent.getStringExtra("tel");
        eFax = intent.getStringExtra("fax");
        eEmail = intent.getStringExtra("email");
        eAddress = intent.getStringExtra("address");
        eMemo = intent.getStringExtra("memo");
        eGroupName = intent.getStringExtra("groupName");

        addListener();
    }

    private void addListener(){
//        Button btnBack, btnDelete, btnUpdate;
//        WebView wvFilePath;
//        EditText edtName, edtJobPosition, edtCompany, edtDept, edtMobile, edtTel, edtFax, edtEmail;
//        EditText edtAddress, edtMemo;
//        TextView tvNamecardNo, tvGroupName;

        wvFilePath = findViewById(R.id.detail_wv_namecardFilePath);
        edtName = findViewById(R.id.detail_edt_name);
        edtJobPosition = findViewById(R.id.detail_edt_jobPosition);
        edtCompany = findViewById(R.id.detail_edt_company);
        edtDept = findViewById(R.id.detail_edt_dept);
        edtMobile = findViewById(R.id.detail_edt_mobile);
        edtTel = findViewById(R.id.detail_edt_tel);
        edtFax = findViewById(R.id.detail_edt_fax);
        edtEmail = findViewById(R.id.detail_edt_email);
        edtAddress = findViewById(R.id.detail_edt_address);
        edtMemo = findViewById(R.id.detail_edt_memo);
        tvGroupName = findViewById(R.id.detail_tv_groupName);

        // Web Setting
        WebSettings webSettings = wvFilePath.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 사용 가능
        webSettings.setBuiltInZoomControls(true); // 확대 축소 가능
        webSettings.setDisplayZoomControls(false); // 돋보기 없애기

        wvFilePath.loadData(htmlData(eNamecardFilePath), "text/html", "UTF-8");
        edtName.setText(eName);
        edtJobPosition.setText(eJobPosition);
        edtCompany.setText(eCompany);
        edtDept.setText(eDept);
        edtMobile.setText(eMobile);
        edtTel.setText(eTel);
        edtFax.setText(eFax);
        edtEmail.setText(eEmail);
        edtAddress.setText(eAddress);
        edtMemo.setText(eMemo);
        tvGroupName.setText(eGroupName);

    }

    // image webView에 띄워주기 위해 html 사용
    private String htmlData(String location){

        String image = "<html><head>"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"+
                "</head><body>"+
                "<img src=\"http://" + macIP + ":8080/first/" + location + "\" width =\"370px\" height=\"auto\">" +
                "</body></html>";

        return image;
    }
}