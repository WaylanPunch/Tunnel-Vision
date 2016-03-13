package com.way.tunnelvision.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ImageLoaderUtil;

import java.io.File;

public class ImageExpandActivity extends BaseActivity {
    private final static String TAG = ImageExpandActivity.class.getName();

    private TextView tv_title;
    private ImageView iv_image;
    private ImageView iv_download;
    private ProgressBar pb_progress;

    private ImageModel imageModel;
    private String imageUrl;
    private String imageThumbUrl;
    private String fileName;

    public static final String IMAGE_FILE_TEMPLATE = "img_%d.jpg";
    private String TARGET_DIR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_expand);

        imageModel = getIntent().getParcelableExtra(Constants.ACTIVITY_PARAMETER);
        if (null != imageModel) {
            imageUrl = imageModel.getSourceurl();
            imageThumbUrl = imageModel.getThumburl();
            Log.d(TAG, "onCreate debug, Image Source Url = " + imageUrl);
            Log.d(TAG, "onCreate debug, Image Thumb Url = " + imageThumbUrl);
            int lastIndexSlash = 0;
            if (imageThumbUrl.contains("/")) {
                lastIndexSlash = imageThumbUrl.lastIndexOf("/");
            } else {
                if (imageThumbUrl.contains("\\")) {
                    lastIndexSlash = imageThumbUrl.lastIndexOf("\\");
                }
            }
            fileName = imageThumbUrl.substring(lastIndexSlash, imageThumbUrl.length());
            Log.d(TAG, "onCreate debug, Image Name = " + fileName);
        }
        TARGET_DIR = this.getFilesDir().getAbsolutePath() + File.separator;

        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_image_expand_title);
        iv_image = (ImageView) findViewById(R.id.iv_image_expand_image);
        iv_download = (ImageView) findViewById(R.id.iv_image_expand_download);
        pb_progress = (ProgressBar) findViewById(R.id.pb_image_expand_progress);
        ImageLoaderUtil.display(ImageExpandActivity.this, iv_image, imageUrl);
        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_progress.setVisibility(View.VISIBLE);
                //pb_progress.show
                ImageLoaderUtil.downloadImage(imageUrl, new ImageLoaderUtil.OnDownloadImageListener() {

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        if (null != bitmap) {
                            Log.d(TAG, "onCreate debug, bitmap != NULL");

                            File file = new File(TARGET_DIR + fileName);
                            if (file.exists())
                                return;
                        }
                        pb_progress.setVisibility(View.GONE);
                        Toast.makeText(ImageExpandActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        pb_progress.setVisibility(View.GONE);
                        Toast.makeText(ImageExpandActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
