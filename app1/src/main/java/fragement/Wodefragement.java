package fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.example.administrator.myyushi.AllsaleActivity;
import com.example.administrator.myyushi.ConnectionActivity;
import com.example.administrator.myyushi.Constant;
import com.example.administrator.myyushi.DesignActivity;
import com.example.administrator.myyushi.GouwuActivity1;
import com.example.administrator.myyushi.JifenActivity;
import com.example.administrator.myyushi.LoginActivity;
import com.example.administrator.myyushi.PersonActivity;
import com.example.administrator.myyushi.R;
import com.example.administrator.myyushi.SearchActivity;
import com.example.administrator.myyushi.ShezhiActivity;
import com.example.administrator.myyushi.ShoucangActivity;
import com.example.administrator.myyushi.SuggestActivity;
import com.example.administrator.myyushi.YouhuiquanActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import h5powendow.SelectPicPopupWindow;
import uploadpicture.CircleImg;
import util.Myapp;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Wodefragement extends Fragment {
    @Bind(R.id.ibshezhi)
    ImageButton ibshezhi;
    @Bind(R.id.ibxiaoxi)
    ImageButton ibxiaoxi;

    @Bind(R.id.tvlogin)
    TextView tvlogin;
    @Bind(R.id.zbj)
    RelativeLayout zbj;
    @Bind(R.id.ivshop)
    ImageView ivshop;
    @Bind(R.id.picture)
    LinearLayout picture;
    @Bind(R.id.yinying)
    TextView yinying;
    @Bind(R.id.gouwu)
    LinearLayout gouwu;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.ib_zhifu)
    ImageButton ibZhifu;
    @Bind(R.id.ib_shouhuo)
    ImageButton ibShouhuo;
    @Bind(R.id.ib_pingjia)
    ImageButton ibPingjia;
    @Bind(R.id.ly)
    LinearLayout ly;
    @Bind(R.id.yinying1)
    TextView yinying1;
    @Bind(R.id.ly_youhui)
    LinearLayout lyYouhui;
    @Bind(R.id.yinying2)
    View yinying2;
    @Bind(R.id.ly_tuihuo)
    LinearLayout lyTuihuo;
    @Bind(R.id.yinying3)
    View yinying3;
    @Bind(R.id.ly_jifen)
    LinearLayout lyJifen;
    @Bind(R.id.yinying4)
    View yinying4;
    @Bind(R.id.ly_lianxi)
    LinearLayout lyLianxi;
    @Bind(R.id.yinying5)
    View yinying5;
    @Bind(R.id.ly_tousu)
    LinearLayout lyTousu;
    @Bind(R.id.iv_jiantou)
    ImageView ivJiantou;
    @Bind(R.id.ding)
    ImageView ding;
    @Bind(R.id.iv_jiantou1)
    ImageView ivJiantou1;
    @Bind(R.id.tubiao_youhui)
    ImageView tubiaoYouhui;
    @Bind(R.id.tubiao_youhui1)
    ImageView tubiaoYouhui1;
    @Bind(R.id.tubiao_youhui2)
    ImageView tubiaoYouhui2;
    @Bind(R.id.tubiao_youhui3)
    ImageView tubiaoYouhui3;
    @Bind(R.id.tubiao_youhui4)
    ImageView tubiaoYouhui4;
    @Bind(R.id.zhifu_rl)
    RelativeLayout zhifuRl;
    @Bind(R.id.shouhuo_ry)
    RelativeLayout shouhuoRy;
    @Bind(R.id.pingjia_rl)
    RelativeLayout pingjiaRl;
    @Bind(R.id.daizhifu_tv)
    TextView daizhifuTv;
    @Bind(R.id.daishouhuo_tv)
    TextView daishouhuoTv;
    @Bind(R.id.daipingjia_tv)
    TextView daipingjiaTv;
    @Bind(R.id.mscrollew)
    ScrollView mscrollew;
    String zhuangtai = "2";
    @Bind(R.id.text_chart_num)
    TextView textChartNum;
    @Bind(R.id.deliver_ry)
    RelativeLayout deliverRy;
    @Bind(R.id.refund_rl)
    RelativeLayout refundRl;
    @Bind(R.id.login_pic)
    RelativeLayout loginPic;
    @Bind(R.id.ib_deliver)
    ImageButton ibDeliver;
    @Bind(R.id.deliver_tv)
    TextView deliverTv;
    @Bind(R.id.ib_refund)
    ImageButton ibRefund;
    @Bind(R.id.refund_tv)
    TextView refundTv;
    @Bind(R.id.indenglu)
    CircleImg indenglu;
    @Bind(R.id.tubiao_shoucang)
    ImageView tubiaoShoucang;
    @Bind(R.id.shoucang_wode)
    LinearLayout shoucangWode;
    @Bind(R.id.design_ib)
    ImageButton designIb;
    private Context mcontext;
    private RequestQueue queue;
    private String result2;
    private SelectPicPopupWindow menuWindow;
    private Handler handle = new Handler();
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private static final int UPLOAD_INIT_PROCESS = 4;//上传初始化
    protected static final int UPLOAD_FILE_DONE = 2;//上传中
    private static final int UPLOAD_IN_PROCESS = 5;//上传文件响应
    private File filepath;//返回的文件地址
    private ProgressDialog pd;
    private String urlpath;            // 图片本地路径
    private String resultStr = "";    // 服务端返回结果集
    String sessionid;
    ImageLoader loader;

    @Override
    public void onAttach(Context mcontext) {
        super.onAttach(mcontext);
        this.mcontext = mcontext;
    }

    public void update2() {
        totaldingan();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mscrollew.smoothScrollTo(0, 0);
        update5();
        totaldingan();
        loader = ((Myapp) getActivity().getApplication()).getLoader();
        setlistener();
    }

    private void totaldingan() {
        queue = Volley.newRequestQueue(mcontext);
        String url = "https://api.aijiaijia.com/api_sellorder_statistics";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("heiji", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                textChartNum.setVisibility(View.VISIBLE);
                                deliverTv.setVisibility(View.VISIBLE);
                                daizhifuTv.setVisibility(View.VISIBLE);
                                daishouhuoTv.setVisibility(View.VISIBLE);
                                daipingjiaTv.setVisibility(View.VISIBLE);
                                String result = resposeobject.getString("tobe_paid");   //待支付
                                String result1 = resposeobject.getString("tobe_received");//待收货
                                String result2 = resposeobject.getString("tobe_comment");//待评论
                                String result4 = resposeobject.getString("shopcard_num");//购物车
                                String result5 = resposeobject.getString("tobe_delivery");//待发货
                                Log.i("meinv", "onResponse: " + result4);
                                if (!result4.equals("0")) {
                                    textChartNum.setBackgroundResource(R.drawable.bubblet);
                                    textChartNum.setText(result4);
                                } else {
                                    textChartNum.setVisibility(View.INVISIBLE);
                                }
                                if (!result.equals("0")) {
                                    daizhifuTv.setText(result);
                                    daizhifuTv.setBackgroundResource(R.drawable.bubblet);
                                } else {
                                    daizhifuTv.setVisibility(View.INVISIBLE);
                                }
                                if (!result1.equals("0")) {
                                    daishouhuoTv.setText(result1);
                                    daishouhuoTv.setBackgroundResource(R.drawable.bubblet);
                                } else {
                                    daishouhuoTv.setVisibility(View.INVISIBLE);
                                }
                                if (!result2.equals("0")) {
                                    daipingjiaTv.setText(result2);
                                    daipingjiaTv.setBackgroundResource(R.drawable.bubblet);
                                } else {
                                    daipingjiaTv.setVisibility(View.INVISIBLE);
                                }
                                if (!result5.equals("0")) {
                                    deliverTv.setText(result5);
                                    deliverTv.setBackgroundResource(R.drawable.bubblet);
                                } else {
                                    deliverTv.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                textChartNum.setVisibility(View.INVISIBLE);
                                daizhifuTv.setVisibility(View.INVISIBLE);
                                daishouhuoTv.setVisibility(View.INVISIBLE);
                                daipingjiaTv.setVisibility(View.INVISIBLE);
                                deliverTv.setVisibility(View.INVISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsda", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };

        queue.add(post);
    }

    public void update5() {
        queue = Volley.newRequestQueue(mcontext);
        String url = "https://api.aijiaijia.com/api_userinfo";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjianef", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                JSONObject object = resposeobject.getJSONObject("list_user");
                                String nickname = object.getString("nickname");
                                String userheadpics = object.getString("user_head_pics");
                                loader.loadImage(userheadpics, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        super.onLoadingComplete(imageUri, view, loadedImage);
                                        indenglu.setImageBitmap(loadedImage);
                                    }
                                });

                                Log.i("jianjianef1", "onResponse: " + nickname);
                                zhuangtai = "1";
                                tvlogin.setText(nickname);
                                setlistener();
                            } else if (result3.equals("6")) {
                                zhuangtai = "2";
                                tvlogin.setText("登录/注册");
                                indenglu.setImageResource(R.drawable.pictureme);
                                setlistener();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsda", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };
        queue.add(post);
    }


    private void setlistener() {
        designIb.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent=new Intent(mcontext, DesignActivity.class);
                      mcontext.startActivity(intent);
                  }
              });
        if (zhuangtai != null && zhuangtai.equals("2")) {
            indenglu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent1, 5);
                }
            });
            tvlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                }
            });
            shoucangWode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent, 5);
                }
            });
        } else {
            shoucangWode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, ShoucangActivity.class);
                    startActivity(intent);
                }
            });
            indenglu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, PersonActivity.class);
                    startActivityForResult(intent, 30);
                  /*  menuWindow = new SelectPicPopupWindow(mcontext, itemsOnClick);
                    menuWindow.showAtLocation(mscrollew,
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);*/

                }
            });
            tvlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mcontext, "你已登录", Toast.LENGTH_SHORT).show();
                }
            });
        }

        refundRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "我的页面";
                String ww = "导购订单";
                String title = "在线客服";
                // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                ConsultSource source = new ConsultSource(url, ww, "custom information string");
                // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                Unicorn.openServiceActivity(mcontext, // 上下文
                        title, // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
            }
        });
        ibRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "我的页面";
                String ww = "导购订单";
                String title = "在线客服";
                // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                ConsultSource source = new ConsultSource(url, ww, "custom information string");
                // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                Unicorn.openServiceActivity(mcontext, // 上下文
                        title, // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
            }
        });
        deliverRy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "3");
                    startActivityForResult(intent, 14);
                }
            }
        });
        ibDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "3");
                    startActivityForResult(intent, 14);
                }
            }
        });
        zhifuRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("guanyunimen", "onClick: "+"sdsdd");
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "2");
                    startActivityForResult(intent, 14);
                }

            }
        });
        ibZhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "2");
                    startActivityForResult(intent, 14);
                }
            }
        });
        shouhuoRy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "10");
                    startActivityForResult(intent, 14);
                }

            }
        });
        ibShouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "10");
                    startActivityForResult(intent, 14);
                }
            }
        });
        pingjiaRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "5");
                    startActivityForResult(intent, 14);
                }


            }
        });
        ibPingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, AllsaleActivity.class);
                    intent.putExtra("status", "5");
                    startActivityForResult(intent, 14);
                }
            }
        });
        ibxiaoxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, SearchActivity.class);
                startActivity(intent);

            }
        });
        lyLianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ConnectionActivity.class);
                startActivity(intent);
            }
        });

        lyTousu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, SuggestActivity.class);
                startActivity(intent);
            }
        });

        ibshezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(mcontext, ShezhiActivity.class);
                startActivityForResult(intent3, 6);
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, GouwuActivity1.class);
                    startActivityForResult(intent, 15);
                }
            }
        });
        gouwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent4 = new Intent(mcontext, AllsaleActivity.class);
                    startActivity(intent4);
                }
            }
        });
        lyYouhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, YouhuiquanActivity.class);
                    startActivity(intent);
                }

            }
        });
        lyJifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhuangtai != null && zhuangtai.equals("2")) {
                    Intent intent2 = new Intent(mcontext, LoginActivity.class);
                    startActivityForResult(intent2, 5);
                } else {
                    Intent intent = new Intent(mcontext, JifenActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_wode, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("caodande", "onActivityResult: " + requestCode);
        Log.i("nanvhai", "onActivityResult: " + resultCode);
        Log.i("hegis", "onActivityResult: " + CAMERA_REQUEST);
        Log.i("hegis1", "onActivityResult: " + PHOTO_REQUEST);
        Log.i("hegis2", "onActivityResult: " + PHOTO_CLIP);
        if (resultCode == 5) {
            Log.i("tinghua", "onActivityResult: " + requestCode);
            if (data != null) {
               /* result2 = data.getStringExtra("result7");
                tvlogin.setText(result2);*/
                update5();
                totaldingan();
            }
        } else if (requestCode == 6) {
            if (data != null) {
                update5();
                totaldingan();
            }
        } else if (requestCode == 30) {
            if (data != null) {
                update5();
                totaldingan();
            }
        } else if (requestCode == 14) {
            update5();
            totaldingan();
        } else if (requestCode == 15) {
            update2();

        }

    }


    @Override
    public void onPause() {
        super.onPause();
        // 页面埋点
        StatService.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 页面埋点
        StatService.onResume(this);
    }


}
