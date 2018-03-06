package com.example.administrator.myyushi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.githang.statusbar.StatusBarCompat;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.FruitAdapter;
import bean.gouwu;
import bean.gouwu1;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.DBHelper1;
import utils.DBHelper2;
import utils.DBHelper3;
import utils.FileUtils;
import utils.FileUtils7;

public class Shoucang1Activity extends Activity implements View.OnClickListener {

    public   CheckBox allselect1;
    public  Button shoucangDelete;
    public    Button shoucangShop;
    @Bind(R.id.shouhuo_iv3)
    ImageView shouhuoIv3;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    @Bind(R.id.bianji2)
    TextView bianji2;
    @Bind(R.id.shouhuo2)
    RelativeLayout shouhuo2;
    @Bind(R.id.list_shoucang1)
    ListView listShoucang1;
    @Bind(R.id.all_select)
    TextView allselect;
    private FruitAdapter shou1;
    private RequestQueue queue;
    private DBHelper3 helper3;
    private Handler handle=new Handler();
    private List<gouwu1> gou1 = new ArrayList<>();
    private ArrayList<String> loginsn=new ArrayList<>();
 private ArrayList<String> shou=new ArrayList<>();
    private  ArrayList<String> shou2=new ArrayList<>();
    private ArrayList<String> shou3=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang1);
        ButterKnife.bind(this);
        allselect1= (CheckBox) findViewById(R.id.allselect1);
        shoucangDelete= (Button) findViewById(R.id.shoucang_delete);

        postdata();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                initdata();
                setadapter();
                setlistener();

            }
        },1000);

        StatusBarCompat.setStatusBarColor(Shoucang1Activity.this, Color.parseColor("#fbd23a"), true);

    }

    private void postdata() {
        loginsn.clear();
        queue = Volley.newRequestQueue(this);
        String url="https://api.aijiaijia.com/api_collection";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("shoucang56fr", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        JSONObject  jsonobject=null;
                        try {
                            jsonobject=new JSONObject(str);
                            JSONObject resposeobject =   jsonobject.getJSONObject("responseJson");
                            JSONArray jsonarry=resposeobject.getJSONArray("list");
                            for (int i = 0; i <jsonarry.length() ; i++) {
                                JSONObject object=jsonarry.getJSONObject(i);
                                String zhuangtai=object.getString("product_status");
                                loginsn.add(zhuangtai);

                            }

                            setadapter();
                            shou1.notifyDataSetChanged();
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

    private void setadapter() {
        ImageLoader loader = ((Myapp) Shoucang1Activity.this.getApplication()).getLoader();
        shou1 = new FruitAdapter(this, loader, gou1,loginsn);
        listShoucang1.setAdapter(shou1);
    }


    private void setlistener() {
        listShoucang1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String url1=gou1.get(position).getProductid();
                Intent intent3=new Intent(Shoucang1Activity.this,h5detailsActivity.class);
                intent3.putExtra("uid",url1);
                Shoucang1Activity.this.startActivity(intent3);
            }
        });


        allselect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!allselect1.isChecked()){
                    for(gouwu1 go:gou1){
                        go.setChecked(false);
                    }
                    shou1.notifyDataSetChanged();
                }
            }
        });
        allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!allselect1.isChecked()){
                    allselect1.setChecked(true);
                    if(allselect1.isChecked()){
                        for(gouwu1 go:gou1){
                            go.setChecked(true);
                        }
                        shou1.notifyDataSetChanged();
                    }
                }else {
                    allselect1.setChecked(false);
                    if(!allselect1.isChecked()){
                        for(gouwu1 go:gou1){
                            go.setChecked(false);
                        }
                        shou1.notifyDataSetChanged();
                    }
                }
            }
        });
        allselect1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean  arg1) {
             if(arg1){
              for(gouwu1 go:gou1){
                  go.setChecked(true);
              }
                 shou1.notifyDataSetChanged();
             }
            }
        });
        shoucangDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allselect1.isChecked()){
                    showAlertDialog();
                }else {
                    boolean flag=false;
                    deleteMsg();
                    if(!flag){
                        return;
                    }
                }

            }
        });

     /*   allselect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allselect1.isChecked()) {
                    for (int i = 0; i < gou1.size(); i++) {
                        shou1.getIsSelected().put(i, true);
                       *//* gou1.get(i).isCheck=true;*//*
                    }


                } else {
                    for (int j = 0; j < gou1.size(); j++) {
                        shou1.getIsSelected().put(j, false);
                    }


                }
                shou1.notifyDataSetChanged();

            }
        });*/
     /*   shoucangDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int k = 0; k < gou1.size(); k++) {
                    if(shou1.getIsSelected().put(k,true)){
                        Toast.makeText(Shoucang1Activity.this, "请选择删除哪一项", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });*/
        shouhuoIv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Shoucang1Activity.this,ShoucangActivity.class);
                startActivity(intent);
            }
        });
        bianji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent intent=new Intent(Shoucang1Activity.this,ShoucangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showAlertDialog() {
        // 建造者模式，将对象初始化的步骤抽取出来，让建造者来实现，设置完所有属性之后再创建对象
        // 这里使用的Context必须是Activity对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 链式编程
        builder.setTitle("提示")
                .setMessage("确认删除所有清单")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        queue = Volley.newRequestQueue(Shoucang1Activity.this);
                        String url="https://api.aijiaijia.com/api_collection_delete";
                        StringRequest post = new StringRequest(
                                StringRequest.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.i("shcda", "onResponse: "+response);
                                      String aa=response.toString().trim();
                                        try {
                                            JSONObject  jsonobject=new JSONObject(aa);
                                            JSONObject object=jsonobject.getJSONObject("responseJson");
                                            String result3 = object.getString("op");
                                            if(result3.equals("0")){
                                                Toast.makeText(Shoucang1Activity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                            }else if(result3.equals("1")){
                                                Toast.makeText(Shoucang1Activity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                 postaquest();
                                            }else if(result3.equals("6")){
                                                Toast.makeText(Shoucang1Activity.this, "没有登录", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("dasf", "onErrorResponse: " + error.getMessage());
                                    }
                                }
                        ) {
                            //通过重写此对象的getParams方法设置请求条件
                            //将所有的请求条件存入返回值的map对象中
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();

                                for (int i = 0; i <gou1.size() ; i++) {

                                   shou.add(gou1.get(i).getProductid());


                                }
                                String str=shou.toString();
                                int len=str.length()-1;
                                //  String ids=str.substring(1,len).replaceAll("","").trim();
                                String regex="\\s*,\\s*";
                                String ids1= str.substring(1,len).replaceAll(regex,",");
                                Log.i("casjdf", "getParams: "+ids1);

                               /* FileUtils7 ff3=new FileUtils7();
                                ff3.saveDataToFile(ShopfinishActivity.this,ids);
                                Log.i("hulala", "getParams: "+ids);*/
                                map.put("pids",ids1);






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

                        initdata();
                        setadapter();
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

    private void postaquest() {
        helper3=new DBHelper3(this);
        gou1=helper3.queryAll();
        if(gou1.size()!=0){
            for (int i = 0; i <gou1.size() ; i++) {
                helper3.delete(gou1.get(i).get_id());
            }

        }
        gou1.clear();
        queue = Volley.newRequestQueue(this);
        String url="https://api.aijiaijia.com/api_collection";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("shoucang566", "onResponse: post  success " + response);
                        String str=response.toString().trim();
                        JSONObject  jsonobject=null;
                        try {
                            jsonobject=new JSONObject(str);
                            JSONObject resposeobject =   jsonobject.getJSONObject("responseJson");
                            JSONArray jsonarry=resposeobject.getJSONArray("list");
                            for (int i = 0; i <jsonarry.length() ; i++) {
                                JSONObject object=jsonarry.getJSONObject(i);
                                String dizhi=object.getString("product_pic");

                                Log.i("yuy1", "onResponse: "+dizhi);
                                String title=object.getString("product_name");

                                String producation=object.getString("product_bn");

                                String nowprice=object.getString("product_nowprice");

                                String price=object.getString("product_price");

                                String productid=object.getString("pid");

                                DBHelper3  helper9=new DBHelper3(Shoucang1Activity.this);
                                helper9.insert(new gouwu1(productid,dizhi,producation,title,nowprice,price));
                                gou1=helper9.queryAll();
                                Log.i("pidie1", "onResponse: "+gou1);


                            }
                            setadapter();
                            shou1.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       /* String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {

                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("6")) {
                                Toast.makeText(GouwuActivity.this, "请检查是否登录", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            jsonArray = resposeobject.getJSONArray("list");
                            Log.i("gouwu599", "onResponse: " + jsonArray);
                            logins1.clear();
                            logins2.clear();
                            logins3.clear();
                            logins4.clear();
                            logins5.clear();
                            logins6.clear();
                            helper2 = new DBHelper2(GouwuActivity.this);
                            gou = helper2.queryAll();

                            if (gou.size() != 0) {
                                DBHelper2  helper3 = new DBHelper2(GouwuActivity.this);
                                for (int i = 0; i < gou.size(); i++) {
                                    helper3.delete(gou.get(i).get_id());
                                }
                            }
                            gou.clear();
                            Log.i("douzi321", "initdata: " + gou.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String s1 = object.getString("product_pic");
                                logins1.add(s1);
                                Log.i("sdha", "onResponse: " + logins1);
                                String s2 = object.getString("num");
                                logins2.add(s2);
                                String s3 = object.getString("product_name");
                                logins3.add(s3);
                                String s4 = object.getString("product_price");
                                logins4.add(s4);
                                String s5 = object.getString("product_nowprice");
                                logins5.add(s5);
                                String s6 = object.getString("product_bn");
                                logins6.add(s6);
                                Log.i("cfafy", "onResponse: "+logins6);
                                String s7 = object.getString("pid");
                                DBHelper2   helper8 = new DBHelper2(GouwuActivity.this);
                                helper8.insert(new gouwu(s7, s1, s2, s3, s5, s4));
                                gou = helper8.queryAll();
                                Log.i("lalad", "onResponse: "+gou.size());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/



                        //gouadapter.notifyDataSetChanged();







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


    public void deleteMsg(){
        final List<gouwu1> deleteList = new ArrayList<gouwu1>();
        deleteList.clear();
        for (gouwu1 go : gou1) {

            if (go.isChecked()) {
                deleteList.add(go);
            }
        }
        shou2.clear();
        for (int i = 0; i <deleteList.size() ; i++) {
            Log.i("ococ", "deleteMsg: "+deleteList.size());
            Log.i("ococ1", "deleteMsg: "+deleteList.toString());
            helper3=new DBHelper3(Shoucang1Activity.this);
            gou1= helper3.queryAll();
            shou2.add(deleteList.get(i).getProductid());

        }
        Log.i("wuyuds", "deleteMsg: "+shou2.toString());
        String str=shou2.toString();
        Log.i("ffgg1", "deleteMsg: "+str);
        int len=str.length()-1;
        String regex="\\s*,\\s*";
        final String ids2= str.substring(1,len).replaceAll(regex,",");
        FileUtils7 ff=new FileUtils7();
        ff.saveDataToFile(this,ids2);
        queue = Volley.newRequestQueue(Shoucang1Activity.this);
        String url="https://api.aijiaijia.com/api_collection_delete";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("fdsfrers", "onResponse: "+response);
                        String aa=response.toString().trim();
                        try {
                            JSONObject  jsonobject=new JSONObject(aa);
                            JSONObject object=jsonobject.getJSONObject("responseJson");
                            String result3 = object.getString("op");
                            if(result3.equals("0")){
                                Toast.makeText(Shoucang1Activity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            }else if(result3.equals("1")){
                                Toast.makeText(Shoucang1Activity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                postaquest();
                                setadapter();
                                shou1.notifyDataSetChanged();

                            }else if(result3.equals("6")){
                                Toast.makeText(Shoucang1Activity.this, "没有登录", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsdarwed", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                            /*    gou.clear();*/
                               /* DBHelper2 helper=new DBHelper2(ShopfinishActivity.this);
                                gou=helper.queryAll();*/

                map.put("pids",ids2);
                Log.i("ffgg", "getParams: "+ids2);






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
        shou1.notifyDataSetChanged();




    }



    private void initdata() {
        helper3 = new DBHelper3(this);
        gou1 = helper3.queryAll();
        Log.i("dasf", "initdata: "+gou1.toString());
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(this);
    }
}
