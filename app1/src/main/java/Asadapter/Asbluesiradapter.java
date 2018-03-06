package Asadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public class Asbluesiradapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListActivitylistBean> activitylist = new ArrayList<>();
    public Asbluesiradapter(Context context, ImageLoader loader, ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist,
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
                holder=new Myholdertype1(inflater.inflate(R.layout.activity_image,parent,false));
                break;
            case 2:
                holder=new Myholdertype2(inflater.inflate(R.layout.activity_imagebutton,parent,false));
                break;
            case 3:
                holder=new Myholdertype3(inflater.inflate(R.layout.activity_teacher,parent,false));
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
        }else {
            return 3;
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
                break;
            case 2:
                final   Myholdertype2  holder2= (Myholdertype2) holder;
                break;
            case 3:
                final Myholdertype3 holder3= (Myholdertype3) holder;

                if(biglist!=null){
                    holder3.gridview.setAdapter(new Asnewgridfenlei((ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean>) biglist.get(position-2).getProductlist(),loader,mContext));
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
    @Override
    public int getItemCount() {
        int count=biglist==null?0:biglist.size()+2;
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
        private ImageView page_ye;
        public Myholdertype1(View itemView) {
            super(itemView);
            page_ye= (ImageView) itemView.findViewById(R.id.page_ye);
        }
    }
    class Myholdertype2 extends  RecyclerView.ViewHolder{
        private Button superstart_button;
        private Button install;
        public Myholdertype2(View itemView) {
            super(itemView);
            install= (Button) itemView.findViewById(R.id.install);
            superstart_button= (Button) itemView.findViewById(R.id.superstart_button);
        }
    }
    class Myholdertype3 extends  RecyclerView.ViewHolder{
        private InnerGridView gridview;
        public Myholdertype3(View itemView) {
            super(itemView);
            gridview= (InnerGridView) itemView.findViewById(R.id.gridview);

        }
    }
}
