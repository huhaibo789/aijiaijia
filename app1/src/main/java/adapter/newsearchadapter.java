package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.HasproductActivity;
import com.example.administrator.myyushi.JifenActivity;
import com.example.administrator.myyushi.LoginActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.Sousuo2Activity;
import com.example.administrator.myyushi.WebviewActivity;
import com.example.administrator.myyushi.newsousuoaActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Listbean;
import bean.gouwu3;
import newfrage.InnerGridView;
import utils.FileUtils;
import utils.FileUtils24;
import utils.FileUtils6;

import view.Lunboviewpager;
import view.RollViewPager;
import view.SearchPagerGaoview;

/**
 * Created by 胡海波 on 2016/12/15.
 */
public class newsearchadapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //存放轮播图的线性布局
    private LinearLayout linearLayout;
    //存放指示点的线性布局
    private LinearLayout pointLinearLayout;
    //存放指示点的集合
    private List<ImageView> pointList=new ArrayList<>();
    //上一个指示点
    RollViewPager rollViewPager;
    private int lastPosition=0;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private Context mContext;
    Handler handle=new Handler();
    private RequestQueue queue;
    private    String result3;
    private boolean flag=false;
    private ArrayList<String> list=new ArrayList<>();
    private ArrayList<String> mclink=new ArrayList<>();
    private ArrayList<String> pic=new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListActivitylistBean> activitylist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    public newsearchadapter(Context context, ImageLoader loader,ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist,ArrayList<Listbean.ResponseJsonBean.ListActivitylistBean> activitylist,
                            ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist ){
        this.mContext=context;
        inflater=LayoutInflater.from(context);
        this.loader=loader;
        this.biglist=biglist;
        this.activitylist=activitylist;
        this.courlist=courlist;
    }
    public  void updatexin2(){
     rollViewPager.startRoll();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
        /*    case 0:
                holder=new MyHolderType(inflater.inflate(R.layout.activity_tablayout,parent,false));
                break;*/
            case 0:
               //holder=new MyHolderType0(inflater.inflate(R.layout.activity_zidingyi,parent,false));
                holder=new MyHolderType0(inflater.inflate(R.layout.layout_roll_view,parent,false));
                break;
            case 1:
                holder=new MyHolderType1(inflater.inflate(R.layout.activity_xiugaifragement,parent,false));
                break;
            case 2:
                holder=new MyHolderType2(inflater.inflate(R.layout.activity_danhansi,parent,false));
                break;
        }
        return holder;
    }
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else {
            return 2;
        }

    }
    @Override

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
          /*  case 0:
                String[] tabArr = {"精选品牌", "一键购家", "缤纷活动","蓝领先生"};
                MyHolderType newholdertype= (MyHolderType) holder;
                newholdertype.tab_layout.setSelectedTabIndicatorColor(Color.parseColor("#FBD23A"));
                newholdertype.tab_layout.setTabTextColors(Color.BLACK,Color.BLACK);
                newholdertype.tab_layout.setTabMode(newholdertype.tab_layout.MODE_FIXED);
                for (int i = 0; i < tabArr.length; i++) {
                    TabLayout.Tab tab = newholdertype.tab_layout.newTab()
                            .setText(tabArr[i])
                            .setTag(i);
                    newholdertype.tab_layout.addTab(tab);
                }*/
            case 0:
                final   MyHolderType0   holder0= (MyHolderType0) holder;
                if(!flag){
                    addaviewpager();
                }

                flag=true;
                break;
            case 1:
                final MyHolderType1 holder1= (MyHolderType1) holder;
                if(activitylist.size()!=0){
                    holder1.yinying7.setVisibility(View.VISIBLE);
                    holder1.yinying6.setVisibility(View.VISIBLE);
                    holder1.middle_radio.setVisibility(View.VISIBLE);
                    holder1.buju.setVisibility(View.VISIBLE);
                    holder1.radio_canguan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(mContext, HasproductActivity.class);
                            intent.putExtra("ww","1");
                            mContext.startActivity(intent);
                        }
                    });
                    holder1.radio_jifen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            queue = Volley.newRequestQueue(mContext);
                            String url = "https://api.aijiaijia.com/api_point";
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
                                                JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                                                result3 = resposeobject.getString("op");
                                                if(result3.equals("6")){
                                                    Intent intent10=new Intent(mContext,LoginActivity.class);
                                                    mContext.startActivity(intent10);
                                                }else {
                                                    Intent intent3=new Intent(mContext,JifenActivity.class);
                                                    mContext.startActivity(intent3);
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
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                                        HashMap<String, String> headers = new HashMap<String, String>();
                                        headers.put("cookie", Constant.localCookie);
                                        return headers;
                                    } else {
                                        return super.getHeaders();
                                    }
                                }
                            };
                            queue.add(post);
                        }
                    });
                    holder1.radio_huati.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE); String DEVICE_ID = tm.getDeviceId();
                            Intent intent3=new Intent(mContext,WebviewActivity.class);
                            FileUtils6 ff1=new FileUtils6();
                            String douzi1=ff1.readDataFromFile(mContext);
                            String douzi= "https://forum.aijiaijia.com/index.php?"+"mobile_device="+DEVICE_ID+"&&mobile_uid="+douzi1;
                            intent3.putExtra("name1",douzi);
                            intent3.putExtra("xingming","业内话题");
                            mContext.startActivity(intent3);
                        }
                    });
                    holder1.radio_kefu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url="https://api.aijiaijia.com/api_homepage";
                            String ww="首页";
                            String title = "在线客服";
                            // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                            // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                            ConsultSource source = new ConsultSource(url, ww, "custom information string");
                            // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                            Unicorn.openServiceActivity(mContext, // 上下文
                                    title, // 聊天窗口的标题
                                    source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                            );
                        }
                    });

                }else {
                    holder1.buju.setVisibility(View.INVISIBLE);
                    holder1.yinying7.setVisibility(View.INVISIBLE);
                    holder1.yinying6.setVisibility(View.INVISIBLE);
                    holder1.middle_radio.setVisibility(View.INVISIBLE);
                }

                if(activitylist.size()!=0){
                    holder1.yinying7.setVisibility(View.VISIBLE);
                    holder1.yinying6.setVisibility(View.VISIBLE);
                    holder1.middle_radio.setVisibility(View.VISIBLE);
                    holder1.buju.setVisibility(View.VISIBLE);
                    for (int i = 0; i <activitylist.size() ; i++) {
                        loader.loadImage(activitylist.get(0).getCategoriePic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder1.iv_tu.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(activitylist.get(3).getCategoriePic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder1.iv_tu1.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(activitylist.get(1).getCategoriePic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder1.iv_cu.setImageBitmap(loadedImage);
                            }
                        });
                        loader.loadImage(activitylist.get(2).getCategoriePic(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder1.iv_re.setImageBitmap(loadedImage);
                            }
                        });
                        holder1.iv_tu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent3=new Intent(mContext,WebviewActivity.class);
                                String douzi=activitylist.get(0).getMc_link();
                                FileUtils24 file=new FileUtils24();
                                String result=file.readDataFromFile(mContext);
                                FileUtils6  ff1=new FileUtils6();
                                String douzi1=ff1.readDataFromFile(mContext);

                                if(result!=null&&!result.equals("登录")){
                                    intent3.putExtra("name1",douzi+"?"+"uid="+douzi1);
                                    intent3.putExtra("color","绿色");
                                    mContext.startActivity(intent3);
                                }else {
                                    intent3.putExtra("name1",douzi);
                                    intent3.putExtra("color","绿色");
                                    mContext.startActivity(intent3);
                                }
                            }
                        });
                        holder1.iv_tu1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=activitylist.get(3).getMc_link();
                                String[] strArray=id.split("=");
                                String   name=strArray[strArray.length-1];
                                Intent intent3=new Intent(mContext,Sousuo2Activity.class);
                                intent3.putExtra("uid",name);
                                mContext.startActivity(intent3);
                            }
                        });
                        holder1.iv_cu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=activitylist.get(1).getMc_link();
                                String[] strArray=id.split("=");
                                String   name=strArray[strArray.length-1];
                                Intent intent3=new Intent(mContext,Sousuo2Activity.class);
                                intent3.putExtra("uid",name);
                                mContext.startActivity(intent3);

                            }
                        });
                        holder1.iv_re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=activitylist.get(2).getMc_link();
                                String[] strArray=id.split("=");
                                String   name=strArray[strArray.length-1];
                                Intent intent3=new Intent(mContext,Sousuo2Activity.class);
                                intent3.putExtra("uid",name);
                                mContext.startActivity(intent3);
                            }
                        });
                    }
                }else {
                    holder1.buju.setVisibility(View.INVISIBLE);
                    holder1.yinying7.setVisibility(View.INVISIBLE);
                    holder1.yinying6.setVisibility(View.INVISIBLE);
                    holder1.middle_radio.setVisibility(View.INVISIBLE);

                }
                break;
            case 2:
                final MyHolderType2 holder2= (MyHolderType2) holder;

                if(biglist!=null){
                    holder2.gridview.setAdapter(new newgridfenlei((ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean>) biglist.get(position-2).getProductlist(),loader,mContext));
                    if(biglist.get(position-2).getAdvpic()==null){
                        holder2.iv_hua.setVisibility(View.GONE);
                    }else {
                        holder2.iv_hua.setVisibility(View.VISIBLE);
                        loader.displayImage(biglist.get(position-2).getAdvpic().getCategoriePic(),holder2.iv_hua);
                        holder2.iv_hua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id= String.valueOf(biglist.get(position-2).getAdvpic().getMc_link());
                                String[] strArray=id.split("=");
                                String   name=strArray[strArray.length-1];
                                Intent intent3=new Intent(mContext,Sousuo2Activity.class);
                                intent3.putExtra("uid",name);
                                mContext.startActivity(intent3);
                            }
                        });
                    }
                    holder2.douzi_tv1.setText(biglist.get(position-2).getAdvtitle().getTitle());
                    holder2.douzi_tv2.setText(biglist.get(position-2).getAdvtitle().getSubtitle());
                    loader.displayImage(biglist.get(position-2).getAdvtitle().getCategoriePic(),holder2.iv_hua1);
                }
                break;
        }
    }

    private void addaviewpager() {
        lastPosition=0;
        pic.clear();
        mclink.clear();
        //第一步：添加轮播图（也可以直接将布局写成咱们自定义的viewpager）
       rollViewPager = new RollViewPager(mContext);
        linearLayout.addView(rollViewPager);
        //第二步：传递轮播图需要的图片url集合或者数组（在真实项目中我们需要获得图片的url集合，然后传递给轮播图）
        for (int i = 0; i <courlist.size() ; i++) {
            pic.add(courlist.get(i).getId());
            mclink.add(courlist.get(i).getMc_link());
        }
        rollViewPager.setImageUrls(pic,loader);

        //第三步：添加指示点
        addPoints();
        //第四步：给轮播图添加界面改变监听，切换指示点
        rollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                position=position%pic.size();
                //切换指示点
                pointList.get(lastPosition).setImageResource(R.mipmap.lunbonom);
                pointList.get(position).setImageResource(R.mipmap.lunbohigh);
                lastPosition=position;
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        //第五步：轮播图点击监听
        rollViewPager.setOnItemClickListener(new RollViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                 String result2=mclink.get(position);
                Intent intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra("name3", result2);
                mContext.startActivity(intent);
            }
        });

        //第六步：设置当前页面，最好不要写最大数除以2;
        rollViewPager.setCurrentItem(50 - 50 % pic.size());

        //第七步：设置完之后就可以轮播了：开启自动轮播
        rollViewPager.startRoll();
    }

    private void addPoints() {
        pointList.clear();
        pointLinearLayout.removeAllViews();
        for(int x=0;x<pic.size();x++){
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.mipmap.lunbonom);
            //导报的时候指示点的父View是什么布局就导什么布局的params,这里导的是线性布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置间隔
            params.leftMargin=36;
            //添加到线性布局;params指定布局参数，不然点就按在一起了
            pointLinearLayout.addView(imageView,params);
            //把指示点存放到集合中
            pointList.add(imageView);
        }
    }


    public int getItemCount() {
        int count=biglist==null?0:biglist.size()+2;
        return count;
    }


