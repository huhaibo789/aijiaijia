package view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
public class SearchPagerGaoview extends LinearLayout {
    private Searchpageradapter adapter;
    private ViewPager pager;
    private RadioGroup group;
    private LinearLayout ll_indicator;
    private LinearLayout ll_hottest_indicator;
    private Context context;
     private WebView webview2;
    private Runnable r;
    private Handler handle=new Handler();
    private boolean flag;
    private ArrayList<String >  arry=new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist;
    private  ArrayList<xiangqingbean.ResponseJsonBean.ListProductBean> xqbean;
    private ArrayList<View> pagerList=new ArrayList<>();
    private ImageLoader loader;
    private static final int NEXT=99;//切换下一张图片的标志
    private boolean isRunning=false;//是否自动轮播的标志，默认不自动轮播
    int bei2;
    int position8;
    private ImageView[] mBottomImages;
    private List<ImageView> mIndicator;

    private int downTime=0;//按下时间
    //按下的XY坐标
    private int downX=0;
    private int downY=0;
    public SearchPagerGaoview(Context context) {
        super(context);
        init(context);
    }
    public SearchPagerGaoview(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);

    }
    private void init(final Context context) {
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.search_more_item,this,true);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==0){
                    String result=arry.get(0).toString();
                    String result1=arry.get(1).toString();
                    String result2=arry.get(2).toString();
                    Intent intent=new Intent(getContext(), WebviewActivity.class);
                    intent.putExtra("name1",result);
                    intent.putExtra("name2",result1);
                    intent.putExtra("name3",result);
                    context.startActivity(intent);
                }
            }
        });
        initView(view);
        setadapter();
       setlistener();

    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                Log.i("zhem", "onTouchEvent: "+"DoWN");
                downX= (int) ev.getX();
                downY= (int) ev.getY();
                downTime= (int) System.currentTimeMillis();
                //停止轮播
                isRunning=false;
                handler.removeMessages(NEXT);
                break;

            case MotionEvent.ACTION_UP:
                Log.i("zhem", "onTouchEvent: "+"Up");
                int upX= (int) ev.getX();
                int upY= (int) ev.getY();
                int disX=Math.abs(upX - downX);
                int disY=Math.abs(upY - downY);
                int upTime=(int) System.currentTimeMillis();
                if(upTime-downTime<500 && disX-disY<5 ){
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(pager.getCurrentItem()%3);//当前位置就是显示的条目
                    }
                }

                //开启轮播
                startSelect();
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.i("zhem", "onTouchEvent: "+"cancel");
                startSelect();
                break;
            case  MotionEvent.ACTION_HOVER_MOVE:
                Log.i("zhem", "onTouchEvent: "+"MOve");
                downX= (int) ev.getX();
                downY= (int) ev.getY();
                downTime= (int) System.currentTimeMillis();
                //停止轮播
                isRunning=false;
                handler.removeMessages(NEXT);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("zhem", "onTouchEvent: "+"moren1");
                break;
            default:
                Log.i("zhem", "onTouchEvent: "+"moren");
                break;
        }

        return super.onTouchEvent(ev);
    }
    private void setlistener() {


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(final int position) {
                setIndicator(position);
              /* int total = mBottomImages.length;
                for (int j = 0; j < total; j++) {
                    if (j == position) {
                        mBottomImages[j].setBackgroundResource(R.mipmap.lunbohigh);
                    } else {
                        mBottomImages[j].setBackgroundResource(R.mipmap.lunbonom);
                    }
                }*/
                pager.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });
               // group.check(position%3);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private  void  initRadio(){
        mIndicator =new ArrayList<ImageView>();
        for (int i = 0; i <3 ; i++) {
            ImageView imag=new ImageView(context);
        imag.setId(i);
        imag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int bei=pager.getCurrentItem()/3;
                Log.i("cvb", "onClick: "+bei);
                pager.setCurrentItem(bei*3+v.getId());
                bei2=bei*3+v.getId();
            }
        });
            mIndicator.add(imag);
          LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 10, 0);
            imag.setLayoutParams(lp);
            ll_indicator.addView(imag);
    }
    }
    private void setIndicator(int position) {
        position %= 3;
        //遍历mIndicator重置src为normal
        for (ImageView indicator : mIndicator){
            indicator.setImageResource(R.mipmap.lunbonom);
        }
        mIndicator.get(position).setImageResource(R.mipmap.lunbohigh);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case NEXT:
                    if(isRunning==true){
                        //设置当前item+1；相当于设置下一个item，然后余图片数量；
                        pager.setCurrentItem(pager.getCurrentItem()+1);
                        //然后发送空消息延时2秒
                        handler.sendEmptyMessageDelayed(NEXT,2000);
                    }
                    break;
            }
        }
    };

    private void startSelect() {
        if (pagerList == null) {
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        } else {
            isRunning=true;
           /* //开启轮播
            isRunning=true;
            //发送handler延时2秒
            if(pager.getCurrentItem()+1==pagerList.size()){
                Log.i("ai", "startSelect: "+"到了");
                pager.setCurrentItem(0);
            }else {
                Log.i("ai", "startSelect: "+"到了1");
                pager.setCurrentItem(pager.getCurrentItem()+1);
            }
            handler.sendEmptyMessageDelayed(NEXT,2000);*/
        r = new Runnable() {
                @Override
                public void run() {
                    if(isRunning==true){
                        position8 = pager.getCurrentItem()+1 ;

                        if (position8 == pagerList.size() ) {
                            pager.setCurrentItem(0);
                            Log.i("gangan2", "run: "+pagerList.size());
                        } else {
                            pager.setCurrentItem(position8);
                            Log.i("cvb9", "run: "+ position8);
                        }
                    }

//                if (pagerList != null && position == pagerList.size()) {
//                    position = 0;
//                }

                    handle.postDelayed(this, 2000);
                }
            };
            handle.postDelayed(r, 2000);
        }
    }
   /* public  object instantiateItem(ViewGroup container, final int position){
        ((ViewPager)container).addView();
    }
*/
    private void setadapter() {
        adapter=new Searchpageradapter(context);
        pager.setAdapter(adapter);
        startSelect();
    }

    private void initView(View view) {
        pager= (ViewPager) view.findViewById(R.id.viewpager);
      /*  group= (RadioGroup) view.findViewById(R.id.radiogroup);*/
        ll_indicator=(LinearLayout)view.findViewById(R.id.ll_indicator);
   /*     ll_hottest_indicator= (LinearLayout) view.findViewById(R.id.ll_hottest_indicator);*/

       /* webview2= (WebView) view.findViewById(R.id.webview2);*/
        initRadio();
    }
    public  void updatePager(final ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist, ImageLoader loader){
       this.courlist=courlist;
        for (int i = 0; i < courlist.size(); i++) {
            arry.add(courlist.get(i).getMc_link());
        }

        for (int i = 0; i <courlist.size() ; i++) {
            final View view=LayoutInflater.from(context).inflate(R.layout.search_page_item,null);
            final ImageView iv1= (ImageView) view.findViewById(R.id.pager_iv1);
            iv1.setTag(courlist.get(i).getId());
            iv1.setScaleType(ImageView.ScaleType.FIT_XY);
            pagerList.add(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                  if (position8==2) {
                            String result2 = arry.get(2).toString();
                            Intent intent = new Intent(getContext(), WebviewActivity.class);
                            intent.putExtra("name3", result2);
                            context.startActivity(intent);


                    }else if(position8==1){
                      String result3 = arry.get(1).toString();
                      Intent intent = new Intent(getContext(), WebviewActivity.class);
                      intent.putExtra("name3", result3);
                      context.startActivity(intent);
                  }else {
                      String result3 = arry.get(0).toString();
                      Intent intent = new Intent(getContext(), WebviewActivity.class);
                      intent.putExtra("name3", result3);
                      context.startActivity(intent);
                  }








                }
            });
            adapter.setPagerList(pagerList,loader);
            adapter.notifyDataSetChanged();


        }

    }
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
