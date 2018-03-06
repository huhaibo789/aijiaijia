package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import bean.huasabean;
import bean.zhantingbean;

/**
 * Created by 胡海波 on 2016/9/27.
 */
public class youhuiadapter extends BaseAdapter {


    private LayoutInflater inflater;
    private ImageLoader loader;
    private ArrayList<String> data=new ArrayList<>();
    private ArrayList<String> data1=new ArrayList<>();
    private ArrayList<String> data2=new ArrayList<>();
    private ArrayList<String> data3=new ArrayList<>();
    private ArrayList<String> data4=new ArrayList<>();
    private Context context;
    public  youhuiadapter(Context context, ImageLoader loader,ArrayList<String> data,ArrayList<String> data1,ArrayList<String> data2,ArrayList<String>
                          data3,ArrayList<String> data4){
        super();
        this.context=context;
        this.loader=loader;
        this.data=data;
        this.data1=data1;
        this.data2=data2;
        this.data3=data3;
        this.data4=data4;

    }
  /*  public  void update(ArrayList<String> data){
        this.data=data;

    }
    public  void update1(ArrayList<String> data){
        this.data1=data;

    }
    public  void update2(ArrayList<String> data){
        this.data2=data;

    }
    public  void update3(ArrayList<String> data){
        this.data3=data;

    }
    public void update4(ArrayList<String> data){
        this.data4=data;
    }
*/
    @Override
    public int getCount() {
        Log.i("laile4", "getView: "+"4");
        return data.size();
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
            convertView=View.inflate(context, R.layout.activity_youhui,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.youhui_tv2= (TextView) convertView.findViewById(R.id.youhui_tv2);
            vh.youhui_tv= (TextView) convertView.findViewById(R.id.youhui_tv);
            vh.youhui_tv1= (TextView) convertView.findViewById(R.id.youhui_tv1);
            vh.youhui_iv= (ImageView) convertView.findViewById(R.id.youhui_iv);
            Log.i("laile1", "getView: "+"1");
        }else {
            vh= (ViewHold) convertView.getTag();
            Log.i("laile2", "getView: "+"2");
        }
        Log.i("laile", "getView: "+"sdasfa");
        vh.youhui_tv.setText(data1.get(position));
        vh.youhui_tv1.setText(data2.get(position)+"至"+data3.get(position));
        vh.youhui_tv2.setText("代金券的使用规则");
        vh.youhui_tv2.setOnClickListener(new View.OnClickListener() {
            String ziliao=data4.get(position);
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(context, YouhuiuserActivity.class);
                intent.putExtra("youhui", ziliao);
                context.startActivity(intent);
            }
        });
        final ViewHold finalVh = vh;
        loader.loadImage(data.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh.youhui_iv.setImageBitmap(loadedImage);
            }
        });
        return convertView;
    }
    public class ViewHold {
        ImageView youhui_iv;
        TextView youhui_tv;
        TextView youhui_tv1;
        TextView youhui_tv2;
    }

}
