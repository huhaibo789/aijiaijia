package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.YouhuiuserActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by 胡海波 on 2016/10/19.
 */
public class evaluateadapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<String> content=new ArrayList<>();
    private ArrayList<String> nickname=new ArrayList<>();
    private ArrayList<String> time=new ArrayList<>();
    private ArrayList<String> merchants=new ArrayList<>();
    private ArrayList<String> merchantstime=new ArrayList<>();

    private Context context;
    public  evaluateadapter (Context context, ImageLoader loader,ArrayList<String> content,ArrayList<String> nickname,ArrayList<String> time
    ,ArrayList<String> merchants,ArrayList<String> merchantstime){
        super();
        this.context=context;
        this.loader=loader;
        this.content=content;
        this.nickname=nickname;
        this.time=time;
        this.merchants=merchants;
        this.merchantstime=merchantstime;
    }
    @Override
    public int getCount() {
        Log.i("laile4", "getView: "+"4");
        return content.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i("laile5", "getView: "+"5");
        return null;
    }

    @Override
    public long getItemId(int position) {
        Log.i("laile6", "getView: "+"6");
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.i("laile4", "getView: "+"4");
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_evalate,null);
            vh=new ViewHold();
            convertView.setTag(vh);
           vh.evalute_tv= (TextView) convertView.findViewById(R.id.evalute_tv);
            vh.evalute_time= (TextView) convertView.findViewById(R.id.evalute_time);
            vh.small_tv= (TextView) convertView.findViewById(R.id.small_tv);
            vh.neirong_tv= (TextView) convertView.findViewById(R.id.neirong_tv);
            vh.neirong_time= (TextView) convertView.findViewById(R.id.neirong_time);

        }else {
            vh= (ViewHold) convertView.getTag();

        }
        Log.i("laile", "getView: "+"sdasfa");
        vh.evalute_tv.setText(nickname.get(position));
        vh.evalute_time.setText(time.get(position));
        vh.small_tv.setText(content.get(position));
        vh.neirong_tv.setText(merchants.get(position));
        vh.neirong_time.setText(merchantstime.get(position));
        return convertView;
    }
    public class ViewHold {
       TextView evalute_tv;
        TextView evalute_time;
        TextView small_tv;
        TextView neirong_tv;
        TextView neirong_time;
    }

}
