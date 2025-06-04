
package com.example.tallybook.activity;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tallybook.util.ActivityCollector;
import com.example.tallybook.util.ForceOfflineReceiver;


public class BaseActivity extends AppCompatActivity {    //作为所有类的父类
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {    //在活动创建的时候，就将该活动加入到ActivityCollector中
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
 
    @Override
    protected void onDestroy() {  //在活动销毁的时候，将该活动从ActivityCollector中移除，因为该活动已经结束不用再在ActivityCollector结束
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.tallybook.FORCE_OFFLINE");
        ForceOfflineReceiver receiver = new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }
}