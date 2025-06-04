package com.example.tallybook.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tallybook.R;
import com.example.tallybook.bean.User;
import com.example.tallybook.util.MySqliteOpenHelper;
import com.example.tallybook.util.SPUtils;

/**
 * 重置密码
 */
public class PasswordActivity extends BaseActivity {
    MySqliteOpenHelper helper = null;
    private Activity activity;
    private EditText etOldPassword;
    private EditText etNewPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity =this;
        helper = new MySqliteOpenHelper(this);
        setContentView(R.layout.activity_password);
        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
    }

    //保存信息
    public void save(View v){
        //关闭虚拟键盘
        InputMethodManager inputMethodManager= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
        String account = (String) SPUtils.get(PasswordActivity.this,SPUtils.ACCOUNT,"");
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        if ("".equals(account)){//账号不能为空
            Toast.makeText(activity,"账号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(oldPassword)){//就密码为空
            Toast.makeText(activity,"就密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(newPassword)){//密码为空
            Toast.makeText(activity,"新密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        User mUser = null;
        //获取用户信息
        String sql = "select * from user where account = ?";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{account});
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                String dbId = cursor.getString(0);
                String dbAccount = cursor.getString(1);
                String dbName = cursor.getString(2);
                String dbPassword = cursor.getString(3);
                mUser = new User(dbId, dbAccount,dbName,dbPassword);
            }
        }
        if (mUser != null) {
            if (oldPassword.equals(mUser.getPassword())) {
                String updateSql = "update user set password =? where account = ?";
                db.execSQL(updateSql,new Object[]{newPassword,account});
                Toast.makeText(PasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(PasswordActivity.this, "旧密码错误", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(PasswordActivity.this, "该账号不存在", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void back(View view){
        finish();
    }
}
