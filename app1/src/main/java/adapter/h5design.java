package adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.DesignActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.ReceivegoodsActivity;
import com.example.administrator.myyushi.YouhuiquanActivity;
import com.example.administrator.myyushi.h5exciseActivity;
import com.example.administrator.myyushi.withdrawalActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Listbean;
import newfrage.InnerGridView;
import paomadeng.UPMarqueeView;

/**
 * Created by misshu on 2017/6/27/027.
 */
public class h5design extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private int mTextCount;
    private ImageLoader loader;
    private DesignActivity activity;
    Runnable runnable;
    List<View> views = new ArrayList<>();
    boolean flag=false;
    private Handler mHandler = new Handler();
    ArrayList<String>  designordersn=new ArrayList<>(); //订单号
    ArrayList<String>  designorderpic=new ArrayList<>();//商品图片
    ArrayList<String>  designordertitle=new ArrayList<>();//商品标题
    ArrayList<String>  designorderprcie=new ArrayList<>();//商品价格
    ArrayList<String>  designorderpreviousprice=new ArrayList<>();//商品原价
    ArrayList<String>  designorderstyle=new ArrayList<>();//商品产品型号
    ArrayList<String>  designordernum=new ArrayList<>();//商品数量
    ArrayList<String>  designorderstatus=new ArrayList<>();//商品数量
    ArrayList<String>  designname=new ArrayList<>();//收货人姓名
    ArrayList<String>  designphone=new ArrayList<>();//收货人号码
    ArrayList<String>  designaddress=new ArrayList<>();//安装地址
    ArrayList<String>  designfreght=new ArrayList<>();//安装运费
    ArrayList<String>  designinstall=new ArrayList<>();//安装价格
    ArrayList<String>  designallsale=new ArrayList<>();//价格总计
    ImageView daohang_iv;
    PopupWindow pop;
    String ab="0";
    String information;
    private RequestQueue queue;
    private View v_order;
    private View v_orderpic;
    private View v_orderinformation;
    public h5design(DesignActivity context, ImageLoader loader,String information, ArrayList<String>  designordersn,ArrayList<String>  designorderpic,ArrayList<String>  designordertitle,ArrayList<String>  designorderprcie,ArrayList<String>  designorderpreviousprice,ArrayList<String>  designorderstyle,ArrayList<String>  designordernum,ArrayList<String>  designorderstatus
            ,ArrayList<String>  designname,ArrayList<String>  designphone, ArrayList<String>  designaddress,ArrayList<String>  designfreght,ArrayList<String>  designinstall,ArrayList<String>  designallsale){
        this.activity=context;
        this.loader=loader;
        inflater=LayoutInflater.from(context);
        this.designordersn=designordersn;
        this.designorderpic=designorderpic;
        this.designordertitle=designordertitle;
        this.designorderprcie=designorderprcie;
        this.designorderpreviousprice=designorderpreviousprice;
        this.designorderstyle=designorderstyle;
        this.designordernum=designordernum;
        this.designorderstatus=designorderstatus;
        this.designname=designname;
        this.designphone=designphone;
        this.designaddress=designaddress;
        this.designfreght=designfreght;
        this.designinstall=designinstall;
        this.designallsale=designallsale;
        this.information=information;
        Log.i("quhecai", "h5design: "+designname.toString());
        Log.i("quhecai1", "h5design: "+designordersn.toString());
        Log.i("quhecai2", "h5design: "+designorderpic.toString());

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new MyHolderType0(inflater.inflate(R.layout.activity_designnumber,parent,false)); //支付宝账号
                break;
            case 1:
                holder=new MyHolderType1(inflater.inflate(R.layout.activity_designorder,parent,false)); //订单信息
                break;
          /*  case 2:
                holder=new MyHolderType2(inflater.inflate(R.layout.activity_showmessage,parent,false)); //文字信息
                break;*/

        }
        return holder;
    }
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else   {
            return 1;
        }

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                final   MyHolderType0   holder0= (MyHolderType0) holder;
                holder0.design_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(activity,withdrawalActivity.class);
                        activity.startActivity(intent);
                    }
                });
                holder0.income_ly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(activity,ReceivegoodsActivity.class);
                        activity.startActivity(intent);
                    }
                });
                break;
            case 1:
                final MyHolderType1 holder1= (MyHolderType1) holder;
                Handler handle=new Handler();
                //   holder1.gridview.setAdapter(new h5designadapter(designname,designphone,designaddress,designfreght,designinstall,designallsale,designordersn,designorderstatus,information,loader,activity));
                Log.i("zhanan", "onBindViewHolder: "+designordersn.size());
                Log.i("zhanan1", "onBindViewHolder: "+designordersn.toString());
                if(ab.equals("0")){
                    Log.i("zhanan2", "onBindViewHolder: "+designordersn.toString());
                    ab="1";
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = new JSONObject(information);
                        JSONObject responseobject = jsonobject.getJSONObject("responseJson");
                        JSONArray jsonarry = responseobject.getJSONArray("sellorders");
                        for (int k = 0; k <jsonarry.length() ; k++) {
                            v_order = LayoutInflater.from(activity).inflate(R.layout.activity_designadd, null);
                            TextView design_bianhao = (TextView) v_order.findViewById(R.id.design_bianhao);
                            TextView design_zhuangtai = (TextView) v_order.findViewById(R.id.design_zhuangtai);
                            design_bianhao.setText("订单编号:"+designordersn.get(k));
                            design_zhuangtai.setText(designorderstatus.get(k));
                            holder1.linear_design.addView(v_order);
                            JSONObject object = jsonarry.getJSONObject(k);
                            JSONArray jsonarry1 = object.getJSONArray("products");
                            for (int i = 0; i <jsonarry1.length() ; i++) {
                                v_orderpic=LayoutInflater.from(activity).inflate(R.layout.activity_designproduct, null);
                                final ImageView design_iv = (ImageView) v_orderpic.findViewById(R.id.design_iv);  //商品图片
                                TextView design_tv = (TextView) v_orderpic.findViewById(R.id.design_tv);    //商品名称
                                TextView designtext= (TextView) v_orderpic.findViewById(R.id.designtext);   //sku名称
                                TextView design_bn = (TextView) v_orderpic.findViewById(R.id.design_bn);   //商品型号
                                TextView design_tv1 = (TextView) v_orderpic.findViewById(R.id.design_tv1);  //商品价格
                                TextView design_shuliang = (TextView) v_orderpic.findViewById(R.id.design_shuliang);//商品数量
                                JSONObject Object1 = jsonarry1.getJSONObject(i);
                                String product_pic = Object1.getString("product_pic");
                                loader.loadImage(product_pic,new SimpleImageLoadingListener(){
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        super.onLoadingComplete(imageUri, view, loadedImage);
                                        design_iv.setImageBitmap(loadedImage);
                                    }
                                });
                                String product_name = Object1.getString("product_name");
                                design_tv.setText(product_name);
                                String skutext1=Object1.getString("skutext");
                                designtext.setText(skutext1);
                                String product_bn1 = Object1.getString("product_bn");
                                design_bn.setText(product_bn1);
                                String product_nowprice = Object1.getString("product_nowprice");
                                String product_price = Object1.getString("product_price");
                                if(product_nowprice.equals("0")||product_nowprice.equals("null")){
                                    design_tv1.setText("¥"+product_price);
                                }else if(product_price.equals("0")||product_price.equals("null")){
                                    design_tv1.setText("¥"+product_nowprice);
                                }else if(product_nowprice.equals(product_price)){
                                    design_tv1.setText("¥"+product_nowprice);

                                }else {
                                    design_tv1.setText("¥"+product_nowprice);
                                }
                                String num = Object1.getString("num");
                                design_shuliang.setText("x"+num);
                                holder1.linear_design.addView(v_orderpic);
                            }
                            v_orderinformation = LayoutInflater.from(activity).inflate(R.layout.activity_designshouhuo, null);
                            TextView receiveman = (TextView)  v_orderinformation.findViewById(R.id.receiveman);    //收获人姓名
                            TextView receivephone = (TextView)  v_orderinformation.findViewById(R.id.receivephone);    //收货人电话
                            TextView receiveaddress = (TextView)  v_orderinformation.findViewById(R.id.receiveaddress);    //收货人地址
                            TextView designfreight = (TextView) v_orderinformation.findViewById(R.id.designfreight);//需要运费
                            TextView designinstall_tv = (TextView)  v_orderinformation.findViewById(R.id.designinstall_tv);    //需要的安装费
                            TextView design_allmoney = (TextView) v_orderinformation.findViewById(R.id.design_allmoney);    //商品总价
                            receiveman.setText("收件人:"+designname.get(k));
                            receivephone.setText("联系方式:"+designphone.get(k));
                            receiveaddress.setText("地址:"+designaddress.get(k));
                            designfreight.setText("运费:"+designfreght.get(k));
                            designinstall_tv.setText("安装费:"+designinstall.get(k));
                            design_allmoney.setText("总计:"+designallsale.get(k));
                            holder1.linear_design.addView( v_orderinformation);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;

        }
    }
    @Override
    public int getItemCount() {
        int count=designordersn==null?0:designordersn.size()-1;
        return count;
    }
    class MyHolderType0 extends  RecyclerView.ViewHolder{
        private ImageView design_pic;
        private RelativeLayout design_rl;
        private LinearLayout income_ly;
        private TextView design_infomation,design_lv,money_income,money_waitincome,money_canincome;
        public MyHolderType0(View itemView) {
            super(itemView);
            design_pic= (ImageView) itemView.findViewById(R.id.design_pic);
            design_rl= (RelativeLayout) itemView.findViewById(R.id.design_rl);
            income_ly= (LinearLayout) itemView.findViewById(R.id.income_ly);
            design_infomation= (TextView) itemView.findViewById(R.id.design_infomation);
            design_lv= (TextView) itemView.findViewById(R.id.design_lv);
            money_income= (TextView) itemView.findViewById(R.id.money_income);
            money_waitincome= (TextView) itemView.findViewById(R.id.money_waitincome);
            money_canincome= (TextView) itemView.findViewById(R.id.money_canincome);
        }
    }
    class MyHolderType1 extends RecyclerView.ViewHolder{
        private InnerGridView gridview;
        private LinearLayout linear_design;
        public MyHolderType1(View itemView) {
            super(itemView);
            linear_design= (LinearLayout) itemView.findViewById(R.id.linear_design);
        }
    }
    /*class  MyHolderType2 extends RecyclerView.ViewHolder{
        private TextView show_youhui;
        public MyHolderType2(View itemView) {
            super(itemView);
            show_youhui= (TextView) itemView.findViewById(R.id.show_youhui);

        }
    }*/

}
