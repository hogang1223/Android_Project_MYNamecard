package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.mynamecard.Adapter.NameCardAdapter;
import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;
import com.aoslec.mynamecard.common.ItemTouchHelperCallback;

import java.util.ArrayList;

public class DetailViewActivity extends AppCompatActivity {

    String urlAddr;
    String macIP;
    int namecardNo, groupNo;
    String eNamecardFilePath, eName, eCompany, eDept, eJobPosition, eMobile, eTel;
    String eFax, eEmail, eAddress, eMemo, eGroupName;
    ArrayList<NameCard> nameCards = null;

    Button btnBack, btnDelete, btnUpdate;
    WebView wvFilePath;
    TextView edtName, edtJobPosition, edtCompany, edtDept, edtMobile, edtTel, edtFax, edtEmail;
    TextView edtAddress, edtMemo, tvGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

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

        addListener();
    }

    private void addListener(){

        btnBack = findViewById(R.id.detail_btn_back);
        btnDelete = findViewById(R.id.detail_btn_delete);
        btnUpdate = findViewById(R.id.detail_btn_update);

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

        btnBack.setOnClickListener(onClickListener);
        btnUpdate.setOnClickListener(onClickListener);
        btnDelete.setOnClickListener(onClickListener);

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
                case R.id.detail_btn_back:
                    finish();
                    break;
                case R.id.detail_btn_delete:
                    new AlertDialog.Builder(DetailViewActivity.this)
                            .setTitle("삭제하기")
                            .setMessage(eName+ "님의 정보를 삭제하시겠습니까?")
                            .setCancelable(false)
                            .setNegativeButton("취소", null)
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    urlAddr = "http://" + macIP + ":8080/first/namecardDeleteReturn.jsp?namecardNo=" + namecardNo;
                                    connectDelete();

                                    String result = connectDelete();

                                    if(result.equals("1")){
                                        Toast.makeText(DetailViewActivity.this, eName + "님의 정보가 삭제되었습니다.", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(DetailViewActivity.this, "삭제에 실패했습니다.", Toast.LENGTH_LONG).show();
                                    }
                                    finish();
                                }
                            })
                            .show();
                    break;
                case R.id.detail_btn_update:

                    Intent intent = null;
                    intent = new Intent(DetailViewActivity.this, UpdateNameCardActivity.class);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("namecardNo", namecardNo);
                    intent.putExtra("groupNo", groupNo);
                    intent.putExtra("namecardFilePath", eNamecardFilePath);
                    intent.putExtra("name", eName);
                    intent.putExtra("company", eCompany);
                    intent.putExtra("dept", eDept);
                    intent.putExtra("jobPosition", eJobPosition);
                    intent.putExtra("mobile", eMobile);
                    intent.putExtra("tel", eTel);
                    intent.putExtra("fax", eFax);
                    intent.putExtra("email", eEmail);
                    intent.putExtra("address", eAddress);
                    intent.putExtra("memo", eMemo);
                    intent.putExtra("groupName", eGroupName);
                    startActivity(intent);
                    break;
            }
        }
    };

    private String connectDelete() {
        String result = null;
        try {
            NetworkTask networkTask = new NetworkTask(DetailViewActivity.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}