package com.example.tl.hnust;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dp.StudentInfoDB;

/**
 * Created by gesangdianzi on 2016/10/27.
 */
public class StudentInfo extends FragmentActivity implements View.OnClickListener {
    private ImageView image;
    private TextView name;
    private TextView xh;
    private TextView sex;
    private TextView ss;
    private TextView address;
    private TextView xshm, fmhm;
    private TextView qq;
    private Button dh;
    private Button dx;
    private StudentInfoDB studentInfoDB;
    private ProgressDialog progressDialog;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentinfo);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        studentInfoDB = StudentInfoDB.getInstance(this);
        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        xh = (TextView) findViewById(R.id.xh);
        sex = (TextView) findViewById(R.id.sex);
        ss = (TextView) findViewById(R.id.ss);
        address = (TextView) findViewById(R.id.address);
        xshm = (TextView) findViewById(R.id.xshm);
        fmhm = (TextView) findViewById(R.id.fmhm);
        qq = (TextView) findViewById(R.id.qq);
        dh = (Button) findViewById(R.id.dh);
        dx = (Button) findViewById(R.id.dx);
        title=(TextView)findViewById(R.id.title_text);
        title.setText("物联网二班");
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("hide",false)){
            address.setVisibility(View.INVISIBLE);
            fmhm.setVisibility(View.INVISIBLE);
        }
        Intent intent = getIntent();
        name.setText(" 姓名：" + intent.getStringExtra("name"));
        xh.setText("学号：" + intent.getStringExtra("id"));
        sex.setText(" 性别：" + intent.getStringExtra("sex"));
        xshm.setText("联系方式 (学生)："+intent.getStringExtra("number"));
        qq.setText(" QQ：" + intent.getStringExtra("qq"));
        address.setText("家庭住址："+intent.getStringExtra("address"));
        ss.setText(" 宿舍："+intent.getStringExtra("home"));
        fmhm.setText("联系方式 (父母)："+intent.getStringExtra("phone"));
        String url = intent.getStringExtra("url");

        Glide
                .with(this)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(image);
        dh.setOnClickListener(this);
        dx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dh:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + xshm.getText());
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
              startActivity(intent);
                break;
            case R.id.dx:
                Uri uri= Uri.parse("smsto:"+xshm.getText());
                intent = new Intent(Intent.ACTION_SENDTO,uri);
                startActivity(intent);
            default:
                break;
        }
    }
}
