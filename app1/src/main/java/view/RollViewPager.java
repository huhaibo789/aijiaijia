package view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import util.Myapp;


/**
 * Created by Administrator on 2016/6/27 0027.
 */
public class RollViewPager extends ViewPager {

    //传递过来的图片数组，这个必须更换，真实项目中有可能是一个集合
    private int[] imageUrls;
    private static final int NEXT=99;//切换下一张图片的标志
    private boolean isRunning=false;//是否自动轮播的标志，默认不自动轮播
    private ArrayList<String> pic=new ArrayList<>();
//    private BitmapUtils bitmapUtils;
    public RollViewPager(Context context) {
        super(context);
    }
    private ImageLoader loader;
    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageUrls(ArrayList<String> pic,ImageLoader loader) {
        this.pic=pic;
        this.loader=loader;
        setAdapter(new MyRollViePagerAdatper());
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case NEXT:
                    if(isRunning==true){
                        //设置当前item+1；相当于设置下一个item，然后余图片数量；
                        setCurrentItem(getCurrentItem()+1);
                        //然后发送空消息延时2秒
                        handler.sendEmptyMessageDelayed(NEXT,2000);
                    }
                    break;
            }
        }
    };

    //开始轮播
    public void startRoll(){
        Log.i("jieshou", "startRoll: "+"接受");
        //开启轮播
        isRunning=true;
        //发送handler延时2秒
        handler.sendEmptyMessageDelayed(NEXT,2000);
    }

    class MyRollViePagerAdatper extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这个必须取余数，不然会下标越界
            position=position%pic.size();
            ImageView imageView = (ImageView) View.inflate(getContext(), R.layout.activity_viewpager_item, null);
            //先获取网络中图片的url
//           在真实项目中使用谷歌官方提供的Glide加载图片
//            Glide.with(context).load(new File(path)).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(vh.imageView);
             loader.displayImage(pic.get(position),imageView);
            //imageView.setImageResource(imageUrls[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

    private int downTime=0;//按下时间
    //按下的XY坐标
    private int downX=0;
    private int downY=0;

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
                        onItemClickListener.onItemClick(getCurrentItem()%pic.size());//当前位置就是显示的条目
                    }
                }

                //开启轮播
                startRoll();
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.i("zhem", "onTouchEvent: "+"cancel");
                startRoll();
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

   @Override
    //当控件挂载到页面上会调用此方法
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

  /*  @Override
    //当控件从页面上移除的时候会调用此方法
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRunning=false;
        handler.removeMessages(NEXT);
    }*/

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}
