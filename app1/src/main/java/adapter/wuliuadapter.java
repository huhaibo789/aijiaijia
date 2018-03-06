package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import bean.gouwu1;

/**
 * Created by 胡海波 on 2016/11/4.
 */
public class wuliuadapter extends BaseAdapter {

    private RequestQueue queue;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private JSONArray jsonArray;
    private  ArrayList<String> list=new ArrayList<>();
    private Context context;
    public  wuliuadapter  (Context context,ArrayList<String> list){
        super();
        this.context=context;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_wuliu,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.icon =  (ImageView) convertView.findViewById(R.id. iv_route_icon );
            vh.icon_top_line =  convertView.findViewById(R.id.icon_top_line);
            vh.icon_bottom_line =  convertView.findViewById(R.id.icon_bottom_line );
            vh.ll_bottom_line =  (LinearLayout) convertView.findViewById(R.id.ll_bottom_line );
            vh.info= (TextView) convertView.findViewById(R.id.tv_route_info);
            vh.info.setAutoLinkMask(Linkify.ALL);
            vh.time =  (TextView) convertView.findViewById(R.id.tv_route_time );
        }else {
            vh= (ViewHold) convertView.getTag();
        }
        if(position==0){
           vh.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.qurebu));
            vh.info.setTextColor(Color.parseColor("#222222"));
            convertView.setBackgroundColor(Color.WHITE);
            vh.icon_top_line.setVisibility(View.INVISIBLE);
            vh.icon_bottom_line.setVisibility(View.VISIBLE);
           vh.ll_bottom_line.setVisibility(View.VISIBLE);
        }else if(position==list.size()-1){
            vh.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.qurehui));
            vh.info.setTextColor(Color.parseColor("#666666"));
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
           vh.icon_bottom_line.setVisibility(View.INVISIBLE);
           vh.ll_bottom_line.setVisibility(View.INVISIBLE);
           vh.icon_top_line.setVisibility(View.VISIBLE);
        }else {
            vh.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.qurehui));
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            vh.info.setTextColor(Color.parseColor("#666666"));
            vh.icon_top_line.setVisibility(View.VISIBLE);
            vh.icon_bottom_line.setVisibility(View.VISIBLE);
           vh.ll_bottom_line.setVisibility(View.VISIBLE);
        }
           vh.info.setText(list.get(position));
        return convertView;
    }
    public class ViewHold {
        private ImageView icon;
        private View icon_top_line;
        private View icon_bottom_line;
        private LinearLayout ll_bottom_line;
        private TextView info;
        private TextView time;
    }

}
