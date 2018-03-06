package com.example.administrator.myyushi;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.gouwulistadapter1;
import bean.gouwu;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Myapp;
import utils.DBHelper2;
import utils.FileUtils7;

public class ShopfinishActivity extends AppCompatActivity {

    @Bind(R.id.shouhuo_iv2)
    ImageView shouhuoIv2;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    /* @Bind(R.id.bianji)
     TextView bianji;*/
    @Bind(R.id.shouhuo1)
    RelativeLayout shouhuo1;
    @Bind(R.id.xian1)
    View xian1;
    @Bind(R.id.gouwuche)
    ListView gouwuche;
    public TextView bianji;
    public Button shopfinsh_delete;
    ;
    public CheckBox allselect;
    public Button shoucang_finish;
    @Bind(R.id.all_xuan)
    TextView allXuan;
    private gouwulistadapter1 gouadapter1;
    private RequestQueue queue;
    private DBHelper2 helper2;
    private Handler handle = new Handler();
    private List<gouwu> gou = new ArrayList<>();
    private ArrayList<String> jihe = new ArrayList<>();
    private ArrayList<String> jihe1 = new ArrayList<>();
    private ArrayList<String> jihe2 = new ArrayList<>();
    private ArrayList<String> jihe3 = new ArrayList<>();
    private ArrayList<String> jihe4 = new ArrayList<>();
    private ArrayList<String> stock = new ArrayList<>();
    private ArrayList<String> status = new ArrayList<>();
    private ArrayList<String> pide=new ArrayList<>();
    private ArrayList<String> number=new ArrayList<>();
    private ArrayList<String> h5skutext=new ArrayList<>();
    private ArrayList<String> h5skuisok=new ArrayList<>();
    private ArrayList<String> h5skuid=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopfinish);
        ButterKnife.bind(this);
        bianji = (TextView) findViewById(R.id.bianji);
        allselect = (CheckBox) findViewById(R.id.allselect);
        shopfinsh_delete = (Button) findViewById(R.id.shopfinsh_delete);
        shoucang_finish = (Button) findViewById(R.id.shoucang_finish);
        postdata();
        initdata();
        setlistener();
        StatusBarCompat.setStatusBarColor(ShopfinishActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void postdata() {
        status.clear();
        stock.clear();
        h5skuid.clear();
        h5skuisok.clear();
        h5skutext.clear();
        queue = Volley.newRequestQueue(this);
        String url = "https://api.aijiaijia.com/api_shopcart";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("frggg", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            JSONArray jsonarry = resposeobject.getJSONArray("list");
                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject object = jsonarry.getJSONObject(i);
                                String s1 = object.getString("skustock");
                                String s2 = object.getString("product_status");
                                String h5sku=object.getString("skuid");
                                h5skuid.add(h5sku);
                                String h5skuwenben=object.getString("skutext");
                                h5skutext.add(h5skuwenben);
                                String h5isok=object.getString("isok");
                                h5skuisok.add(h5isok);
                                stock.add(s1);
                                status.add(s2);
                            }

                            setadapter();
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


    private void setlistener() {

        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMesg3();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Intent intent = new Intent(ShopfinishActivity.this, GouwuActivity1.class);
                        startActivity(intent);
                    }
                }, 1000);


            }
        });
        shouhuoIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        allXuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allselect.isChecked()) {
                    allselect.setChecked(true);
                    for (gouwu go : gou) {
                        go.setChecked(true);
                    }
                    gouadapter1.notifyDataSetChanged();
                }else {
                    allselect.setChecked(false);
                    for (gouwu go : gou) {
                        go.setChecked(false);
                    }
                    gouadapter1.notifyDataSetChanged();
                }
            }
        });

        allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allselect.isChecked()) {
                    for (gouwu go : gou) {
                        go.setChecked(false);
                    }
                    gouadapter1.notifyDataSetChanged();
                }
            }
        });
        allselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    for (gouwu go : gou) {
                        go.setChecked(true);
                    }
                    gouadapter1.notifyDataSetChanged();
                }
            }
        });
        shopfinsh_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allselect.isChecked()) {
                    showAlertDialog();
                } else {
                    boolean flag = false;
                    deleteMsg();
                    if (!flag) {
                        return;
                    }
                }

            }
        });
        shoucang_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag1 = false;
                deleteMsg2();
                if (!flag1) {
                    return;
                }

            }
        });

    }

    private void deleteMesg3() {

        List<gouwu> deleteList1 = new ArrayList<gouwu>();
        deleteList1.clear();
        for (gouwu go : gou) {

            if (go.isChecked()) {
                deleteList1.add(go);
            }
        }
        if (deleteList1.size() == 0) {
            return;
        } else {
            jihe2.clear();
            jihe4.clear();
            h5skuid.clear();
            h5skutext.clear();
            h5skuisok.clear();
            for (int i = 0; i < deleteList1.size(); i++) {
                helper2 = new DBHelper2(ShopfinishActivity.this);
                gou = helper2.queryAll();
                jihe2.add(deleteList1.get(i).getProductid());
                jihe4.add(deleteList1.get(i).getNum());
                h5skuid.add(deleteList1.get(i).getSkuid());
                h5skuisok.add(deleteList1.get(i).getSkuisok());
                h5skutext.add(deleteList1.get(i).getSkutext());
            }
            final String h5isok=h5skuisok.toString();
            String h5wenben=h5skutext.toString();
            int h5text=h5wenben.length()-1;
            int isoklength=h5isok.length()-1;
            String h5id=h5skuid.toString();
            int length=h5id.length()-1;
            String str = jihe2.toString();
            int len = str.length() - 1;
            String regex = "\\s*,\\s*";
            final String ids2 = str.substring(1, len).replaceAll(regex, ",");
            final String h5skuid=h5id.substring(1,length).replaceAll(regex,",");
            final String h5ok=h5isok.substring(1,isoklength).replaceAll(regex,",");
            final String h5skuwenben=h5wenben.substring(1,h5text).replaceAll(regex,",");
            FileUtils7 ff = new FileUtils7();
            ff.saveDataToFile(this, ids2);
            queue = Volley.newRequestQueue(ShopfinishActivity.this);
            String url = "https://api.aijiaijia.com/api_shopcart_save";
            StringRequest post = new StringRequest(
                    StringRequest.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        String ss=response.toString();
                            Log.i("halous", "onResponse: "+ss);
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            ) {
                //通过重写此对象的getParams方法设置请求条件
                //将所有的请求条件存入返回值的map对象中
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    FileUtils7 ss = new FileUtils7();
                    String ee = ss.readDataFromFile(ShopfinishActivity.this);
                    map.put("pids", ids2);
                  /*  FileUtils8  gg=new FileUtils8();
                    String aa=gg.readDataFromFile(ShopfinishActivity.this);*/
                    String aa = jihe4.toString();
                    Log.i("hur", "getParams: " + aa.toString());
                    int len1 = aa.length() - 1;
                    String regex = "\\s*,\\s*";
                    final String idnum = aa.substring(1, len1).replaceAll(regex, ",");
                    map.put("nums", idnum);
                    map.put("skuids",h5skuid);
                    map.put("isoks",h5ok);
                    try {
                        String str4=new String(h5skuwenben.getBytes("utf-8"),"ISO-8859-1");
                        map.put("skutexts",str4);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.i("heiheia", "getParams: "+map);
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


    }

    private void deleteMsg2() {
        List<gouwu> deleteList11 = new ArrayList<gouwu>();
        deleteList11.clear();
        for (gouwu go : gou) {

            if (go.isChecked()) {
                deleteList11.add(go);
            }
        }
        jihe3.clear();
        for (int i = 0; i < deleteList11.size(); i++) {
            helper2 = new DBHelper2(ShopfinishActivity.this);
            gou = helper2.queryAll();
            jihe3.add(deleteList11.get(i).getProductid());

        }
        String str = jihe3.toString();
        int len = str.length() - 1;
        String regex = "\\s*,\\s*";
        final String ids3 = str.substring(1, len).replaceAll(regex, ",");
        queue = Volley.newRequestQueue(ShopfinishActivity.this);
        String url = "https://api.aijiaijia.com/api_collection_add";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonobject = null;
                        try {
                            jsonobject = new JSONObject(str);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("0")) {
                                Toast.makeText(ShopfinishActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                Toast.makeText(ShopfinishActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("6")) {
                                Toast.makeText(ShopfinishActivity.this, "没有登录", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("shoucang2", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("pids", ids3);

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
       /* List<gouwu> deleteList = new ArrayList<gouwu>();
        for (gouwu go : gou) {

            if (go.isChecked()) {
                deleteList.add(go);
                Log.i("caoyy", "deleteMsg1: "+databaseList().toString());
            }
        }
        DBHelper3 helper3=new DBHelper3(ShopfinishActivity.this);
        for (int i = 0; i <deleteList.size() ; i++) {
            String s1=deleteList.get(i).getPicture().toString();
            String s2=deleteList.get(i).getBigtitle().toString();
            String s3=String.valueOf(deleteList.get(i).getNowprice().toString());
            String s4=String.valueOf(deleteList.get(i).getPreviousprice().toString());
            String s5=String.valueOf(deleteList.get(i).getNum());
            helper3.insert(new gouwu1(s1,s5,s2,s3,s4));
        }
        Toast.makeText(ShopfinishActivity.this, "已加入收藏", Toast.LENGTH_SHORT).show();*/
    }

    public void deleteMsg() {

        List<gouwu> deleteList = new ArrayList<gouwu>();
        deleteList.clear();
        for (gouwu go : gou) {

            if (go.isChecked()) {
                deleteList.add(go);
            }
        }
        jihe1.clear();
        for (int i = 0; i < deleteList.size(); i++) {
            helper2 = new DBHelper2(ShopfinishActivity.this);
            gou = helper2.queryAll();
            jihe1.add(deleteList.get(i).getProductid());

        }
        String str = jihe1.toString();
        int len = str.length() - 1;
        String regex = "\\s*,\\s*";
        final String ids2 = str.substring(1, len).replaceAll(regex, ",");
        FileUtils7 ff = new FileUtils7();
        ff.saveDataToFile(this, ids2);
        queue = Volley.newRequestQueue(ShopfinishActivity.this);
        String url = "https://api.aijiaijia.com/api_shopcart_delete";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonobject = null;
                        try {
                            jsonobject = new JSONObject(str);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("0")) {
                                Toast.makeText(ShopfinishActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("1")) {
                                Toast.makeText(ShopfinishActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                handle.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                        Intent intent = new Intent(ShopfinishActivity.this, GouwuActivity1.class);
                                        startActivity(intent);
                                    }
                                }, 500);
                            } else if (result3.equals("6")) {
                                Toast.makeText(ShopfinishActivity.this, "没有登录", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsdarwe", "onErrorResponse: " + error.getMessage());
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
                FileUtils7 ss = new FileUtils7();
                String ee = ss.readDataFromFile(ShopfinishActivity.this);
                map.put("pids", ids2);
                Log.i("ffgg", "getParams: " + ids2);
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

        /*for (int j = 0; j <deleteList.size() ; j++) {
            helper2=new DBHelper2(ShopfinishActivity.this);
            gou= helper2.queryAll();
            int ide=  gou.get(j).get_id();
            helper2.delete(ide);
            gou.remove(j);

        }
        //gou1.removeAll(deleteList);
        gouadapter1.setList(gou);*/
        gouadapter1.notifyDataSetChanged();


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

                        queue = Volley.newRequestQueue(ShopfinishActivity.this);
                        String url = "https://api.aijiaijia.com/api_shopcart_delete";
                        StringRequest post = new StringRequest(
                                StringRequest.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.i("fdsfrer", "onResponse: " + response);
                                        String str = response.toString().trim();
                                        JSONObject jsonobject = null;
                                        try {
                                            jsonobject = new JSONObject(str);
                                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                                            String result3 = resposeobject.getString("op");
                                            if (result3.equals("0")) {
                                                Toast.makeText(ShopfinishActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                            } else if (result3.equals("1")) {
                                                Toast.makeText(ShopfinishActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                handle.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        finish();
                                                        Intent intent = new Intent(ShopfinishActivity.this, GouwuActivity1.class);
                                                        startActivity(intent);
                                                    }
                                                }, 500);

                                            } else if (result3.equals("6")) {
                                                Toast.makeText(ShopfinishActivity.this, "没有登录", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("dsdarwe", "onErrorResponse: " + error.getMessage());
                                    }
                                }
                        ) {
                            //通过重写此对象的getParams方法设置请求条件
                            //将所有的请求条件存入返回值的map对象中
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();

                                for (int i = 0; i < gou.size(); i++) {

                                    jihe.add(gou.get(i).getProductid());


                                }
                                String str = jihe.toString();
                                int len = str.length() - 1;
                                //  String ids=str.substring(1,len).replaceAll("","").trim();
                                String regex = "\\s*,\\s*";
                                String ids1 = str.substring(1, len).replaceAll(regex, ",");
                                Log.i("casjdf", "getParams: " + ids1);

                               /* FileUtils7 ff3=new FileUtils7();
                                ff3.saveDataToFile(ShopfinishActivity.this,ids);
                                Log.i("hulala", "getParams: "+ids);*/
                                map.put("pids", ids1);


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

    private void setadapter() {
        ImageLoader loader = ((Myapp) ShopfinishActivity.this.getApplication()).getLoader();
        gouadapter1 = new gouwulistadapter1(ShopfinishActivity.this, loader, gou, stock, status);
        gouwuche.setAdapter(gouadapter1);

    }

    private void initdata() {
        helper2 = new DBHelper2(this);
        gou = helper2.queryAll();
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
