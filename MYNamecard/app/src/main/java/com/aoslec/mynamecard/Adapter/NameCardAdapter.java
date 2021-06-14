//package com.aoslec.mynamecard.Adapter;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.aoslec.mynamecard.Bean.NameCard;
//import com.aoslec.mynamecard.R;
//import com.aoslec.mynamecard.common.ItemTouchHelperListener;
//
//import java.util.ArrayList;
//
//public class NameCardAdapter extends RecyclerView.Adapter<NameCardAdapter.ViewHolder> implements ItemTouchHelperListener {
//
//    private ArrayList<NameCard> data = null;
//    private String macIP = null;
//
//    public NameCardAdapter(ArrayList<NameCard> data, String macIP){
//        this.data = data;
//        this.macIP = macIP;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView name, jobPosition, company, mobile;
//        public WebView webView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            webView = itemView.findViewById(R.id.wv_card_img);
//            name = itemView.findViewById(R.id.tv_card_name);
//            jobPosition = itemView.findViewById(R.id.tv_card_jobPosition);
//            company = itemView.findViewById(R.id.tv_card_company);
//            mobile = itemView.findViewById(R.id.tv_card_mobile);
//
//            // Web Setting
//            WebSettings webSettings = webView.getSettings();
//            webSettings.setJavaScriptEnabled(true); // JavaScript 사용 가능
//            webSettings.setBuiltInZoomControls(true); // 확대 축소 가능
//            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
//        }
//    }
//
//    @NonNull
//    @Override
//    public NameCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.namecard_layout, parent, false);
//        ViewHolder viewHolder = new ViewHolder(v);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NameCardAdapter.ViewHolder holder, int position) {
//        holder.webView.loadData(htmlData(data.get(position).getNamecardFilePath()), "text/html", "UTF-8");
//        holder.name.setText(data.get(position).getName());
//        holder.jobPosition.setText(data.get(position).getJobPosition());
//        holder.company.setText(data.get(position).getCompany());
//        holder.mobile.setText(data.get(position).getMobile());
//    }
//
//    // image webView에 띄워주기 위해 html 사용
//    private String htmlData(String location){
//
//        Log.v("SelectNamecard", "start htmldata - location : " + location);
//
//        String image = "<html><head>"+
//                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"+
//                "</head><body>"+
//                "<img src=\"http://"+ macIP + ":8080/first/" + location + "\" width =\"180px\" height=\"110px\">" +
//                "</body></html>";
//
//        Log.v("SelectNamecard", "image : " + image);
//
//        return image;
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    @Override
//    public boolean onItemMove(int from_position, int to_position) {
//        // 이동할 객체 저장
//        NameCard nameCard = data.get(from_position);
//        // 이동할 객체 삭제
//        data.remove(from_position);
//        // 이동하고 싶은 position에 추가
//        data.add(to_position, nameCard);
//
//        // Adapter에 데이터 이동 알림
//        notifyItemMoved(from_position, to_position);
//        return true;
//    }
//
//    @Override
//    public void onItemSwipe(int position) {
//        data.remove(position);
//        notifyItemRemoved(position);
//    }
//}
package com.aoslec.mynamecard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.mynamecard.Activity.DetailViewActivity;
import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.NetworkTask.NetworkTask;
import com.aoslec.mynamecard.R;
import com.aoslec.mynamecard.common.ItemTouchHelperListener;

import java.util.ArrayList;

public class NameCardAdapter extends RecyclerView.Adapter<NameCardAdapter.ViewHolder> implements ItemTouchHelperListener {

    private Context context;
    private ArrayList<NameCard> data = null;
    private String macIP = null;

    public NameCardAdapter(Context context, ArrayList<NameCard> data, String macIP){
        this.context = context;
        this.data = data;
        this.macIP = macIP;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name, jobPosition, company, mobile, namecardNo;
        public WebView webView;
        Intent intent = null;

        public ViewHolder(View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.wv_card_img);
            name = itemView.findViewById(R.id.tv_card_name);
            jobPosition = itemView.findViewById(R.id.tv_card_jobPosition);
            company = itemView.findViewById(R.id.tv_card_company);
            mobile = itemView.findViewById(R.id.tv_card_mobile);

            // Web Setting
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true); // JavaScript 사용 가능
            webSettings.setBuiltInZoomControls(true); // 확대 축소 가능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기

            // Recycler view Click Event -> detail view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        intent = new Intent(v.getContext(), DetailViewActivity.class);
                        intent.putExtra("macIP", macIP);
                        intent.putExtra("namecardNo", data.get(position).getNamecardNo());
                        intent.putExtra("groupNo", data.get(position).getGroup_groupNo());
                        intent.putExtra("namecardFilePath", data.get(position).getNamecardFilePath());
                        intent.putExtra("name", data.get(position).getName());
                        intent.putExtra("company", data.get(position).getCompany());
                        intent.putExtra("dept", data.get(position).getDept());
                        intent.putExtra("jobPosition", data.get(position).getJobPosition());
                        intent.putExtra("mobile", data.get(position).getMobile());
                        intent.putExtra("tel", data.get(position).getTel());
                        intent.putExtra("fax", data.get(position).getFax());
                        intent.putExtra("email", data.get(position).getEmail());
                        intent.putExtra("address", data.get(position).getAddress());
                        intent.putExtra("memo", data.get(position).getMemo());
                        intent.putExtra("groupName", data.get(position).getGroupName());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public NameCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.namecard_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        Log.v("SelectNamecard", "viewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NameCardAdapter.ViewHolder holder, int position) {
        holder.webView.loadData(htmlData(data.get(position).getNamecardFilePath()), "text/html", "UTF-8");
        holder.name.setText(data.get(position).getName());
        holder.jobPosition.setText(data.get(position).getJobPosition());
        holder.company.setText(data.get(position).getCompany());
        holder.mobile.setText(data.get(position).getMobile());
    }

    // image webView에 띄워주기 위해 html 사용
    private String htmlData(String location){

        String image = "<html><head>"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"+
                "</head><body>"+
                "<img src=\"http://" + macIP + ":8080/first/" + location + "\" width =\"180px\" height=\"110px\">" +
                "</body></html>";

        return image;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        // 이동할 객체 저장
        NameCard nameCard = data.get(from_position);
        // 이동할 객체 삭제
        data.remove(from_position);
        // 이동하고 싶은 position에 추가
        data.add(to_position, nameCard);

        // Adapter에 데이터 이동 알림
        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        String swipeName = data.get(position).getName();
        int namecardNo = data.get(position).getNamecardNo();

        data.remove(position);
        notifyItemRemoved(position);

        String result = connectFavoriteData(namecardNo);

        if(result.equals("1")){
            notifyItemRemoved(position);
            Toast.makeText(context, swipeName+"님이 즐겨찾기에 등록되었습니다.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "즐겨찾기 등록에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    private String connectFavoriteData(int namecardNo){
        String result = null;
        String urlAddr = "http://" + macIP + ":8080/first/namecardFavoriteUpdate.jsp?namecardNo=" + namecardNo;
        try {
            NetworkTask networkTask = new NetworkTask(context, urlAddr, "favorite");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
