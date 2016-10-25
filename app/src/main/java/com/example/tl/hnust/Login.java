package com.example.tl.hnust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tools.Tools;

/**
 * Created by gesangdianzi on 2016/10/25.
 */
public class Login extends Activity implements View.OnClickListener{
    private Button login;
    private EditText editText_admin;
    private EditText editText_password;
    String admin;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login=(Button)findViewById(R.id.login);
        editText_admin=(EditText)findViewById(R.id.login_admin) ;
        editText_password=(EditText)findViewById(R.id.login_password);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        admin=editText_admin.getText().toString();
        password=editText_password.getText().toString();
        if (Tools.loginGrade(admin, password) == 0) {
            Toast.makeText(this, "错误的账号或密码", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
    }

