package adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.system.ErrnoException;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.ShopfinishActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu;
import bean.gouwu1;
import bean.zhantingbean;
import utils.DBHelper2;
import utils.FileUtils;
import utils.FileUtils3;
import utils.FileUtils4;
import utils.FileUtils7;
import utils.FileUtils8;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class gouwulistadapter1 extends BaseAdapter {
  /*  private  ArrayList<String> jihe2=new ArrayList<>();
    private DBHelper2 helper2;
    private RequestQueue queue;
    private Handler handle=new Handler();*/
  //定义hashMap 用来存放之前创建的每一项item
  HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    private ShopfinishActivity  activity;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private List<gouwu> data=new ArrayList<>();
    private Context context;
    private ArrayList<String> finish=new ArrayList<>();
    private  ArrayList<String> finish1=new ArrayList<>();
    private  ArrayList<String> finish2=new ArrayList<>();
    private ArrayList<String> stock=new ArrayList<>();
    private ArrayList<String> status=new ArrayList<>();
    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    public  gouwulistadapter1(ShopfinishActivity context, ImageLoader loader,List<gouwu> data,ArrayList<String> stock,ArrayList<String> status){
        super();
        this.activity=context;
        this.data=data;
        this.context=context;
        this.loader=loader;
        this.stock=stock;
        this.status=status;
    }
    public List<gouwu> getList() {
        return data;
    }
    public void setList(List<gouwu> gou) {
        this.data = gou;
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
        ViewHold vh = null;
       /* //Item的位置
        final int listPosition = position;*/
        if(lmap.get(position)==null){
            convertView=View.inflate(context, R.layout.activity_gouwulist1,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            lmap.put(position,convertView);
            vh.gouwu_iv1= (ImageView) convertView.findViewById(R.id.gouwu_iv1);
            vh.left_button= (Button) convertView.findViewById(R.id.left_button);
            vh.right_button= (Button) convertView.findViewById(R.id.right_button);
            vh.edit= (EditText) convertView.findViewById(R.id.edit);
            vh.cbox= (CheckBox) convertView.findViewById(R.id.cbox);
            vh.stock_tv= (TextView) convertView.findViewById(R.id.stock_tv);
            vh.sss= (TextView) convertView.findViewById(R.id.sss);
            vh.sss1= (TextView) convertView.findViewById(R.id.sss1);
        }else {
            convertView = lmap.get(position);
            vh= (ViewHold) convertView.getTag();
        }
       /* if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_gouwulist1,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.gouwu_iv1= (ImageView) convertView.findViewById(R.id.gouwu_iv1);
            vh.left_button= (Button) convertView.findViewById(R.id.left_button);
            vh.right_button= (Button) convertView.findViewById(R.id.right_button);
            vh.edit= (EditText) convertView.findViewById(R.id.edit);
            vh.cbox= (CheckBox) convertView.findViewById(R.id.cbox);
            vh.stock_tv= (TextView) convertView.findViewById(R.id.stock_tv);
            vh.sss= (TextView) convertView.findViewById(R.id.sss);
            vh.sss1= (TextView) convertView.findViewById(R.id.sss1);
        }else {
            vh= (ViewHold) convertView.getTag();
        }*/
        if(stock.size()!=0){
            vh.stock_tv.setText("还剩"+stock.get(position)+"件");
            final gouwu dataBean3 = data.get(position);
            final ViewHold finalVh1 = vh;
            vh.edit.setText(data.get(position).getNum().toString());
            final ViewHold finalVh18 = vh;
            final ViewHold finalVh19 = vh;
            loader.loadImage(data.get(position).getPicture(),new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    finalVh1.gouwu_iv1.setImageBitmap(loadedImage);


                }
            });
            if(status.get(position).equals("2")){
                vh.sss.setVisibility(View.VISIBLE);
                vh.sss1.setVisibility(View.VISIBLE);
                vh.sss1.setBackgroundResource(R.drawable.toumingyuan);
            }else {
                vh.sss.setVisibility(View.INVISIBLE);
                vh.sss1.setVisibility(View.INVISIBLE);
            }
            vh.cbox.setChecked(dataBean3.isChecked());
            vh.cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                    if(!isChecked){
                        selectConut();
                        dataBean3.setChecked(isChecked);
                        notifyDataSetChanged();
                        activity.allselect.setChecked(false);
                        for (int i = 0; i <data.size() ; i++) {
                            if(data.get(i).isChecked()){
                                finish.add(data.get(i).getProductid().toString());
                                Log.i("gsfref", "onCheckedChanged: "+finish);
                            }
                            String str=finish.toString();
                            int len=str.length()-1;
                            String regex="\\s*,\\s*";
                            String ids2= str.substring(1,len).replaceAll(regex,",");
                            FileUtils7  ff=new FileUtils7();
                            ff.saveDataToFile(context,ids2);

                        }
                    }

                    dataBean3.setChecked(isChecked);
                    notifyDataSetChanged();
                    if(isChecked){
                        selectConut();
                        for (int i = 0; i <data.size() ; i++) {
                            if(data.get(i).isChecked()){
                                finish1.add(data.get(i).getProductid().toString());

                            }
                            String str=finish1.toString();
                            int len=str.length()-1;
                            String regex="\\s*,\\s*";
                            String ids2= str.substring(1,len).replaceAll(regex,",");
                            FileUtils7  ff=new FileUtils7();
                            ff.saveDataToFile(context,ids2);
                        }
                    }
                    if(selectConut==data.size()){
                        activity.allselect.setChecked(true);
                        for (int i = 0; i <data.size() ; i++) {
                            finish2.add(data.get(i).getProductid().toString()) ;
                        }
                        String str=finish2.toString();
                        int len=str.length()-1;
                        String regex="\\s*,\\s*";
                        String ids2= str.substring(1,len).replaceAll(regex,",");
                        FileUtils7  ff1=new FileUtils7();
                        ff1.saveDataToFile(context,ids2);
                    }
                    if(!isChecked){
                        activity.allselect.setChecked(false);
                    }

                    dataBean3.setChecked(isChecked);
                    notifyDataSetChanged();
                    if(isChecked){
                        selectConut();
                        if(selectConut==data.size()){
                            activity.allselect.setChecked(true);
                        }
                    }
                    if(changeState()){
                        activity. shopfinsh_delete.setEnabled(true);
                        activity. shopfinsh_delete.setBackgroundColor(Color.parseColor("#ff3933"));
                    }else{
                        activity. shopfinsh_delete.setEnabled(false);
                        activity. shopfinsh_delete.setBackgroundColor(Color.parseColor("#B1B1B1"));
                    }
                }
            });
        }







        final ViewHold finalVh = vh;
        final ViewHold finalVh2 = vh;
        final ViewHold finalVh3 = vh;
        final ViewHold finalVh6 = vh;

        final ViewHold finalVh7 = vh;
        final ViewHold finalVh8 = vh;
        final ViewHold finalVh9 = vh;
        final ViewHold finalVh10 = vh;


        final ViewHold finalVh12 = vh;
        final ViewHold finalVh13 = vh;
        final ViewHold finalVh14 = vh;
        final ViewHold finalVh15 = vh;
        final ViewHold finalVh16 = vh;
        vh.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                List<gouwu> deleteList7= new ArrayList<gouwu>();
                ArrayList<String> list1=new ArrayList<String>();
                for (gouwu go : data) {
                    if (go.isChecked()) {
                        for (int i = 0; i <data.size() ; i++) {
                            if(position==i){
                                Log.i("sffe", "afterTextChanged: "+position);
                            }
                        }
                    }
                }
                String ww=finalVh13.edit.getText().toString();
                list1.add(ww);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ss=finalVh7.edit.getText().toString();
              if(!TextUtils.isEmpty(ss)){
                    try{
                        int zhi=Integer.parseInt(ss.toString());
                        int cunchi=Integer.parseInt(stock.get(position));
                        int num2=Integer.valueOf(ss.trim());
                        if(num2<1){
                            finalVh8.edit.setText(Integer.toString(1));
                           // Toast.makeText(context, "请输入1-9999之间的数", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception num2){

                    }



                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String shuru= finalVh16.edit.getText().toString();
                if(shuru.length()!=0){
                    int zhi=Integer.parseInt(s.toString());
                    int cunchi=Integer.parseInt(stock.get(position));
                    if(zhi!=1&&zhi>cunchi){
                        Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT).show();
                        finalVh8.edit.setText(stock.get(position));
                        hashMap.put(position,s.toString());
                    }
                    for (int i = 0; i <data.size() ; i++) {
                        if(position==i){
                            data.get(i).setNum(s.toString());

                        }
                    }
                    post();
                    // finalVh15.edit.setText(s);
                    String ww=finalVh13.edit.getText().toString();
                }

            }

        });
        final ViewHold finalVh17 = vh;
        vh.left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           int num=Integer.valueOf(finalVh2.edit.getText().toString()) ;

                if(num!=1&&num>=1){
                    num--;
                }else {
                    finalVh3.edit.setText(Integer.toString(1));
                }
              finalVh3.edit.setText(Integer.toString(num));
            }
        });
        final ViewHold finalVh4 = vh;
        final ViewHold finalVh5 = vh;
        final ViewHold finalVh11 = vh;
        vh.right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1=Integer.valueOf(finalVh4.edit.getText().toString());
                int cunchi=Integer.parseInt(stock.get(position));
                if(num1==cunchi){
                    finalVh11.edit.setText(Integer.toString(cunchi));
                }else {
                    num1++;
                    finalVh5.edit.setText(Integer.toString(num1));
                }
            }
        });

        return convertView;
    }

    private void post() {
        List<gouwu> deleteList6= new ArrayList<gouwu>();
        ArrayList<String> list8=new ArrayList<String>();
        deleteList6.clear();
        list8.clear();
        for (gouwu go : data) {

            if (go.isChecked()) {
                list8.add(go.getNum().toString());
                deleteList6.add(go);
            }
        }
        FileUtils8  file=new FileUtils8();
        file. saveDataToFile(context,list8.toString());
    }

    public class ViewHold {
        ImageView gouwu_iv1;
        Button left_button;
        Button right_button;
        EditText edit;
        CheckBox cbox;
        TextView stock_tv;
        TextView sss1;
        TextView sss;
    }
    public  int selectConut=0;
    public void selectConut() {
        selectConut=0;
        for (gouwu go : data) {
            if(go.isChecked()){
                selectConut++;
            }
        }
    }
    public boolean changeState(){
        for(gouwu go:data){
            if(go.isChecked()){
                return true;
            }
        }
        return false;
    }

}
