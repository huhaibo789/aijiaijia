package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.EnableyouhuiquanActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.ShouhuoActivity;
import com.example.administrator.myyushi.YuyueActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import utils.FileUtils10;
import utils.FileUtils11;
import utils.FileUtils12;
import utils.FileUtils13;
import utils.FileUtils14;
import utils.FileUtils15;
import utils.FileUtils17;
import utils.FileUtils19;
import utils.FileUtils2;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.FileUtils22;
import utils.Fileutils18;

/**
 * Created by 胡海波 on 2016/9/19.
 */
public class yuyueadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  String gdd2;
    private YuyueActivity activity;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private Context mContext;
    private ArrayList<String> ss=new ArrayList<>();
    private ArrayList<String> ss1=new ArrayList<>();
    private  ArrayList<String> ss2=new ArrayList<>();
    private ArrayList<String> ss3=new ArrayList<>();
    private ArrayList<String> data=new ArrayList<>();
    private   String us_address;
    private   String us_name;
    private  String us_phone;
    private    String us_pscsds;
    boolean flag;
    boolean state=false;
    public yuyueadapter(YuyueActivity context, ImageLoader loader){
        this.mContext=context;
        inflater=LayoutInflater.from(context);
        this.loader=loader;
        this.activity=context;

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
    public  void updatexin6(ArrayList<String> ss3){
        this.ss3=ss3;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new DmyHolderType0(inflater.inflate(R.layout.activity_queren,parent,false));
                break;


            case 1:
                holder=new cDmyHolderType1(inflater.inflate(R.layout.activity_queren7,parent,false));
                break;
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
                FileUtils10 ff=new FileUtils10();
                String aa=ff.readDataFromFile(mContext);
                FileUtils11 ff1=new FileUtils11();
                String aa1=ff1.readDataFromFile(mContext);
                FileUtils12 ff2=new FileUtils12();
                String aa2=ff2.readDataFromFile(mContext);
                FileUtils13 ff3=new FileUtils13();
                String aa3=ff3.readDataFromFile(mContext);
                holder2.Dingdanshutv.setText(aa1);
                holder2.Dingdanshutv1.setText(aa2);
                holder2.Dingdanshutv2.setText(aa3);
                holder2.Dingdanshutv3.setText(aa);
                holder2.shouhuo_return.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3=new Intent(mContext, ShouhuoActivity.class);
                        mContext.startActivity(intent3);
                    }
                });
                break;
            case  1:
                final cDmyHolderType1 holder3= (cDmyHolderType1) holder;
                if(ss3.size()==1){
                    loader.loadImage(ss3.get(0),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu1.setImageBitmap(loadedImage);
                        }
                    });
                    holder3.gouwu2.setVisibility(View.INVISIBLE);
                    holder3.gouwu3.setVisibility(View.INVISIBLE);
                    holder3.gouwu4.setVisibility(View.INVISIBLE);
                }else if(ss3.size()==2){
                    loader.loadImage(ss3.get(0),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu1.setImageBitmap(loadedImage);
                        }
                    });
                    loader.loadImage(ss3.get(1),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu2.setImageBitmap(loadedImage);
                        }
                    });
                    holder3.gouwu3.setVisibility(View.INVISIBLE);
                    holder3.gouwu4.setVisibility(View.INVISIBLE);
                }else if(ss3.size()==3){
                    loader.loadImage(ss3.get(0),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu1.setImageBitmap(loadedImage);
                        }
                    });
                    loader.loadImage(ss3.get(1),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu2.setImageBitmap(loadedImage);
                        }
                    });
                    loader.loadImage(ss3.get(2),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu3.setImageBitmap(loadedImage);
                        }
                    });
                    holder3.gouwu4.setVisibility(View.INVISIBLE);
                }else if(ss3.size()>=4){
                    loader.loadImage(ss3.get(0),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu1.setImageBitmap(loadedImage);
                        }
                    });
                    loader.loadImage(ss3.get(1),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu2.setImageBitmap(loadedImage);
                        }
                    });
                    loader.loadImage(ss3.get(2),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu3.setImageBitmap(loadedImage);

                        }
                    });
                    loader.loadImage(ss3.get(3),new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            holder3.gouwu4.setImageBitmap(loadedImage);
                        }
                    });
                }
                holder3.zhifu_tv2.setText("共"+ss3.size()+"件");
                break;
            case 2:

                final DmyHolderType2   dmholedr2= (DmyHolderType2) holder;
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
                dmholedr2.geren_tv.addTextChangedListener(new TextWatcher() {
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
                });



                break;
            case 3:
                final DmyHolderType3 holder5= (DmyHolderType3) holder;
                holder5.zhifu_baioti.setText("支付方式");
                holder5.zhifu_tv.setText("支付宝");
                Log.i("ueow", "onBindViewHolder: "+state);
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
                        holder5.zhifu_ly.setBackgroundResource(R.drawable.border);
                        holder5.yinglian_ly.setBackgroundResource(R.drawable.border1);
                        FileUtils22 filepay=new FileUtils22();
                        filepay.saveDataToFile(mContext,"1");
                        state=false;
                    }
                });
                holder5.yinglian_ly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder5.yinglian_ly.setBackgroundResource(R.drawable.border);
                        holder5.zhifu_ly.setBackgroundResource(R.drawable.border1);
                        FileUtils22 file=new FileUtils22();
                        file.saveDataToFile(mContext,"3");
                        state=true;

                    }
                });

                break;
            case 4:
                final DmyHolderType4 holder6= (DmyHolderType4) holder;
                FileUtils15 gg=new FileUtils15();
                final String bb=gg.readDataFromFile(mContext);        //总积分
                Log.i("lalad", "onBindViewHolder: "+bb.toString());
                FileUtils17 rr=new FileUtils17();
                String bb1=rr.readDataFromFile(mContext);           //换算比率
                Log.i("rrw", "onBindViewHolder: "+bb1);
                try {
                    Number num3 = NumberFormat.getInstance().parse(bb);
                    Number num4 = NumberFormat.getInstance().parse(bb1);
                    float num=num3.floatValue();
                    float num1=num4.floatValue();
                    float num2=num1*num;
                    DecimalFormat df = new DecimalFormat("0.00");
                    String gdd=df.format(num2);
                    Log.i("meizi", "onBindViewHolder: "+num+num1);
                    holder6.alljifen.setText("共"+bb+"积分,"+"积分抵用"+gdd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                FileUtils14 yy=new FileUtils14();
                String gg1=yy.readDataFromFile(mContext);    //运费
                holder6.jiage1_tv.setText(gg1);
                if(ss3.size()!=0){
                    Fileutils18 rr1=new Fileutils18();
                    String ss2=rr1.readDataFromFile(mContext);   //优惠券
                    FileUtils2 ffe=new FileUtils2();
                    String dd1=ffe.readDataFromFile(mContext);  //总价
                    FileUtils15 ww=new FileUtils15();
                    final String ww1=ww.readDataFromFile(mContext);
                    FileUtils17 ww2=new FileUtils17();
                    String ww3=ww2.readDataFromFile(mContext);
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
                                activity.zhifuJiage.setText(gdd2);
                            }else {
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd2=df.format(num7);
                                String gdd3=df.format(num);
                                holder6.zhi_qian.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd2);
                                activity.zhifuJiage.setText(gdd2);
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
                                float num3=num2-num1;
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd3=df.format(num3);
                                DecimalFormat df1 = new DecimalFormat("0.00");
                                String gdd4=df1.format(num2);
                                holder6.zhi_qian.setText(gdd4);
                                activity.zhifuJiage.setText(gdd3);
                                holder6.shiji_tv1.setText(gdd3);
                            }else {
                                float num3=num2-num1-num10;
                                DecimalFormat df = new DecimalFormat("0.00");
                                String gdd3=df.format(num3);
                                DecimalFormat df1 = new DecimalFormat("0.00");
                                String gdd4=df1.format(num2);
                                holder6.zhi_qian.setText(gdd4);
                                activity.zhifuJiage.setText(gdd3);
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
                holder6.sureyouhui_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(activity, EnableyouhuiquanActivity.class);
                        activity.startActivityForResult(intent,456);
                        Fileutils18  ff=new Fileutils18();



                    }
                });

                holder6.select_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!flag){
                            holder6.select_iv.setImageResource(R.mipmap.opon);
                            String aa="积分";
                            FileUtils19 qq=new FileUtils19();
                            qq.saveDataToFile(mContext,aa);
                            FileUtils17 rr3=new FileUtils17();
                            String cc2=rr3.readDataFromFile(mContext);
                            if(ss3.size()!=0){
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
                                        activity.zhifuJiage.setText(gdd2);
                                        holder6. shiji_tv1.setText(gdd2);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }else {
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
                                        Log.i("fsfd", "onClick: "+num);
                                        Log.i("dssd", "onClick: "+num9);
                                        num=num-num2-num8;
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        gdd2=df.format(num);
                                        activity.zhifuJiage.setText(gdd2);
                                        holder6. shiji_tv1.setText(gdd2);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
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
                            if(ss3.size()!=0){
                                if(ss6!=null&&ss6.equals("请选择")){
                                    FileUtils2 ffe=new FileUtils2();
                                    String dd=ffe.readDataFromFile(mContext);
                                    try {
                                        Number num3= NumberFormat.getInstance().parse(dd);
                                        int num=num3.intValue();
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        String gdd3=df.format(num);
                                        activity.zhifuJiage.setText(String .valueOf(gdd3));
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
                                        activity.zhifuJiage.setText(String .valueOf(gdd3));
                                        holder6. shiji_tv1.setText(String .valueOf(gdd3));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
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

    @Override
    public int getItemCount() {
        return 6;
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

        private TextView shuliang_tv,shuliang_tv1,shuliang_tv2,shuliang_tv3,shuliang_tv4;
        private ImageView shuliang_iv;
        private ImageView gouwu1,gouwu2,gouwu3,gouwu4;
        private TextView zhifu_tv2;
        public cDmyHolderType1(View itemView) {
            super(itemView);

                gouwu1= (ImageView) itemView.findViewById(R.id.gouwu1);
                gouwu2= (ImageView) itemView.findViewById(R.id.gouwu2);
                gouwu3= (ImageView) itemView.findViewById(R.id.gouwu3);
                gouwu4= (ImageView) itemView.findViewById(R.id.gouwu4);
                zhifu_tv2= (TextView) itemView.findViewById(R.id.zhifu_tv2);



        }
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
        private RelativeLayout yinglian_ly,zhifu_ly;
        private TextView zhifu_baioti,zhifu_tv,zhifu_tv1,yinglian_tv,yinglian_tv1;
        private ImageView zhifu_iv,yinglian_iv;

        public DmyHolderType3(View itemView) {
            super(itemView);
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
        private ImageView select_iv;
        public DmyHolderType4(View itemView) {
            super(itemView);
            sureyouhui_tv= (TextView) itemView.findViewById(R.id.sureyouhui_tv);
            zhi_qian= (TextView) itemView.findViewById(R.id.zhi_qian);
            alljifen= (TextView) itemView.findViewById(R.id.alljifen);
            jiage1_tv= (TextView) itemView.findViewById(R.id.jiage1_tv);
            shiji_tv1= (TextView) itemView.findViewById(R.id.shiji_tv1);
            sale_youhui= (RelativeLayout) itemView.findViewById(R.id.sale_youhui);
            select_iv= (ImageView) itemView.findViewById(R.id.select_iv);
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
