package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Shoucang1Activity;
import com.example.administrator.myyushi.h5detailsActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import bean.gouwu1;

/**
 * Created by misshu on 2017/5/19/019.
 */
public class h5detailActivityadapter  extends BaseAdapter {

    private h5detailsActivity activity;
    private Context mContext;
    private ImageLoader loader;
    private LayoutInflater mInflater;
    private ArrayList<String> detail2 = new ArrayList<>();
    private ArrayList<String> detail3 = new ArrayList<>();
    public  h5detailActivityadapter (h5detailsActivity activity,ArrayList<String> detail2,ArrayList<String> detail3){
        super();
        this.activity=activity;
        this.detail2=detail2;
        this.detail3=detail3;


    }


    @Override
    public int getCount() {
        return detail3.size();
    }

    @Override
    public Object getItem(int position) {
        return detail3.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView=View.inflate(activity,R.layout.activity_h5adapter,null);
            holder=new ViewHolder();
            holder.left_list= (TextView) convertView.findViewById(R.id.left_list);
            holder.right_list= (TextView) convertView.findViewById(R.id.right_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.left_list.setText(detail2.get(position));
        holder.right_list.setText(detail3.get(position));
        return convertView;
    }



    class ViewHolder {
        TextView left_list;
        TextView right_list;
    }


}
