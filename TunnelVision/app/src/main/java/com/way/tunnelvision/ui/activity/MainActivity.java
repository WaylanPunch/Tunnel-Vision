package com.way.tunnelvision.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.desmond.ripple.RippleCompat;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.NewsViewPagerAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.entity.service.ChannelDaoHelper;
import com.way.tunnelvision.entity.service.HeaderImageDaoHelper;
import com.way.tunnelvision.onekeyshare.OnekeyShare;
import com.way.tunnelvision.onekeyshare.OnekeyShareTheme;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;
import com.way.tunnelvision.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = MainActivity.class.getName();

    private int requestCode;

    //About the Database
//    private SQLiteDatabase db;
//    private DaoMaster daoMaster;
//    private DaoSession daoSession;
//    private ChannelDao channelDao;
//    private Cursor cursor;
    private ChannelDaoHelper channelDaoHelper;
    //private HeaderImageDaoHelper headerImageDaoHelper;

    private MaterialViewPager mMaterialViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private List<HeaderImageModel> headerImageModels = new ArrayList<>();
    private List<ChannelModel> channelModels = new ArrayList<>();
    private NewsViewPagerAdapter newsViewPagerAdapter;
//    private int FIRST_TIME_NEWS = 0;
//    private int FIRST_TIME_COLLECTION = 0;
//    private int FIRST_TIME_PHOTO = 0;
//    private int FIRST_TIME_WEATHER = 0;
//    private int FIRST_TIME_SETTINGS = 0;
//    private static int MENU_ITEM_CHOSEN_INDEX = 0;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RippleCompat.init(this);

        //view
        setTitle("");

        mMaterialViewPager = (MaterialViewPager) findViewById(R.id.mvp_main_view_pager);

        toolbar = mMaterialViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.dl_main_drawer_layout);
        //mDrawer.
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                Drawable colorDrawable = new ColorDrawable(getResources().getColor(android.R.color.black));
                actionBar.setBackgroundDrawable(colorDrawable);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_menu);
        navigationView.setNavigationItemSelectedListener(this);

        //FIRST_TIME_NEWS = 1;
        initChannelData();

        fragmentManager = getSupportFragmentManager();
        newsViewPagerAdapter = new NewsViewPagerAdapter(fragmentManager);
        newsViewPagerAdapter.setData(channelModels);
        mMaterialViewPager.getViewPager().setAdapter(newsViewPagerAdapter);
        mMaterialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int position) {
                ChannelModel channelModel = channelModels.get(position);
                if (headerImageModels != null) {
                    if(headerImageModels.size() > 1) {
                        return HeaderDesign.fromColorResAndUrl(R.color.colorPrimary, headerImageModels.get(1).getUrl());
                    } else {
                        return HeaderDesign.fromColorResAndUrl(R.color.colorPrimary, headerImageModels.get(0).getUrl());
                    }
                } else {
                    return HeaderDesign.fromColorResAndUrl(R.color.colorPrimary, Constants.NEWS.HEADER_IMAGE_DEFAULT);
                }
            }
        });

        mMaterialViewPager.getViewPager().setOffscreenPageLimit(mMaterialViewPager.getViewPager().getAdapter().getCount());
        mMaterialViewPager.getPagerTitleStrip().setViewPager(mMaterialViewPager.getViewPager());

        View logo = findViewById(R.id.iv_fragment_header_logo);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialViewPager.notifyHeaderChanged();
                    //Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initChannelData() {
        LogUtil.d(TAG, "initChannelData debug, start");
        channelModels.clear();
        ///*
        try {

            if (null == channelDaoHelper) {
                channelDaoHelper = ChannelDaoHelper.getInstance();
            }
            long totalCount = channelDaoHelper.getTotalCount();
            LogUtil.d(TAG, "initChannelData debug, Total Count = " + totalCount);
            channelModels = channelDaoHelper.getAllDataByChosen();
            LogUtil.d(TAG, "initChannelData debug, Chosen Count = " + channelModels.size());

            HeaderImageDaoHelper headerImageDaoHelper = HeaderImageDaoHelper.getInstance();
            headerImageModels = headerImageDaoHelper.getDataByDefautAndChosen();
        } catch (Exception e) {
            LogUtil.e(TAG, "initChannelData error", e);
        }
        LogUtil.d(TAG, "initChannelData debug, end");
    }

    private View.OnClickListener viewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

        }
    };

    /**
     * Activity彻底运行起来之后的回调
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_subscribe) {
            requestCode = 0;
            openActivityForResult(ChannelLibraryActivity.class, requestCode);
            return true;
        } else if (id == R.id.action_share) {
            //openActivity(TestActivity.class);
            showShare(this, null, true);
            //showShare();
            return true;
        }
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:
                refreshNewsViewPager();
                break;
            case 2:
                //mText02.setText(change02);
                break;
            default:
                break;
        }
    }

    public void refreshNewsViewPager() {
        LogUtil.d(TAG, "refreshNewsViewPager debug, Get Data from Database Again.");
        initChannelData();
        newsViewPagerAdapter.setData(channelModels);
        newsViewPagerAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_menu_item_news) {
            LogUtil.d(TAG, "onNavigationItemSelected debug, nav_menu_item_news index = " + 0);
            //MENU_ITEM_CHOSEN_INDEX = 0;
        } else if (id == R.id.nav_menu_item_image) {
            LogUtil.d(TAG, "onNavigationItemSelected debug, nav_menu_item_photo index = " + 2);
            openActivity(ImageActivity.class);
            //MENU_ITEM_CHOSEN_INDEX = 2;
        } else if (id == R.id.nav_menu_item_collection) {
            LogUtil.d(TAG, "onNavigationItemSelected debug, nav_menu_item_collection index = " + 1);
            openActivity(CollectionActivity.class);
            //MENU_ITEM_CHOSEN_INDEX = 1;
        } else if (id == R.id.nav_menu_item_weather) {
            LogUtil.d(TAG, "onNavigationItemSelected debug, nav_menu_item_weather index = " + 3);
            openActivity(WeatherActivity.class);
            //MENU_ITEM_CHOSEN_INDEX = 3;
        } else if (id == R.id.nav_menu_item_settings) {
            LogUtil.d(TAG, "onNavigationItemSelected debug, nav_menu_item_settings index = " + 4);
            openActivity(BackgroundActivity.class);
            //MENU_ITEM_CHOSEN_INDEX = 4;
        } else if (id == R.id.nav_menu_item_about) {
            LogUtil.d(TAG, "onNavigationItemSelected debug, nav_menu_item_about index = " + 5);
            openActivity(AboutActivity.class);
            //MENU_ITEM_CHOSEN_INDEX = 4;
        }
        else if (id == R.id.nav_menu_item_exit) {
            LogUtil.d(TAG, "onNavigationItemSelected debug, nav_menu_item_exit index = " + 6);
            //MENU_ITEM_CHOSEN_INDEX = 5;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle(getString(R.string.text_dialog_title));
            builder.setMessage(getString(R.string.text_dialog_exit_warning));
            builder.setPositiveButton(getString(R.string.text_dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCollector.finishAll();
                }
            });
            builder.setNegativeButton(getString(R.string.text_dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle(getString(R.string.text_dialog_title));
            builder.setMessage(getString(R.string.text_dialog_exit_warning));
            builder.setPositiveButton(getString(R.string.text_dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCollector.finishAll();
                }
            });
            builder.setNegativeButton(getString(R.string.text_dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            //super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityCollector.finishAll();
    }


    private void showShare() {
        LogUtil.d(TAG, "showShare debug, Default");
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

    /**
     * 演示调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare  指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     * @param showContentEdit  是否显示编辑页
     */
    public void showShare(Context context, String platformToShare, boolean showContentEdit) {
        LogUtil.d(TAG, "showShare debug, 3 Parameters");
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle("Tunnel Vision");
        oks.setTitleUrl("http://fir.im/lad8");
        oks.setText("我发现一个优秀的APP，分享给大家，希望你们也喜欢！");
        oks.setImagePath("file:///android_asset/ic_launcher.png");  //分享sdcard目录下的图片
        //oks.setImageUrl(randomPic()[0]);
        oks.setUrl("http://fir.im/lad8"); //微信不绕过审核分享链接
        //oks.setFilePath("/sdcard/test-pic.jpg");  //filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
        oks.setComment("美翻了╮(╯_╰)╭"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite("fir.im");  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setSiteUrl("http://fir.im/lad8");//QZone分享参数
        oks.setVenueName("下载地址");
        oks.setVenueDescription("扫描二维码下载，或用手机浏览器输入这个网址:  http://fir.im/lad8");
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标
        //Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        //String label = "ShareSDK";
        //View.OnClickListener listener = new View.OnClickListener() {
            //public void onClick(View v) {

            //}
        //};
        //oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }

    public static String[] randomPic() {
        String url = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/";
        String urlSmall = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/small/";
        String[] pics = new String[] {
                "120.JPG",
                "127.JPG",
                "130.JPG",
                "18.JPG",
                "184.JPG",
                "22.JPG",
                "236.JPG",
                "237.JPG",
                "254.JPG",
                "255.JPG",
                "263.JPG",
                "265.JPG",
                "273.JPG",
                "37.JPG",
                "39.JPG",
                "IMG_2219.JPG",
                "IMG_2270.JPG",
                "IMG_2271.JPG",
                "IMG_2275.JPG",
                "107.JPG"
        };
        int index = (int) (System.currentTimeMillis() % pics.length);
        return new String[] {
                url + pics[index],
                urlSmall + pics[index]
        };
    }
}
