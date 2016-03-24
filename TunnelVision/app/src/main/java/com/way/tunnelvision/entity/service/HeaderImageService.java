package com.way.tunnelvision.entity.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.impl.HeaderImageModelImpl;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.ui.activity.MainActivity;
import com.way.tunnelvision.util.LogUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pc on 2016/3/23.
 */
public class HeaderImageService extends Service {
    private final static String TAG = HeaderImageService.class.getName();
    static Timer timer = null;

    //清除通知
    public static void cleanAllNotification() {
        LogUtil.d(TAG, "cleanAllNotification debug, start");
        try {
            NotificationManager mn = (NotificationManager) MainApp.getContext().getSystemService(NOTIFICATION_SERVICE);
            mn.cancelAll();
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "cleanAllNotification error", e);
        }
        LogUtil.d(TAG, "cleanAllNotification debug, end");
    }

    //添加通知
    public static void addNotification(int delayTime, String tickerText, String contentTitle, String contentText) {
        LogUtil.d(TAG, "addNotification debug, start");
        try {
            Intent intent = new Intent(MainApp.getContext(), HeaderImageService.class);
            intent.putExtra("delayTime", delayTime);
            intent.putExtra("tickerText", tickerText);
            intent.putExtra("contentTitle", contentTitle);
            intent.putExtra("contentText", contentText);
            MainApp.getContext().startService(intent);
        } catch (Exception e) {
            LogUtil.e(TAG, "addNotification error", e);
        }
        LogUtil.d(TAG, "addNotification debug, end");

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "onStartCommand debug, start");
        try {
            long period = 12 * 60 * 60 * 1000; //12小时一个周期
            //long period = 5 * 60 * 1000; //5分钟一个周期
            int delay = intent.getIntExtra("delayTime", 0);
            if (null == timer) {
                timer = new Timer();
            }
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    HeaderImageModelImpl headerImageModelImpl = new HeaderImageModelImpl();
                    headerImageModelImpl.loadHeaderImageList(new HeaderImageModelImpl.OnLoadHeaderImageListListener() {
                        @Override
                        public void onSuccess(List<HeaderImageModel> list) {
                            if (list != null && list.size() > 0) {
                                LogUtil.d(TAG, "onStartCommand debug, HeaderImageModels COUNT = " + list.size());
                                HeaderImageDaoHelper headerImageDaoHelper = HeaderImageDaoHelper.getInstance();
                                headerImageDaoHelper.deleteAll();
                                for (HeaderImageModel item : list) {
                                    headerImageDaoHelper.addData(item);
                                }

                                NotificationManager mn = (NotificationManager) HeaderImageService.this.getSystemService(NOTIFICATION_SERVICE);
                                Notification.Builder builder = new Notification.Builder(HeaderImageService.this);
                                Intent notificationIntent = new Intent(HeaderImageService.this, MainActivity.class);//点击跳转位置
                                PendingIntent contentIntent = PendingIntent.getActivity(HeaderImageService.this, 0, notificationIntent, 0);
                                builder.setContentIntent(contentIntent);
                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                builder.setTicker(intent.getStringExtra("tickerText")); //测试通知栏标题
                                builder.setContentText(intent.getStringExtra("contentText")); //下拉通知啦内容
                                builder.setContentTitle(intent.getStringExtra("contentTitle"));//下拉通知栏标题
                                builder.setAutoCancel(true);
                                builder.setDefaults(Notification.DEFAULT_ALL);
                                Notification notification = builder.build();
                                mn.notify((int) System.currentTimeMillis(), notification);
                            }
                        }

                        @Override
                        public void onFailure(String msg, Exception e) {
                            LogUtil.e(TAG, "onStartCommand error, " + msg, e);
                        }
                    });


                }
            }, delay, period);
        } catch (Exception e) {
            LogUtil.e(TAG, "onCreate error", e);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "onBind debug");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate debug");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy debug");
    }
}
