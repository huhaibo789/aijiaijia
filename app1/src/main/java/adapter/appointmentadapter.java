package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
 * Created by 胡海波 on 2016/10/28.
 */
public class appointmentadapter extends BaseAdapter {
    private ArrayList<String> picture=new ArrayList<>();
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> product=new ArrayList<>();
    private  ArrayList<String> nowprice=new ArrayList<>();
    private ArrayList<String> beforeprice=new ArrayList<>();
    private ArrayList<String> num1=new ArrayList<>();
    private RequestQueue queue;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private JSONArray jsonArray;
    private List<gouwu1> data=new ArrayList<>();
    private Context context;
    public  appointmentadapter  (Context context, ImageLoader loader,ArrayList<String> picture,ArrayList<String> title,
                              ArrayList<String> product,ArrayList<String>  nowprice,ArrayList<String> beforeprice,ArrayList<String> num1){
        super();
        this.context=context;
        this.loader=loader;
         this.picture=picture;
        this.title=title;
        this.product=product;
        this.nowprice=nowprice;
        this.beforeprice=beforeprice;
        this.num1=num1;
    }


    @Override
    public int getCount() {
        return picture.size();
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
            convertView=View.inflate(context, R.layout.activity_yuyue,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.detail_iv= (ImageView) convertView.findViewById(R.id.detail_iv);
            vh.detail_tv= (TextView) convertView.findViewById(R.id.detail_tv);
            vh.detail_tv1= (TextView) convertView.findViewById(R.id.detail_tv1);
            vh.detail_tv2= (TextView) convertView.findViewById(R.id.detail_tv2);
            vh. detail_jieshao= (TextView) convertView.findViewById(R.id. detail_jieshao);
            vh. detail_shuliang= (TextView) convertView.findViewById(R.id. detail_shuliang);

        }else {
            vh= (ViewHold) convertView.getTag();
        }
       vh.detail_tv.setText(title.get(position));
        Log.i("sedeefd", "getView: "+position);
        vh.detail_jieshao.setText(product.get(position));
        String jiage=nowprice.get(position);
        if(jiage.equals("0")||jiage.equals("null")){
            vh.detail_tv1.setText("¥"+beforeprice.get(position));
            vh.detail_tv2.setVisibility(View.INVISIBLE);
           }else if(beforeprice.get(position).equals("0")||beforeprice.get(position).equals("null")){
            vh.detail_tv1.setText("¥"+nowprice.get(position));
            vh.detail_tv2.setVisibility(View.INVISIBLE);
        }else {
            vh.detail_tv1.setVisibility(View.VISIBLE);
            vh.detail_tv2.setVisibility(View.VISIBLE);
            vh.detail_tv1.setText("¥"+nowprice.get(position));
            vh.detail_tv2.setText("¥"+beforeprice.get(position));
            vh.detail_tv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        }

        vh.detail_shuliang.setText(num1.get(position));
        final ViewHold finalVh1 = vh;
        loader.loadImage(picture.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
               finalVh1.detail_iv.setImageBitmap(loadedImage);
            }
        });
        return convertView;
    }
    public class ViewHold {
        ImageView detail_iv;
        TextView  detail_tv;
        TextView detail_tv2;
        TextView detail_tv1;
        TextView detail_jieshao;
        TextView detail_shuliang;
    }

}
