package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import bean.huasabean;
import bean.zhantingbean;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class zhantingadapter1 extends BaseAdapter {


    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<huasabean.ResponseJsonBean.ZuListBean> data1=new ArrayList<>();
    private ArrayList<String> list2=new ArrayList<>();
    private Context context;
    public  zhantingadapter1(Context context, ImageLoader loader){
        super();
        this.context=context;
        this.loader=loader;
    }
    public  void updatehuasa(ArrayList<huasabean.ResponseJsonBean.ZuListBean> data1){
        this.data1=data1;

    }
    @Override
    public int getCount() {
        return data1.size();
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
            convertView=View.inflate(context, R.layout.activity_zhangting1,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.fengge_tv= (TextView) convertView.findViewById(R.id.fengge_tv);


        }else {
            vh= (ViewHold) convertView.getTag();
        }
        String aa=data1.get(position).getAttr_value();
        vh.fengge_tv.setText(aa);

        String[] sourcrAttr=aa.split(",");
        for (int i = 0; i <sourcrAttr.length ; i++) {
            Log.i("tyt", "getView: "+sourcrAttr[i]);
            //vh.fengge_tv.setText(sourcrAttr[i]);

        }


   return convertView;


    }
    public class ViewHold {
       TextView fengge_tv;
    }

}
