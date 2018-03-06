package com.example.administrator.myyushi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.DataBean;
import bean.gouwu;
import bean.gouwu2;
import bean.gouwu3;
import bean.h5gouwu;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;
import util.Myapp;
import utils.DBHelper2;
import utils.DBHelper4;
import utils.DBHelper5;
import utils.DBHelper6;
import utils.FileUtils1;
import utils.FileUtils19;
import utils.FileUtils2;
import utils.FileUtils20;
import utils.FileUtils21;
import utils.FileUtils35;
import utils.Fileutils18;

public class GouwuActivity1 extends Activity implements View.OnClickListener {
    boolean flag1 = false;
    private static final int INITIALIZE = 0;
    private RequestQueue queue;
    private PullToRefreshListView mListView;// �б�
    private ListAdapter mListAdapter;// adapter
    private List<gouwu3> goudss = new ArrayList<>();
    private List<DataBean> mListData = new ArrayList<DataBean>();
    private CheckBox mCheckAll;
    private TextView mEdit;
    private Button yuyue_shop;
    private Button jiesuan;
    private TextView mPriceAll;
    private Handler handle = new Handler();
    LoadingDialog dialog;
    private JSONArray jsonArray;
    private double totalPrice = 0;
    private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();
    private ImageView back;
    private RelativeLayout gouwu_rl;
    private boolean flag = true;
    private ArrayList<String> logins1 = new ArrayList<>();
    private ArrayList<String> logins2 = new ArrayList<>();
    private ArrayList<String> logins3 = new ArrayList<>();
    private ArrayList<String> logins4 = new ArrayList<>();
    private ArrayList<String> logins5 = new ArrayList<>();
    private ArrayList<String> logins6 = new ArrayList<>();
    private ArrayList<String> logins7 = new ArrayList<>();
    private ArrayList<String> logins8 = new ArrayList<>();
    private ArrayList<String> h5skuid = new ArrayList<>();   //skuid
    private ArrayList<String> h5skuisok = new ArrayList<>();  //sku是否选择了安装服务
    private ArrayList<String> h5skute = new ArrayList<>();  //sku内容
    private ArrayList<String> newjihe = new ArrayList<>();
    private ArrayList<String> jishu = new ArrayList<>();
    private ArrayList<String> pide = new ArrayList<>();
    private ArrayList<String> number = new ArrayList<>();
    private ArrayList<String> discounttip = new ArrayList<>();//折扣集合
    private ArrayList<String> servicetip = new ArrayList<>();//服务集合
    List<gouwu2> gou2 = new ArrayList<>();
    private List<gouwu3> ll = new ArrayList<>();
    ImageLoader loader;
    int biaoshi = 1;
    boolean hh = false;
    private TextView go_guang;
    private RelativeLayout wushouhuo;
    private DBHelper2 helper2;
    private List<gouwu> gou = new ArrayList<>();
    private List<gouwu3> gou1 = new ArrayList<>();
    TextView all_xuanze;
    //定义hashMap 用来存放之前创建的每一项item
    HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    private List<h5gouwu> h5gou = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouwu1);
        ButterKnife.bind(this);
        dialog = new LoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        loader = ((Myapp) GouwuActivity1.this.getApplication()).getLoader();
        initView();
        postdata();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#fbd23a"), true);
    }
    private void postdata() {
        number.clear();
        queue = Volley.newRequestQueue(this);
        String url = "https://api.aijiaijia.com/api_shopcart";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("zhabisa", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("6")) {
                                Toast.makeText(GouwuActivity1.this, "请检查是否登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(GouwuActivity1.this, LoginActivity.class);
                                startActivity(intent);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            jsonArray = resposeobject.getJSONArray("list");
                            if (jsonArray.length() == 0) {
                                dialog.dismiss();
                                setContentView(R.layout.activvity_wugouwu);
                                go_guang = (TextView) findViewById(R.id.go_guang);
                                wushouhuo= (RelativeLayout) findViewById(R.id.wushouhuo);
                                go_guang.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(GouwuActivity1.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                wushouhuo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });
                                StatusBarCompat.setStatusBarColor(GouwuActivity1.this, Color.parseColor("#fbd23a"), true);
                            }
                            logins1.clear();
                            logins2.clear();
                            logins3.clear();
                            logins4.clear();
                            logins5.clear();
                            logins6.clear();
                            logins8.clear();
                            h5skuid.clear();
                            h5skuisok.clear();
                            h5skute.clear();
                            jishu.clear();
                            servicetip.clear();
                            discounttip.clear();
                            helper2 = new DBHelper2(GouwuActivity1.this);
                            gou = helper2.queryAll();
                            if (gou.size() != 0) {
                                DBHelper2 helper3 = new DBHelper2(GouwuActivity1.this);
                                for (int i = 0; i < gou.size(); i++) {
                                    helper3.delete(gou.get(i).get_id());
                                }
                            }
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String s1 = object.getString("product_pic");
                                logins1.add(s1);
                                String s2 = object.getString("num");
                                logins2.add(s2);
                                String s3 = object.getString("product_name");
                                logins3.add(s3);
                                String s4 = object.getString("skuprice");
                                logins4.add(s4);
                                String s5 = object.getString("skusellprice");
                                logins5.add(s5);
                                String serviceti = object.getString("stringtips");
                                servicetip.add(serviceti);
                                if(str.contains("stringtipdiscountorsave")){
                                    String zhekou = object.getString("stringtipdiscountorsave");
                                    discounttip.add(zhekou);
                                }
                                String s6 = object.getString("product_bn");
                                logins6.add(s6);
                                String s7 = object.getString("pid");
                                logins7.add(s7);
                                String s8 = object.getString("product_status");
                                logins8.add(s8);
                                String h5sku = object.getString("skuid");
                                h5skuid.add(h5sku);
                                String h5service = object.getString("isok");
                                h5skuisok.add(h5service);
                                String h5s = object.getString("skutext");
                                h5skute.add(h5s);
                                DBHelper2 helper8 = new DBHelper2(GouwuActivity1.this);
                                helper8.insert(new gouwu(s7, s1, s2, s3, s5, s4, h5sku, h5service, h5s));
                                gou = helper8.queryAll();
                                dialog.dismiss();
                            }
                            getData();
                            mListData.addAll(getData());
                            refreshListView();
                            initListener();
                            enableLoadMore();
                            setlistener2();
                            setPulltoRefreshStyle();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(GouwuActivity1.this, "网络不给力了", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        dialog.dismiss();
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
    private void setPulltoRefreshStyle() {
        ILoadingLayout il = mListView.getLoadingLayoutProxy();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = format1.format(curDate);
        il.setLastUpdatedLabel("最近更新：" + str);
        il.setLoadingDrawable(getResources().getDrawable(R.mipmap.ic_pulltorefresh_arrow));
        //设置下拉状态时的提示文字
        il.setPullLabel("下拉刷新");
        //设置正在刷新过程中的提示文字
        il.setRefreshingLabel("正在刷新");
        //设置松手提示文字
        il.setReleaseLabel("松开刷新");
    }
    private void setlistener2() {
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListAdapter.notifyDataSetChanged();
                        mListView.onRefreshComplete();
                    }
                }, 1000);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
    }

    private void enableLoadMore() {
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }
    private void initView() {
        all_xuanze = (TextView) findViewById(R.id.all_xuanze);
        back = (ImageView) findViewById(R.id.back);
        gouwu_rl= (RelativeLayout) findViewById(R.id.gouwu_rl);
        yuyue_shop = (Button) findViewById(R.id.yuyue_shop);
        jiesuan = (Button) findViewById(R.id.jiesuan);
        mCheckAll = (CheckBox) findViewById(R.id.check_box_all);
        mEdit = (TextView) findViewById(R.id.subtitle);
        mPriceAll = (TextView) findViewById(R.id.tv_cart_total);
        mListView = (PullToRefreshListView) findViewById(R.id.listview);
    }

    private void initListener() {
        mEdit.setOnClickListener(this);
        mCheckAll.setOnClickListener(this);
        gouwu_rl.setOnClickListener(this);
        jiesuan.setOnClickListener(this);
        all_xuanze.setOnClickListener(this);
    }

    private void refreshListView() {
        if (mListAdapter == null) {
            mListAdapter = new ListAdapter();
            mListView.setAdapter(mListAdapter);
            mListView.setOnItemClickListener(mListAdapter);
        } else {
            hh = true;
            mListAdapter.notifyDataSetChanged();
        }
    }

    private List<DataBean> getData() {
        int maxId = 0;
        jishu.clear();
        if (mListData != null && mListData.size() > 0)
            maxId = mListData.get(mListData.size() - 1).getId();
        List<DataBean> result = new ArrayList<DataBean>();
        DataBean data = null;
        for (int i = 0; i < logins3.size(); i++) {
            data = new DataBean();
            data.setId(maxId + i + 1);
            data.setShopName(logins3.get(i));
            data.setContent(logins6.get(i));
            data.setPicture(logins1.get(i));
            data.setIsok(h5skuisok.get(i));
            data.setSkuid(h5skuid.get(i));
            data.setSkutext(h5skute.get(i));
            data.setStatus(logins8.get(i));
            data.setIde(logins7.get(i));
            data.setStringtips(servicetip.get(i));
            if(discounttip.size()!=0){
                data.setShop_discount(discounttip.get(i));
            }
            data.setCarNum(Integer.parseInt(logins2.get(i)));
            if(logins4.get(i)!=null&&!logins4.get(i).equals("null")){
                Double d = Double.parseDouble(logins4.get(i));
                data.setPrice(d);
            }
            if (!logins8.get(i).equals("2")) {
                jishu.add(logins8.get(i));
            }
            if (logins5.get(i).equals("null") || logins5.get(i).equals("0")) {
                newjihe.add("0");
                Double d1 = Double.parseDouble(newjihe.get(i));
                data.setPreviousprice(d1);
            } else {
                newjihe.add(logins5.get(i));
                Double d2 = Double.parseDouble(newjihe.get(i));
                data.setPreviousprice(d2);
            }
            result.add(data);
        }
        Log.i("hahsda", "getData: " + h5skuid);
        return result;
    }

    boolean isSelect = false;

    private class ListAdapter extends BaseAdapter implements
            AdapterView.OnItemClickListener {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ViewHolder holder = null;

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view = convertView;
            if (lmap.get(position) == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(GouwuActivity1.this).inflate(
                        R.layout.cart_list_item, null);
                holder.checkBox = (CheckBox) view.findViewById(R.id.check_box);
                holder.image = (ImageView) view.findViewById(R.id.iv_adapter_list_pic);
                holder.shopName = (TextView) view.findViewById(R.id.gouwu_tv);
                holder.xx = (TextView) view.findViewById(R.id.xx);
                holder.content = (TextView) view.findViewById(R.id.tv_intro);
                holder.carNum = (TextView) view.findViewById(R.id.tv_num);
                holder.nowprice = (TextView) view.findViewById(R.id.tv_price);
                holder.previousprice = (TextView) view.findViewById(R.id.tv_price1);
                holder.sss = (TextView) view.findViewById(R.id.sss);
                holder.standard = (TextView) view.findViewById(R.id.standard);
                holder.sss1 = (TextView) view.findViewById(R.id.sss1);
                holder.servicetip = (TextView) view.findViewById(R.id.servicetip);  //服务费
                holder.shop_discount= (TextView) view.findViewById(R.id.shop_discount);//折扣
                view.setTag(holder);
            } else {
                view = lmap.get(position);
                holder = (ViewHolder) view.getTag();
            }
            final DataBean data = mListData.get(position);
            if (!hh) {
                if (!logins8.get(position).equals("2")) {
                    for (int i = 0; i < mListData.size(); i++) {
                        if (!logins8.get(i).equals("2")) {
                            pide.clear();
                            pide.add(data.getIde());
                            number.add(String.valueOf(data.getCarNum()));
                            mListData.get(i).setChoose(true);
                        }
                    }
                } else {
                    holder.checkBox.setClickable(false);
                }
                totalPrice = 0;
                jiesuan.setText("结算" + jishu.size());
                if (mListData != null && mListData.size() > 0) {
                    int dandan = 0;
                    for (int i = 0; i < mListData.size(); i++) {
                        if (!logins8.get(i).equals("2")) {
                            dandan++;
                            if (String.valueOf(mListData.get(i).getPreviousprice()).equals("0.0")) {
                                totalPrice = totalPrice + mListData.get(i).getCarNum()
                                        * mListData.get(i).getPrice();
                            } else {
                                totalPrice = totalPrice + mListData.get(i).getCarNum()
                                        * mListData.get(i).getPreviousprice();
                            }
                        }
                    }
                    DecimalFormat df = new DecimalFormat("0.00");
                    String newtotal = df.format(totalPrice);
                    mPriceAll.setText("￥" + newtotal);
                    FileUtils2 yy2 = new FileUtils2();
                    yy2.saveDataToFile(GouwuActivity1.this, String.valueOf(newtotal));
                }
            }
            bindListItem(holder, data);
            if (data != null && !data.getStatus().equals("2")) {
                if (data.isChoose()) {
                    holder.checkBox.setChecked(true);
                } else {
                    holder.checkBox.setChecked(false);
                }
                holder.checkBox.setOnClickListener(new CheckBoxOnClick(data));
            } else {
                holder.checkBox.setClickable(false);
            }
            return view;
        }

        class CheckBoxOnClick implements View.OnClickListener {
            DataBean shopcartEntity;

            public CheckBoxOnClick(DataBean shopcartEntity) {
                this.shopcartEntity = shopcartEntity;
            }

            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                if (cb.isChecked()) {
                    shopcartEntity.setChoose(true);
                } else {
                    shopcartEntity.setChoose(false);
                }
                count();
                select();
            }
        }

        private void bindListItem(final ViewHolder holder, DataBean data) {
            if (!data.getStatus().equals("2")) {
                holder.sss.setVisibility(View.INVISIBLE);
                holder.sss1.setVisibility(View.INVISIBLE);
                holder.checkBox.setClickable(true);
                loader.loadImage(data.getPicture(), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        holder.image.setImageBitmap(loadedImage);
                    }
                });
            } else {
                holder.xx.setTextColor(Color.parseColor("#ACACAC"));
                holder.content.setTextColor(Color.parseColor("#ACACAC"));
                holder.nowprice.setTextColor(Color.parseColor("#ACACAC"));
                holder.carNum.setTextColor(Color.parseColor("#ACACAC"));
                holder.shopName.setTextColor(Color.parseColor("#ACACAC"));
                holder.sss.setVisibility(View.VISIBLE);
                holder.sss1.setBackgroundResource(R.drawable.toumingyuan);
                loader.loadImage(data.getPicture(), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        holder.image.setImageBitmap(loadedImage);
                    }
                });
            }
            holder.shopName.setText(data.getShopName());
            holder.content.setText("产品编号:" + data.getContent());
            if (!data.getStringtips().equals("null")) {
                holder.servicetip.setVisibility(View.VISIBLE);
                holder.servicetip.setText("(" + data.getStringtips() + ")");
            } else {
                holder.servicetip.setVisibility(View.GONE);
            }
           /*  if(data.getShop_discount()!=null&&!data.getShop_discount().equals("null")){
                 holder.shop_discount.setVisibility(View.VISIBLE);
                 holder.shop_discount.setText("(" + data.getShop_discount() + ")");
             }else {
                 holder.shop_discount.setVisibility(View.GONE);
             }*/
            holder.standard.setText(data.getSkutext());
            holder.previousprice.setText("￥" + String.valueOf(data.getPrice()));
            holder.nowprice.setText(String.valueOf("￥" + data.getPreviousprice()));
            if (String.valueOf(data.getPreviousprice()).equals("0.0") || String.valueOf(data.getPreviousprice()).equals("null") || String.valueOf(data.getPreviousprice()).equals("0")) {
                holder.nowprice.setText("￥" + String.valueOf(data.getPrice()));
                // holder.previousprice.setVisibility(View.INVISIBLE);
            } else if (String.valueOf(data.getPrice()).equals(String.valueOf(data.getPreviousprice()))) {
                holder.nowprice.setText("￥" + String.valueOf(data.getPrice()));
                // holder.previousprice.setVisibility(View.INVISIBLE);
            } else {
                // holder.previousprice.setVisibility(View.VISIBLE);
                holder.nowprice.setText(String.valueOf("￥" + data.getPreviousprice()));
                holder.previousprice.setText("￥" + String.valueOf(data.getPrice()));
                // holder.previousprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            holder.carNum.setText(data.getCarNum() + "");
            int _id = data.getId();
            boolean selected = mSelectState.get(_id, false);
            holder.checkBox.setChecked(selected);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            String url2 = logins7.get(position - 1).toString();
           /* String url = url1 + "pid=" + url2;
            FileUtils fu = new FileUtils();
            fu.saveDataToFile(GouwuActivity1.this, url);*/
            Intent intent3 = new Intent(GouwuActivity1.this, h5detailsActivity.class);
            intent3.putExtra("uid", url2);
            GouwuActivity1.this.startActivity(intent3);
        }
    }
    class ViewHolder {
        CheckBox checkBox;
        TextView sss;
        TextView sss1;
        ImageView image;
        TextView shopName;
        TextView content;
        TextView carNum;
        TextView nowprice;
        TextView xx;
        TextView previousprice;
        TextView standard;
        TextView servicetip;
        TextView shop_discount;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.jiesuan:
                if (biaoshi == 0) {
                    Toast toast = Toast.makeText(GouwuActivity1.this, "请选择商品", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                } else {
                    deleteMsg3();
                    DBHelper4 hel4 = new DBHelper4(GouwuActivity1.this);
                    gou2 = hel4.queryAll();
                    if (gou2.size() != 0) {
                        for (int j = 0; j < gou2.size(); j++) {
                            hel4.delete(gou2.get(j).get_id());
                        }
                    }
                    DBHelper6 help6=new DBHelper6(GouwuActivity1.this);
                    h5gou=help6.queryAll();
                    if(h5gou.size()!=0){
                        for (int j = 0; j < h5gou.size(); j++) {
                            help6.delete(h5gou.get(j).get_id());
                        }
                    }
                    String ss = "请选择";
                    String ss1 = "Null";
                    String ss2 = "Null";
                    String aa = "没有使用积分";
                    FileUtils19 qq = new FileUtils19();
                    qq.saveDataToFile(GouwuActivity1.this, aa);
                    FileUtils21 kk1 = new FileUtils21();
                    kk1.saveDataToFile(GouwuActivity1.this, ss2);
                    FileUtils20 kk = new FileUtils20();
                    kk.saveDataToFile(GouwuActivity1.this, ss1);
                    Fileutils18 gg = new Fileutils18();
                    gg.saveDataToFile(GouwuActivity1.this, ss);
                    FileUtils35 fil = new FileUtils35();
                    fil.saveDataToFile(GouwuActivity1.this, "2");
                    Intent intent = new Intent(GouwuActivity1.this, h5BaseActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.subtitle:
                finish();
                Intent intent1 = new Intent(GouwuActivity1.this, ShopfinishActivity.class);
                startActivity(intent1);
                break;
            case R.id.check_box_all:
                totalPrice = 0;
                pide.clear();
                number.clear();
                if (mCheckAll.isChecked()) {
                    biaoshi = 1;
                    for (int i = 0; i < mListData.size(); i++) {
                        if (!logins8.get(i).equals("2")) {
                            jiesuan.setText("结算" + jishu.size());
                            mListData.get(i).setChoose(true);
                            if (mListData.get(i).isChoose()) {
                                pide.add(mListData.get(i).getIde());
                                number.add(String.valueOf(mListData.get(i).getCarNum()));
                                if (String.valueOf(mListData.get(i).getPreviousprice()).equals("0.0")) {
                                    totalPrice = totalPrice + mListData.get(i).getCarNum()
                                            * mListData.get(i).getPrice();
                                } else {
                                    totalPrice = totalPrice + mListData.get(i).getCarNum()
                                            * mListData.get(i).getPreviousprice();
                                }

                            }
                        }
                    }

                    hh = true;
                    mListAdapter.notifyDataSetChanged();
                    DecimalFormat df = new DecimalFormat("0.00");
                    String newtotal = df.format(totalPrice);
                    mPriceAll.setText("￥" + newtotal);
                    FileUtils2 yy2 = new FileUtils2();
                    yy2.saveDataToFile(GouwuActivity1.this, String.valueOf(newtotal));
                } else {
                    jiesuan.setText("结算" + "0");
                    biaoshi = 0;
                    pide.clear();
                    number.clear();
                    for (int i = 0; i < mListData.size(); i++) {
                        mListData.get(i).setChoose(false);
                        hh = true;
                        mListAdapter.notifyDataSetChanged();

                    }
                    mPriceAll.setText("￥" + String.valueOf(totalPrice));
                    FileUtils2 yy2 = new FileUtils2();
                    yy2.saveDataToFile(GouwuActivity1.this, String.valueOf(totalPrice));
                }
                break;
            case R.id.all_xuanze:
                if (mCheckAll.isChecked() == true) {
                    mCheckAll.setChecked(false);
                } else {
                    mCheckAll.setChecked(true);
                }
                totalPrice = 0;
                pide.clear();
                number.clear();
                if (mCheckAll.isChecked()) {
                    biaoshi = 1;
                    for (int i = 0; i < mListData.size(); i++) {
                        if (!logins8.get(i).equals("2")) {
                            jiesuan.setText("结算" + jishu.size());
                            mListData.get(i).setChoose(true);
                            if (mListData.get(i).isChoose()) {
                                pide.add(mListData.get(i).getIde());
                                number.add(String.valueOf(mListData.get(i).getCarNum()));
                                if (String.valueOf(mListData.get(i).getPreviousprice()).equals("0.0")) {
                                    totalPrice = totalPrice + mListData.get(i).getCarNum()
                                            * mListData.get(i).getPrice();
                                    Log.i("qingchu1", "onClick: " + totalPrice + "jhshd1" + i);
                                } else {
                                    totalPrice = totalPrice + mListData.get(i).getCarNum()
                                            * mListData.get(i).getPreviousprice();
                                    Log.i("qingchu", "onClick: " + totalPrice + "jhshd" + i);
                                }

                            }
                        }
                    }
                    hh = true;
                    mListAdapter.notifyDataSetChanged();
                    DecimalFormat df = new DecimalFormat("0.00");
                    String newtotal = df.format(totalPrice);
                    Log.i("sayangs", "onClick: " + totalPrice);
                    mPriceAll.setText("￥" + newtotal);
                    FileUtils2 yy2 = new FileUtils2();
                    yy2.saveDataToFile(GouwuActivity1.this, String.valueOf(newtotal));
                } else {
                    jiesuan.setText("结算" + "0");
                    biaoshi = 0;
                    pide.clear();
                    number.clear();
                    for (int i = 0; i < mListData.size(); i++) {
                        mListData.get(i).setChoose(false);
                        hh = true;
                        mListAdapter.notifyDataSetChanged();
                    }
                    mPriceAll.setText("￥" + String.valueOf(totalPrice));
                    FileUtils2 yy2 = new FileUtils2();
                    yy2.saveDataToFile(GouwuActivity1.this, String.valueOf(totalPrice));
                }
                break;
            case R.id.gouwu_rl:
                Intent intent5 = new Intent();
                setResult(15, intent5);
                finish();
                break;
            default:
                break;
        }
    }

    private void deleteMsg3() {
        int shuju = 0;
        DBHelper5 hell = new DBHelper5(this);
        gou1 = hell.queryAll();
        if (gou1.size() != 0) {
            for (int j = 0; j < gou1.size(); j++) {
                hell.delete(gou1.get(j).get_id());
            }
        }
        Log.i("hayuea", "deleteMsg3: " + mListData.size());
        if (mListData != null && mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                if (mListData.get(i).isChoose()) {
                    shuju++;
                    DBHelper5 helper5 = new DBHelper5(this);
                    helper5.insert(new gouwu3(mListData.get(i).getPicture(), mListData.get(i).getIde(), String.valueOf(mListData.get(i).getCarNum()), String.valueOf(mListData.get(i).getSkuid()), String.valueOf(mListData.get(i).getIsok())));
                }
            }
            Log.i("hahss", "deleteMsg3: " + mListData);
            FileUtils1 gg = new FileUtils1();
            gg.saveDataToFile(GouwuActivity1.this, String.valueOf(shuju));
        }
    }

    public void count() {
        int result = 0;
        totalPrice = 0;
        if (mListData != null && mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                if (mListData.get(i).isChoose()) {
                    result++;
                    if (String.valueOf(mListData.get(i).getPreviousprice()).equals("0.0")) {
                        totalPrice = totalPrice + mListData.get(i).getCarNum()
                                * mListData.get(i).getPrice();
                    } else {
                        totalPrice = totalPrice + mListData.get(i).getCarNum()
                                * mListData.get(i).getPreviousprice();
                    }
                }
            }
            jiesuan.setText("结算" + result);
            if (result == 0) {
                biaoshi = 0;
            } else {
                biaoshi = 1;
            }
            DecimalFormat df = new DecimalFormat("0.00");
            String newtotal = df.format(totalPrice);
            mPriceAll.setText("￥" + newtotal);
            FileUtils2 yy2 = new FileUtils2();
            yy2.saveDataToFile(GouwuActivity1.this, String.valueOf(newtotal));
        }
    }

    public void select() {
        int count = 0;
        for (int i = 0; i < mListData.size(); i++) {
            if (mListData.get(i).isChoose()) {
                count++;
                Log.i("ffr", "select: " + count);
            }
        }
        if (count == mListData.size()) {
            mCheckAll.setChecked(true);
            Log.i("ffr1", "select: " + count);
        } else {
            isSelect = true;
            mCheckAll.setChecked(false);
        }
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
