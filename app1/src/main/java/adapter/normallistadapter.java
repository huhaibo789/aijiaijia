package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.myyushi.R;


import java.util.ArrayList;

import bean.zhantingbean;
import utils.FileUtils;
import utils.FileUtils25;

/**
 * Created by 胡海波 on 2016/8/29.
 */
public class normallistadapter extends BaseAdapter {
    private int selectItem = -1;
    private int selectItem1=-1;
    private int lastPosition;
    private LayoutInflater inflater;
    //private ArrayList<zhantingbean.ResponseJsonBean.ListBean> data=new ArrayList<>();
    private ArrayList<String> fenlist=new ArrayList<>();
    private Context context;
    String result;
    public   normallistadapter(Context context,String result){
        super();
        this.context=context;
        this.result=result;

    }
    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
    public void setSelectItem1(int selectItem1){
        this.selectItem1=selectItem1;
    }
   /* public  void updateData(ArrayList<zhantingbean.ResponseJsonBean.ListBean> data){
        this.data=data;

    }*/
    public  void upshuju(ArrayList<String> fenlist){
       this.fenlist=fenlist;

    }
    @Override
    public int getCount() {
        return fenlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.activity_normal,null);
            vh=new ViewHold();
            convertView.setTag(vh);
            vh.tv11= (TextView) convertView.findViewById(R.id.tv11);


        }else {
            vh= (ViewHold) convertView.getTag();
        }
        vh.tv11.setText(fenlist.get(position));
    /*    FileUtils25 file=new FileUtils25();
        String weizhi=file. readDataFromFile(context);
        Log.i("didndd", "getView: "+result);
        if(result.equals("1")){
            if(weizhi!=null){
                int weizhi1=Integer.parseInt(weizhi);
                Log.i("ssssdsd", "getView: "+weizhi);
                if(weizhi1==position){
                    Log.i("dodmdfre", "getView: "+selectItem);
                    vh.tv11.setTextColor(Color.parseColor("#149985"));
                }else {
                    vh.tv11.setTextColor(Color.parseColor("#000000"));
                }
            }

        }else {

        }*/



        if(selectItem==position){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.click));
            vh.tv11.setTextColor(Color.parseColor("#149985"));
        }else {
            vh.tv11.setTextColor(Color.parseColor("#222222"));
        }




        //TextView lastPosition=
       // vh.tv11.setTextColor(position,Color.GREEN);
        // parent.getChildAt(position);.setTextColor();
       /* for (int i = 0; i <fenlist.size() ; i++) {

        }
        FileUtils fi=new FileUtils();
        String xiaoxi=fi.readDataFromFile(context);
        int p=Integer.valueOf(xiaoxi);*/



        final ViewHold finalVh = vh;

        return convertView;
    }
    public class ViewHold {

        TextView tv11;
    }

}
