package com.aoslec.mynamecard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;

import java.util.ArrayList;

public class TrashCanDetailViewActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_trash_can_detail_view);

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

    private void addListener() {

        btnBack = findViewById(R.id.delete_btn_back);
        btnDelete = findViewById(R.id.delete_btn_delete);
        btnUpdate = findViewById(R.id.delete_btn_update);

        wvFilePath = findViewById(R.id.delete_wv_namecardFilePath);
        edtName = findViewById(R.id.delete_edt_name);
        edtJobPosition = findViewById(R.id.delete_edt_jobPosition);
        edtCompany = findViewById(R.id.delete_edt_company);
        edtDept = findViewById(R.id.delete_edt_dept);
        edtMobile = findViewById(R.id.delete_edt_mobile);
        edtTel = findViewById(R.id.delete_edt_tel);
        edtFax = findViewById(R.id.delete_edt_fax);
        edtEmail = findViewById(R.id.delete_edt_email);
        edtAddress = findViewById(R.id.delete_edt_address);
        edtMemo = findViewById(R.id.delete_edt_memo);
        tvGroupName = findViewById(R.id.delete_tv_groupName);

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
    private String htmlData(String location) {

        String image = "<html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                "</head><body>" +
                "<img src=\"http://" + macIP + ":8080/first/" + location + "\" width =\"370px\" height=\"auto\">" +
                "</body></html>";

        return image;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.delete_btn_back:
                    finish();
                    break;
                case R.id.delete_btn_delete:
                    new AlertDialog.Builder(TrashCanDetailViewActivity.this)
                        .setTitle("삭제하기")
                        .setMessage(eName + "님의 정보를 정말 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                connectTrashCanDelete();
                                String result = connectTrashCanDelete();

                                if (result.equals("1")) {
                                    Toast.makeText(TrashCanDetailViewActivity.this, eName + "님의 정보가 완전히 삭제되었습니다.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(TrashCanDetailViewActivity.this, "삭제에 실패했습니다.", Toast.LENGTH_LONG).show();
                                }
                                finish();
                            }
                        })
                        .show();
                    break;

                case R.id.delete_btn_update:
                    connectTrashCanUpDate();
                    String result = connectTrashCanUpDate();

                    if (result.equals("1")) {
                        Toast.makeText(TrashCanDetailViewActivity.this, eName + "님의 정보가 복구되었습니다.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(TrashCanDetailViewActivity.this, "복구에 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                    finish();
                    break;
            }
        }
    };
    private String connectTrashCanDelete(){
        String result = null;
        String urlAddr = "http://" + macIP + ":8080/first/namecardTrashCanDelete.jsp?namecardNo=" + namecardNo;
        try {
            NetworkTask networkTask = new NetworkTask(TrashCanDetailViewActivity.this, urlAddr, "trashcan");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private String connectTrashCanUpDate(){
        String result = null;
        String urlAddr = "http://" + macIP + ":8080/first/namecardTrashCanUpdate.jsp?namecardNo=" + namecardNo;
        try {
            NetworkTask networkTask = new NetworkTask(TrashCanDetailViewActivity.this, urlAddr, "trashcan");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}