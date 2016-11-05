package com.example.tl.hnust;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
public class PersonList extends Activity implements AdapterView.OnItemClickListener{
    private List<Person> personList=new ArrayList<Person>();
    private TextView titleText;
    private StudentInfoDB studentInfoDB;
    private ProgressDialog progressDialog;
    private String[] image;
    private PersonAdapter adapter;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personlist);
        studentInfoDB=StudentInfoDB.getInstance(this);
        titleText=(TextView)findViewById(R.id.title_text);
        initPersons();
        ListView listView=(ListView)findViewById(R.id.list_view);
        adapter=new PersonAdapter(this,R.layout.person_item,personList,image);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){

    }
    //初始化列表数据
       private void initPersons(){
           personList=studentInfoDB.loadStudentList();
           if(personList.size()>0){
               i=0;
               image=new String[40];
               Iterator<Person> it=personList.iterator();
               while(it.hasNext()){
                   Person p=it.next();
                   image[i++]=p.getImageId();
               }
               titleText.setText("物联网二班");
           }else{
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
}
