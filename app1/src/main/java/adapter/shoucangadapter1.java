package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import bean.gouwu;
import bean.gouwu1;
import bean.zhantingbean;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class shoucangadapter1 extends BaseAdapter {


    private LayoutInflater inflater;
    private ImageLoader loader;
    private List<gouwu1> data=new ArrayList<>();
    private Context context;
    public   shoucangadapter1 (Context context, ImageLoader loader){
        super();
        this.context=context;
        this.loader=loader;
    }
    public  void updateshuju6(List<gouwu1> data){
        this.data=data;
        Log.i("douzi1234", "updateshuju: "+data.toString());

    }
    @Override
    public int getCount() {
        return data.size();
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
            convertView=View.inflate(context, R.layout.activity_shoucangye1,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.ca_iv= (ImageView) convertView.findViewById(R.id.ca_iv);
            vh.ca_tv= (TextView) convertView.findViewById(R.id.ca_tv);
            vh.ca_tv1= (TextView) convertView.findViewById(R.id.ca_tv1);
            vh.ca_tv2= (TextView) convertView.findViewById(R.id.ca_tv2);


        }else {
            vh= (ViewHold) convertView.getTag();
        }
        vh.ca_tv.setText(data.get(position).getBigtitle());
        vh.ca_tv1.setText(data.get(position).getNowprice());
        vh.ca_tv2.setText(data.get(position).getPreviousprice());
        final ViewHold finalVh1 = vh;
        loader.loadImage(data.get(position).getPicture(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh1.ca_iv.setImageBitmap(loadedImage);
            }
        });
        return convertView;
    }
    public class ViewHold {
        ImageView ca_iv;
        TextView  ca_tv;
        TextView ca_tv1;
        TextView ca_tv2;
    }

}
