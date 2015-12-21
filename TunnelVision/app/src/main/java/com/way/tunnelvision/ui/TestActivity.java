package com.way.tunnelvision.ui;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.way.tunnelvision.R;

public class TestActivity extends Activity implements SurfaceHolder.Callback {
    private final static String TAG = TestActivity.class.getName();

    /**
     * Called when the activity is first created.
     */
    MediaPlayer player;
    SurfaceView surface;
    SurfaceHolder surfaceHolder;
    Button play, pause, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onCreate debug, start");
        initView();
        Log.d(TAG, "onCreate debug, end");
    }

    private void initView() {
        Log.d(TAG, "onCreate debug, start");
        try {
            play = (Button) findViewById(R.id.button1);
            pause = (Button) findViewById(R.id.button2);
            stop = (Button) findViewById(R.id.button3);
            surface = (SurfaceView) findViewById(R.id.surface);

            surfaceHolder = surface.getHolder();//SurfaceHolder是SurfaceView的控制接口
            surfaceHolder.addCallback(this); //因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
            surfaceHolder.setFixedSize(320, 220);//显示的分辨率,不设置为视频默认
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//Surface类型

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.start();
                }
            });
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.pause();
                }
            });
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.stop();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onCreate error", e);
        }
        Log.d(TAG, "onCreate debug, end");
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        //必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(surfaceHolder);
        //设置显示视频显示在SurfaceView上
        try {
            //player.setDataSource("/sdcard/3.mp4");
            //player.prepare();

            //其中使用FileDescriptor时，需要将文件放到与res文件夹平级的assets文件夹里，然后使用：
            AssetFileDescriptor fileDescriptor = getAssets().openFd("meiyan_light.ts");
            player.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        //Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
    }

}
