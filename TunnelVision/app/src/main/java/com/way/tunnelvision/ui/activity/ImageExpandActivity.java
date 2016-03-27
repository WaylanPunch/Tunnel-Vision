package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.entity.service.ImageDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ImageLoaderUtil;
import com.way.tunnelvision.util.LogUtil;

public class ImageExpandActivity extends BaseActivity {
    private final static String TAG = ImageExpandActivity.class.getName();

    //private TextView tv_title;
    private ImageView iv_image;
    private ImageView iv_download;
    private ProgressBar pb_progress;

    private ImageModel imageModel;
    private String imageTitle;
    private String imageUrl;
    private String imageThumbUrl;
    private String fileName;
    private String fileExtension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_expand);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_image_expand_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageModel = getIntent().getParcelableExtra(Constants.ACTIVITY_PARAMETER);
        if (null != imageModel) {
            imageTitle = imageModel.getTitle();
            toolbar.setTitle(imageTitle);
        }

        initView();
    }

    private void initView() {
        //tv_title = (TextView) findViewById(R.id.tv_image_expand_title);
        iv_image = (ImageView) findViewById(R.id.iv_image_expand_image);
        iv_download = (ImageView) findViewById(R.id.iv_image_expand_download);
        pb_progress = (ProgressBar) findViewById(R.id.pb_image_expand_progress);

        //tv_title.setText(imageTitle);

        ImageLoaderUtil.display(ImageExpandActivity.this, iv_image, imageUrl);
        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderUtil.downloadImageToStorage(imageModel, ImageExpandActivity.this);
                /*
                pb_progress.setVisibility(View.VISIBLE);
                //pb_progress.show
                ImageLoaderUtil.downloadImage(imageUrl, new ImageLoaderUtil.OnDownloadImageListener() {

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        if (null != bitmap) {
                            LogUtil.d(TAG, "onCreate debug, bitmap != NULL");
                            String imageFullPath = ImageUtil.saveBitmapToExternalStorage(bitmap, fileName);
                            //saveBitmap(bitmap);
                            Toast.makeText(ImageExpandActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ImageExpandActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                        }
                        pb_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        pb_progress.setVisibility(View.GONE);
                        Toast.makeText(ImageExpandActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });
                */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_expand, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_image_expand_collection) {
            try {
                if (null != imageModel) {
                    imageModel.setIsCollection(1);
                    ImageDaoHelper imageDaoHelper = ImageDaoHelper.getInstance();
                    imageDaoHelper.addData(imageModel);
                    Toast.makeText(ImageExpandActivity.this, "您已收藏该图片", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "onOptionsItemSelected error, action_image_refresh", e);
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
