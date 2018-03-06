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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Sousuo2Activity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import bean.Listbean;
import utils.FileUtils;

/**
 * Created by misshu on 2017/6/27/027.
 */
public class h5designadapter extends BaseAdapter {
    private ImageLoader loader;
    private Context context;
    String info;
    HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    ArrayList<String> ordersn=new ArrayList<>();
    ArrayList<String> orderstate=new ArrayList<>();
    private View v_order;
    private View v_orderpic;
    private View v_orderinformation;
    ArrayList<String>  designname=new ArrayList<>();//收货人姓名
    ArrayList<String>  designphone=new ArrayList<>();//收货人号码
    ArrayList<String>  designaddress=new ArrayList<>();//安装地址
    ArrayList<String>  designfreght=new ArrayList<>();//安装运费
    ArrayList<String>  designinstall=new ArrayList<>();//安装价格
    ArrayList<String>  designallsale=new ArrayList<>();//价格总计
    public h5designadapter(ArrayList<String>  designname, ArrayList<String>  designphone, ArrayList<String>  designaddress,ArrayList<String>  designfreght,ArrayList<String>  designinstall,ArrayList<String>  designallsale,ArrayList<String> ordersn,ArrayList<String> orderstate,String info,ImageLoader loader,Context context) {
        this.ordersn=ordersn;
        this.info=info;
        this.context=context;
        this.loader=loader;
        this.orderstate=orderstate;
        this.designname=designname;
        this.designphone=designphone;
        this.designaddress=designaddress;
        this.designfreght=designfreght;
        this.designinstall=designinstall;
        this.designallsale=designallsale;
        Log.i("huhusa", "h5designadapter: "+ordersn.toString());
    }

    @Override
    public int getCount() {
        return ordersn.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return ordersn.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        GridHolder holder = null;
        if (lmap.get(position) == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_designinfo, null);
            holder = new GridHolder();
            convertView.setTag(holder);
            lmap.put(position,convertView);
            holder.linear_design = (LinearLayout) convertView.findViewById(R.id.linear_design);
        } else {
            convertView = lmap.get(position);
            holder=(GridHolder) convertView.getTag();
        }
        try {
            JSONObject jsonobject = new JSONObject(info);
            JSONObject responseobject = jsonobject.getJSONObject("responseJson");
            JSONArray jsonarry = responseobject.getJSONArray("sellorders");
            for (int i = 0; i <jsonarry.length() ; i++) {
                JSONObject object = jsonarry.getJSONObject(i);
                String result = object.getString("order_sn");
                if(ordersn.get(position).equals(result)){
                    v_order = LayoutInflater.from(context).inflate(R.layout.activity_designadd, null);
                    TextView design_bianhao = (TextView) v_order.findViewById(R.id.design_bianhao);
                    TextView design_zhuangtai = (TextView) v_order.findViewById(R.id.design_zhuangtai);
                    design_bianhao.setText(ordersn.get(position));
                    design_zhuangtai.setText(orderstate.get(position));

                    holder.linear_design.addView(v_order);
                    JSONArray jsonarry1 = object.getJSONArray("products");
                    for (int j = 0; j <jsonarry1.length() ; j++) {
                        v_orderpic=LayoutInflater.from(context).inflate(R.layout.activity_designproduct, null);
                        final ImageView design_iv = (ImageView) v_orderpic.findViewById(R.id.design_iv);  //商品图片
                        TextView design_tv = (TextView) v_orderpic.findViewById(R.id.design_tv);    //商品名称
                        TextView designtext= (TextView) v_orderpic.findViewById(R.id.designtext);   //sku名称
                        TextView design_bn = (TextView) v_orderpic.findViewById(R.id.design_bn);   //商品型号
                        TextView design_tv1 = (TextView) v_orderpic.findViewById(R.id.design_tv1);  //商品价格
                        TextView design_shuliang = (TextView) v_orderpic.findViewById(R.id.design_shuliang);//商品数量
                        JSONObject Object1 = jsonarry1.getJSONObject(j);
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
                        designtext.setText("标准");
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
                        holder.linear_design.addView(v_orderpic);
                    }
                    v_orderinformation = LayoutInflater.from(context).inflate(R.layout.activity_designshouhuo, null);
                    TextView receiveman = (TextView)  v_orderinformation.findViewById(R.id.receiveman);    //收获人姓名
                    TextView receivephone = (TextView)  v_orderinformation.findViewById(R.id.receivephone);    //收货人电话
                    TextView receiveaddress = (TextView)  v_orderinformation.findViewById(R.id.receiveaddress);    //收货人地址
                    TextView designfreight = (TextView) v_orderinformation.findViewById(R.id.designfreight);//需要运费
                    TextView designinstall_tv = (TextView)  v_orderinformation.findViewById(R.id.designinstall_tv);    //需要的安装费
                    TextView design_allmoney = (TextView) v_orderinformation.findViewById(R.id.design_allmoney);    //商品总价
                    receiveman.setText("收件人:"+designname.get(position));
                    receivephone.setText("联系方式:"+designphone.get(position));
                    receiveaddress.setText("地址"+designaddress.get(position));
                    designfreight.setText("运费:"+designfreght.get(position));
                    designinstall_tv.setText("安装费:"+designinstall.get(position));
                    design_allmoney.setText("总计:"+designallsale.get(position));
                    holder.linear_design.addView( v_orderinformation);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
    public static class GridHolder {
       LinearLayout linear_design;
    }
}
