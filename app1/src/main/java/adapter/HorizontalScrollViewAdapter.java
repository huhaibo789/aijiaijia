package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

import java.util.List;

/**
 * Created by misshu on 2017/4/10/010.
 */
public class HorizontalScrollViewAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mDatas;

    public HorizontalScrollViewAdapter(Context context, List<Integer> mDatas)
    {
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
                    R.layout.activity_index_gallery_items, parent, false);
            viewHolder.mImg = (ImageView) convertView
                    .findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mText = (TextView) convertView
                    .findViewById(R.id.id_index_gallery_item_text);

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImg.setImageResource(mDatas.get(position));
        if(position==0){
            viewHolder.mText.setText("现代简约A");
        }else if(position==1){
            viewHolder.mText.setText("现代简约B");
        }else if(position==2){
            viewHolder.mText.setText("现代简约C");
        }else if(position==3){
            viewHolder.mText.setText("现代简约D");
        }else {
            viewHolder.mText.setText("现代简约E");
        }


        return convertView;
    }

    private class ViewHolder
    {
        ImageView mImg;
        TextView mText;
    }
}
