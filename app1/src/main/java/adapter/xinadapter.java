package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

import java.util.ArrayList;

/**
 * Created by 胡海波 on 2016/12/10.
 */
public class xinadapter extends BaseAdapter {
    private int selectItem = -1;
    private int selectItem1=-1;
    private int lastPosition;
    private LayoutInflater inflater;
    //private ArrayList<zhantingbean.ResponseJsonBean.ListBean> data=new ArrayList<>();
    private ArrayList<String> fenlist=new ArrayList<>();
    private Context context;
    String result;
    public   xinadapter(Context context,String result){
        super();
        this.context=context;
        this.result=result;

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


        final ViewHold finalVh = vh;

        return convertView;
    }
    public class ViewHold {

        TextView tv11;
    }

}
