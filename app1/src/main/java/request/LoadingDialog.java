package request;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myyushi.R;

import butterknife.Bind;
import view.GifView;

/**
 * Created by 胡海波 on 2016/11/23.
 */
public class LoadingDialog extends Dialog {


    private TextView tv;
    private GifView wvgif;
    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogloading);
       wvgif= (GifView) findViewById(R.id.wv_gif1);   //新改的
     /*   String gifPath = "file:///android_asset/progress.gif";
        wvgif.loadUrl(gifPath);*/
   /*     tv = (TextView) findViewById(R.id.tv);
        tv.setText("正在加载.....");
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);*/
        // 设置背景gif图片资源
        wvgif.setMovieResource(R.raw.wait); //新改的
    }




}
