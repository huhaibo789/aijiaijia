package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.AllsaleActivity;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.SearchActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡海波 on 2016/10/19.
 */
public class Messageadapter extends BaseAdapter {
    private ImageLoader loader;
    private RequestQueue queue;
    private SearchActivity activity;
    private int lastPosition;
    private LayoutInflater inflater;
    private String id1;
    private ArrayList<String> title=new ArrayList<>();
    private  ArrayList<String> content=new ArrayList<>();
    private  ArrayList<String> time=new ArrayList<>();
    private Context context;
    public   Messageadapter(SearchActivity context,ImageLoader loader,ArrayList<String> title,ArrayList<String> content,ArrayList<String> time){
        super();
        this.context=context;
        this.loader=loader;
        this.activity=context;
        this.title=title;
        this.content=content;
        this.time=time;
    }
    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return title.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_message,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.message_time= (TextView) convertView.findViewById(R.id.message_time);
            vh.big_title= (TextView) convertView.findViewById(R.id.big_title);
            vh.small_title= (TextView) convertView.findViewById(R.id.small_title);
            vh.yuandian= (ImageView) convertView.findViewById(R.id.yuandian);
        }else {
            vh= (ViewHold) convertView.getTag();
        }
        vh.message_time.setText(time.get(position));
        vh.big_title.setText(title.get(position));
        vh.small_title.setText(content.get(position));
        return convertView;
    }



    public class ViewHold {
        TextView message_time,big_title,small_title;
        ImageView yuandian;

    }

}
