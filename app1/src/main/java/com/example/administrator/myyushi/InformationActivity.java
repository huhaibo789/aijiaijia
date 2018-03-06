package com.example.administrator.myyushi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.bigkoo.pickerview.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.ProvinceBean;
import butterknife.Bind;
import butterknife.ButterKnife;

public class InformationActivity extends AppCompatActivity {

    @Bind(R.id.return_iv2)
    ImageView returnIv2;
    @Bind(R.id.shuru_name)
    EditText shuruName;
    @Bind(R.id.shuru_phone)
    EditText shuruPhone;
    @Bind(R.id.shuru_address)
    EditText shuruAddress;
    @Bind(R.id.shuru_memo)
    EditText shuruMemo;
    @Bind(R.id.sure_button)
    Button sureButton;
    @Bind(R.id.sanji_province)
    TextView sanjiProvince;
    @Bind(R.id.diqu_select)
    RelativeLayout diquSelect;
    @Bind(R.id.tishi_qu)
    TextView tishiQu;
    private RequestQueue queue;
    private String phone;
    private String name;
    private String address2;
    String address;
    private String memo;
    private String zu_id;
    private String style;
    private String manner;
    private String appointshop;
    private String time;
    private String pids;
    private String nums;
    OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        province_shouhuo();
        Intent intent = getIntent();
        pids = intent.getStringExtra("pids");
        nums = intent.getStringExtra("num");
        zu_id = intent.getStringExtra("zu_id");
        style = intent.getStringExtra("style");
        manner = intent.getStringExtra("manner");
        appointshop = intent.getStringExtra("appointshop");
        time = intent.getStringExtra("appointtime");
        setlistener();
    }

    private void province_shouhuo() {
        //  创建选项选择器
        pvOptions = new OptionsPickerView(this);
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(this, "province_data.json");
        //  解析json数据
        parseJson(province_data_json);
        //  设置三级联动效果
        pvOptions.setPicker(provinceBeanList, cityList, districtList, true);
        //  设置是否循环滚动
        pvOptions.setCyclic(false, false, false);
        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = provinceBeanList.get(options1).getPickerViewText();

                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + districtList.get(options1).get(option2).get(options3);
                } else {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + cityList.get(options1).get(option2)
                            + " " + districtList.get(options1).get(option2).get(options3);
                }
                sanjiProvince.setText(address);
                tishiQu.setText("所在地区:");
            }
        });
        //  点击弹出选项选择器
        diquSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
    }

    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setlistener() {
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(TextUtils.isEmpty(phone)||phone.length()!=11){
                        Toast toast = Toast.makeText(InformationActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }
                if(!TextUtils.isEmpty(address2)&&!TextUtils.isEmpty(name)){
                    postdata1();
                }else {
                    Toast toast = Toast.makeText(InformationActivity.this, "手机或地址为空", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //Toast.makeText(InformationActivity.this, "姓名或手机为空", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        returnIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shuruName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = shuruName.getText().toString();
            }
        });
        shuruPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = shuruPhone.getText().toString();

            }
        });
        shuruAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address2 = shuruAddress.getText().toString();
            }
        });
        shuruMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                memo = shuruMemo.getText().toString();
            }
        });
    }

    private void postdata1() {
        queue = Volley.newRequestQueue(this);
        String url = "https://api.aijiaijia.com/api_appointorder_add";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("gouwu566ed", "onResponse: post  success " + response);
                        String str = response.toString().trim();
                        try {
                            JSONObject jsonobject = new JSONObject(str);
                            JSONObject resposeobject = jsonobject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                dialog();
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
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (pids != null) {
                    map.put("pids", pids);
                }
                if (nums != null) {
                    map.put("nums", nums);
                }
                if (name != null) {
                    try {
                        String str = new String(name.getBytes("utf-8"), "ISO-8859-1");
                        map.put("appointname", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                if (phone.length() != 0) {
                    map.put("appointphone", phone);
                }
                if (address2 != null||address!=null) {
                    try {
                        String sta=address2+address;
                        String str = new String(sta.getBytes("utf-8"), "ISO-8859-1");
                        map.put("appointaddress", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                map.put("appointtime", time);
                if (memo != null) {
                    try {
                        String str = new String(memo.getBytes("utf-8"), "ISO-8859-1");
                        map.put("appointmemo", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                if (appointshop != null) {
                    try {
                        String str = new String(appointshop.getBytes("utf-8"), "ISO-8859-1");
                        map.put("appointshop", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                if (zu_id != null) {
                    try {
                        String str = new String(zu_id.getBytes("utf-8"), "ISO-8859-1");
                        map.put("zuid", str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                if (style != null && manner != null) {
                    try {
                        String str = new String(style.getBytes("utf-8"), "ISO-8859-1");
                        String str1 = new String(manner.getBytes("utf-8"), "ISO-8859-1");
                        map.put("zuvalues", str + "," + str1);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                map.put("fromdevice", "2");
                Log.i("shiworuxi", "getParams: " + map.toString());
                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    Log.d("调试8", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };

        queue.add(post);
    }

    private void dialog() {
        // 建造者模式，将对象初始化的步骤抽取出来，让建造者来实现，设置完所有属性之后再创建对象
        // 这里使用的Context必须是Activity对象
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationActivity.this);
        // 链式编程
        builder.setTitle("提示")
                .setMessage("您已预约成功，注意查收短信")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })

                .setCancelable(false); // 能否通过点击对话框以外的区域（包括返回键）关闭对话框
        // 通过建造者创建Dialog对象
        // AlertDialog dialog = builder.create();
        // dialog.show();
        // 以上两行代码可以简化成以下这一行代码
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 页面结束埋点，需要使用Activity的引用，以便代码能够统计到具体页面名
        StatService.onPause(this);
    }

}
