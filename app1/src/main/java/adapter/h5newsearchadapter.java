package adapter;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.h5newSousuoActivity;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import bean.newsanji;
import bean.newsanji1;
import request.LoadingDialog;


public class h5newsearchadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,
        PopupWindow.OnDismissListener {

    private h5newSousuoActivity activity;
    private RequestQueue queue;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private ImageView icon1, icon2, icon3;
    private ListView lv1, lv2,lv3, lv4;
    private PopupWindow popLeft,popMid,popRight;
    private List<newsanji> fatlist;
    private List<newsanji1> sonlist,sonlist1,sonlist2,sonlist3,sonlist4;
    private List<newsanji1> right;
    private List<newsanji1> mid;
    private int screenWidth;
    private int screenHeight;
    private MyAdapterLeft adapterleft;
    private MyAdapterSon adapterleftson;
    private MyAdapterRight adapterRight;
    private int imaPosition;//选中的
    private PullToRefreshGridView gridview2;
    private JSONArray jsonarry;
    private ArrayList<String> name=new ArrayList<>();
    private ArrayList<String> brand=new ArrayList<>();
    private ArrayList<String> dsid=new ArrayList<>();
    private ArrayList<String> id1=new ArrayList<>();
    private Handler handle=new Handler();
    private ArrayList<String> picture=new ArrayList<>();
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> nowprice=new ArrayList<>();
    private ArrayList<String> beforeprice=new ArrayList<>();
    int  currentpage=1;
    private   String data;
    private  String aa="0";
    private  String bb="0";
    private String cc="0";
    String xinjieguo="2";
    private sanjisousuo adapter5;
    private  String result="品牌";
    private   String ss=null;
    private String ss1=null;
    private  String jieguo;
    ImageView shoptu_iv;
    LoadingDialog dialog;
    private ArrayList<String> jihename=new ArrayList<>();
    private ArrayList<String> fenggename=new ArrayList<>();
    private ArrayList<String> userid=new ArrayList<>();
    private boolean falg;
    public h5newsearchadapter(h5newSousuoActivity context, ImageLoader loader){
        inflater=LayoutInflater.from(context);
        this.loader=loader;
        this.activity=context;
        queue = Volley.newRequestQueue(activity);
    }
    public  void setinfo(String aa){
        this.data=aa;


    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                holder=new DmyHolderType0(inflater.inflate(R.layout.h5upload,parent,false));

                break;
            case 1:
                    holder=new cDmyHolderType1(inflater.inflate(R.layout.activity_h5download,parent,false));
                    break;

        }
        return holder;
    }

    private void setlistener() {

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return  0;
        }else {
            return 1;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
               final DmyHolderType0  holder0= (DmyHolderType0) holder;
               holder0.xiala_iv.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                      holder0.click_showtv.setVisibility(View.VISIBLE);
                       holder0.content_tv.setVisibility(View.GONE );
                   }
               });
                break;
            case  1:
                final cDmyHolderType1 holder1= (cDmyHolderType1) holder;
                break;


        }
    }










    @Override
    public int getItemCount() {

        return 2


                ;

    }


    public void onConfigurationChanged(Configuration newConfig) {
        activity.onConfigurationChanged(newConfig);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDismiss() {

    }

    class DmyHolderType0  extends RecyclerView.ViewHolder{
       private ImageView show_iv,logo_iv,xiala_iv;
        private TextView miaoxu_tv,scripe_tv,content_tv,click_showtv;

        public DmyHolderType0(View itemView) {
            super(itemView);
            gridview2= (PullToRefreshGridView) itemView.findViewById(R.id.gridview2);
            show_iv= (ImageView) itemView.findViewById(R.id.show_iv);
            logo_iv= (ImageView) itemView.findViewById(R.id.logo_iv);
            xiala_iv= (ImageView) itemView.findViewById(R.id.xiala_iv);
            miaoxu_tv= (TextView) itemView.findViewById(R.id.miaoxu_tv);
            scripe_tv= (TextView) itemView.findViewById(R.id.scripe_tv);
            content_tv= (TextView) itemView.findViewById(R.id.content_tv);
            click_showtv= (TextView) itemView.findViewById(R.id.click_showtv);
        }
    }
    class cDmyHolderType1  extends RecyclerView.ViewHolder{
        private TextView tishi_newsou,fenlei, diqu, paixu;
        private  LinearLayout ll_top;
        private RelativeLayout ll_fenlei, ll_diqu, ll_paixu;
        private ImageView swith_on,swith_on1,swith_on2;
        public cDmyHolderType1(View itemView) {
            super(itemView);


        }
    }


}
