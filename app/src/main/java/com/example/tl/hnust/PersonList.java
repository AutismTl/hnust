package com.example.tl.hnust;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dp.StudentInfoDB;
import tools.HttpCallbackListener;
import tools.HttpUtil;
import tools.Person;
import tools.PersonAdapter;

/**
 * Created by gesangdianzi on 2016/10/27.
 */
public class PersonList extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{
    private List<Person> personList=new ArrayList<Person>();
    private TextView titleText;
    private ListView listView;
    private StudentInfoDB studentInfoDB;
    private ProgressDialog progressDialog;
    private String[] image=new String[40];
    private PersonAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private Button updata;
    private Button stupdata;
    private Button change;
    private Button our;
    private Button respond;
    private Button gps;
    private Button id;
    private Button cd;
    int i=0;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personlist);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        studentInfoDB=StudentInfoDB.getInstance(this);
        titleText=(TextView)findViewById(R.id.title_text1);
        updata=(Button)findViewById(R.id.updata);
        stupdata=(Button)findViewById(R.id.stupdata);
        change=(Button)findViewById(R.id.change);
        our=(Button)findViewById(R.id.our);
        respond=(Button)findViewById(R.id.respond);
        gps=(Button)findViewById(R.id.title_gps);
        cd=(Button)findViewById(R.id.title_cd);
        id=(Button)findViewById(R.id.changeid);
        gps.setVisibility(View.VISIBLE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        initEvents();
        initPersons();
        listView=(ListView)findViewById(R.id.list_view);
        adapter=new PersonAdapter(this,R.layout.person_item,personList,image);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        updata.setOnClickListener(this);
        stupdata.setOnClickListener(this);
        change.setOnClickListener(this);
        our.setOnClickListener(this);
        respond.setOnClickListener(this);
        gps.setOnClickListener(this);
        id.setOnClickListener(this);
        cd.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> arg0,View view,int index,long arg3){
        Person selectedPerson=personList.get(index);
        String url=selectedPerson.getImageId();
        String name=selectedPerson.getName();
        int id=selectedPerson.getId();
        Intent intent=new Intent(this,StudentInfo.class);
        intent.putExtra("name",name);
        intent.putExtra("url",url);
        intent.putExtra("home",selectedPerson.getHome());
        intent.putExtra("phone",selectedPerson.getPhone());
        intent.putExtra("id",String.valueOf(id));
        intent.putExtra("sex",selectedPerson.getSex());
        intent.putExtra("number",selectedPerson.getNumber());
        intent.putExtra("qq",selectedPerson.getQq());
        intent.putExtra("remarks",selectedPerson.getRemarks());
        intent.putExtra("address",selectedPerson.getAddress());

        startActivity(intent);
    }
    //初始化列表数据
       private void initPersons(){
           List<Person> list= new ArrayList<Person>();
           list=studentInfoDB.loadStudentList();
           if(list.size()>0){
               personList.clear();
               personList.addAll(list);
               Iterator<Person> it=personList.iterator();
               i=0;
               while(it.hasNext()){
                   Person p=it.next();
                   image[i++]=p.getImageId();
               }
               titleText.setText("物联网二班");
               if(flag==1){
                   adapter.notifyDataSetChanged();
               }
           }else{
               flag=1;
               queryFromServer("StudentList");
               }
           }
    private  void queryFromServer(final String type ){
        String address = null;
        if("StudentList".equals(type)){
            address="http://www.mad-tg.cn/blog/home";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result=false;
                 if("StudentList".equals(type)){
                     result=studentInfoDB.handleStudentListResponse(response);
                 }else{
                            //待完善
                 }
                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("StudentList".equals(type)){
                                initPersons();
                            }else{
                                //待完善
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          closeProgressDialog();
                          Toast.makeText(PersonList.this,"加载失败",Toast.LENGTH_SHORT).show();
                      }
                  });
            }
        });
    }

    //显示进度对话框
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    //关闭进度对话框
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    private void initEvents()
    {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerStateChanged(int newState)
            {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT"))
                {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 1.0f - 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else
                {
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_cd:  mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.updata:     showProgressDialog(); queryFromServer("StudentList"); initPersons();
                                  Toast.makeText(this,"数据更新成功",Toast.LENGTH_SHORT).show();
                                  mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.stupdata:  Toast.makeText(this,"该版本已是最新版本",Toast.LENGTH_SHORT).show();
                break;
            case R.id.change:     SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(this).edit();
                                  editor.putBoolean("zd_selected",false);
                                  editor.commit(); Intent intent= new Intent(this,Login.class); startActivity(intent);
                break;
            case R.id.our:        Intent intent1= new Intent(this,Our.class); startActivity(intent1);
                break;
            case R.id.respond:    Intent intent2= new Intent(this,Respond.class); startActivity(intent2);
                break;
            case R.id.title_gps:  Intent intent3= new Intent(this,Map.class); startActivity(intent3);
                break;
            case R.id.changeid:   mDrawerLayout.closeDrawer(Gravity.LEFT);
                final EditText editText=new EditText(this);
                SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
                editText.setText(prefs.getString("id","NULL"));
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("请输入新的ID").setIcon(R.drawable.icon_geo)
                        .setView(editText)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(PersonList.this).edit();
                                editor.putString("id", String.valueOf(editText.getText()));
                                editor.commit();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
        }
    }
}
