package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.impl.HeaderImageModelImpl;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.entity.service.HeaderImageService;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.LogUtil;

import java.util.List;

public class TestActivity extends BaseActivity {
    private final static String TAG = TestActivity.class.getName();

    HeaderImageModelImpl headerImageModelImpl;
    Button btnStart;
    Button btnStop;
    Button btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        headerImageModelImpl = new HeaderImageModelImpl();

        btnStart = (Button) findViewById(R.id.btn_test_startservice);
        btnStop = (Button) findViewById(R.id.btn_test_stopservice);
        btnImage = (Button) findViewById(R.id.btn_test_getimage);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeaderImageService.addNotification(5000, "通知", "测试消息", "测试内容");
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeaderImageService.cleanAllNotification();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (headerImageModelImpl == null) {
                        headerImageModelImpl = new HeaderImageModelImpl();
                    }
                    headerImageModelImpl.loadHeaderImageList(new HeaderImageModelImpl.OnLoadHeaderImageListListener() {
                        @Override
                        public void onSuccess(List<HeaderImageModel> list) {
                            if (list != null && list.size() > 0) {
                                LogUtil.d(TAG, "btnImage.setOnClickListener debug, HeaderImageModels COUNT = " + list.size());
                            }
                        }

                        @Override
                        public void onFailure(String msg, Exception e) {
                            LogUtil.e(TAG, "btnImage.setOnClickListener error, " + msg, e);
                        }
                    });
                } catch (Exception e) {
                    LogUtil.e(TAG, "btnImage.setOnClickListener error", e);
                }
            }
        });
    }
}
