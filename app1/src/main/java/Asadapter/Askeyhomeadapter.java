package Asadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.HorizontalScrollViewAdapter;
import bean.Listbean;
import newfrage.InnerGridView;
import view.RollViewPager;
/**
 * Created by misshu on 2017/4/26/026.
 */
public class Askeyhomeadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    //存放轮播图的线性布局
    private LinearLayout linearLayout;
    //存放指示点的线性布局
    private LinearLayout pointLinearLayout;
    private List<ImageView> pointList=new ArrayList<>();
    //上一个指示点
    private int lastPosition=0;
    private ArrayList<String> mclink=new ArrayList<>();
    private ArrayList<String> pic=new ArrayList<>();
    RollViewPager rollViewPager;
    private Context mContext;
    private ImageLoader loader;
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListActivitylistBean> activitylist = new ArrayList<>();
    private HorizontalScrollViewAdapter mAdapter;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.androidlogo, R.drawable.androidlogo, R.drawable.androidlogo, R.drawable.androidlogo,
            R.drawable.androidlogo));
    public Askeyhomeadapter(Context context, ImageLoader loader, ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist,
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
                holder=new Myholdertype(inflater.inflate(R.layout.activity_horizatall,parent,false));
                break;
            case 1:
                holder=new Myholdertype1(inflater.inflate(R.layout.activity_askeyhometv,parent,false));
                break;
            case 2:
                holder=new Myholdertype2(inflater.inflate(R.layout.activity_lineargrid,parent,false));
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
                holder0.gridview.setAdapter(new Asnewkeyhomegridviewadapter(loader,mContext));
                break;
            case 1:
                final   Myholdertype1   holder1= (Myholdertype1) holder;
                break;
            case 2:
                final   Myholdertype2  holder2= (Myholdertype2) holder;
                holder2.gridview.setAdapter(new Asnewkeyhomelingridviewadapter(loader,mContext));
                break;
            case 3:
                final Myholdertype3 holder3= (Myholdertype3) holder;

                if(biglist!=null){
                    holder3.gridview.setAdapter(new Asnewgridfenlei((ArrayList<Listbean.ResponseJsonBean.ListBigBean.ProductlistBean>) biglist.get(position-2).getProductlist(),loader,mContext));
                }
                break;
        }
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
    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public int getItemCount() {
        int count=biglist==null?0:biglist.size()+2;
        return count;
    }
    class Myholdertype extends  RecyclerView.ViewHolder{
        private InnerGridView gridview;
        public Myholdertype(View itemView) {
            super(itemView);
            gridview= (InnerGridView) itemView.findViewById(R.id.gridview);
        }
    }
    class  Myholdertype1 extends RecyclerView.ViewHolder{
        private TextView as_tv;
        public Myholdertype1(View itemView) {
            super(itemView);
            as_tv= (TextView) itemView.findViewById(R.id.as_tv);
        }
    }
    class Myholdertype2 extends  RecyclerView.ViewHolder{
        private InnerGridView gridview;
        public Myholdertype2(View itemView) {
            super(itemView);
            gridview= (InnerGridView) itemView.findViewById(R.id.gridview);
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
