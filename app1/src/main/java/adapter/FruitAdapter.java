package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Shoucang1Activity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.gouwu;
import bean.gouwu1;
import utils.FileUtils;

/**
 * Created by 胡海波 on 2016/9/12.
 */
public class  FruitAdapter  extends BaseAdapter {
    private Shoucang1Activity activity;
    private Context mContext;
    private ImageLoader loader;
    private LayoutInflater mInflater;
    private List<gouwu1> data=new ArrayList<>();
    private ArrayList<String> loginsn=new ArrayList<>();
   public  FruitAdapter (Shoucang1Activity dContext,ImageLoader loader,List<gouwu1> data,ArrayList<String> loginsn){
       super();
       this.activity=dContext;
       this.data=data;
       Log.i("caole1", "FruitAdapter: "+data.toString());
       this.loader=loader;
       this.loginsn=loginsn;


   }
    public List<gouwu1> getList() {
        return data;
    }
    public void setList(List<gouwu1> gou1) {
        this.data = gou1;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        mInflater = LayoutInflater.from(activity);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.activity_shoucanglist,null);
            holder=new ViewHolder();
            holder.cb= (CheckBox) convertView.findViewById(R.id.cb);
            holder.gouwu_iv= (ImageView) convertView.findViewById(R.id.gouwu_iv);
            holder.gouwu_tv= (TextView) convertView.findViewById(R.id.gouwu_tv);
            holder.gouwu_tv1= (TextView) convertView.findViewById(R.id.gouwu_tv1);
            holder.gouwu_tv2= (TextView) convertView.findViewById(R.id.gouwu_tv2);
            holder.product_bn= (TextView) convertView.findViewById(R.id.product_bn);
            holder.sss= (TextView) convertView.findViewById(R.id.sss);
            holder.sss1= (TextView) convertView.findViewById(R.id.sss1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(loginsn.get(position).equals("2")){
            holder.gouwu_tv.setTextColor(Color.parseColor("#ACACAC"));
            holder.gouwu_tv1.setTextColor(Color.parseColor("#ACACAC"));
            holder.sss.setVisibility(View.VISIBLE);
            holder.sss1.setBackgroundResource(R.drawable.toumingyuan);
        }else {
            holder.sss.setVisibility(View.INVISIBLE);
            holder.sss1.setVisibility(View.INVISIBLE);

        }
        final gouwu1 dataBean1 = data.get(position);
        if(dataBean1!=null){
            holder.gouwu_tv.setText(data.get(position).getBigtitle());
            if(data.get(position).getNowprice().equals("0")||data.get(position).getNowprice().equals("null")){
                holder.gouwu_tv1.setText("¥"+data.get(position).getPreviousprice());
                holder.gouwu_tv2.setVisibility(View.GONE);
            }else if(data.get(position).getPreviousprice().equals("0")||data.get(position).getPreviousprice().equals("null")){
                holder.gouwu_tv1.setText("¥"+data.get(position).getNowprice());
                holder.gouwu_tv2.setVisibility(View.GONE);
            }else if(data.get(position).getNowprice().equals(data.get(position).getPreviousprice())){
                holder.gouwu_tv1.setText("¥"+data.get(position).getNowprice());
                holder.gouwu_tv2.setVisibility(View.GONE);
            }else {
                holder.gouwu_tv1.setText("¥"+data.get(position).getNowprice());
                holder.gouwu_tv2.setText("¥"+data.get(position).getPreviousprice());
                holder.gouwu_tv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            holder.product_bn.setText("产品编号:"+data.get(position).getProductbn());
        }


        final ViewHolder finalHolder = holder;
        loader.loadImage(data.get(position).getPicture(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalHolder.gouwu_iv.setImageBitmap(loadedImage);
            }
        });
            holder.cb.setChecked(dataBean1.isChecked());
       holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
               if(!isChecked){
                  activity.allselect1.setChecked(false);
               }
               dataBean1.setChecked(isChecked);
               notifyDataSetChanged();
               if(isChecked){
                   selectConut();
                   if(selectConut==data.size()){
                       activity.allselect1.setChecked(true);
                   }
               }
               if(changeState()){
                   activity.shoucangDelete.setEnabled(true);
                   activity.shoucangDelete.setBackgroundColor(Color.parseColor("#fbd23a"));
                   activity.shoucangDelete.setTextColor(Color.parseColor("#ffffff"));
               }else{
                   activity.shoucangDelete.setEnabled(false);
                   activity.shoucangDelete.setBackgroundColor(Color.parseColor("#ffffff"));
                   activity.shoucangDelete.setBackgroundResource(R.drawable.border);
                   activity.shoucangDelete.setTextColor(Color.parseColor("#222222"));
                   activity.shoucangDelete.setText("删除");
               }
           }
       });

        return convertView;
    }



    class ViewHolder {

        ImageView gouwu_iv;
        TextView gouwu_tv;
        TextView gouwu_tv1;
        TextView gouwu_tv2;
        TextView sss;
        TextView sss1;
        TextView product_bn;
        CheckBox cb;

    }
    public  int selectConut=0;
    public void selectConut() {
        selectConut=0;
        for (gouwu1 go : data) {
            if(go.isChecked()){
                selectConut++;
            }
        }
    }
    public boolean changeState(){
        for(gouwu1 go:data){
            if(go.isChecked()){
                return true;
            }
        }
        return false;
    }

}
