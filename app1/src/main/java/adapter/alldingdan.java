package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.AllsaleActivity;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.zhantingbean;
import utils.FileUtils;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class alldingdan extends BaseAdapter {
    private ImageLoader loader;
    private RequestQueue queue;
    private AllsaleActivity activity;
    private int lastPosition;
    private LayoutInflater inflater;
    private String id1;
    private ArrayList<String> array=new ArrayList<>();
    private  ArrayList<String> array1=new ArrayList<>();
    private  ArrayList<String> arry2=new ArrayList<>();
    private ArrayList<String> array3=new ArrayList<>();
    private ArrayList<String> array4=new ArrayList<>();
    private ArrayList<String> array5=new ArrayList<>();
    private ArrayList<String> array6=new ArrayList<>();
    private ArrayList<String> array7=new ArrayList<>();
    private ArrayList<String> array8=new ArrayList<>();
  private ArrayList<String> array9=new ArrayList<>();
    private Context context;
    public   alldingdan(AllsaleActivity context,ImageLoader loader){
        super();
        this.context=context;
        this.loader=loader;
        this.activity=context;

    }
   public  void updateId(ArrayList<String> array9){
       this.array9=array9;
       Log.i("ydbe", "updateId: "+array9.toString());
   }

    public  void updateordersn(ArrayList<String> array){
        this.array=array;

    }
    public  void updateorderstatustext(ArrayList<String> array1){
        this.array1=array1;

    }
    public  void updateproductname(ArrayList<String> array3){
        this.array3=array3;

    }
    public  void updateproductbn(ArrayList<String> array4){
        this.array4=array4;

    }
    public  void updateproductpic(ArrayList<String> arry2){
        this.arry2=arry2;

    }
    public  void updateproductnowprice(ArrayList<String> array5){
        this.array5=array5;

    }
    public  void updateproductprice(ArrayList<String> array6){
        this.array6=array6;

    }
    public  void updateproductNum(ArrayList<String> array7){
        this.array7=array7;

    }
    public  void updateorderprice(ArrayList<String> array8){
        this.array8=array8;
        Log.i("fshei", "updateorderprice: "+array8.toString());

    }
    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_normal1,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.gouwu_tv= (TextView) convertView.findViewById(R.id.gouwu_tv);
            vh.product_bn= (TextView) convertView.findViewById(R.id.product_bn);
            vh.gouwu_tv1= (TextView) convertView.findViewById(R.id.gouwu_tv1);
            vh.gouwu_tv2= (TextView) convertView.findViewById(R.id.gouwu_tv2);
            vh.gouwu_shuliang= (TextView) convertView.findViewById(R.id.gouwu_shuliang);
            vh. gouwu_iv= (ImageView) convertView.findViewById(R.id. gouwu_iv);
        }else {
            vh= (ViewHold) convertView.getTag();
        }
          vh.order_number.setText("订单编号:"+array.get(position));
          vh.sale_zhuangtai.setText(array1.get(position));
          vh.gouwu_tv.setText(array3.get(position));
          vh.product_bn.setText(array4.get(position));
          vh.gouwu_tv1.setText(array5.get(position));
         vh.gouwu_tv2.setText(array6.get(position));
         vh.gouwu_shuliang.setText(array7.get(position));
        vh.produce_tv.setText("共"+array5.size()+"件商品"+"合计¥"+array8.get(position)+"(含运费¥0.00）");
        final ViewHold finalVh1 = vh;
        loader.loadImage(arry2.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh1.gouwu_iv.setImageBitmap(loadedImage);
            }
        });
        if(array1.get(position).equals("等待付款")){
             vh.check_information.setVisibility(View.GONE);
        }
       /* activity.listShangpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("dgutr", "onItemClick: "+"点了");
            }
        });*/
        vh.cancell_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("dskid", "onClick: "+"ksasd");
                /*activity.listShangpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("fdssge", "onItemClick: "+"wamfd");
                      id1=array9.get(position);
                        Log.i("dsdwef", "onItemClick: "+id1);
                        postshuju1();
                    }
                });*/

            }
        });

       // vh.tv11.setText(fenlist.get(position));
        //TextView lastPosition=
        // vh.tv11.setTextColor(position,Color.GREEN);
        // parent.getChildAt(position);.setTextColor();
       /* for (int i = 0; i <fenlist.size() ; i++) {

        }
        FileUtils fi=new FileUtils();
        String xiaoxi=fi.readDataFromFile(context);
        int p=Integer.valueOf(xiaoxi);*/



        final ViewHold finalVh = vh;

        return convertView;
    }

    private void postshuju1() {
        queue = Volley.newRequestQueue(context);
        String url="http://api.aijiaijia.com/api_sellorder_update";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfi", "onResponse: post  success " + response);
                        String ss=response.toString().trim();
                        JSONObject jsonObject=null;
                        try {
                            jsonObject=new JSONObject(ss);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String op=resposeobject.getString("op");
                            if(op.equals("1")){
                                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();

                            }else  if(op.equals("21")){
                                Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                            }else if(op.equals("6")){
                                Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsda", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("orderid",id1 );
                map.put("s","8");
                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };

        queue.add(post);

    }

    public class ViewHold {

        TextView sale_zhuangtai,order_number,gouwu_tv,product_bn,gouwu_tv1,gouwu_tv2,gouwu_shuliang,produce_tv;
        ImageView gouwu_iv;
        Button cancell_order,payment,check_information;
    }

}
