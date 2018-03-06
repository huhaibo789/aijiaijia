package fragement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.example.administrator.myyushi.MainActivity;
import com.example.administrator.myyushi.NewsousuoActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Sousuo2Activity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Urlutil.Utils;
import adapter.new1searchadapter;
import adapter.newfenleiadapter;
import adapter.newsearchadapter;
import adapter.normallistadapter;
import bean.fenleibean;
import butterknife.Bind;
import butterknife.ButterKnife;
import newfrage.GridViewData;
import newfrage.InnerGridView;
import newfrage.ListviewData;
import request.BiZhiRequest;
import request.LoadingDialog;
import util.Myapp;

/**
 * Created by 胡海波 on 2016/12/20.
 */
public class newfenleifragement1 extends Fragment {

    //GridViewAdapter ss;
    @Bind(R.id.imag_iv)
    ImageView imagIv;
    @Bind(R.id.frage_tv)
    TextView frageTv;
    @Bind(R.id.but_nowifi)
    Button butNowifi;
    @Bind(R.id.suoyou_ry)
    RelativeLayout suoyouRy;
    @Bind(R.id.newlistview_lvd)
    ListView newlistviewLvd;
    @Bind(R.id.new1griedvi_rcv)
    RecyclerView new1griedviRcv;
    @Bind(R.id.newzonghe_lyd)
    LinearLayout newzongheLyd;
    private  ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist3=new ArrayList<>();
    private  ArrayList<fenleibean.ResponseJsonBean.ListBigBean> biglist4=new ArrayList<>();
    private List<ListviewData> listData = new ArrayList<ListviewData>();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list3 = new ArrayList<String>();
    ArrayList<String> tuijiane = new ArrayList<>();
    ArrayList<String> tupian = new ArrayList<>();
    ArrayList<String> wenbensa = new ArrayList<>();
    ArrayList<String> lianjie = new ArrayList<>();
    LoadingDialog dialog;
    String result = "1";
    String resultno;
    float width;
    private new1searchadapter newadapter;
    private ImageLoader loader;
    private normallistadapter normaladapter;
    private newfenleiadapter adapter7;
    private RequestQueue queue;
    private ArrayList<String> list1 = new ArrayList<>();
    ListviewData data;
    private int imaPosition;//选中的
    private Context context;
    private Handler handle = new Handler();
    private ArrayList<String> firstpic = new ArrayList<>();
    private String sds;
    private boolean flag = false;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);

    }



    public interface ItemCallback {
        void item(String s);
    }

    ItemCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = ((Myapp) getActivity().getApplication()).getLoader();
        queue = Volley.newRequestQueue(getActivity());
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = displayMetrics.density; //得到密度
         width = displayMetrics.widthPixels;//得到宽度
        float height = displayMetrics.heightPixels;//得到高度
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_newfen1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getsub();
        setlistener();
    }

    private void getsub() {
        suoyouRy.setVisibility(View.GONE);
        newzongheLyd.setVisibility(View.VISIBLE);
        biglist3.clear();
        biglist4.clear();
        tuijiane.clear();
        BiZhiRequest<fenleibean> request=new BiZhiRequest<fenleibean>(fenleibean.class, Utils.TYPE_FENLEI_URL, new Response.Listener<fenleibean>() {
            @Override
            public void onResponse(fenleibean response) {
                if(response!=null&&response.getResponseJson().getList_big()!=null){
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    },500);

                    biglist3.addAll(response.getResponseJson().getList_big());
                    for (int i = 1; i < biglist3.size(); i++) {
                           biglist4.add(biglist3.get(i));

                    }
                    for (int j = 0; j <biglist3.size() ; j++) {
                        tuijiane.add(biglist3.get(j).getCategorieName());
                    }
                    setadapter();

                }
               if(tuijiane.size()!=0){
                   normaladapter = new normallistadapter(getActivity(), result);
                   normaladapter.upshuju(tuijiane);
                   newlistviewLvd.setAdapter(normaladapter);
               }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        suoyouRy.setVisibility(View.VISIBLE);
                        newzongheLyd.setVisibility(View.GONE);
                    }
                });
        queue.add(request);
    }
    private void setadapter() {
        ImageLoader loader = ((Myapp) getActivity().getApplication()).getLoader();
        newadapter = new new1searchadapter(getActivity(), loader,biglist3,biglist4,width);
        new1griedviRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        new1griedviRcv.setAdapter(newadapter);
    }
    private void setlistener() {
        butNowifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getsub();
            }
        });
        newlistviewLvd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                normaladapter.setSelectItem(arg2);
                imaPosition = arg2;
                new1griedviRcv.smoothScrollToPosition(arg2);
                normaladapter.notifyDataSetChanged();
            }
        });
        new1griedviRcv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int firstVisibleItem, int dy) {
                super.onScrolled(recyclerView, firstVisibleItem, dy);

            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (new1griedviRcv.getScrollState() == 1) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取最后一个可见view的位置
                        int lastItemPosition = linearManager.findLastVisibleItemPosition();
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        normaladapter.setSelectItem(firstItemPosition);
                        imaPosition = firstItemPosition;
                        normaladapter.notifyDataSetChanged();


                    }
                }

            }
        });

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
