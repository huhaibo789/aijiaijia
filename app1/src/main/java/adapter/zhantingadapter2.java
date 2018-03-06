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

import bean.zhantingbean;
import bean.zuijinbean;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class zhantingadapter2 extends BaseAdapter {

   private String weizhi;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<zuijinbean.ResponseJsonBean.ListBean> data=new ArrayList<>();
    private ArrayList<String> huan1=new ArrayList<>();
    private  ArrayList<String> huan2=new ArrayList<>();
    private ArrayList<String> huan3=new ArrayList<>();
    private Context context;
    public  zhantingadapter2(Context context,ArrayList<String> huan1,ArrayList<String> huan2,ArrayList<String> huan3,String weizhi){
        super();
        this.context=context;
        this.huan1=huan1;
        this.huan2=huan2;
        this.huan3=huan3;
        this.weizhi=weizhi;
        Log.i("eedeee", "zhantingadapter2: "+weizhi);
    }


    @Override
    public int getCount() {
        return huan1.size();
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
            convertView=View.inflate(context, R.layout.activity_zhangting2,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.biao_tv= (TextView) convertView.findViewById(R.id.biao_tv);
            vh.biao_tv1= (TextView) convertView.findViewById(R.id.biao_tv1);
            vh.biao_tv2= (TextView) convertView.findViewById(R.id.biao_tv2);
            vh.yuan_iv= (ImageView) convertView.findViewById(R.id.yuan_iv);

        }else {
            vh= (ViewHold) convertView.getTag();
        }
        vh.yuan_iv.setBackgroundResource(R.drawable.yuyuenormal);
        if(weizhi.equals(String.valueOf(position))){
            vh.yuan_iv.setBackgroundResource(R.drawable.yuyuechose);
        }
        vh.biao_tv.setText(huan1.get(position));
        vh.biao_tv1.setText(huan3.get(position));
        vh.biao_tv2.setText(huan2.get(position));


        return convertView;
    }
    public class ViewHold {

        TextView biao_tv;
        TextView biao_tv1;
        TextView biao_tv2;
        ImageView yuan_iv;
    }

}
