package nativetwoFragement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Asadapter.Asbluesiradapter;
import Urlutil.Utils;
import bean.Listbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import request.LoadingDialog;
import util.Myapp;
import utils.FileUtils24;
import utils.FileUtils26;

/**
 * Created by misshu on 2017/4/19/019.
 */
public class AsbulesirFragement extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.search_rcv)
    RecyclerView searchRcv;
    private String mParam1;
    private String mParam2;
    private Context context;
    private RequestQueue queue;
    String phone;
    String password;
    LoadingDialog dialog;
    ImageLoader loader;
    private Asbluesiradapter adapter;
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }
    public AsbulesirFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_asbluesir, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postdata();
        getbiglist();
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }



    private void getbiglist() {
        searchRcv.setVisibility(View.VISIBLE);
        biglist.clear();
        courlist.clear();
        BiZhiRequest<Listbean> request = new BiZhiRequest<Listbean>(Listbean.class, Utils.TYPE_MORE_URL, new Response.Listener<Listbean>() {
            @Override
            public void onResponse(Listbean response) {
                if (response != null && response.getResponseJson().getList_big() != null) {
                    biglist.addAll(response.getResponseJson().getList_big());
                    courlist.addAll(response.getResponseJson().getList_carousel());
                    setadapter();
                    dialog.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("frf", "onErrorResponse: "+error.toString());



                    }
                });
        queue.add(request);
    }

    private void setadapter() {
        loader = ((Myapp) getActivity().getApplication()).getLoader();
        adapter = new Asbluesiradapter(getActivity(), loader,biglist,courlist);
        searchRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRcv.setAdapter(adapter);
    }

    private void postdata() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
