package adapter;

import android.content.Context;
import android.graphics.Bitmap;
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

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class zhantingadapter extends BaseAdapter {


    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<zhantingbean.ResponseJsonBean.ListBean> data=new ArrayList<>();
    private ArrayList<String> mendian_address=new ArrayList<>();
    private ArrayList<String> mendian_image=new ArrayList<>();
    private ArrayList<String> mendian_name=new ArrayList<>();
    private ArrayList<String> mendian_phone=new ArrayList<>();
    private Context context;
    public  zhantingadapter(Context context, ImageLoader loader,ArrayList<String> mendian_name,ArrayList<String> mendian_phone,ArrayList<String> mendian_address,
                            ArrayList<String> mendian_image){
        super();
         this.context=context;
        this.loader=loader;
        this.mendian_name=mendian_name;
        this.mendian_phone=mendian_phone;
        this.mendian_address=mendian_address;
        this.mendian_image=mendian_image;
    }
   /* public  void updateData(ArrayList<zhantingbean.ResponseJsonBean.ListBean> data){
        this.data=data;

    }*/


    @Override
    public int getCount() {
        return mendian_address.size();
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
            convertView=View.inflate(context, R.layout.activity_zhangting,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.biao_tv= (TextView) convertView.findViewById(R.id.biao_tv);
            vh.biao_tv1= (TextView) convertView.findViewById(R.id.biao_tv1);
            vh.biao_tv2= (TextView) convertView.findViewById(R.id.biao_tv2);
            vh.zh_iv= (ImageView) convertView.findViewById(R.id.zh_iv);

        }else {
            vh= (ViewHold) convertView.getTag();
        }
        vh.biao_tv.setText(mendian_name.get(position));
        vh.biao_tv1.setText(mendian_phone.get(position));
        vh.biao_tv2.setText(mendian_address.get(position));
        final ViewHold finalVh = vh;
        loader.loadImage(mendian_image.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh.zh_iv.setImageBitmap(loadedImage);
            }
        });
        return convertView;
    }
    public class ViewHold {
        ImageView zh_iv;
        TextView biao_tv;
        TextView biao_tv1;
        TextView biao_tv2;
    }

}
