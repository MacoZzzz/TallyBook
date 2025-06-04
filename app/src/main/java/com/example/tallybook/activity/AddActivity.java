package com.example.tallybook.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tallybook.R;
import com.example.tallybook.bean.Tally;
import com.example.tallybook.util.MySqliteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 添加或者修改
 */
public class AddActivity extends BaseActivity {
    MySqliteOpenHelper helper = null;
    private TextView tvTitle;
    private EditText etMoney;
    private EditText etRemark;
    private RadioGroup rgType;
    private Tally mTally;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tvTitle = findViewById(R.id.tv_title);
        etMoney = findViewById(R.id.et_money);
        etRemark = findViewById(R.id.et_remark);
        rgType = findViewById(R.id.rg_type);
        helper = new MySqliteOpenHelper(this);
        mTally = (Tally) getIntent().getSerializableExtra("tally");
        if (mTally!=null) {
            etMoney.setText(mTally.getMoney());
            etRemark.setText(mTally.getRemark());
            rgType.check(mTally.getTypeId()==0?R.id.rb_income:R.id.rb_expend);
            tvTitle.setText("修改");
        }
    }

    /**
     * 保存
     * @param view
     */
    public void save(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        String money = etMoney.getText().toString();
        String remark = etRemark.getText().toString();
        Integer typeId = rgType.getCheckedRadioButtonId()==R.id.rb_income?0:1;

        if ("".equals(money)) {
            Toast.makeText(AddActivity.this,"金额不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(remark)) {
            Toast.makeText(AddActivity.this,"备注不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mTally == null) {//新增
            String sql = "insert into tally(typeId,money,remark,date) values(?,?,?,?)";
            db.execSQL(sql,new Object[]{typeId,money,remark,sf.format(new Date())});
            Toast.makeText(AddActivity.this,"新增成功",Toast.LENGTH_SHORT).show();
        }else {//修改
            db.execSQL("update tally set typeId = ?, money = ?, remark = ?, date = ? where id=?", new Object[]{typeId,money, remark,sf.format(new Date()), mTally.getId()});
            Toast.makeText(AddActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
        }
        db.close();
        finish();
    }

    /**
     * 判断数据是否为空
     * @param name
     * @param age
     * @param info
     */
    private boolean isEmpty(String name,String age,String info){

        if ("".equals(info)) {
            Toast.makeText(AddActivity.this,"描述不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //返回
    public void back(View view){
        finish();
    }
}
