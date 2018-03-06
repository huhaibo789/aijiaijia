package fragement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Urlutil.Utils;
import adapter.newsearchadapter;
import bean.Listbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import request.LoadingDialog;
import util.Myapp;
import utils.FileUtils24;
import utils.FileUtils26;
import view.RollViewPager;

/**
 * Created by 胡海波 on 2016/12/15.
 */
public class Searchfragement1 extends Fragment {
  @Bind(R.id.search_rcv)
    RecyclerView searchRcv;
    @Bind(R.id.imag_iv)
    ImageView imagiv;
    @Bind(R.id.but_nowifi)
    Button but_nowifi;
    @Bind(R.id.frage_tv)
    TextView frage_tv;
    @Bind(R.id.suoyou_ry)
    RelativeLayout suoyouRy;
    private newsearchadapter adapter;
    private RequestQueue queue;
    private ArrayList<JSONArray> list0 = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.AdvpicBean> advpiclist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean.AdvtitleBean> advtitlist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListActivitylistBean> activitylist = new ArrayList<>();
    private Context context;
    String phone;
    Handler handle=new Handler();
    ImageLoader loader;
    String password;
    LoadingDialog dialog;
    RollViewPager roll;
    private boolean flag=false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        qingqiu();
        initData();
        postdata();
        setlistener();
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void qingqiu() {
        FileUtils24 file=new FileUtils24();
        phone=file.readDataFromFile(context);
        FileUtils26 file1=new FileUtils26();
        password=file1.readDataFromFile(context);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String JSONDataUrl = "https://api.aijiaijia.com/api_login";
        //POST方式更加安全
        StringRequest stringrequest = new StringRequest(Request.Method.POST, JSONDataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        //就是在主线程上操作,弹出结果
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject resposeobject=jsonObject.getJSONObject("responseJson");
                            String ss=resposeobject.getString("op");
                            String ss1=resposeobject.getString("trsuid");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
            }
        }) {

            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("dish", "getParams: "+"daolema");
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", password);
                return map;
            }

            //重写parseNetworkResponse方法，返回的数据中 Set-Cookie:***************;
            //其中**************为session id
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                Response<String> superResponse = super
                        .parseNetworkResponse(response);
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                //Constant是一个自建的类，存储常用的全局变量
                Constant.localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                Log.d("sessionid", "sessionid----------------" + Constant.localCookie);
                return superResponse;
            }
        };
        requestQueue.add(stringrequest);
    }

    private void setlistener() {
        but_nowifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
               // adapter.notifyDataSetChanged();
            }
        });
    }
    private void postdata() {
        queue = Volley.newRequestQueue(context);
        String url = "https://url.aijiaijia.com/androidconfig.php";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            String result = jsonObject.getString("version");
                            String title = jsonObject.getString("title");
                            final String content = jsonObject.getString("content");
                            String btn = jsonObject.getString("btn");
                            final String btnlink = jsonObject.getString("btnlink");
                            String btnend = jsonObject.getString("btnend");
                            PackageManager manager;
                            PackageInfo info = null;
                            manager = context.getPackageManager();
                            try {
                                info = manager.getPackageInfo(context.getPackageName(), 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String ss = info.versionName;
                            if (!result.equals(ss)) {
                                // 建造者模式，将对象初始化的步骤抽取出来，让建造者来实现，设置完所有属性之后再创建对象
                                // 这里使用的Context必须是Activity对象
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                // 链式编程
                                builder.setTitle(title)
                                        .setMessage(content)
                                        .setPositiveButton(btn, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Uri uri = Uri.parse(btnlink);

                                              //  Uri uri = Uri.parse("https://url.aijiaijia.com/androidAPK/");
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton(btnend, new DialogInterface.OnClickListener() {
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {

        };

        queue.add(post);
    }

    private void setadapter() {
         loader = ((Myapp) getActivity().getApplication()).getLoader();
        adapter = new newsearchadapter(getActivity(), loader,biglist,activitylist,courlist);
        searchRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRcv.setAdapter(adapter);
    }

    private void initData() {
        getbiglist();
        //读取更多数据
       // getCourData();
        //读取列表数据
        //getProductData();
       handle.postDelayed(new Runnable() {
           @Override
           public void run() {
               getActivityData();
           }
       },1000);
    }
    private void getbiglist() {
        searchRcv.setVisibility(View.VISIBLE);
        suoyouRy.setVisibility(View.GONE);
        biglist.clear();
        BiZhiRequest<Listbean> request = new BiZhiRequest<Listbean>(Listbean.class, Utils.TYPE_MORE_URL, new Response.Listener<Listbean>() {
            @Override
            public void onResponse(Listbean response) {
                if (response != null && response.getResponseJson().getList_big() != null) {
                    biglist.addAll(response.getResponseJson().getList_big());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("frf", "onErrorResponse: "+error.toString());
                           if(suoyouRy!=null){
                               suoyouRy.setVisibility(View.VISIBLE);
                               searchRcv.setVisibility(View.GONE);
                               dialog.dismiss();
                           }
                    }
                });
        queue.add(request);
    }
    private void getActivityData() {
        searchRcv.setVisibility(View.VISIBLE);
       suoyouRy.setVisibility(View.GONE);
        activitylist.clear();
        courlist.clear();
        BiZhiRequest<Listbean> request = new BiZhiRequest<Listbean>(Listbean.class, Utils.TYPE_MORE_URL, new Response.Listener<Listbean>() {
            @Override
            public void onResponse(Listbean response) {
                if (response != null && response.getResponseJson().getList_activitylist() != null&&response.getResponseJson().getList_carousel()!=null) {
                    activitylist.addAll(response.getResponseJson().getList_activitylist());
                    courlist.addAll(response.getResponseJson().getList_carousel());
                    setadapter();
                    dialog.dismiss();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(searchRcv!=null){
                            searchRcv.setVisibility(View.GONE);
                            suoyouRy.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }

                    }
                });
        queue.add(request);
    }
    private void getCourData() {
        searchRcv.setVisibility(View.VISIBLE);
        suoyouRy.setVisibility(View.GONE);
        courlist.clear();
        BiZhiRequest<Listbean> request = new BiZhiRequest<Listbean>(Listbean.class, Utils.TYPE_MORE_URL, new Response.Listener<Listbean>() {
            @Override
            public void onResponse(Listbean response) {
                if (response != null && response.getResponseJson().getList_carousel() != null) {
                    courlist.addAll(response.getResponseJson().getList_carousel());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        suoyouRy.setVisibility(View.VISIBLE);
                        searchRcv.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
        queue.add(request);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragement1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        // 页面埋点
        StatService.onPause(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        // 页面埋点
        StatService.onResume(this);
    }
}
