package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myyushi.EnableyouhuiquanActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.ShouhuoActivity;
import com.example.administrator.myyushi.h5BaseActivity;
import com.example.administrator.myyushi.h5detailsActivity;
import com.example.administrator.myyushi.payincashActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import bean.Food;
import bean.gouwu2;
import bean.gouwu3;
import bean.h5gouwu;
import utils.FileUtils14;
import utils.FileUtils15;
import utils.FileUtils17;
import utils.FileUtils19;
import utils.FileUtils2;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.FileUtils22;
import utils.FileUtils44;
import utils.Fileutils18;

/**
 * Created by 胡海波 on 2016/9/19.
 */
public class h5dingdanadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  String gdd2;
    private h5BaseActivity activity;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private Context mContext;
    private List<Food> food1 = new ArrayList<>();
    private List<gouwu2> goudd = new ArrayList<>();
    private  List<gouwu3> goud=new ArrayList<>();
    private List<h5gouwu> h5gou=new ArrayList<>();
    private ArrayList<String> ss=new ArrayList<>();
    private ArrayList<String> ss1=new ArrayList<>();
    private  ArrayList<String> ss2=new ArrayList<>();
    private ArrayList<String> data=new ArrayList<>();
    private ArrayList<String> h5picture=new ArrayList<>();
    private   String us_address;
    private   String us_name;
    private  String us_phone;
    private    String us_pscsds;
    String confirminfomation;
    int shuliang;
    String result="1";
    boolean flag;
    boolean wupin=true;
    boolean bizohi;
    String require; //判断优惠券
    private View v_city;
    boolean state=false;
    private View v_city2;
    PopupWindow pop;
    String isname="个人";
    private int screenWidth;
    private int screenHeight;
    private boolean isShow;
    public h5dingdanadapter(h5BaseActivity context, ImageLoader loader,String confirminfomation,String us_address,String us_name,String us_phone,String us_pscsds,int shuliang, ArrayList<String> h5picture){
        this.mContext=context;
        inflater=LayoutInflater.from(context);
        this.loader=loader;
        this.activity=context;
        this.us_address=us_address;
        this.h5picture=h5picture;
        this.us_name=us_name;
        this.us_phone=us_phone;
        this.us_pscsds=us_pscsds;
        this.shuliang=shuliang;
        this.confirminfomation=confirminfomation;
    }
    public  void upgengxin(List<gouwu2> goudd){
        this.goudd=goudd;
        Log.i("aini", "upgengxin: "+goudd.toString());
    }
    public void upgenxin1(List<Food> food){
        this.food1=food;

    }
    public void showmessage1(boolean isShow){
        this.isShow=isShow;
        notifyDataSetChanged();


    }
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
      activity.getWindow().setAttributes(lp);
    }
    public void dissmiss() {
        pop.dismiss();
    }
    public  void updatexin2(List<gouwu3> goud){
        this.goud=goud;
    }
    public  void updatexin3(ArrayList<String> ss){
        this.ss=ss;
    }
    public  void updatexin4(ArrayList<String> ss1){
        this.ss1=ss1;
    }
    public  void updatexin5(ArrayList<String> ss2){
        this.ss2=ss2;
    }
    public void updatexin6(List<h5gouwu> h5gou ){
        this.h5gou=h5gou;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new DmyHolderType0(inflater.inflate(R.layout.activity_queren,parent,false));
                break;
            case 1:
                holder=new cDmyHolderType1(inflater.inflate(R.layout.activity_confirm,parent,false));
                break;
               /* if(goud.size()==0&&h5gou.size()==0){
                    holder=new cDmyHolderType1(inflater.inflate(R.layout.activity_queren1,parent,false));
                    break;
                }else {
                    holder=new cDmyHolderType1(inflater.inflate(R.layout.activity_queren7,parent,false));
                    break;
                }*/

            case 2:
                holder=new DmyHolderType2(inflater.inflate(R.layout.activity_queren2,parent,false));
                break;

            case 3:
                holder=new DmyHolderType3(inflater.inflate(R.layout.activity_queren3,parent,false));
                break;
            case 4:
                holder=new DmyHolderType4(inflater.inflate(R.layout.activity_queren5,parent,false));
                break;
            case 5:
                holder=new DmyHolderType5(inflater.inflate(R.layout.activity_queren6,parent,false));
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return  0;
        }else if(position==1){
            return  1;
        }else if(position==2){
            return 2;
        }else if(position==3){
            return  3;
        }else if(position==4){
            return 4;
        }else {
            return 5;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                DmyHolderType0  holder2= (DmyHolderType0) holder;
                if(goudd.size()!=0){
                    try {
                        Number num4= NumberFormat.getInstance().parse(goudd.get(0).getNowprice());
                        float num1=num4.floatValue();
                        DecimalFormat df = new DecimalFormat("0.00");
                        String gdd3=df.format(num1);
                        activity.zhifu_jiage.setText(gdd3);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
               Log.i("heihiesa", "onBindViewHolder: "+us_name);
                holder2.Dingdanshutv.setText(us_name);
                holder2.Dingdanshutv1.setText(us_phone);
                holder2.Dingdanshutv2.setText(us_pscsds);
                holder2.Dingdanshutv3.setText(us_address);

                if(us_name==null){
                    holder2.Dingdanshutv2.setText("您还没有收货地址，点击添加");
                    result="2";
                }
             /*   if(us_name==null){
                    holder2.Dingdanshutv2.setText("您还没有收货地址，点击添加");
                    result="2";
                }*/
                holder2.shouhuo_return.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3=new Intent(activity, ShouhuoActivity.class);
                        Log.i("heihusa", "onClick: "+result);
                        if(result.equals("2")){
                            intent3.putExtra("sure","2");
                            activity.startActivity(intent3);
                            activity.finish();
                        }else {
                            intent3.putExtra("sure","1");
                            activity.startActivityForResult(intent3,7);
                            //activity.startActivity(intent3);
                        }


                    }
                });
                break;
            case  1:
                final cDmyHolderType1 holder3= (cDmyHolderType1) holder;
                JSONObject jsonobject=null;
                try {
                   jsonobject = new JSONObject(confirminfomation);
                    JSONObject responseobject = jsonobject.getJSONObject("responseJson");
                    JSONArray jsonArry = responseobject.getJSONArray("product_list");
                    holder3.all_num.setText("共"+jsonArry.length()+"件");
                    if(wupin){
                        for (int i = 0; i <jsonArry.length() ; i++) {
                            final JSONObject object1 = jsonArry.getJSONObject(i);
                            v_city2 = LayoutInflater.from(activity).inflate(R.layout.activity_addconfirm, null);
                            TextView  gouwu_tv= (TextView) v_city2.findViewById(R.id.gouwu_tv);
                            TextView  tv_intro= (TextView) v_city2.findViewById(R.id.tv_intro);
                            TextView  standard= (TextView) v_city2.findViewById(R.id.standard);
                            TextView  tv_price= (TextView) v_city2.findViewById(R.id.tv_price);
                            TextView  tv_price1=(TextView) v_city2.findViewById(R.id.tv_price1);
                            TextView servicetip=(TextView) v_city2.findViewById(R.id.servicetip);
                            TextView  tv_num=(TextView) v_city2.findViewById(R.id.tv_num);
                            TextView service_tv= (TextView) v_city2.findViewById(R.id.service_tv);
                            TextView show_discount= (TextView) v_city2.findViewById(R.id.show_discount);
                            final ImageView iv_adapter_list_pic= (ImageView) v_city2.findViewById(R.id.iv_adapter_list_pic);
                            gouwu_tv.setText(object1.getString("product_name"));
                            tv_intro.setText("型号:"+object1.getString("product_bn"));
                            standard.setText(object1.getString("skutext"));
                            String skuprice=object1.getString("skuprice");
                            String skucom=object1.getString("stringtipscom");
                            String product_tipshow_discount=object1.getString("stringtipdiscountorsave");
                            final String usid=object1.getString("id");
                            String skusellprice=object1.getString("skusellprice");
                            if(skucom!=null&&!skucom.equals("null")){
                                service_tv.setVisibility(View.VISIBLE);
                                service_tv.setText("("+skucom+")");
                            }else {
                                service_tv.setVisibility(View.GONE);
                            }
                            if(product_tipshow_discount!=null&&!product_tipshow_discount.equals("null")){
                                show_discount.setText(product_tipshow_discount);
                            }
                            if(skusellprice.equals("0")||skusellprice.equals("null")){
                                tv_price.setText("¥"+skuprice);
                            }else if(skuprice.equals("0")||skuprice.equals("null")){
                                tv_price.setText("¥"+skusellprice);
                            }else {
                                tv_price.setText("¥"+skusellprice);
                            }
                            Log.i("sakseew", "onBindViewHolder: "+object1.getString("num"));
                            tv_num.setText(object1.getString("num"));
                            loader.loadImage(object1.getString("product_pic"),new SimpleImageLoadingListener(){
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    iv_adapter_list_pic.setImageBitmap(loadedImage);
                                }
                            });
                            holder3.confirmbase_ly.addView(v_city2);
                            v_city2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(activity, h5detailsActivity.class);
                                    intent.putExtra("uid", usid);
                                    activity.startActivity(intent);
                                }
                            });
                        }
                        wupin=false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    break;


              /*  Log.i("12dwsw", "onBindViewHolder: "+goudd.toString());
                Log.i("12dws1w", "onBindViewHolder: "+goud.toString());
                if(goud.size()==0&&h5gou.size()==0){
                    holder3.shuliang_tv.setText("共1件");
                    Log.i("12dww", "onBindViewHolder: "+goudd.toString());
                    if(goudd.size()!=0){
                        if(goudd.get(0).getNowprice().equals("0")||goudd.get(0).getNowprice().equals("null")){
                            holder3.shuliang_tv1.setText(goudd.get(0).getBigtitle());
                            holder3.shuliang_tv2.setText("¥"+goudd.get(0).getPreviousprice());
                            holder3.shuliang_tv3.setVisibility(View.INVISIBLE);
                        }else if(goudd.get(0).getPreviousprice().equals("0")||goudd.get(0).getPreviousprice().equals("null")){
                            holder3.shuliang_tv1.setText(goudd.get(0).getBigtitle());
                            holder3.shuliang_tv2.setText("¥"+goudd.get(0).getNowprice());
                            holder3.shuliang_tv3.setVisibility(View.INVISIBLE);
                        }else if(goudd.get(0).getNowprice().equals(goudd.get(0).getPreviousprice())){
                            holder3.shuliang_tv1.setText(goudd.get(0).getBigtitle());
                            holder3.shuliang_tv2.setText("¥"+goudd.get(0).getNowprice());
                            holder3.shuliang_tv3.setVisibility(View.INVISIBLE);
                        }else {
                            holder3.shuliang_tv3.setVisibility(View.VISIBLE);
                            holder3.shuliang_tv1.setText(goudd.get(0).getBigtitle());
                            holder3.shuliang_tv2.setText("¥"+goudd.get(0).getNowprice());
                            holder3.shuliang_tv3.setText(goudd.get(0).getPreviousprice());
                            holder3.shuliang_tv3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                        }
                        loader.loadImage(goudd.get(0).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.shuliang_iv.setImageBitmap(loadedImage);
                            }
                        });
                    }

                    break;
                }else if(goudd.size()==0&&h5gou.size()==0){
                    Log.i("12de", "onBindViewHolder: "+goud.toString());

                    if(goud.size()==0){
                        return;
                    }else if(goud.size()==1){
                        loader.loadImage(goud.get(0).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        holder3.gouwu2.setVisibility(View.INVISIBLE);
                        holder3.gouwu3.setVisibility(View.INVISIBLE);
                        holder3.gouwu4.setVisibility(View.INVISIBLE);
                    }else if(goud.size()==2){
                        loader.loadImage(goud.get(0).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(goud.get(1).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu2.setImageBitmap(loadedImage);
                            }
                        });
                        holder3.gouwu3.setVisibility(View.INVISIBLE);
                        holder3.gouwu4.setVisibility(View.INVISIBLE);
                    }else if(goud.size()==3){
                        loader.loadImage(goud.get(0).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(goud.get(1).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu2.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(goud.get(2).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu3.setImageBitmap(loadedImage);
                            }
                        });
                        holder3.gouwu4.setVisibility(View.INVISIBLE);
                    }else{
                        loader.loadImage(goud.get(0).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(goud.get(1).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu2.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(goud.get(2).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu3.setImageBitmap(loadedImage);

                            }
                        });
                        loader.loadImage(goud.get(3).getPicture(),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu4.setImageBitmap(loadedImage);
                            }
                        });





                    }
                    Log.i("11", "onBindViewHolder: "+shuliang);
                    holder3.zhifu_tv2.setText("共"+shuliang+"件");
                }else {
                    Log.i("hengni", "onBindViewHolder: "+h5gou.size());
                    Log.i("hengni", "onBindViewHolder: "+h5picture.size());
                    Log.i("hengni1", "onBindViewHolder: "+h5picture.toString());
                    if(h5gou.size()==0){
                        return;
                    }else if(h5gou.size()==1){
                        loader.loadImage(h5picture.get(0),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        holder3.gouwu2.setVisibility(View.INVISIBLE);
                        holder3.gouwu3.setVisibility(View.INVISIBLE);
                        holder3.gouwu4.setVisibility(View.INVISIBLE);
                        holder3.zhifu_tv2.setText("共"+h5gou.size()+"件");
                    }else if(h5gou.size()==2){
                        loader.loadImage(h5picture.get(0),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(h5picture.get(1),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu2.setImageBitmap(loadedImage);
                            }
                        });
                        holder3.zhifu_tv2.setText("共"+h5gou.size()+"件");
                        holder3.gouwu3.setVisibility(View.INVISIBLE);
                        holder3.gouwu4.setVisibility(View.INVISIBLE);
                    }else if(h5picture.size()==3){
                        loader.loadImage(h5picture.get(0),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(h5picture.get(1),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu2.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(h5picture.get(2),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu3.setImageBitmap(loadedImage);
                            }
                        });
                        holder3.zhifu_tv2.setText("共"+h5gou.size()+"件");
                        holder3.gouwu4.setVisibility(View.INVISIBLE);
                    }else{
                        loader.loadImage(h5picture.get(0),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(h5picture.get(1),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu2.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(h5picture.get(2),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu3.setImageBitmap(loadedImage);

                            }
                        });
                        loader.loadImage(h5picture.get(3),new SimpleImageLoadingListener(){
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder3.gouwu4.setImageBitmap(loadedImage);
                            }
                        });

                        holder3.zhifu_tv2.setText("共"+h5gou.size()+"件");



                    }
                }*/


            case 2:

                final DmyHolderType2   dmholedr2= (DmyHolderType2) holder;
                v_city = LayoutInflater.from(activity).inflate(R.layout.activity_showdingdan, null);
                final ImageView personchose_iv= (ImageView) v_city.findViewById(R.id.personchose_iv);
                final ImageView entrisnochose_iv= (ImageView) v_city.findViewById(R.id.entrisnochose_iv);
                final EditText company_ein= (EditText) v_city.findViewById(R.id.company_ein);
                final EditText person_ein= (EditText) v_city.findViewById(R.id.person_ein);
                final EditText company_ein1= (EditText) v_city.findViewById(R.id.company_ein1);
                LinearLayout pop_bottom= (LinearLayout) v_city.findViewById(R.id.pop_bottom);
                initScreenWidth();
                pop = new PopupWindow(v_city, screenWidth,screenHeight/2 , true);
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1f);
                    }
                });
                personchose_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            person_ein.setVisibility(View.VISIBLE);
                            personchose_iv.setImageResource(R.mipmap.chosi);
                            entrisnochose_iv.setImageResource(R.mipmap.chose);
                            company_ein.setVisibility(View.INVISIBLE);
                            company_ein1.setVisibility(View.INVISIBLE);
                            isname="个人";
                    }
                });
                entrisnochose_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        person_ein.setVisibility(View.INVISIBLE);
                        personchose_iv.setImageResource(R.mipmap.chose);
                        entrisnochose_iv.setImageResource(R.mipmap.chosi);
                        company_ein.setVisibility(View.VISIBLE);
                        company_ein1.setVisibility(View.VISIBLE);
                        isname="企业";
                    }
                });
                pop_bottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isname.equals("个人")){
                            String shuihao=person_ein.getText().toString().trim(); //个人税号
                            if(shuihao!=null&&shuihao.equals("")){
                                dmholedr2.geren_tv.setText("个人");
                                FileUtils20 kk=new FileUtils20();
                                kk. saveDataToFile(mContext,"个人")  ;
                            }else {
                                dmholedr2.geren_tv.setText("个人"+shuihao);
                                FileUtils20 kk=new FileUtils20();
                                kk. saveDataToFile(mContext,"个人"+shuihao)  ;
                            }

                            dissmiss();
                        }else if(isname.equals("企业")){
                            String shuihao=company_ein.getText().toString().trim(); //企业税号
                            String companyname=company_ein1.getText().toString().trim(); //公司名字
                            if(shuihao!=null&&!shuihao.equals("")&&!companyname.equals("")){
                                Log.i("zamenjie", "onClick: "+"1");
                                dmholedr2.geren_tv.setText("企业:"+companyname+shuihao);
                                FileUtils20 kk=new FileUtils20();
                                kk. saveDataToFile(mContext,"企业:"+companyname+shuihao)  ;
                                dissmiss();
                            }else if(shuihao!=null&&shuihao.equals("")&&!companyname.equals(""))  {
                                Log.i("zamenjie1", "onClick: "+"1");
                                Toast.makeText(activity, "请填写公司名称", Toast.LENGTH_SHORT).show();
                            }else if(shuihao!=null&&!shuihao.equals("")&&companyname.equals("")){
                                Log.i("zamenjie2", "onClick: "+"1");
                                Toast.makeText(activity, "请填写税号", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.i("zamenjie3", "onClick: "+"1");
                                Toast.makeText(activity, "公司号和税号必须填写", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dmholedr2.geren_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setBackgroundAlpha(0.5f);
                        pop.setBackgroundDrawable(new BitmapDrawable());
                        pop.setOutsideTouchable(true);
                        pop.setFocusable(true);
                        pop.showAtLocation(v_city, Gravity.BOTTOM, 0, 0);
                    }
                });
                dmholedr2.shuru_et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String ww1=dmholedr2.shuru_et.getText().toString();
                        FileUtils21  kk1=new FileUtils21();
                        kk1. saveDataToFile(mContext,ww1);
                    }
                });
              /*  dmholedr2.geren_tv.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String ww=dmholedr2.geren_tv.getText().toString();

                        FileUtils20 kk=new FileUtils20();
                        kk. saveDataToFile(mContext,ww)  ;

                    }
                });*/



                break;
            case 3:
                final DmyHolderType3 holder5= (DmyHolderType3) holder;
                holder5.zhifu_baioti.setText("支付方式");
                holder5.zhifu_tv.setText("支付宝");
                if(!state){
                    FileUtils22 filepay=new FileUtils22();
                    filepay.saveDataToFile(mContext,"1");
                }else {
                    FileUtils22 file=new FileUtils22();
                    file.saveDataToFile(mContext,"3");
                }
                holder5.zhifu_ly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder5.weichartcrash_ly.setBackgroundResource(R.drawable.border1);
                        holder5.yinglian_ly.setBackgroundResource(R.drawable.border1);
                        holder5.zhifu_ly.setBackgroundResource(R.drawable.border_zhifu);
                        holder5.payincrash_ly.setBackgroundResource(R.drawable.border1);
                        FileUtils22 filepay=new FileUtils22();
                        filepay.saveDataToFile(mContext,"1");
                        state=false;
                    }
                });
                holder5.weichartcrash_ly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder5.weichartcrash_ly.setBackgroundResource(R.drawable.border_zhifu);
                        holder5.yinglian_ly.setBackgroundResource(R.drawable.border1);
                        holder5.zhifu_ly.setBackgroundResource(R.drawable.border1);
                        holder5.payincrash_ly.setBackgroundResource(R.drawable.border1);
                        Toast.makeText(activity, "待开发", Toast.LENGTH_SHORT).show();
                       /* FileUtils22 filepay=new FileUtils22();
                        filepay.saveDataToFile(mContext,"1");*/
                       // state=false;
                    }
                });
                holder5.payincrash_ly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder5.weichartcrash_ly.setBackgroundResource(R.drawable.border1);
                        holder5.yinglian_ly.setBackgroundResource(R.drawable.border1);
                        holder5.zhifu_ly.setBackgroundResource(R.drawable.border1);
                        holder5.payincrash_ly.setBackgroundResource(R.drawable.border_zhifu);
                        Intent intent=new Intent(activity,payincashActivity.class);
                        activity.startActivity(intent);


                    }
                });
                holder5.yinglian_ly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder5.weichartcrash_ly.setBackgroundResource(R.drawable.border1);
                        holder5.yinglian_ly.setBackgroundResource(R.drawable.border_zhifu);
                        holder5.zhifu_ly.setBackgroundResource(R.drawable.border1);
                        holder5.payincrash_ly.setBackgroundResource(R.drawable.border1);
                        FileUtils22 file=new FileUtils22();
                        file.saveDataToFile(mContext,"3");
                        state=true;

                    }
                });

                break;
            case 4:
                final DmyHolderType4 holder6= (DmyHolderType4) holder;
                if(isShow){
                    holder6.select_chose.setImageResource(R.drawable.turnoff);
                    holder6.sureyouhui_tv.setVisibility(View.INVISIBLE);
                    holder6.right_iv.setVisibility(View.INVISIBLE);
                    FileUtils2 ffe=new FileUtils2();
                    String dd1=ffe.readDataFromFile(activity);  //总价
                    try {
                        Number num4= NumberFormat.getInstance().parse(dd1);
                        float num=num4.floatValue();
                        DecimalFormat df = new DecimalFormat("0.00");
                        String gdd2=df.format(num);
                        holder6.shiji_tv1.setText(gdd2);
                        activity.zhifu_jiage.setText(gdd2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    isShow=false;
                    bizohi=false;
                }
                if(goudd.size()!=0){
                    Fileutils18 rr=new Fileutils18();
                    String ss=rr.readDataFromFile(mContext);
                    FileUtils15 ww=new FileUtils15();
                    final String ww1=ww.readDataFromFile(mContext);
                    FileUtils17 ww2=new FileUtils17();
                    String ww3=ww2.readDataFromFile(mContext);
                    if(ss!=null&&ss.equals("请选择")){
                        holder6.sureyouhui_tv.setText(ss);
                        try {
                            Number num5= NumberFormat.getInstance().parse(ww1);   //积分
                            Number num4= NumberFormat.getInstance().parse(goudd.get(0).getNowprice());
                            Number num6= NumberFormat.getInstance().parse(ww3);//换算比率
                            float num1=num4.floatValue();
                            float num7=num5.floatValue();
                            float num8=num6.floatValue();
                            float num9=num7*num8;
                            float num10=num1-num9;
                            Log.i("heihei", "onBindViewHolder: "+flag);
                            if(flag==false){
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd3=df.format(num1);
                                holder6.zhi_qian.setText(gdd3);
                                Log.i("chasshe", "onBindViewHolder: "+gdd3);
                                activity.zhifu_jiage.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd3);
                            }else {
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd3=df.format(num10);
                                String gdd4=df.format(num1);
                                holder6.zhi_qian.setText(gdd4);
                                activity.zhifu_jiage.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd3);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                        holder6.sureyouhui_tv.setText(ss);
                        FileUtils15 gg=new FileUtils15();
                        final String bb1=gg.readDataFromFile(mContext);
                        FileUtils17 rr1=new FileUtils17();
                        String cc=rr1.readDataFromFile(mContext);

                        try {
                            Number num6= NumberFormat.getInstance().parse(bb1);
                            Number num9= NumberFormat.getInstance().parse(cc);
                            Number num4= NumberFormat.getInstance().parse(goudd.get(0).getNowprice());
                            Number num5= NumberFormat.getInstance().parse(ss);
                            float num1=num5. floatValue();
                            float num2=num4. floatValue();
                            float num7=num6.floatValue();
                            float num10=num9.floatValue();
                            float num8=num7*num10;
                            if(flag==false){
                                if(require!=null&&require.equals("1")){
                                    float num3=num2;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    String gdd3=df.format(num3);
                                    DecimalFormat df1 = new DecimalFormat("0.00");
                                    String gdd4=df1.format(num2);
                                    holder6.zhi_qian.setText(gdd4);
                                    activity.zhifu_jiage.setText(gdd3);
                                    holder6.shiji_tv1.setText(gdd3);
                                }else {
                                    float num3=num2-num1;
                                    int price_jiage=(int)num3;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    String gdd3=df.format(num3);
                                    DecimalFormat df1 = new DecimalFormat("0.00");
                                    String gdd4=df1.format(num2);
                                    holder6.zhi_qian.setText(gdd4);
                                    if(gdd3.equals("0.00")||price_jiage<0){
                                        activity.zhifu_jiage.setText("0.01");  //用户实际支付0元时显示0.01
                                        holder6.shiji_tv1.setText("0.01");
                                    }else {
                                        activity.zhifu_jiage.setText(gdd3);
                                        holder6.shiji_tv1.setText(gdd3);
                                    }

                                }



                            }else {
                                float num3=num2-num1-num8;
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd3=df.format(num3);
                                DecimalFormat df1 = new DecimalFormat("0.00");
                                String gdd4=df1.format(num2);
                                holder6.zhi_qian.setText(gdd4);
                                activity.zhifu_jiage.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd3);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                FileUtils15 gg=new FileUtils15();
                final String bb=gg.readDataFromFile(mContext);        //总积分
                FileUtils17 rr=new FileUtils17();
                String bb1=rr.readDataFromFile(mContext);           //换算比率
                try {
                    Number num3 = NumberFormat.getInstance().parse(bb);
                    Number num4 = NumberFormat.getInstance().parse(bb1);
                    float num=num3.floatValue();
                    float num1=num4.floatValue();
                    float num2=num1*num;
                    DecimalFormat df = new DecimalFormat("0.00");
                    String gdd=df.format(num2);
                    holder6.alljifen.setText("共"+bb+"积分,"+"积分抵用"+gdd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                FileUtils14 yy=new FileUtils14();
                String gg1=yy.readDataFromFile(mContext);
                holder6.jiage1_tv.setText(gg1);
                if(goud.size()!=0){
                    Fileutils18 rr1=new Fileutils18();
                    String ss2=rr1.readDataFromFile(mContext);   //优惠券
                    FileUtils2 ffe=new FileUtils2();
                    String dd1=ffe.readDataFromFile(mContext);  //总价
                    FileUtils15 ww=new FileUtils15();
                    final String ww1=ww.readDataFromFile(mContext);  //积分
                    FileUtils17 ww2=new FileUtils17();
                    String ww3=ww2.readDataFromFile(mContext);    //换算比例
                    if(ss2!=null&&ss2.equals("请选择")){
                        holder6.sureyouhui_tv.setText(ss2);
                        try {
                            Number num3= NumberFormat.getInstance().parse(ww1);
                            Number num5= NumberFormat.getInstance().parse(ww3);
                            Number num4= NumberFormat.getInstance().parse(dd1);
                            float num=num4.floatValue();
                            float num1=num3.floatValue();
                            float num2=num5.floatValue();
                            float num6=num1*num2;
                            float num7=num-num6;
                            if(flag==false){
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd2=df.format(num);
                                holder6.zhi_qian.setText(gdd2);
                                holder6.shiji_tv1.setText(gdd2);
                                activity.zhifu_jiage.setText(gdd2);
                            }else {
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd2=df.format(num7);
                                String gdd3=df.format(num);
                                holder6.zhi_qian.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd2);
                                activity.zhifu_jiage.setText(gdd2);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                        holder6.sureyouhui_tv.setText(ss2);
                        FileUtils15 file_sumpoint=new FileUtils15();
                        final String bb2= file_sumpoint.readDataFromFile(mContext);
                        FileUtils17 rr2=new FileUtils17();
                        String cc1=rr2.readDataFromFile(mContext);
                        try {
                            Number num6= NumberFormat.getInstance().parse(bb2);
                            Number num7= NumberFormat.getInstance().parse(cc1);
                            Number num4= NumberFormat.getInstance().parse(dd1);
                            Number num5= NumberFormat.getInstance().parse(ss2);
                            float num1=num5. floatValue();
                            float num2=num4. floatValue();
                            float num8=num6.floatValue();
                            float num9=num7.floatValue();
                            float num10=num8*num9;
                            if(flag==false){
                                if(require!=null&&require.equals("1")){
                                    float num3=num2;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    String gdd3=df.format(num3);
                                    DecimalFormat df1 = new DecimalFormat("0.00");
                                    String gdd4=df1.format(num2);
                                    holder6.zhi_qian.setText(gdd4);
                                    activity.zhifu_jiage.setText(gdd3);
                                    holder6.shiji_tv1.setText(gdd3);
                                }else {
                                    float num3=num2-num1;
                                    int price_jiage=(int)num3;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    String gdd3=df.format(num3);
                                    DecimalFormat df1 = new DecimalFormat("0.00");
                                    String gdd4=df1.format(num2);
                                    holder6.zhi_qian.setText(gdd4);
                                    if(gdd3.equals("0.00")||price_jiage<0){
                                        activity.zhifu_jiage.setText("0.01");  //用户实际支付0元时显示0.01
                                        holder6.shiji_tv1.setText("0.01");
                                    }else {
                                        activity.zhifu_jiage.setText(gdd3);
                                        holder6.shiji_tv1.setText(gdd3);
                                    }
                                }

                            }else {
                                float num3=num2-num1-num10;
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd3=df.format(num3);
                                DecimalFormat df1 = new DecimalFormat("0.00");
                                String gdd4=df1.format(num2);
                                holder6.zhi_qian.setText(gdd4);
                                activity.zhifu_jiage.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd3);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
                FileUtils14 tt=new FileUtils14();
                String ee=tt.readDataFromFile(mContext);
                holder6.jiage1_tv.setText(ee);           //运费
             /*   holder6.sureyouhui_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(activity, EnableyouhuiquanActivity.class);
                        activity.startActivityForResult(intent,456);
                        Fileutils18  ff=new Fileutils18();
                    }
                });*/
                if(h5gou.size()!=0){
                    Fileutils18 rr1=new Fileutils18();
                    String ss2=rr1.readDataFromFile(mContext);   //优惠券
                    FileUtils2 ffe=new FileUtils2();
                    String dd1=ffe.readDataFromFile(mContext);  //总价
                    FileUtils15 ww=new FileUtils15();
                    final String ww1=ww.readDataFromFile(mContext); //积分
                    FileUtils17 ww2=new FileUtils17();
                    String ww3=ww2.readDataFromFile(mContext);  //换算比例
                    Log.i("tongku", "onBindViewHolder: "+ss2);

                    Log.i("tongku2", "onBindViewHolder: "+ww3);

                    if(ss2!=null&&ss2.equals("请选择")){
                        holder6.sureyouhui_tv.setText(ss2);
                        try {
                            Number num3= NumberFormat.getInstance().parse(ww1);
                            Number num5= NumberFormat.getInstance().parse(ww3);
                            Number num4= NumberFormat.getInstance().parse(dd1);
                            float num=num4.floatValue();
                            float num1=num3.floatValue();
                            float num2=num5.floatValue();
                            float num6=num1*num2;
                            float num7=num-num6;
                            if(flag==false){
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd2=df.format(num);
                                holder6.zhi_qian.setText(gdd2);
                                holder6.shiji_tv1.setText(gdd2);
                                activity.zhifu_jiage.setText(gdd2);
                            }else {
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd2=df.format(num7);
                                String gdd3=df.format(num);
                                holder6.zhi_qian.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd2);
                                activity.zhifu_jiage.setText(gdd2);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }else {
                        holder6.sureyouhui_tv.setText(ss2);
                        FileUtils15 file_sumpoint=new FileUtils15();
                        final String bb2= file_sumpoint.readDataFromFile(mContext);
                        FileUtils17 rr2=new FileUtils17();
                        String cc1=rr2.readDataFromFile(mContext);
                        try {
                            Number num6= NumberFormat.getInstance().parse(bb2);
                            Number num7= NumberFormat.getInstance().parse(cc1);
                            Number num4= NumberFormat.getInstance().parse(dd1);
                            Number num5= NumberFormat.getInstance().parse(ss2);
                            float num1=num5. floatValue();
                            float num2=num4. floatValue();
                            float num8=num6.floatValue();
                            float num9=num7.floatValue();
                            float num10=num8*num9;
                            if(flag==false){
                                if(require!=null&&require.equals("1")){
                                    float num3=num2;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    String gdd3=df.format(num3);
                                    DecimalFormat df1 = new DecimalFormat("0.00");
                                    String gdd4=df1.format(num2);
                                    holder6.zhi_qian.setText(gdd4);
                                    activity.zhifu_jiage.setText(gdd3);
                                    holder6.shiji_tv1.setText(gdd3);
                                }else {
                                    float num3=num2-num1;
                                    int price_jiage=(int)num3;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    String gdd3=df.format(num3);
                                    DecimalFormat df1 = new DecimalFormat("0.00");
                                    String gdd4=df1.format(num2);
                                    holder6.zhi_qian.setText(gdd4);
                                    if(gdd3.equals("0.00")||price_jiage<0){
                                        activity.zhifu_jiage.setText("0.01");  //用户实际支付0元时显示0.01
                                        holder6.shiji_tv1.setText("0.01");
                                    }else {
                                        activity.zhifu_jiage.setText(gdd3);
                                        holder6.shiji_tv1.setText(gdd3);
                                    }
                                }

                            }else {
                                float num3=num2-num1-num10;
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd3=df.format(num3);
                                DecimalFormat df1 = new DecimalFormat("0.00");
                                String gdd4=df1.format(num2);
                                holder6.zhi_qian.setText(gdd4);
                                activity.zhifu_jiage.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd3);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
                FileUtils14 tt1=new FileUtils14();
                String ee1=tt1.readDataFromFile(mContext);
                holder6.jiage1_tv.setText(ee1);           //运费
                holder6.sureyouhui_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(activity, EnableyouhuiquanActivity.class);
                        activity.startActivityForResult(intent,456);
                        Fileutils18  ff=new Fileutils18();
                    }
                });
                holder6.select_chose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(bizohi){
                            holder6.select_chose.setImageResource(R.drawable.turnoff);
                            holder6.sureyouhui_tv.setVisibility(View.INVISIBLE);
                            holder6.right_iv.setVisibility(View.INVISIBLE);
                           /* Fileutils18 rr1=new Fileutils18();
                            rr1.saveDataToFile(activity,"请选择");
                            notifyDataSetChanged();*/
                              require="1";
                            FileUtils44 files=new FileUtils44();
                            files.saveDataToFile(activity,"1");
                            notifyDataSetChanged();
                            bizohi=false;
                        }else {
                            holder6.select_chose.setImageResource(R.drawable.turnon);
                            holder6.sureyouhui_tv.setVisibility(View.VISIBLE);
                            holder6.right_iv.setVisibility(View.VISIBLE);
                            require="2";
                            FileUtils44 files=new FileUtils44();
                            files.saveDataToFile(activity,"2");
                            notifyDataSetChanged();
                            bizohi=true;

                        }
                    }
                });
                holder6.select_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!flag){
                            Log.i("xunzhao", "onClick: "+"有几分");
                            holder6.select_iv.setImageResource(R.mipmap.opon);
                            String aa="积分";
                            FileUtils19 qq=new FileUtils19();
                            qq.saveDataToFile(mContext,aa);
                            FileUtils17 rr3=new FileUtils17();
                            String cc2=rr3.readDataFromFile(mContext);
                            if(goud.size()!=0){
                                Fileutils18 rr1=new Fileutils18();
                                String ss4=rr1.readDataFromFile(mContext);
                                FileUtils2 ffe=new FileUtils2();
                                String dd=ffe.readDataFromFile(mContext);
                                if(ss4!=null&&ss4.equals("请选择")){
                                    try {
                                        Number num5= NumberFormat.getInstance().parse(cc2);
                                        Number num4= NumberFormat.getInstance().parse(bb);
                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        float num=num3. floatValue();
                                        float num9=num4. floatValue();
                                        float num6=num5.floatValue();
                                        float num2=num9*num6;
                                        num=num-num2;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        gdd2=df.format(num);
                                        activity.zhifu_jiage.setText(gdd2);
                                        holder6. shiji_tv1.setText(gdd2);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    try {
                                        Number num6= NumberFormat.getInstance().parse(cc2);
                                        Number num4= NumberFormat.getInstance().parse(bb);
                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        Number num5= NumberFormat.getInstance().parse(ss4);
                                        float num=num3.intValue();
                                        float num9=num4.intValue();
                                        float num8=num5.intValue();
                                        float num7=num6.intValue();
                                        float num2=num9*num7;
                                        num=num-num2-num8;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        gdd2=df.format(num);
                                        activity.zhifu_jiage.setText(gdd2);
                                        holder6. shiji_tv1.setText(gdd2);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else if(h5gou.size()!=0){
                                Fileutils18 rr1=new Fileutils18();
                                String ss4=rr1.readDataFromFile(mContext);
                                FileUtils2 ffe=new FileUtils2();
                                String dd=ffe.readDataFromFile(mContext);
                                Log.i("xunzhao1", "onClick: "+ss4);
                                Log.i("xunzhao", "onClick: "+dd);
                                if(ss4!=null&&ss4.equals("请选择")){
                                    try {
                                        Number num5= NumberFormat.getInstance().parse(cc2);
                                        Number num4= NumberFormat.getInstance().parse(bb);
                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        float num=num3. floatValue();
                                        float num9=num4. floatValue();
                                        float num6=num5.floatValue();
                                        float num2=num9*num6;
                                        num=num-num2;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        gdd2=df.format(num);
                                        activity.zhifu_jiage.setText(gdd2);
                                        holder6. shiji_tv1.setText(gdd2);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    try {
                                        Number num6= NumberFormat.getInstance().parse(cc2);
                                        Number num4= NumberFormat.getInstance().parse(bb);
                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        Number num5= NumberFormat.getInstance().parse(ss4);
                                        float num=num3.intValue();
                                        float num9=num4.intValue();
                                        float num8=num5.intValue();
                                        float num7=num6.intValue();
                                        float num2=num9*num7;
                                        num=num-num2-num8;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        gdd2=df.format(num);
                                        activity.zhifu_jiage.setText(gdd2);
                                        holder6. shiji_tv1.setText(gdd2);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else {
                                Fileutils18 rr1=new Fileutils18();
                                String ss5=rr1.readDataFromFile(mContext);
                                if(goudd.size()!=0){
                                    if(ss5!=null&&ss5.equals("请选择")){
                                        try {
                                            Number num4= NumberFormat.getInstance().parse(cc2);
                                            Number num5= NumberFormat.getInstance().parse(bb);
                                            Number num3= NumberFormat.getInstance().parse(goudd.get(0).getNowprice().trim());
                                            float num=num3.floatValue();
                                            float num6=num5.floatValue();
                                            float num2=num4.floatValue();
                                            float num1=num6*num2;
                                            num=num-num1;
                                            DecimalFormat df = new DecimalFormat("0.00");
                                            gdd2=df.format(num);
                                            activity.zhifu_jiage.setText(gdd2);
                                            holder6. shiji_tv1.setText(gdd2);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }else {
                                        try {
                                            Number num8= NumberFormat.getInstance().parse(cc2);
                                            Number num5= NumberFormat.getInstance().parse(bb);
                                            Number num3= NumberFormat.getInstance().parse(goudd.get(0).getNowprice().trim());
                                            Number num4= NumberFormat.getInstance().parse(ss5);
                                            float num=num3.floatValue();
                                            float num6=num5.floatValue();
                                            float num7=num4.floatValue();
                                            float num9=num8.floatValue();
                                            float num1=num6*num9;
                                            num=num-num1-num7;
                                            DecimalFormat df = new DecimalFormat("0.00");
                                            gdd2=df.format(num);
                                            activity.zhifu_jiage.setText(gdd2);
                                            holder6. shiji_tv1.setText(gdd2);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            flag=true;
                            notifyDataSetChanged();
                        }else {
                            holder6.select_iv.setImageResource(R.mipmap.colse);
                            String cc="没使用积分";
                            FileUtils19 qq=new FileUtils19();
                            qq.saveDataToFile(mContext,cc);
                            Fileutils18 rr1=new Fileutils18();
                            String ss6=rr1.readDataFromFile(mContext);
                            if(goud.size()!=0){
                                if(ss6!=null&&ss6.equals("请选择")){
                                    FileUtils2 ffe=new FileUtils2();
                                    String dd=ffe.readDataFromFile(mContext);
                                    try {
                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        int num=num3.intValue();
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        String gdd3=df.format(num);
                                        activity.zhifu_jiage.setText(String .valueOf(gdd3));
                                        holder6. shiji_tv1.setText(String .valueOf(gdd3));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    FileUtils2 ffe=new FileUtils2();
                                    String dd=ffe.readDataFromFile(mContext);
                                    try {

                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        Number num4= NumberFormat.getInstance().parse(ss6);
                                        float num=num3. floatValue();
                                        float num2=num4. floatValue();
                                        float num5=num-num2;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        String gdd3=df.format(num5);
                                        activity.zhifu_jiage.setText(String .valueOf(gdd3));
                                        holder6. shiji_tv1.setText(String .valueOf(gdd3));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }else if(h5gou.size()!=0){
                                if(ss6!=null&&ss6.equals("请选择")){
                                    FileUtils2 ffe=new FileUtils2();
                                    String dd=ffe.readDataFromFile(mContext);
                                    try {
                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        int num=num3.intValue();
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        String gdd3=df.format(num);
                                        activity.zhifu_jiage.setText(String .valueOf(gdd3));
                                        holder6. shiji_tv1.setText(String .valueOf(gdd3));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    FileUtils2 ffe=new FileUtils2();
                                    String dd=ffe.readDataFromFile(mContext);
                                    try {

                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        Number num4= NumberFormat.getInstance().parse(ss6);
                                        float num=num3. floatValue();
                                        float num2=num4. floatValue();
                                        float num5=num-num2;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        String gdd3=df.format(num5);
                                        activity.zhifu_jiage.setText(String .valueOf(gdd3));
                                        holder6. shiji_tv1.setText(String .valueOf(gdd3));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }

                            } else {
                                if(goudd.size()!=0){
                                    if(ss6!=null&&ss6.equals("请选择")){
                                        try {
                                            Number num3= NumberFormat.getInstance().parse(goudd.get(0).getNowprice().trim());
                                            float num=num3. floatValue();
                                            DecimalFormat df = new DecimalFormat("0.00");
                                            String gdd4=df.format(num);
                                            activity.zhifu_jiage.setText(String .valueOf(gdd4));
                                            holder6. shiji_tv1.setText(String .valueOf(gdd4));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }else {
                                        try {
                                            Number num3= NumberFormat.getInstance().parse(goudd.get(0).getNowprice().trim());
                                            Number num4= NumberFormat.getInstance().parse(ss6);
                                            float num=num3. floatValue();
                                            float num2=num4. floatValue();
                                            float num5=num-num2;
                                            DecimalFormat df = new DecimalFormat("0.00");
                                            String gdd4=df.format(num5);
                                            activity.zhifu_jiage.setText(String .valueOf(gdd4));
                                            holder6. shiji_tv1.setText(String .valueOf(gdd4));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            flag=false;
                            notifyDataSetChanged();
                        }


                    }
                });



                break;
            case 5:
                break;
        }
    }


        private void initScreenWidth() {
            DisplayMetrics dm = new DisplayMetrics();
            dm = activity.getResources().getDisplayMetrics();
            screenHeight = dm.heightPixels;
            screenWidth = dm.widthPixels;
        }


    @Override
    public int getItemCount() {
        Log.i("cccc", "getItemCount: "+goudd.size());
        return 6


                ;

    }
    class DmyHolderType0  extends RecyclerView.ViewHolder{
        private TextView Dingdanshutv,Dingdanshutv1,Dingdanshutv2,Dingdanshutv3;
        private LinearLayout shouhuo_return;

        public DmyHolderType0(View itemView) {
            super(itemView);
            Dingdanshutv= (TextView) itemView.findViewById(R.id.Dingdanshutv);
            Dingdanshutv1=(TextView) itemView.findViewById(R.id.Dingdanshutv1);
            Dingdanshutv2=(TextView) itemView.findViewById(R.id.Dingdanshutv2);
            Dingdanshutv3=(TextView) itemView.findViewById(R.id.Dingdanshutv3);
            shouhuo_return= (LinearLayout) itemView.findViewById(R.id.shouhuo_return);
        }
    }
    class cDmyHolderType1  extends RecyclerView.ViewHolder{
        private LinearLayout confirmbase_ly;
        private TextView all_num;
        public cDmyHolderType1(View itemView) {
            super(itemView);
            confirmbase_ly= (LinearLayout)itemView.findViewById(R.id.confirmbase_ly);
            all_num= (TextView) itemView.findViewById(R.id.all_num);
        }
       /* private TextView sss1,sss,gouwu_tv,tv_intro,standard,tv_price,tv_price1,servicetip,tv_num;
        private ImageView iv_adapter_list_pic;
        public cDmyHolderType1(View itemView) {
            super(itemView);
                sss1= (TextView) itemView.findViewById(R.id.sss1);
                sss= (TextView) itemView.findViewById(R.id.sss);
                gouwu_tv= (TextView) itemView.findViewById(R.id.gouwu_tv);
                tv_intro= (TextView) itemView.findViewById(R.id.tv_intro);
                standard= (TextView) itemView.findViewById(R.id.standard);
                tv_price= (TextView) itemView.findViewById(R.id.tv_price);
                tv_price1=(TextView) itemView.findViewById(R.id.tv_price1);
                servicetip=(TextView) itemView.findViewById(R.id.servicetip);
                tv_num=(TextView) itemView.findViewById(R.id.tv_num);
                iv_adapter_list_pic= (ImageView) itemView.findViewById(R.id.iv_adapter_list_pic);


        }*/
    /*    private TextView shuliang_tv,shuliang_tv1,shuliang_tv2,shuliang_tv3,shuliang_tv4;
        private ImageView shuliang_iv;
        private ImageView gouwu1,gouwu2,gouwu3,gouwu4;
        private TextView zhifu_tv2;
        public cDmyHolderType1(View itemView) {
            super(itemView);
            if(goud.size()==0&&h5gou.size()==0){
                shuliang_tv= (TextView) itemView.findViewById(R.id.shuliang_tv);
                shuliang_tv1= (TextView) itemView.findViewById(R.id.shuliang_tv1);
                shuliang_tv2= (TextView) itemView.findViewById(R.id.shuliang_tv2);
                shuliang_tv3= (TextView) itemView.findViewById(R.id.shuliang_tv3);
                shuliang_tv4= (TextView) itemView.findViewById(R.id.shuliang_tv4);
                shuliang_iv= (ImageView) itemView.findViewById(R.id.shuliang_iv);
            }else {
                gouwu1= (ImageView) itemView.findViewById(R.id.gouwu1);
                gouwu2= (ImageView) itemView.findViewById(R.id.gouwu2);
                gouwu3= (ImageView) itemView.findViewById(R.id.gouwu3);
                gouwu4= (ImageView) itemView.findViewById(R.id.gouwu4);
                zhifu_tv2= (TextView) itemView.findViewById(R.id.zhifu_tv2);

            }

        }*/
    }
    class DmyHolderType2  extends RecyclerView.ViewHolder{
        private TextView taitou_tv;
        private EditText shuru_et,geren_tv;
        public DmyHolderType2(View itemView) {
            super(itemView);
            taitou_tv= (TextView) itemView.findViewById(R.id.taitou_tv);
            geren_tv= (EditText) itemView.findViewById(R.id.geren_tv);
            shuru_et= (EditText) itemView.findViewById(R.id.shuru_et);
        }
    }
    class DmyHolderType3  extends RecyclerView.ViewHolder{
        private RelativeLayout yinglian_ly,zhifu_ly,weichartcrash_ly,payincrash_ly;
        private TextView zhifu_baioti,zhifu_tv,zhifu_tv1,yinglian_tv,yinglian_tv1;
        private ImageView zhifu_iv,yinglian_iv;

        public DmyHolderType3(View itemView) {
            super(itemView);
            weichartcrash_ly= (RelativeLayout) itemView.findViewById(R.id.weichartcrash_ly);
            payincrash_ly= (RelativeLayout) itemView.findViewById(R.id.payincrash_ly);
            yinglian_ly= (RelativeLayout) itemView.findViewById(R.id.yinglian_ly);
            zhifu_ly= (RelativeLayout) itemView.findViewById(R.id.zhifu_ly);
            zhifu_baioti= (TextView) itemView.findViewById(R.id.zhifu_baioti);
            zhifu_tv= (TextView) itemView.findViewById(R.id. zhifu_tv);
            zhifu_tv1= (TextView) itemView.findViewById(R.id. zhifu_tv1);
            yinglian_tv= (TextView) itemView.findViewById(R.id.yinglian_tv);
            yinglian_tv1= (TextView) itemView.findViewById(R.id.yinglian_tv1);
            zhifu_iv= (ImageView) itemView.findViewById(R.id.zhifu_iv);
            yinglian_iv= (ImageView) itemView.findViewById(R.id.yinglian_iv);
        }
    }
    class DmyHolderType4  extends RecyclerView.ViewHolder{
        private TextView zhi_qian,alljifen,jiage1_tv,shiji_tv1,sureyouhui_tv;
        private RelativeLayout sale_youhui;
        private ImageView select_iv,select_chose,right_iv;
        public DmyHolderType4(View itemView) {
            super(itemView);
            sureyouhui_tv= (TextView) itemView.findViewById(R.id.sureyouhui_tv);
            zhi_qian= (TextView) itemView.findViewById(R.id.zhi_qian);
            alljifen= (TextView) itemView.findViewById(R.id.alljifen);
            jiage1_tv= (TextView) itemView.findViewById(R.id.jiage1_tv);
            shiji_tv1= (TextView) itemView.findViewById(R.id.shiji_tv1);
            sale_youhui= (RelativeLayout) itemView.findViewById(R.id.sale_youhui);
            select_iv= (ImageView) itemView.findViewById(R.id.select_iv);
            select_chose= (ImageView) itemView.findViewById(R.id.select_chose);
            right_iv= (ImageView) itemView.findViewById(R.id.right_iv);
        }
    }
    class DmyHolderType5  extends RecyclerView.ViewHolder{
        private TextView tv6_queren;

        public DmyHolderType5(View itemView) {
            super(itemView);
            tv6_queren= (TextView) itemView.findViewById(R.id.tv6_queren);
        }
    }
}
