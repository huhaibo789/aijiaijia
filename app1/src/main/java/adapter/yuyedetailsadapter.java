package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
 * Created by 胡海波 on 2016/11/1.
 */
public class yuyedetailsadapter extends BaseAdapter {


    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<String> picture = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> smalltitle = new ArrayList<>();
    private ArrayList<String> nowprice = new ArrayList<>();
    private ArrayList<String> beforeprice = new ArrayList<>();
    private ArrayList<String> number = new ArrayList<>();
    private Context context;
    public yuyedetailsadapter (Context context, ImageLoader loader,ArrayList<String> picture,ArrayList<String> title,ArrayList<String> smalltitle,
                               ArrayList<String> nowprice,ArrayList<String> beforeprice, ArrayList<String> number ){
        super();
        this.context=context;
        this.loader=loader;
        this.picture=picture;
        this.title=title;
        this.smalltitle=smalltitle;
        this.nowprice=nowprice;
        this.beforeprice=beforeprice;
        this.number=number;
    }

    @Override
    public int getCount() {
        Log.i("laile4", "getView: "+"4");
        Log.i("renxiao", "getCount: "+picture.size());
        return picture.size();
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
            convertView=View.inflate(context, R.layout.activity_yuyuedetails,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.shuliang_tv1= (TextView) convertView.findViewById(R.id.shuliang_tv1);
            vh. shuliang_tv2= (TextView) convertView.findViewById(R.id. shuliang_tv2);
            vh. product_bn= (TextView) convertView.findViewById(R.id. product_bn);
            vh.shuliang_iv= (ImageView) convertView.findViewById(R.id.shuliang_iv);
            vh.shuliang_tv3= (TextView) convertView.findViewById(R.id.shuliang_tv3);
            vh.shuliang_tv4= (TextView) convertView.findViewById(R.id.shuliang_tv4);

            vh.jinbi1= (TextView) convertView.findViewById(R.id.jinbi1);
            Log.i("laile1", "getView: "+"1");
        }else {
            vh= (ViewHold) convertView.getTag();
        }
       vh.shuliang_tv1.setText(title.get(position).toString());
        if(nowprice.get(position).equals("null")){

            vh.shuliang_tv3.setVisibility(View.INVISIBLE);
            vh.jinbi1.setVisibility(View.INVISIBLE);
            vh.shuliang_tv2.setText(String.valueOf(beforeprice.get(position)));
        }else if(nowprice.get(position).equals(beforeprice.get(position))){
            vh.shuliang_tv3.setVisibility(View.INVISIBLE);
            vh.jinbi1.setVisibility(View.INVISIBLE);
            vh.shuliang_tv2.setText(String.valueOf(beforeprice.get(position)));
        }else if(beforeprice.get(position).equals("0")||beforeprice.get(position).equals("null")){
            vh.shuliang_tv3.setVisibility(View.INVISIBLE);
            vh.jinbi1.setVisibility(View.INVISIBLE);
            vh.shuliang_tv2.setText(String.valueOf(nowprice.get(position)));
        }else {
            vh.shuliang_tv3.setVisibility(View.VISIBLE);
            vh.jinbi1.setVisibility(View.VISIBLE);
            vh.shuliang_tv2.setText(String.valueOf(nowprice.get(position)));
            vh.shuliang_tv3.setText(String.valueOf(beforeprice.get(position)));
            vh.shuliang_tv3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        vh.product_bn.setText(smalltitle.get(position).toString());
        vh.shuliang_tv4.setText("x"+number.get(position));
        final ViewHold finalVh = vh;
        loader.loadImage(picture.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh.shuliang_iv.setImageBitmap(loadedImage);
            }
        });
        return convertView;
    }
    public class ViewHold {
        ImageView  shuliang_iv;
        TextView shuliang_tv1;
        TextView product_bn;
        TextView shuliang_tv2;
        TextView shuliang_tv3;
        TextView shuliang_tv4;
        TextView jinbi1;

    }


}
