package com.example.tallybook.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.tallybook.activity.LoginActivity;


/**
 * 退出登录广播
 */
public class ForceOfflineReceiver extends BroadcastReceiver {
 
    @Override
    public void onReceive(final Context context, Intent intent) {
        ActivityCollector.finishAll();  //销毁所有活动
        Intent intent1 = new Intent(context, LoginActivity.class);  //重启LoginActivity
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    //因为是在广播接收器里启动活动的，因此一定要给Intent加入FLAG_ACTIVITY_NEW_TASK这个标志
        context.startActivity(intent1);
        Toast.makeText(context,"您已强制下线，请重新登录",Toast.LENGTH_SHORT).show();
    }
}