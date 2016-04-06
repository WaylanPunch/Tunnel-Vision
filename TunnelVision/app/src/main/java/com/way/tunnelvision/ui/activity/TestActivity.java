package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.impl.HeaderImageModelImpl;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.entity.service.HeaderImageDaoHelper;
import com.way.tunnelvision.entity.service.HeaderImageService;
import com.way.tunnelvision.onekeyshare.OnekeyShare;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.LogUtil;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;

public class TestActivity extends BaseActivity {
    private final static String TAG = TestActivity.class.getName();

    HeaderImageModelImpl headerImageModelImpl;
    Button btnStart;
    Button btnStop;
    Button btnImage;
    Button btnQuery;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        headerImageModelImpl = new HeaderImageModelImpl();

        btnStart = (Button) findViewById(R.id.btn_test_startservice);
        btnStop = (Button) findViewById(R.id.btn_test_stopservice);
        btnImage = (Button) findViewById(R.id.btn_test_getimage);
        btnQuery = (Button) findViewById(R.id.btn_test_querydata);
        btnShare = (Button) findViewById(R.id.btn_test_share);

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

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeaderImageDaoHelper headerImageDaoHelper = HeaderImageDaoHelper.getInstance();
                Long COUNT = headerImageDaoHelper.getTotalCount();
                LogUtil.d(TAG, "btnQuery.setOnClickListener debug, HeaderImageModels COUNT = " + COUNT);
                if (COUNT > 0) {
                    List<HeaderImageModel> headerImageModels = headerImageDaoHelper.getAllDataByNumber(5);
                    if (headerImageModels != null && headerImageModels.size() > 0) {
                        LogUtil.d(TAG, "btnQuery.setOnClickListener debug, HeaderImageModels Sub COUNT = " + headerImageModels.size());
                    }
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("Just ShareSDK Test");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
