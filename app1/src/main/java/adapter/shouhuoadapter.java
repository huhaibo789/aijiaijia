package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.example.administrator.myyushi.Newadd1Activity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.ShouhuoActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Food;
import bean.zhantingbean;
import bean.zuijinbean;
import request.LoadingDialog;
import utils.DBHelper1;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class shouhuoadapter extends BaseAdapter {
    private DBHelper1 help4;
   private ShouhuoActivity activity;
    private RequestQueue queue;
  private ArrayList<String> douzi=new ArrayList<>();
    private ArrayList<String> us_name1=new ArrayList<>();
    private  ArrayList<String> us_phone1=new ArrayList<>();
    private ArrayList<String>  us_pscsds1=new ArrayList<>();
    private ArrayList<String> us_address1=new ArrayList<>();
    private ArrayList<String> us_id=new ArrayList<>();
    private  ArrayList<String> us_is_default1=new ArrayList<>();
    Handler handle=new Handler();
    private List<Food> food;
    LoadingDialog dialog1;
    private String s1,s2,s3;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private Context context;
    private int num;
    private   String id1;
    String panduan="1";
    private ArrayList<String>  list2=new ArrayList<>();
   /* public   shouhuoadapter(Activity context, List<Food> food){
        super();
        this.context=context;
        this.food=food;
        inflater=LayoutInflater.from(context);
        //this.loader=loader;
    }*/
    public shouhuoadapter(ShouhuoActivity context,ArrayList<String> us_name1,ArrayList<String> us_phone1,ArrayList<String> us_pscsds1,ArrayList<String> us_address1,ArrayList<String> us_id,ArrayList<String> us_is_default1 ){
        super();
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.us_name1=us_name1;
        this.us_phone1=us_phone1;
        this.us_pscsds1=us_pscsds1;
        this.us_address1=us_address1;
        this.us_id=us_id;
        this.us_is_default1=us_is_default1;
        this.activity=context;
    }

    public static int temp = -1;


    @Override
    public int getCount() {
        return  us_name1.size();
    }

    @Override
    public Object getItem(int position) {
        return us_name1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
  public void  updateziliao(ArrayList<String> douzi){
      this.douzi=douzi;
  }
    public  void   updateziliao1(String s1,String s2,String s3){

        this.s1=s1;
        this.s2=s2;
        this.s3=s3;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_shouhuo1,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.shutv= (TextView) convertView.findViewById(R.id.shutv);
            vh.shutv1= (TextView) convertView.findViewById(R.id.shutv1);
            vh.shutv2= (TextView) convertView.findViewById(R.id.shutv2);
            vh.delete= (TextView) convertView.findViewById(R.id.delete);
            vh.binaji= (TextView) convertView.findViewById(R.id.binaji);
            vh.check= (CheckBox) convertView.findViewById(R.id.check);
            vh.xiangxiaddress= (TextView) convertView.findViewById(R.id.xiangxiaddress);
        }else {
            vh= (ViewHold) convertView.getTag();
        }
        postchangdu();
        final ViewHold finalVh = vh;
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(panduan.equals("2")){
                    Log.i("ff", "run: "+panduan);
                }else {
                    finalVh.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            id1=us_id.get(position);
                            // 建造者模式，将对象初始化的步骤抽取出来，让建造者来实现，设置完所有属性之后再创建对象
                            // 这里使用的Context必须是Activity对象
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            // 链式编程
                            builder.setTitle("提示")
                                    .setMessage("确认删除")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            postshuju1();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setCancelable(false); // 能否通过点击对话框以外的区域（包括返回键）关闭对话框
                            // 通过建造者创建Dialog对象
                            // AlertDialog dialog = builder.create();
                            // dialog.show();
                            // 以上两行代码可以简化成以下这一行代码
                            builder.show();

                        }
                    });
                }
            }
        },1000);

    vh.binaji.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id=us_id.get(position);
            Intent intent=new Intent(context,Newadd1Activity.class);
            intent.putExtra("ids",id);
            context.startActivity(intent);
          /*  if(Activity.class.isInstance(context)){
                Activity activity=(Activity)context;
                activity.finish();
            }*/
        }
    });
        vh.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postshuju();
            }

            private void postshuju() {
                final String id1=us_id.get(position);
                queue = Volley.newRequestQueue(context);
                String url = "https://api.aijiaijia.com/api_usershipping_setdefault";
                StringRequest post = new StringRequest(
                        StringRequest.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String str = response.toString().trim();
                                Log.i("jianjianrd", "onResponse: " + str);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(str);
                                    JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                    String result3 = resposeobject.getString("op");
                                    if (result3.equals("1")) {
                                        Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                                        if(context instanceof Activity){
                                            Activity activity= (Activity) context;
                                            activity.finish();
                                        }
                                        Intent intent=new Intent(context,ShouhuoActivity.class);

                                         context.startActivity(intent);
                                    } else if (result3.equals("6")) {
                                        Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
                                    }else if(result3.equals("0")){
                                        Toast.makeText(context, "设置失败", Toast.LENGTH_SHORT).show();
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
                        map.put("usid", id1);
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
        });
        String ss=us_is_default1.get(position);
        if(ss.equals("1")){
            vh.check.setChecked(true);
        }else {
            vh.check.setChecked(false);
        }
            vh.shutv.setText(us_name1.get(position));
            vh.shutv1.setText(us_phone1.get(position));
            vh.shutv2.setText(us_pscsds1.get(position));
            vh.xiangxiaddress.setText(us_address1.get(position));
            vh.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        if(temp!=-1){

                        }
                    }
                }
            });





        return convertView;
    }

    private void postchangdu() {
        queue = Volley.newRequestQueue(context);
        String url = "https://api.aijiaijia.com/api_usershipping_list";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjian", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {


                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                if (jsonarry.length() == 1) {
                                    panduan="2";
                                }else {
                                    panduan="1";
                                }

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

    private void postshuju1() {

        queue = Volley.newRequestQueue(context);
        String url="https://api.aijiaijia.com/api_usershipping_delete";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str=response.toString().trim();
                        Log.i("gydgd", "onResponse: "+str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if(result3.equals("1")){
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                if(context instanceof Activity){
                                    Activity activity= (Activity) context;
                                    activity.finish();
                                }
                                 Intent intent=new Intent(context,ShouhuoActivity.class);
                                context.startActivity(intent);

                            }else if(result3.equals("6")){
                                Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
                            }else if(result3.equals("0")){
                                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
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
                map.put("usid", id1);
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
       TextView shutv,shutv1,shutv2,delete,binaji,xiangxiaddress;
       CheckBox check;
    }

}
