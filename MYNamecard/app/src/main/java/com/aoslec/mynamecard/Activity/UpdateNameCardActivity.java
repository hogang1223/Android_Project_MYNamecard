package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;

import java.util.ArrayList;

public class UpdateNameCardActivity extends AppCompatActivity {

    String urlAddr;
    String macIP;
    int namecardNo, groupNo;
    String eNamecardFilePath, eName, eCompany, eDept, eJobPosition, eMobile, eTel;
    String eFax, eEmail, eAddress, eMemo, eGroupName;
    ArrayList<NameCard> nameCards = null;
    Button btnBack, btnDelete, btnUpdate;
    WebView wvFilePath;
    EditText edtName, edtJobPosition, edtCompany, edtDept, edtMobile, edtTel, edtFax, edtEmail;
    EditText edtAddress, edtMemo;
    TextView tvGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name_card);
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        namecardNo = intent.getIntExtra("namecardNo", 0);
        groupNo = intent.getIntExtra("groupNo", 0);
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
        urlAddr = "http://" + macIP + ":8080/first/namecardUpdateReturn.jsp?";

        addListener();
    }
    private void addListener(){

        btnBack = findViewById(R.id.update_btn_back);
        btnUpdate = findViewById(R.id.update_btn_update);

        wvFilePath = findViewById(R.id.update_wv_namecardFilePath);
        edtName = findViewById(R.id.update_edt_Name);
        edtJobPosition = findViewById(R.id.update_edt_jobPosition);
        edtCompany = findViewById(R.id.update_edt_company);
        edtDept = findViewById(R.id.update_edt_dept);
        edtMobile = findViewById(R.id.update_edt_mobile);
        edtTel = findViewById(R.id.update_edt_tel);
        edtFax = findViewById(R.id.update_edt_fax);
        edtEmail = findViewById(R.id.update_edt_email);
        edtAddress = findViewById(R.id.update_edt_address);
        edtMemo = findViewById(R.id.update_edt_memo);

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

        btnBack.setOnClickListener(onClickListener);
        btnUpdate.setOnClickListener(onClickListener);

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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.update_btn_back:
                    finish();
                    break;
                case R.id.update_btn_update:

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

                    urlAddr += "namecardNo=" + namecardNo + "&name=" + eName + "&jobPosition=" + eJobPosition + "&company=" + eCompany + "&dept=" + eDept +
                            "&mobile=" + eMobile + "&tel=" + eTel + "&fax=" + eFax + "&email=" + eEmail + "&address=" + eAddress + "&memo=" + eMemo ;

                    String result = connectUpdateData();

                    if(result.equals("1")){
                        Toast.makeText(UpdateNameCardActivity.this, eName + "님의 정보가 수정되었습니다.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(UpdateNameCardActivity.this, "수정에 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                    finish();
                    break;
            }
        }
    };
    private String connectUpdateData(){
        String result = null;
        try{
            NetworkTask networkTask = new NetworkTask(UpdateNameCardActivity.this, urlAddr, "update");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}