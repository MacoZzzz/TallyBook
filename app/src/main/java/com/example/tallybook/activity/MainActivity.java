package com.example.tallybook.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tallybook.R;
import com.example.tallybook.adapter.TallyAdapter;
import com.example.tallybook.bean.Tally;
import com.example.tallybook.util.MySqliteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 主页面
 */
public class MainActivity extends BaseActivity {
    MySqliteOpenHelper helper = null;
    private Activity myActivity;
    private EditText etRemark;//备注内容
    private ImageButton btnSearch;//搜索
    private Button btnAdd;//添加
    private TextView tvIncome;//收入
    private TextView tvExpend;//支出
    private TextView tvTotal;//总数
    private RecyclerView rvTallyList;
    private TallyAdapter mTallyAdapter;
    private List<Tally> mTallies;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_main);
        helper = new MySqliteOpenHelper(myActivity);
        etRemark = findViewById(R.id.et_remark);
        btnSearch = findViewById(R.id.btn_search);
        btnAdd = findViewById(R.id.btn_add);
        tvIncome = findViewById(R.id.income);
        tvExpend = findViewById(R.id.expend);
        tvTotal = findViewById(R.id.total);
        rvTallyList = findViewById(R.id.rv_tally_list);
        initView();
        setViewListener();
    }

    private void setViewListener() {
        //搜索
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        //添加
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myActivity,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvTallyList.setLayoutManager(layoutManager);
        //=2.1、初始化适配器
        mTallyAdapter=new TallyAdapter();
        //=2.3、设置recyclerView的适配器
        rvTallyList.setAdapter(mTallyAdapter);
        mTallyAdapter.setItemListener(new TallyAdapter.ItemListener() {
            @Override
            public void ItemClick(Tally tally) {
                Intent intent = new Intent(myActivity, AddActivity.class);
                intent.putExtra("tally",tally);
                startActivity(intent);
            }

            @Override
            public void ItemLongClick(Tally tally) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(myActivity);
                dialog.setMessage("确认要删除该数据吗");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        if (db.isOpen()) {
                            db.execSQL("delete from tally where id = "+tally.getId());
                            db.close();
                        }
                        Toast.makeText(myActivity,"删除成功",Toast.LENGTH_LONG).show();
                        loadData();
                    }
                });
                dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        loadData();
    }

    private void loadData() {
        mTallies = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String content = etRemark.getText().toString();
        Cursor cursor ;
        if ("".equals(content)) {
            String sql = "select * from tally";
            cursor = db.rawQuery(sql, null);
        }else {
            String sql = "select * from tally where remark like ?";
            cursor = db.rawQuery(sql, new String[]{"%"+content+"%"});
        }

        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Integer dbId = cursor.getInt(0);
                Integer typeId = cursor.getInt(1);
                String money = cursor.getString(2);
                String remark = cursor.getString(3);
                String date = cursor.getString(4);
                Tally tally = new Tally(dbId,typeId, money,remark,date);
                mTallies.add(tally);
            }
        }
        //查询收入总金额
        String incomeSql = "select sum(money) from tally where typeId = 0";
        cursor = db.rawQuery(incomeSql, null);
        Float incomeMoney = 0f;
        Float expendMoney = 0f;
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                incomeMoney = cursor.getFloat(0);
            }
        }
        tvIncome.setText(String.format("收：%s元",incomeMoney));
        //查询支出总金额
        String expendSql = "select sum(money) from tally where typeId = 1";
        cursor = db.rawQuery(expendSql, null);
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                expendMoney = cursor.getFloat(0);
            }
        }
        tvExpend.setText(String.format("支：%s元",expendMoney));
        tvTotal.setText(String.format("合计：%s元",incomeMoney-expendMoney));
        db.close();
        mTallyAdapter.addItem(mTallies);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void my(View view){
        startActivity(new Intent(MainActivity.this,MyActivity.class));
    }
}
