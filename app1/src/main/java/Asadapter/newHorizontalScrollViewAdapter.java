package Asadapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by misshu on 2017/4/25/025.
 */
public class newHorizontalScrollViewAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mDatas;
    private ArrayList<String> title=new ArrayList<>();
    private ArrayList<String> price=new ArrayList<>();
    private ArrayList<String> previous=new ArrayList<>();
    public newHorizontalScrollViewAdapter(ArrayList<String> title,ArrayList<String> price,ArrayList<String> previous,Context context, List<Integer> mDatas) {
        this.title=title;
        this.price=price;
        this.previous=previous;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }
    public int getCount()
    {
        return mDatas.size();
    }
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.activity_index_newgally, parent, false);
            viewHolder.newgally_iv = (ImageView) convertView
                    .findViewById(R.id.newgally_iv);
            viewHolder.galley_title = (TextView) convertView
                    .findViewById(R.id.galley_title);
            viewHolder.gally_price = (TextView) convertView
                    .findViewById(R.id.gally_price);
            viewHolder.gally_previousprice = (TextView) convertView
                    .findViewById(R.id.gally_previousprice);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.newgally_iv.setImageResource(mDatas.get(position));
        viewHolder.galley_title.setText(title.get(position));
        viewHolder.gally_price.setText("¥"+price.get(position));
        viewHolder.gally_previousprice.setText("¥"+previous.get(position));
        if(String.valueOf(previous.get(position)).equals("0")||String.valueOf(previous.get(position)).equals("null")){
                 viewHolder.gally_price.setText("¥"+price.get(position));
                viewHolder.gally_previousprice.setVisibility(View.INVISIBLE);
        }else if(String.valueOf(price.get(position)).equals(previous.get(position))){
            viewHolder.gally_price.setText("¥"+price.get(position));
            viewHolder.gally_previousprice.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.gally_previousprice.setVisibility(View.VISIBLE);
            viewHolder.gally_price.setText("¥"+price.get(position));
            viewHolder.gally_previousprice.setText("¥"+previous.get(position));
            viewHolder.gally_previousprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return convertView;
    }
    private class ViewHolder
    {
        ImageView newgally_iv;
        TextView gally_previousprice,gally_price,galley_title;
    }
}
