package com.example.administrator.myyushi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.githang.statusbar.StatusBarCompat;

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

public class Newadd1Activity extends AppCompatActivity {

    @Bind(R.id.shouhuo_iv1)
    ImageView shouhuoIv1;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    @Bind(R.id.add_tv1)
    TextView addTv1;
    @Bind(R.id.shouhuo)
    RelativeLayout shouhuo;
    @Bind(R.id.yanse)
    TextView yanse;
    @Bind(R.id.user_name1)
    EditText userName1;
    @Bind(R.id.name)
    RelativeLayout name;
    @Bind(R.id.yanse1)
    TextView yanse1;
    @Bind(R.id.phone1)
    EditText phone1;
    @Bind(R.id.name1)
    RelativeLayout name1;
    @Bind(R.id.yanse2)
    TextView yanse2;
    @Bind(R.id.sanji_province1)
    TextView sanjiProvince;
    @Bind(R.id.chengshi)
    TextView chengshi;
    @Bind(R.id.province)
    RelativeLayout province;
    @Bind(R.id.address1)
    EditText address1;
    @Bind(R.id.xiantiao)
    View xiantiao;
    @Bind(R.id.youbian)
    TextView youbian;
    @Bind(R.id.youbian1)
    RelativeLayout youbian1;
    @Bind(R.id.xiantiao1)
    View xiantiao1;
    @Bind(R.id.yabse)
    TextView yabse;
    @Bind(R.id.moren_tv)
    TextView morenTv;
    @Bind(R.id.textmoren)
    TextView textmoren;
    OptionsPickerView pvOptions;
    @Bind(R.id.new_et)
    EditText newEt;
    @Bind(R.id.sure_iv1)
    ImageView sureIv1;
    @Bind(R.id.more_tv1)
    RelativeLayout moreTv1;
    @Bind(R.id.shouhuo_return)
    RelativeLayout shouhuoReturn;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.xianhua)
    View xianhua;
    @Bind(R.id.chaoyou3)
    ImageView chaoyou3;
    private RequestQueue queue;
    private String ss;
    private String name_add;
    private String phone_add;
    private String address_add;
    private String youbian2;
    private String isdefault = "1";
    boolean flag;
    String psdscs;
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
        setContentView(R.layout.activity_newadd1);
        ButterKnife.bind(this);
        setlistener();
        Intent intent = getIntent();
        ss = intent.getStringExtra("ids");
        postshuju();
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
                String address;
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
            }
        });
        //  点击弹出选项选择器
        province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
        StatusBarCompat.setStatusBarColor(Newadd1Activity.this, Color.parseColor("#fbd23a"), true);
    }

    private void postshuju() {
        queue = Volley.newRequestQueue(Newadd1Activity.this);
        String url = "https://api.aijiaijia.com/api_usershipping_info";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                JSONArray jsonarry = resposeobject.getJSONArray("list");
                                for (int i = 0; i < jsonarry.length(); i++) {
                                    JSONObject object = jsonarry.getJSONObject(i);
                                    String us_name = object.getString("us_name");
                                    userName1.setText(us_name);
                                    String us_phone = object.getString("us_phone");
                                    phone1.setText(us_phone);
                                    String us_pscsds = object.getString("us_pscsds");
                                    chengshi.setText(us_pscsds);
                                    String us_address = object.getString("us_address");
                                    address1.setText(us_address);
                                    String us_zipcode = object.getString("us_zipcode");
                                    newEt.setText(us_zipcode);
                                    String us_is_default = object.getString("us_is_default");
                                    if (us_is_default.equals("0")) {
                                        sureIv1.setImageResource(R.drawable.turnoff);
                                    } else if (us_is_default.equals("1")) {
                                        sureIv1.setImageResource(R.drawable.turnon);
                                    }
                                }
                            } else if (result3.equals("6")) {
                                Toast.makeText(Newadd1Activity.this, "未登录", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(Newadd1Activity.this, "请检查网络", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("usid", ss);
                return map;
            }

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
        shouhuoReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sureIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    sureIv1.setImageResource(R.drawable.turnoff);
                    flag = true;
                    isdefault = "0";
                } else {
                    sureIv1.setImageResource(R.drawable.turnon);
                    flag = false;
                    isdefault = "1";
                }
            }
        });
        newEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                youbian2 = newEt.getText().toString().trim();
            }
        });
        address1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address_add = address1.getText().toString().trim();
            }
        });
        userName1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name_add = userName1.getText().toString().trim();
            }
        });
        phone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone_add = phone1.getText().toString().trim();
            }
        });
        addTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psdscs = chengshi.getText().toString().trim();
                if (TextUtils.isEmpty(name_add)) {
                    Toast toast = Toast.makeText(Newadd1Activity.this, "请输入收货人的姓名", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (TextUtils.isEmpty(phone_add)) {
                    Toast toast = Toast.makeText(Newadd1Activity.this, "请输入收货人手机号", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (psdscs.length() == 0 || psdscs == null) {
                    Toast toast = Toast.makeText(Newadd1Activity.this, "请选择您所在的地区", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (TextUtils.isEmpty(address_add)) {
                    Toast toast = Toast.makeText(Newadd1Activity.this, "请输入详细地址", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (phone_add.length() != 11) {
                    Toast toast = Toast.makeText(Newadd1Activity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    postdata();
                }
            }
        });
    }

    private void postdata() {
        queue = Volley.newRequestQueue(Newadd1Activity.this);
        String url = "https://api.aijiaijia.com/api_usershipping_save";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                Toast.makeText(Newadd1Activity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(Newadd1Activity.this, ShouhuoActivity.class);
                                startActivity(intent);
                            } else if (result3.equals("6")) {
                                Toast.makeText(Newadd1Activity.this, "未登录", Toast.LENGTH_SHORT).show();
                            } else if (result3.equals("0")) {
                                Toast.makeText(Newadd1Activity.this, "保存失败", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            //通过重写此对象的getParams方法设置请求条件
            //将所有的请求条件存入返回值的map对象中
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                psdscs = chengshi.getText().toString().trim();
                Log.i("grt", "getParams: " + ss);
                try {
                    String str = new String(name_add.getBytes("utf-8"), "ISO-8859-1");
                    String str5 = new String(isdefault.getBytes("utf-8"), "ISO-8859-1");
                    String str1 = new String(ss.getBytes("utf-8"), "ISO-8859-1");
                    String str3 = new String(psdscs.getBytes("utf-8"), "ISO-8859-1");
                    String str2 = new String(address_add.getBytes("utf-8"), "ISO-8859-1");
                    map.put("usid", str1);
                    map.put("name", str);
                    map.put("phone", phone_add);
                    map.put("psdscs", str3);
                    map.put("address", str2);
                    map.put("zipcode", youbian2);
                    map.put("isdefault", str5);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return map;
            }

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

    //  解析json填充集合
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
