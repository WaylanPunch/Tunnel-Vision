package com.way.tunnelvision.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.model.MenuModel;
import com.way.tunnelvision.model.dao.DaoMaster;
import com.way.tunnelvision.model.dao.DaoSession;
import com.way.tunnelvision.model.dao.MenuDao;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

public class FeedsLibraryActivity extends BaseActivity {
    private final static String TAG = FeedsLibraryActivity.class.getName();
    private int resultCode = 0;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private MenuDao menuDao;

    private EditText et_title, et_link;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds_library);

        initDataBase();
        initView();

    }

    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        menuDao = daoSession.getMenuDao();
    }

    private void initView() {
        et_title = (EditText) findViewById(R.id.et_feeds_library_title);
        et_link = (EditText) findViewById(R.id.et_feeds_library_link);
        btn_add = (Button) findViewById(R.id.btn_feeds_library_add_feed);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCreate debug, start");
                try {
                    if (TextUtils.isEmpty(et_title.getText()) || TextUtils.isEmpty(et_link.getText())) {
                        Toast.makeText(FeedsLibraryActivity.this, "请添加标题或链接", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String title = et_title.getText().toString();
                    String link = et_link.getText().toString();
                    MenuModel menuModel = new MenuModel(null, link, title, link);
                    menuDao.insert(menuModel);
                    //MainActivity.refreshMenu();

                    et_title.setText("");
                    et_link.setText("");

//                    Intent mIntent = new Intent();
//                    mIntent.putExtra("change01", "1000");
//                    mIntent.putExtra("change02", "2000");
//                    // 设置结果，并进行传送
//                    setResult(resultCode, mIntent);
                    setResult(resultCode);
                } catch (Exception e) {
                    Log.e(TAG, "onCreate error", e);
                }
                Log.d(TAG, "onCreate debug, end");

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(FeedsLibraryActivity.this);
        db.close();
        daoSession.clear();
    }
}