//  class  MyHolderType extends RecyclerView.ViewHolder{
//      private TabLayout tab_layout;
//      public MyHolderType(View itemView) {
//          super(itemView);
//          tab_layout= (TabLayout) itemView.findViewById(R.id.tab_layout);
//
//      }
//  }
    class MyHolderType0 extends  RecyclerView.ViewHolder{
       // SearchPagerGaoview gaoview1;
          // Lunboviewpager gaoview1;
        public MyHolderType0(View itemView) {
            super(itemView);
         linearLayout= (LinearLayout)itemView.findViewById(R.id.top_news_viewpager_ll);
            pointLinearLayout= (LinearLayout)itemView.findViewById(R.id.dots_ll);
       // gaoview1= (Lunboviewpager) itemView.findViewById(R.id.lunsearch);

        }
    }
    class MyHolderType1 extends RecyclerView.ViewHolder{
        private RadioButton radio_canguan,radio_jifen,radio_huati,radio_kefu;
        private ImageView iv_tu1,iv_tu2,iv_tu,iv_cu,iv_re;
        private LinearLayout buju;
        private  TextView yinying6,yinying7;
        private RadioGroup middle_radio;
        public MyHolderType1(View itemView) {
            super(itemView);
            radio_canguan= (RadioButton) itemView.findViewById(R.id.radio_canguan);
            radio_jifen= (RadioButton) itemView.findViewById(R.id.radio_jifen);
            radio_huati= (RadioButton) itemView.findViewById(R.id.radio_huati);
            radio_kefu= (RadioButton) itemView.findViewById(R.id.radio_kefu);
            iv_tu= (ImageView) itemView.findViewById(R.id.iv_tu);
            iv_tu1= (ImageView) itemView.findViewById(R.id.iv_tu1);
            iv_cu= (ImageView) itemView.findViewById(R.id.iv_cu);
            iv_re= (ImageView) itemView.findViewById(R.id.iv_re);
            buju= (LinearLayout) itemView.findViewById(R.id.buju);
            middle_radio= (RadioGroup) itemView.findViewById(R.id.middle_radio);
            yinying6= (TextView) itemView.findViewById(R.id.yinying6);
            yinying7= (TextView) itemView.findViewById(R.id.yinying7);
        }
    }
    class  MyHolderType2 extends RecyclerView.ViewHolder{
        private InnerGridView gridview;
        private ImageView  iv_hua,iv_hua1;
        private TextView  douzi_tv1,douzi_tv2;
        private ArrayList<TextView> hotTvs = new ArrayList<>();
        private ArrayList<TextView> hotTvs1 = new ArrayList<>();
        private ArrayList<TextView> hotTvs2 = new ArrayList<>();
        public MyHolderType2(View itemView) {
            super(itemView);
            gridview= (InnerGridView) itemView.findViewById(R.id.gridview);
            iv_hua= (ImageView) itemView.findViewById(R.id.iv_hua);
            iv_hua1= (ImageView) itemView.findViewById(R.id.iv_hua1);
            douzi_tv1= (TextView) itemView.findViewById(R.id.douzi_tv1);
            douzi_tv2= (TextView) itemView.findViewById(R.id.douzi_tv2);
        }
    }



}
