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
import utils.FileUtils35;

public class NewaddActivity extends AppCompatActivity {
    @Bind(R.id.address)
    EditText address;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.user_name)
    EditText uesrname;
    @Bind(R.id.shouhuo_iv1)
    ImageView shouhuoIv1;
    @Bind(R.id.fengge_tv1)
    TextView fenggeTv1;
    @Bind(R.id.add_tv)
    TextView addTv;
    @Bind(R.id.shouhuo)
    RelativeLayout shouhuo;
    @Bind(R.id.yanse)
    TextView yanse;
    @Bind(R.id.name)
    RelativeLayout name;
    @Bind(R.id.yanse1)
    TextView yanse1;
    @Bind(R.id.name1)
    RelativeLayout name1;
    @Bind(R.id.yanse2)
    TextView yanse2;
    @Bind(R.id.province)
    RelativeLayout province;
    @Bind(R.id.xiangxi)
    RelativeLayout xiangxi;
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
    @Bind(R.id.sanji_province)
    TextView sanji_province;
    OptionsPickerView pvOptions;
    @Bind(R.id.zipcode_et)
    EditText zipcodeEt;
    @Bind(R.id.textmoren)
    TextView textmoren;
    @Bind(R.id.sure_iv)
    ImageView sureIv;
    @Bind(R.id.moren_rl)
    RelativeLayout morenRl;
    @Bind(R.id.shouhuo_return1)
    RelativeLayout shouhuoReturn1;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.xiangxi_tv)
    TextView xiangxiTv;
    private RequestQueue queue;
    private String name2;
    private String number;
    private String address1;
    private String zipcode;
    String address2;
    boolean flag;
    private String isdefault = "1";
    String msure;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();



   /* private List<Map<String, String>> provinceData = new ArrayList<Map<String,String>>();
    private List<Map<String, String>> cityData = new ArrayList<Map<String,String>>();
    private List<String> districtData = new ArrayList<String>();
    private SimpleAdapter provinceAdapter; // 省份下拉框对应的适配器
    private SimpleAdapter cityAdapter; // 城市下拉框对应的适配器
    private ArrayAdapter<String> districtAdapter; // 区域下拉框对应的适配器*/

    ArrayList<String> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newadd);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        msure = intent.getStringExtra("msure");
        Log.i("huhe", "onCreate: " + msure);
        province_shouhuo();
        setlistener();
        StatusBarCompat.setStatusBarColor(NewaddActivity.this, Color.parseColor("#fbd23a"), true);
    }

    private void postshuju() {
        queue = Volley.newRequestQueue(NewaddActivity.this);
        String url = "https://api.aijiaijia.com/api_usershipping_add";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjianr", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            try {
                                String s = new String(response.getBytes("ISO-8859-1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                Toast.makeText(NewaddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                FileUtils35 fil = new FileUtils35();
                                String biaozhi = fil.readDataFromFile(NewaddActivity.this);
                                if (biaozhi.equals("1")) {
                                  /*  Log.i("sengcad", "onResponse: "+"dup");
                                    Intent intent5 = new Intent();
                                    intent5.putExtra("result8", "8");
                                    setResult(8, intent5);
                                    finish();*/
                                    Intent intent = new Intent(NewaddActivity.this, h5BaseActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (biaozhi.equals("2")) {
                                  /*  Intent intent5 = new Intent();
                                    setResult(8, intent5);
                                    finish();*/
                                    Intent intent = new Intent(NewaddActivity.this, h5BaseActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else if (equals("6")) {
                                Toast.makeText(NewaddActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                address2 = sanji_province.getText().toString();
                Log.i("gugu", "getParams: " + address2);
                try {
                    String str = new String(name2.getBytes("utf-8"), "ISO-8859-1");
                    // String str1=new String(number.getBytes("utf-8"),"ISO-8859-1");
                    String str2 = new String(address2.getBytes("utf-8"), "ISO-8859-1");
                    String str3 = new String(address1.getBytes("utf-8"), "ISO-8859-1");
                    // String str4=new String(zipcode.getBytes("utf-8"),"ISO-8859-1");
                    String str5 = new String(isdefault.getBytes("utf-8"), "ISO-8859-1");


                  /*  String str = URLEncoder.encode(name2, "ISO-8859-1");
                    String str1 = URLEncoder.encode(number, "ISO-8859-1");
                    String str2 = URLEncoder.encode(address2, "ISO-8859-1");
                    String str3 = URLEncoder.encode(address1, "ISO-8859-1");
                    String str4 = URLEncoder.encode(zipcode, "ISO-8859-1");
                    String str5 = URLEncoder.encode(isdefault, "ISO-8859-1");*/
                    map.put("name", str);
                    map.put("phone", number);
                    map.put("psdscs", str2);
                    map.put("address", str3);
                    if (zipcode != null) {
                        map.put("zipcode", zipcode);
                    } else {
                        map.put("zipcode", "");
                    }
                    map.put("isdefault", str5);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                Log.i("hahas", "getParams: " + map.toString());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    headers.put("Charset", "ISO-8859-1");

                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }

        };

        queue.add(post);
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
                sanji_province.setText(address);
            }
        });
        //  点击弹出选项选择器
        province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
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

    private void setlistener() {
        shouhuoReturn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    sureIv.setImageResource(R.mipmap.turnoff);
                    isdefault = "0";
                    flag = true;
                } else {
                    sureIv.setImageResource(R.mipmap.turnon);
                    isdefault = "1";
                    flag = false;
                }
            }
        });
        zipcodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                zipcode = zipcodeEt.getText().toString().trim();
            }
        });
        uesrname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name2 = uesrname.getText().toString().trim();
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                number = phone.getText().toString().trim();
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address1 = address.getText().toString().trim();
            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("honglong", "onClick: " + msure);
                if (msure != null && msure.equals("2")) {
                    Log.i("juju", "onClick: " + "fanren");
                    address2 = sanji_province.getText().toString();

                    if (TextUtils.isEmpty(name2)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入收货人的姓名", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (TextUtils.isEmpty(number)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入收货人手机号", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (address2.length() == 0 || address2 == null) {
                        Log.i("suiran", "onClick: " + address2.toString());
                        Toast toast = Toast.makeText(NewaddActivity.this, "请选择您所在的地区", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (TextUtils.isEmpty(address1)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入详细地址", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    } else if (number.length() != 11) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        postshuju();

                    }
                } else if (msure != null && msure.equals("1")) {
                    address2 = sanji_province.getText().toString();
                    if (TextUtils.isEmpty(name2)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入收货人的姓名", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (TextUtils.isEmpty(number)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入收货人手机号", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (address2.length() == 0 || address2 == null) {
                        Log.i("suiran", "onClick: " + address2.toString());
                        Toast toast = Toast.makeText(NewaddActivity.this, "请选择您所在的地区", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (TextUtils.isEmpty(address1)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入详细地址", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    } else if (number.length() != 11) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        postshuju1();

                    }
                    Log.i("juju", "onClick: " + "fanren1");

                } else {
                    address2 = sanji_province.getText().toString();
                    Log.i("juju", "onClick: " + "fanren2");
                    if (TextUtils.isEmpty(name2)) {

                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入收货人的姓名", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (TextUtils.isEmpty(number)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入收货人手机号", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (address2.length() == 0 || address2 == null) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请选择您所在的地区", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (TextUtils.isEmpty(address1)) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入详细地址", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    } else if (number.length() != 11) {
                        Toast toast = Toast.makeText(NewaddActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        postshuju2();
                    }

                }

             /*   Intent intent = new Intent(NewaddActivity.this, ShouhuoActivity.class);
                startActivity(intent);
                finish();*/
              /*  String username = uesrname.getText().toString();
                String phonenum = phone.getText().toString();
                String address1 = address.getText().toString();
                String address2 = sanji_province.getText().toString();
                Log.i("xiapeng11", "onClick: " + username);
                Log.i("xiapeng22", "onClick: " + phonenum);
                Log.i("xiapeng33", "onClick: " + address1);
                DBHelper1 helper1 = new DBHelper1(NewaddActivity.this);
                helper1.insert(new Food(username, phonenum, address2, address1));

               */

            }
        });

    }

    private void postshuju2() {
        queue = Volley.newRequestQueue(NewaddActivity.this);
        String url = "https://api.aijiaijia.com/api_usershipping_add";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjianr", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            try {
                                String s = new String(response.getBytes("ISO-8859-1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                Toast.makeText(NewaddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                Intent intent5 = new Intent();
                                intent5.putExtra("result5", "1");
                                setResult(6, intent5);
                                finish();
                            } else if (equals("6")) {
                                Toast.makeText(NewaddActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                address2 = sanji_province.getText().toString();
                Log.i("gugu", "getParams: " + address2);
                try {
                    String str = new String(name2.getBytes("utf-8"), "ISO-8859-1");
                    // String str1=new String(number.getBytes("utf-8"),"ISO-8859-1");
                    String str2 = new String(address2.getBytes("utf-8"), "ISO-8859-1");
                    String str3 = new String(address1.getBytes("utf-8"), "ISO-8859-1");
                    // String str4=new String(zipcode.getBytes("utf-8"),"ISO-8859-1");
                    String str5 = new String(isdefault.getBytes("utf-8"), "ISO-8859-1");


                  /*  String str = URLEncoder.encode(name2, "ISO-8859-1");
                    String str1 = URLEncoder.encode(number, "ISO-8859-1");
                    String str2 = URLEncoder.encode(address2, "ISO-8859-1");
                    String str3 = URLEncoder.encode(address1, "ISO-8859-1");
                    String str4 = URLEncoder.encode(zipcode, "ISO-8859-1");
                    String str5 = URLEncoder.encode(isdefault, "ISO-8859-1");*/
                    map.put("name", str);
                    map.put("phone", number);
                    map.put("psdscs", str2);
                    map.put("address", str3);
                    if (zipcode != null) {
                        map.put("zipcode", zipcode);
                    } else {
                        map.put("zipcode", "");
                    }

                    map.put("isdefault", str5);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                Log.i("hahas", "getParams: " + map.toString());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    headers.put("Charset", "ISO-8859-1");

                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }

        };

        queue.add(post);
    }

    private void postshuju1() {
        queue = Volley.newRequestQueue(NewaddActivity.this);
        String url = "https://api.aijiaijia.com/api_usershipping_add";
        StringRequest post = new StringRequest(
                StringRequest.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str = response.toString().trim();
                        Log.i("jianjianr", "onResponse: " + str);
                        JSONObject jsonObject = null;
                        try {
                            try {
                                String s = new String(response.getBytes("ISO-8859-1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            jsonObject = new JSONObject(str);
                            JSONObject resposeobject = jsonObject.getJSONObject("responseJson");
                            String result3 = resposeobject.getString("op");
                            if (result3.equals("1")) {
                                Toast.makeText(NewaddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                Intent intent5 = new Intent();
                                intent5.putExtra("result5", "1");
                                setResult(5, intent5);
                                finish();
                            } else if (equals("6")) {
                                Toast.makeText(NewaddActivity.this, "未登录", Toast.LENGTH_SHORT).show();
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
                address2 = sanji_province.getText().toString();
                Log.i("gugu", "getParams: " + address2);
                try {
                    String str = new String(name2.getBytes("utf-8"), "ISO-8859-1");
                    // String str1=new String(number.getBytes("utf-8"),"ISO-8859-1");
                    String str2 = new String(address2.getBytes("utf-8"), "ISO-8859-1");
                    String str3 = new String(address1.getBytes("utf-8"), "ISO-8859-1");
                    // String str4=new String(zipcode.getBytes("utf-8"),"ISO-8859-1");
                    String str5 = new String(isdefault.getBytes("utf-8"), "ISO-8859-1");


                  /*  String str = URLEncoder.encode(name2, "ISO-8859-1");
                    String str1 = URLEncoder.encode(number, "ISO-8859-1");
                    String str2 = URLEncoder.encode(address2, "ISO-8859-1");
                    String str3 = URLEncoder.encode(address1, "ISO-8859-1");
                    String str4 = URLEncoder.encode(zipcode, "ISO-8859-1");
                    String str5 = URLEncoder.encode(isdefault, "ISO-8859-1");*/
                    map.put("name", str);
                    map.put("phone", number);
                    map.put("psdscs", str2);
                    map.put("address", str3);
                    if (zipcode != null) {
                        map.put("zipcode", zipcode);
                    } else {
                        map.put("zipcode", "");
                    }

                    map.put("isdefault", str5);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                Log.i("hahas", "getParams: " + map.toString());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                if (Constant.localCookie != null && Constant.localCookie.length() > 0) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("cookie", Constant.localCookie);
                    headers.put("Charset", "ISO-8859-1");

                    Log.d("调试88", "headers----------------" + headers);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }

        };

        queue.add(post);
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
