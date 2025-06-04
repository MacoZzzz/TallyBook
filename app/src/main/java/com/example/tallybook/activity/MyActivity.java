package com.example.tallybook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.tallybook.R;


/**
 * 登录页面
 */
public class MyActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    //返回
    public void back(View view) {
        finish();//关闭页面
    }

    //退出登录
    public void logout(View view) {
        //发送了一条广播， 这条广播就是用于通知程序强制用户下线的
        Intent intent = new Intent();
        intent.setAction("com.example.tallybook.FORCE_OFFLINE");
        intent.setPackage("com.example.tallybook");
        sendBroadcast(intent);
    }
    public void person(View view){
        startActivity(new Intent(MyActivity.this,PersonActivity.class));
    }

    public void password(View view){
        startActivity(new Intent(MyActivity.this,PasswordActivity.class));
    }

    public void about(View view){
        startActivity(new Intent(MyActivity.this,AboutActivity.class));
    }
}
