package view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.SecondActivity;
import com.example.administrator.myyushi.WebviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import adapter.Searchpageradapter;
import bean.Listbean;
import bean.xiangqingbean;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class Lunboviewpager extends LinearLayout {
    private ArrayList<String> mclink=new ArrayList<>();
    private ArrayList<String> pic=new ArrayList<>();
    //存放轮播图的线性布局
    private LinearLayout linearLayout;
    //存放指示点的线性布局
    private LinearLayout pointLinearLayout;
    //存放指示点的集合
    private List<ImageView> pointList=new ArrayList<>();
    //上一个指示点
    private Context context;
    private int lastPosition=0;
    private ImageLoader loader;
    ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist=new ArrayList<>();

    public Lunboviewpager(Context context) {
        super(context);
        init(context);
    }

    public Lunboviewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Lunboviewpager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    /*public  Lunboviewpager(Context context) {
        super(context);
        init(context);
    }
    public  Lunboviewpager(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);

    }*/
    private void init(final Context context) {
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.layout_roll_view,this,true);
        lastPosition=0;
        linearLayout= (LinearLayout) view.findViewById(R.id.top_news_viewpager_ll);
        pointLinearLayout= (LinearLayout) view.findViewById(R.id.dots_ll);
        //第一步：添加轮播图（也可以直接将布局写成咱们自定义的viewpager）
        RollViewPager rollViewPager = new RollViewPager(context);
        linearLayout.addView(rollViewPager);
        //第二步：传递轮播图需要的图片url集合或者数组（在真实项目中我们需要获得图片的url集合，然后传递给轮播图）
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
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("name3", result2);
              context.startActivity(intent);
            }
        });

        //第六步：设置当前页面，最好不要写最大数除以2，其实写了50就足够了，谁没事无聊到打开app二话不说直接对着轮播图往相反方向不停的划几十下
        rollViewPager.setCurrentItem(50 - 50 %pic.size());

        //第七步：设置完之后就可以轮播了：开启自动轮播
        rollViewPager.startRoll();
    }



    /* public  object instantiateItem(ViewGroup container, final int position){
         ((ViewPager)container).addView();
     }
 */

    public  void updatePager(final ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist, ImageLoader loader){

        this.courlist=courlist;
        this.loader=loader;
        mclink.clear();
        pic.clear();
        for (int i = 0; i < courlist.size(); i++) {
            mclink.add(courlist.get(i).getMc_link());
            pic.add(courlist.get(i).getId());
        }

    }

    private void addPoints() {
        pointList.clear();
        pointLinearLayout.removeAllViews();
        for(int x=0;x<pic.size();x++){
            ImageView imageView = new ImageView(context);
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

}
