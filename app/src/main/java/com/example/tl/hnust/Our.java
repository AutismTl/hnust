package com.example.tl.hnust;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by gesangdianzi on 2016/11/29.
 */
public class Our extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.our);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
