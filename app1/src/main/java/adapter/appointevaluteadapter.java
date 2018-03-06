package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.administrator.myyushi.appointevaluteActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.gouwu1;

/**
 * Created by 胡海波 on 2016/11/2.
 */
public class appointevaluteadapter extends BaseAdapter {
    private ArrayList<String> picture=new ArrayList<>();
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> ordersn=new ArrayList<>();
    private ArrayList<String> pid=new ArrayList<>();
    private RequestQueue queue;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private appointevaluteActivity  activity;
    private String data;
    private  String ss1;
    private String qubie;
    public  appointevaluteadapter  (appointevaluteActivity activity, ImageLoader loader,String qubie,ArrayList<String> pid,String data,ArrayList<String> picture,ArrayList<String> ordersn,
                                    ArrayList<String> title){
        super();
        this.activity=activity;
        this.loader=loader;
        this.picture=picture;
        this.title=title;
        this.ordersn=ordersn;
        this.data=data;
        this.pid=pid;
        this.qubie=qubie;
    }


    @Override
    public int getCount() {
        return picture.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(activity, R.layout.activity_appointyyuue,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.appointyuyue_pic= (ImageView) convertView.findViewById(R.id.appointyuyue_pic);
            vh.appointyuyue_sn= (TextView) convertView.findViewById(R.id.appointyuyue_sn);
            vh.appointyuyue_tv= (TextView) convertView.findViewById(R.id.appointyuyue_tv);
            vh.appoint_edit= (EditText) convertView.findViewById(R.id.appoint_edit);
            vh.appointyuyue_post= (Button) convertView.findViewById(R.id. appointyuyue_post);
        }else {
            vh= (ViewHold) convertView.getTag();
        }
        vh.appointyuyue_sn.setText("订单编号:"+ordersn.get(position));
        vh.appointyuyue_tv.setText(title.get(position));
        final ViewHold finalVh1 = vh;
        loader.loadImage(picture.get(position),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                finalVh1.appointyuyue_pic.setImageBitmap(loadedImage);
            }
        });
        final ViewHold finalVh = vh;
        vh.appoint_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              ss1= finalVh.appoint_edit.getText().toString();

            }
        });
        vh.appointyuyue_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("idmdded", "onClick: "+qubie);
                if(qubie.equals("1")){
                    postdata(position);
                }else if(qubie.equals("2")){
                    postdata1(position);
                }
              
            }
        });
        return convertView;
    }

    private void postdata1(final int position) {
        queue = Volley.newRequestQueue(activity);
        String url = "https://api.aijiaijia.com/api_comments_selladd";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfsws", "onResponse: post  success " + response);
                        String  ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(activity, "评价成功", Toast.LENGTH_SHORT).show();
                                Intent intent5 = new Intent();
                                intent5.putExtra("resultok", "ok");
                                activity.setResult(9, intent5);
                                activity.finish();
                            } else if (op.equals("21")) {
                                Toast.makeText(activity, "评价失败", Toast.LENGTH_SHORT).show();
                            }else if(op.equals("6")){
                                Toast.makeText(activity, "未登录", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                Log.i("idmsd", "getParams: "+data);
                map.put("oid",data);
                map.put("pid",pid.get(position));
                if(ss1!=null){
                    try {
                        String str=new String(ss1.getBytes("utf-8"),"ISO-8859-1");
                        map.put("commentarea",str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                map.put("generalrate","5");
                map.put("servicerate","5");
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

    private void postdata(final int position) {
        queue = Volley.newRequestQueue(activity);
        String url = "https://api.aijiaijia.com/api_comments_appointadd";
        final StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwudasfsws", "onResponse: post  success " + response);
                        String  ss = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(ss);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String op = resposeobject.getString("op");
                            if (op.equals("1")) {
                                Toast.makeText(activity, "成功", Toast.LENGTH_SHORT).show();
                            } else if (op.equals("21")) {
                                Toast.makeText(activity, "失败", Toast.LENGTH_SHORT).show();
                            }else if(op.equals("6")){
                                Toast.makeText(activity, "未登录", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("oid",data);
                map.put("pid",pid.get(position));
                if(ss1!=null){
                    map.put("commentarea",ss1);
                }
                map.put("generalrate","5");
                map.put("servicerate","5");
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
        ImageView appointyuyue_pic;
        TextView appointyuyue_sn;
        TextView appointyuyue_tv;
       EditText appoint_edit;
        Button appointyuyue_post;
    }

}
