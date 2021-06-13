package com.aoslec.mynamecard.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.mynamecard.Bean.NameCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<NameCard> nameCards;

    // Network Task를 검색 입력, 수정, 삭제 구분없이 하나로 사용키위해 생성자 변수 추가
    String where = null;

    public NetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.nameCards = nameCards;
        this.nameCards = new ArrayList<NameCard>();
        this.where = where;
    }
        @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get....");
        progressDialog.show();
    }

    @Override
    //바뀌는 데이터... 진행중에 쓰는것
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");

                }
                switch (where) {
                    case "select":
                        parserSelect(stringBuffer.toString());
                        break;
                    default:
                        result = parserAction(stringBuffer.toString());
                        break;

                }
            }

//                if(where.equals("select")) {
//                    parserSelect(stringBuffer.toString());
//                }else{
//                    result = parserAction(stringBuffer.toString());
//                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        switch (where) {
            case "select":
                return nameCards;
            default:
                return result;
        }
//        if(where.equals("select")){
//            return nameCards;
//        }else{
//            return result;
//        }
    }

    private String parserAction(String str){
        String returnValue = null;
        try{
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");


        }catch (Exception e){
            e.printStackTrace();
        }

        return returnValue;
    }

    private void parserSelect(String str){
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("namecards_info"));
            nameCards.clear();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int namecardNo = jsonObject1.getInt("namecardNo");
                int group_groupNo = jsonObject1.getInt("groupNo");
                String namecardFilePath = jsonObject1.getString("namecardFilePath");
                String name = jsonObject1.getString("name");
                String jobPosition = jsonObject1.getString("jobPosition");
                String groupName = jsonObject1.getString("groupName");
                String company = jsonObject1.getString("company");
                String dept = jsonObject1.getString("dept");
                String mobile = jsonObject1.getString("mobile");
                String tel = jsonObject1.getString("tel");
                String fax = jsonObject1.getString("fax");
                String email = jsonObject1.getString("email");
                String address = jsonObject1.getString("address");
                String memo = jsonObject1.getString("memo");
                Log.v("SelectNamecard", "memo : " + memo);

                NameCard namecard = new NameCard(namecardNo, group_groupNo, namecardFilePath,
                        name, jobPosition, groupName, company, dept, mobile, tel, fax,
                        email, address, memo);

                nameCards.add(namecard);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

} // end line
