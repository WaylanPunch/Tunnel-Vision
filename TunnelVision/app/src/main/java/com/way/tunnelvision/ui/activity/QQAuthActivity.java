package com.way.tunnelvision.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.way.tunnelvision.R;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ImageLoaderUtil;
import com.way.tunnelvision.util.LogUtil;
import com.way.tunnelvision.util.PreferencesUtil;

import org.json.JSONObject;

public class QQAuthActivity extends BaseActivity {
    private final static String TAG = QQAuthActivity.class.getName();
    private int resultCode = 3;

    private TextView tv_QQNickname;
    private ImageView iv_QQFigureIcon;
    private Button btn_QQAuthLogin;

    private Tencent mTencent; //qq主操作对象
    private IUiListener loginListener; //授权登录监听器
    private IUiListener userInfoListener; //获取用户信息监听器
    private String scope; //获取信息的范围参数
    private UserInfo userInfo; //qq用户信息

    private static int LOGIN_OR_LOGOUT = 1;//1未登录，需要登录；2已登录，需要登出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqauth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_qqauth_toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_qqauth));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        LogUtil.d(TAG, "onCreate debug");
        initData();
        initViews();

        final String nickName = PreferencesUtil.getString(QQAuthActivity.this,Constants.QQ_AUTH_NICKNAME);
        final String gender = PreferencesUtil.getString(QQAuthActivity.this,Constants.QQ_AUTH_GENDER);
        final String figureurl_qq_2 = PreferencesUtil.getString(QQAuthActivity.this,Constants.QQ_AUTH_FIGUREURL);
        if(nickName != null){ //已登录
            //btn_QQAuthLogin.setText("退出");
            //LOGIN_OR_LOGOUT = 2;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //前往Handler获取用户信息
                    Message msg = new Message();
                    msg.what = 1;
                    msg.getData().putString("nickName", nickName);
                    msg.getData().putString("gender", gender);
                    msg.getData().putString("figureurl_qq_2", figureurl_qq_2);
                    mHandler.sendMessage(msg);
                }
            }).start();
        }else {//未登录
            LOGIN_OR_LOGOUT = 1;
            btn_QQAuthLogin.setText("QQ授权登录");
            tv_QQNickname.setText("昵称");
            iv_QQFigureIcon.setImageResource(android.R.drawable.sym_def_app_icon);
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                LogUtil.d(TAG, "mHandler handleMessage debug, msg.what == 0, 开始获取用户信息");
                userInfo = new UserInfo(QQAuthActivity.this, mTencent.getQQToken());
                userInfo.getUserInfo(userInfoListener);
            }else if(msg.what == 1){
                LogUtil.d(TAG, "mHandler handleMessage debug, msg.what == 1, 开始处理用户信息");
                btn_QQAuthLogin.setText("退出");
                LOGIN_OR_LOGOUT = 2;
                String nickName = msg.getData().getString("nickName");
                String gender = msg.getData().getString("gender");
                String figureurl_qq_2 = msg.getData().getString("figureurl_qq_2");
                LogUtil.d(TAG, "mHandler handleMessage debug, msg.what == 1, nickName = " + nickName);
                LogUtil.d(TAG, "mHandler handleMessage debug, msg.what == 1, gender = " + gender);
                LogUtil.d(TAG, "mHandler handleMessage debug, msg.what == 1, figureurl_qq_2 = " + figureurl_qq_2);
                tv_QQNickname.setText(nickName);
                ImageLoaderUtil.display(QQAuthActivity.this, iv_QQFigureIcon,figureurl_qq_2);

                PreferencesUtil.putString(QQAuthActivity.this,Constants.QQ_AUTH_NICKNAME,nickName);
                PreferencesUtil.putString(QQAuthActivity.this,Constants.QQ_AUTH_GENDER,gender);
                PreferencesUtil.putString(QQAuthActivity.this,Constants.QQ_AUTH_FIGUREURL,figureurl_qq_2);
            }
        }
    };

    /**
     * http://blog.csdn.net/bear_huangzhen/article/details/46602741
     */
    private void initData() {
        LogUtil.d(TAG, "initData debug");
        //初始化qq主操作对象
        mTencent = Tencent.createInstance(Constants.QQ_OPEN_APP_ID, QQAuthActivity.this);
        //要所有权限，不然会再次申请增量权限，这里不要设置成get_user_info,add_t
        scope = "all";

        loginListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                LogUtil.d(TAG, "initData loginListener onError UiError, " + arg0.errorDetail);
            }

            /**
             * 返回json数据样例
             *
             * {"ret":0,"pay_token":"D3D678728DC580FBCDE15722B72E7365",
             * "pf":"desktop_m_qq-10000144-android-2002-",
             * "query_authority_cost":448,
             * "authority_cost":-136792089,
             * "openid":"015A22DED93BD15E0E6B0DDB3E59DE2D",
             * "expires_in":7776000,
             * "pfkey":"6068ea1c4a716d4141bca0ddb3df1bb9",
             * "msg":"",
             * "access_token":"A2455F491478233529D0106D2CE6EB45",
             * "login_cost":499}
             */
            @Override
            public void onComplete(Object value) {
                LogUtil.d(TAG, "initData loginListener onComplete, 有数据返回..");
                if (value == null) {
                    return;
                }

                try {
                    JSONObject jo = (JSONObject) value;
                    int ret = jo.getInt("ret");
                    LogUtil.d(TAG, "initData loginListener onComplete, json = " + String.valueOf(jo));
                    if (ret == 0) {
                        Toast.makeText(QQAuthActivity.this, "登录成功",
                                Toast.LENGTH_LONG).show();

                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);

                        //前往Handler获取用户信息
                        Message msg = new Message();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    LogUtil.e(TAG, "initData loginListener onComplete Exception", e);
                }

            }

            @Override
            public void onCancel() {
                LogUtil.d(TAG, "initData loginListener onCancel");
            }
        };

        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                LogUtil.d(TAG, "initData userInfoListener onError UiError, " + arg0.errorDetail);
            }

            /**
             * 返回用户信息样例
             *
             * {
             * "ret":0,
             * "msg":"",
             * "is_lost":0,
             * "nickname":"Waylan Punch",
             * "gender":"男",
             * "province":"浙江",
             * "city":"",
             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1105316728\/F04A4270C3ECFF652383DCF1475AA0DA\/30",
             * "figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1105316728\/F04A4270C3ECFF652383DCF1475AA0DA\/50",
             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1105316728\/F04A4270C3ECFF652383DCF1475AA0DA\/100",
             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1105316728\/F04A4270C3ECFF652383DCF1475AA0DA\/40",
             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1105316728\/F04A4270C3ECFF652383DCF1475AA0DA\/100",
             * "is_yellow_vip":"0",
             * "vip":"0",
             * "yellow_vip_level":"0",
             * "level":"0",
             * "is_yellow_year_vip":"0"
             }
             */
            @Override
            public void onComplete(Object arg0) {
                LogUtil.d(TAG, "initData userInfoListener onComplete");
                if(arg0 == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    LogUtil.d(TAG, "initData userInfoListener onComplete, json = " + String.valueOf(jo));
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    String figureurl_qq_2 = jo.getString("figureurl_qq_2");
                    Toast.makeText(QQAuthActivity.this, "你好，" + nickName,
                            Toast.LENGTH_LONG).show();

                    //前往Handler获取用户信息
                    Message msg = new Message();
                    msg.what = 1;
                    msg.getData().putString("nickName", nickName);
                    msg.getData().putString("gender", gender);
                    msg.getData().putString("figureurl_qq_2", figureurl_qq_2);
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    LogUtil.e(TAG, "initData userInfoListener onComplete Exception", e);
                }
            }

            @Override
            public void onCancel() {
                LogUtil.d(TAG, "initData userInfoListener onCancel");
            }
        };
    }

    private void initViews() {
        LogUtil.d(TAG, "initViews debug");
        // 初始化视图
        tv_QQNickname = (TextView) findViewById(R.id.tv_qqauth_nickname);
        iv_QQFigureIcon = (ImageView) findViewById(R.id.iv_qqauth_figureicon);
        btn_QQAuthLogin = (Button) findViewById(R.id.btn_qqauth_login);


        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeFace_xingshu =Typeface.createFromAsset(getAssets(),"fonts/YuWeiXingShuJianTi.ttf");
        //Typeface typeFace_rmedium =Typeface.createFromAsset(getActivity().getAssets(),"fonts/rmedium.ttf");
        Typeface typeFace_ritalic =Typeface.createFromAsset(getAssets(),"fonts/ritalic.ttf");
        //使用字体
        btn_QQAuthLogin.setTypeface(typeFace_xingshu);
        tv_QQNickname.setTypeface(typeFace_ritalic);

        btn_QQAuthLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LOGIN_OR_LOGOUT == 1) {
                    LogUtil.d(TAG, "initViews btn_QQAuthLogin.setOnClickListener onClick debug, QQ授权登录");
                    // 是否支持sso登录
                    if (mTencent.isSupportSSOLogin(QQAuthActivity.this)) {
                        login();
                    }
                }else if(LOGIN_OR_LOGOUT == 2){
                    if (mTencent != null) {
                        LogUtil.d(TAG, "initViews btn_QQAuthLogin.setOnClickListener onClick debug, 退出");
                        //注销登录
                        mTencent.logout(QQAuthActivity.this);
                    }
                    PreferencesUtil.putString(QQAuthActivity.this,Constants.QQ_AUTH_NICKNAME,null);
                    PreferencesUtil.putString(QQAuthActivity.this,Constants.QQ_AUTH_GENDER,null);
                    PreferencesUtil.putString(QQAuthActivity.this,Constants.QQ_AUTH_FIGUREURL,null);
                    LOGIN_OR_LOGOUT = 1;
                    btn_QQAuthLogin.setText("QQ授权登录");
                    tv_QQNickname.setText("昵称");
                    iv_QQFigureIcon.setImageResource(android.R.drawable.sym_def_app_icon);
                }
            }
        });
    }

    private void login() {
        LogUtil.d(TAG, "login debug");
        //如果session无效，就开始登录
        if (!mTencent.isSessionValid()) {
            LogUtil.d(TAG, "login debug, mTencent.isSessionValid() == false");
            //开始qq授权登录
            mTencent.login(QQAuthActivity.this, scope, loginListener);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.d(TAG, "onActivityResult debug, requestCode = " + requestCode);
        LogUtil.d(TAG, "onActivityResult debug, resultCode = " + resultCode);
        if(data != null){
            LogUtil.d(TAG, "onActivityResult debug, data != null");
        }
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN) {
            if (resultCode == com.tencent.connect.common.Constants.ACTIVITY_OK) {
                Tencent.handleResultData(data, loginListener);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(TAG, "onDestroy debug");
        setResult(resultCode);
//        if (mTencent != null) {
//            //注销登录
//            mTencent.logout(QQAuthActivity.this);
//        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(resultCode);
        finish();
    }
}
