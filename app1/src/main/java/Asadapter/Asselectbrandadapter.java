package Asadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.WebviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import bean.Listbean;
import newfrage.InnerGridView;
import view.RollViewPager;

/**
 * Created by misshu on 2017/4/26/026.
 */
public class Asselectbrandadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private long mHour = 02;
    private long mMin = 15;
    private long mSecond = 36;
    private boolean isRun = true;
    private LayoutInflater inflater;
    //存放轮播图的线性布局
    private LinearLayout linearLayout;
    //存放指示点的线性布局
    private LinearLayout pointLinearLayout;
    private List<ImageView> pointList=new ArrayList<>();
    //上一个指示点
    private boolean flag=false;
    private int lastPosition=0;
    private ArrayList<String> mclink=new ArrayList<>();
    private ArrayList<String> pic=new ArrayList<>();
    RollViewPager rollViewPager;
    private Context mContext;
    private ImageLoader loader;
    private int[] mImgIds;
    private ArrayList<String> titlelist=new ArrayList<>();
    private ArrayList<String> pricelist=new ArrayList<>();
    private ArrayList<String> previouslist=new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListActivitylistBean> activitylist = new ArrayList<>();
    public Asselectbrandadapter(Context context, ImageLoader loader, ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist,
                                ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist ){
        this.mContext=context;
        inflater=LayoutInflater.from(context);
        this.loader=loader;
        this.biglist=biglist;
        this.courlist=courlist;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new Myholdertype(inflater.inflate(R.layout.layout_roll_view,parent,false));
                break;
            case 1:
                holder=new Myholdertype1(inflater.inflate(R.layout.activity_galley,parent,false));
                break;
            case 2:
                holder=new Myholdertype2(inflater.inflate(R.layout.activity_galleyproduct,parent,false));
                break;
            case 3:
                holder=new Myholdertype3(inflater.inflate(R.layout.activity_galleyhorizontally,parent,false));
                break;
            case 4:
                holder=new Myholdertype4(inflater.inflate(R.layout.activity_locationselectbrand,parent,false));
                break;
            case 5:
                holder=new Myholdertype5(inflater.inflate(R.layout.activity_brandshow,parent,false));
                break;
            case 6:
                holder=new Myholdertype6(inflater.inflate(R.layout.activity_galleynext,parent,false));
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else if(position==1){
            return 1;
        }else if(position==2){
            return 2;
        }else if(position==3){
            return 3;
        }else if(position==4){
            return 4;
        }else if(position==5){
            return 5;
        }else {
            return 6;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                final   Myholdertype   holder0= (Myholdertype) holder;
                if(!flag){
                    addaviewpager();
                }
                flag=true;
                break;
            case 1:
                final   Myholdertype1   holder1= (Myholdertype1) holder;
                final Handler timeHandler = getHandler(holder1);
                sendmessage(timeHandler);
                initdata();
                holder1.newgridview_select.setAdapter(new Asenewgridviewadapter(titlelist,pricelist,previouslist,loader,mContext));
                break;
            case 2:
                final   Myholdertype2  holder2= (Myholdertype2) holder;
                holder2.gridview_select.setAdapter(new Asbrandgridviewadapter(loader,mContext));
                break;
            case 3:
                final   Myholdertype3  holder3= (Myholdertype3) holder;
                holder3.gridviewhor.setAdapter(new Asbrandhorgridviewadapter(loader,mContext));
                break;
            case 4:
                final   Myholdertype4  holder4= (Myholdertype4) holder;
                holder4.gridviewlocation.setAdapter(new Aslocationselectbrand(loader,mContext));
                break;
            case 5:
                final   Myholdertype5  holder5= (Myholdertype5) holder;
                break;
            case 6:
                final Myholdertype6 holder6= (Myholdertype6) holder;
                if(biglist!=null){
                    holder6.gridviewrecommend.setAdapter(new Asbrandgridlastadapter((ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean>) biglist.get(position-5).getProductlist(),loader,mContext));
                }
                break;
        }
    }
    private void sendmessage(final Handler timeHandler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while ((isRun)){
                    try {
                        Thread.sleep(1000);
                        Message message = Message.obtain();
                        message.what=1;
                        timeHandler.sendMessage(message);
                    }catch (Exception e){

                    }
                }
            }
        }).start();
    }
    @NonNull
    private Handler getHandler(final Myholdertype1 holder1) {
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    computeTime();
                    if(mHour<10){
                        holder1.tv_hour.setText("0"+mHour+"");
                    }else {
                        holder1.tv_hour.setText(mHour+"");
                    }
                    if(mMin<10){
                        holder1.tv_minute.setText("0"+mMin+"");
                    }else {
                        holder1.tv_minute.setText(mMin+"");
                    }
                    if(mSecond<10){
                        holder1.tv_second.setText("0"+mSecond+"");
                    }else {
                        holder1.tv_second.setText(mSecond+"");
                    }
                }
            }
        };
    }
    private void computeTime() {
        mSecond--;
        if(mSecond<0){
            mMin--;
            mSecond=59;
            if(mMin<0){
                mMin=59;
                mHour--;
            }
        }
    }
    private void initdata() {
        pricelist.add("1999");
        previouslist.add("8659");
        pricelist.add("2999");
        previouslist.add("7699");
        pricelist.add("1799");
        previouslist.add("8599");
        pricelist.add("1899");
        previouslist.add("8999");
        pricelist.add("1599");
        previouslist.add("9999");
        titlelist.add("汉斯格雅26521400");
        titlelist.add("德国艾利秀三件套");
        titlelist.add("汉斯格雅2589623");
        titlelist.add("唯宝马桶");
        titlelist.add("艾利秀双面镜");
        mImgIds = new int[] { R.drawable.androidlogo, R.drawable.androidlogo, R.drawable.androidlogo,
                R.drawable.androidlogo, R.drawable.androidlogo
        };
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
    @Override
    public int getItemCount() {
        int count=biglist==null?0:biglist.size()+3;
        return count;
    }
    class Myholdertype extends  RecyclerView.ViewHolder{
        public Myholdertype(View itemView) {
            super(itemView);
            linearLayout= (LinearLayout)itemView.findViewById(R.id.top_news_viewpager_ll);
            pointLinearLayout= (LinearLayout)itemView.findViewById(R.id.dots_ll);
        }
    }
    class  Myholdertype1 extends RecyclerView.ViewHolder{
        private InnerGridView newgridview_select;
        private TextView tv_hour,tv_minute,tv_second,galley_tv;
        public Myholdertype1(View itemView) {
            super(itemView);
            galley_tv= (TextView) itemView.findViewById(R.id.galley_tv);
            newgridview_select= (InnerGridView) itemView.findViewById(R.id.newgridview_select);
            tv_hour= (TextView) itemView.findViewById(R.id.tv_hour);
            tv_minute= (TextView) itemView.findViewById(R.id.tv_minute);
            tv_second= (TextView) itemView.findViewById(R.id.tv_second);
        }
    }
    class Myholdertype2 extends  RecyclerView.ViewHolder{
        private InnerGridView   gridview_select;
        public Myholdertype2(View itemView) {
            super(itemView);
            gridview_select= (InnerGridView) itemView.findViewById(R.id.gridview_select);
        }
    }
    class Myholdertype3 extends  RecyclerView.ViewHolder{
        private  InnerGridView gridviewhor;
        public Myholdertype3(View itemView) {
            super(itemView);
            gridviewhor= (InnerGridView) itemView.findViewById(R.id.gridviewhor);

        }
    }
    class Myholdertype4 extends  RecyclerView.ViewHolder{
        private  InnerGridView gridviewlocation;
        public Myholdertype4(View itemView) {
            super(itemView);
            gridviewlocation= (InnerGridView) itemView.findViewById(R.id.gridviewlocation);

        }
    }
    class Myholdertype5 extends  RecyclerView.ViewHolder{
        private ImageView feipai,jilai,Ailixiu,brand_hs,brand_wb,kadewei;
        public Myholdertype5(View itemView) {
            super(itemView);
            feipai= (ImageView) itemView.findViewById(R.id.feipai);
            jilai= (ImageView) itemView.findViewById(R.id.jilai);
            Ailixiu= (ImageView) itemView.findViewById(R.id.Ailixiu);
            brand_hs= (ImageView) itemView.findViewById(R.id.brand_hs);
            brand_wb= (ImageView) itemView.findViewById(R.id.brand_wb);
            kadewei= (ImageView) itemView.findViewById(R.id.kadewei);
        }
    }
    class Myholdertype6 extends  RecyclerView.ViewHolder{
        private TextView recommend;
        private InnerGridView gridviewrecommend;
        public Myholdertype6(View itemView) {
            super(itemView);
            gridviewrecommend= (InnerGridView) itemView.findViewById(R.id.gridviewrecommend);
            recommend= (TextView) itemView.findViewById(R.id.recommend);
        }
    }
}
