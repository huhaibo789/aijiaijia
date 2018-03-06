package com.example.administrator.myyushi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;

import fragement.TiNianfragement;
import utils.FileUtils;


public class Calendar1Activity extends AppCompatActivity{
    private CalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;
    private Button sure,quxiao;
    String aa;
    public static final String KEY_USER_ID="KEY_USER_ID";
    public static final String KEY_USER_PASSWORD="KEY_USER_PASSWORD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar1);

        format = new SimpleDateFormat("yyyy-MM-dd");
        String dat=format.format(new java.util.Date());
        aa=dat;
        //获取日历控件对象
        calendar = (CalendarView)findViewById(R.id.calendar);
        calendar.setSelectMore(false); //单选
        quxiao= (Button) findViewById(R.id.quxiao);
         sure= (Button) findViewById(R.id.sure);
        calendarLeft = (ImageButton)findViewById(R.id.calendarLeft);
        calendarCenter = (TextView)findViewById(R.id.calendarCenter);
        calendarRight = (ImageButton)findViewById(R.id.calendarRight);

        try {
            //设置日历日期
            Date date = format.parse("2015-01-01日");
            calendar.setCalendarData(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
        String[] ya = calendar.getYearAndmonth().split("-");
        calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
        calendarLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
                String leftYearAndmonth = calendar.clickLeftMonth();
                String[] ya = leftYearAndmonth.split("-");
                calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            }
        });

        calendarRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                String rightYearAndmonth = calendar.clickRightMonth();
                String[] ya = rightYearAndmonth.split("-");
                calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            }
        });

        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                if(calendar.isSelectMore()){
                    //Toast.makeText(getApplicationContext(), format.format(selectedStartDate)+"到"+format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
                }else{
                     aa= format.format(selectedEndDate);
                  /*  Intent intent3=new Intent();
                    intent3.putExtra("result5", aa);
                    setResult(3,intent3);
                    finish();*/
                 /*   Calendar1Activity.this.setResult(RESULT_OK, intent3);
                    Calendar1Activity.this.finish();
*/
                   /* FileUtils fu=new FileUtils();
                    fu.saveDataToFile(Calendar1Activity.this,aa);*/

                 /*   Log.i("kong1", "OnItemClick: "+aa);*/
                /*
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("numone1",aa);
                    Log.i("chanshu1", "initData: "+bundle);
                    intent.putExtras(bundle);
                    intent.setClass(Calendar1Activity.this,TiNianfragement.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), format.format(selectedEndDate), Toast.LENGTH_SHORT).show();*/
                  /*  calendar.setTime(curDate);*/

                   /* Intent intent2=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("numone1",aa);
                    TiNianfragement frag=new TiNianfragement();
                    frag.setArguments(bundle);
                    intent2.putExtras(bundle);
                    intent2.setClass(Calendar1Activity.this, TiNianfragement.class);
                    startActivity(intent2);
                    Log.i("ggg", "OnItemClick: "+aa);*/

                }
            }
        });
         sure.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent3=new Intent();
                 intent3.putExtra("result5", aa);
                 FileUtils fu=new FileUtils();
                 fu.saveDataToFile(Calendar1Activity.this,aa);
                 setResult(3,intent3);
                 finish();
             }
         });
        quxiao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
