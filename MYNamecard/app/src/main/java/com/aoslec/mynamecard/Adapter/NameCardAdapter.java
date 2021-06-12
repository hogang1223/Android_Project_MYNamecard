package com.aoslec.mynamecard.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.mynamecard.Bean.NameCard;
import com.aoslec.mynamecard.R;
import com.aoslec.mynamecard.common.ItemTouchHelperListener;

import java.util.ArrayList;

public class NameCardAdapter extends RecyclerView.Adapter<NameCardAdapter.ViewHolder> implements ItemTouchHelperListener {

    private ArrayList<NameCard> data = null;

    public NameCardAdapter(ArrayList<NameCard> data){
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name, jobPosition, company, mobile;
        public WebView webView;

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
        }
    }

    @NonNull
    @Override
    public NameCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.namecard_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NameCardAdapter.ViewHolder holder, int position) {
        holder.webView.loadData(htmlData(data.get(position).getNamecardFilePath()), "text/html", "UTF-8");
//        holder.webView.loadUrl("http://192.168.219.105:8080/first/Arithmetic.jsp");
        holder.name.setText(data.get(position).getName());
        holder.jobPosition.setText(data.get(position).getJobPosition());
        holder.company.setText(data.get(position).getCompany());
        holder.mobile.setText(data.get(position).getMobile());
    }

    // image webView에 띄워주기 위해 html 사용
    private String htmlData(String location){

        Log.v("SelectNamecard", "start htmldata - location : " + location);

        String image = "<html><head>"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"+
                "</head><body>"+
                "<img src=\"http://192.168.219.105:8080/first/" + location + "\" width =\"180px\" height=\"110px\">" +
                "</body></html>";

        Log.v("SelectNamecard", "image : " + image);

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
        data.remove(position);
        notifyItemRemoved(position);
    }
}
