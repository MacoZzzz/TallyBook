package com.example.tallybook.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tallybook.R;
import com.example.tallybook.bean.User;
import com.example.tallybook.util.MySqliteOpenHelper;
import com.example.tallybook.util.SPUtils;

/**
 * 个人信息
 */
public class PersonActivity extends BaseActivity {
    MySqliteOpenHelper helper = null;
    private Activity mActivity;
    private TextView tvAccount;
    private TextView etNickName;
    private Button btnSave;//保存
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mActivity = this;
        helper = new MySqliteOpenHelper(this);
        tvAccount = findViewById(R.id.tv_account);
        etNickName = findViewById(R.id.tv_nickName);
        btnSave = findViewById(R.id.btn_save);
        initView();
    }

    private void initView() {
        String account = (String) SPUtils.get(mActivity,"account","");
        User user = null;
        String sql = "select * from user where account = ?";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{account});
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                String dbId = cursor.getString(0);
                String dbAccount = cursor.getString(1);
                String dbName = cursor.getString(2);
                String dbPassword = cursor.getString(3);
                user = new User(dbId, dbAccount,dbName,dbPassword);
            }
        }
        db.close();
        if (user != null) {
            tvAccount.setText(user.getAccount());
            etNickName.setText(user.getName());
        }
        //保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = tvAccount.getText().toString();
                String nickName = etNickName.getText().toString();
                if ("".equals(nickName)) {
                    Toast.makeText(mActivity,"昵称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String updateSql = "update user set name =? where account = ?";
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL(updateSql,new Object[]{nickName,account});
                Toast.makeText(PersonActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }
        });
    }
    public void back(View view){
        finish();
    }
}
