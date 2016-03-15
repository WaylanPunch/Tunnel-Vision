package com.way.tunnelvision.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.way.tunnelvision.util.ImageUtil;
import com.way.tunnelvision.util.LogUtil;

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
    private String fileExtension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_expand);

        imageModel = getIntent().getParcelableExtra(Constants.ACTIVITY_PARAMETER);
        if (null != imageModel) {
            imageUrl = imageModel.getSourceurl();
            imageThumbUrl = imageModel.getThumburl();
            LogUtil.d(TAG, "onCreate debug, Image Source Url = " + imageUrl);
            LogUtil.d(TAG, "onCreate debug, Image Thumb Url = " + imageThumbUrl);
            int lastIndexSlash = 0;
            if (imageThumbUrl.contains("/")) {
                lastIndexSlash = imageThumbUrl.lastIndexOf("/");
            } else {
                if (imageThumbUrl.contains("\\")) {
                    lastIndexSlash = imageThumbUrl.lastIndexOf("\\");
                }
            }
            fileName = imageThumbUrl.substring(lastIndexSlash + 1, imageThumbUrl.length());
            int lastIndexDot = 0;
            lastIndexDot = fileName.lastIndexOf(".");
            fileExtension = fileName.substring(lastIndexDot, fileName.length());
            LogUtil.d(TAG, "onCreate debug, Image Name = " + fileName);
            LogUtil.d(TAG, "onCreate debug, Image Format = " + fileExtension);
        }
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
                            LogUtil.d(TAG, "onCreate debug, bitmap != NULL");
                            ImageUtil.saveBitmapToExternalStorage(bitmap, fileName);
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
            }
        });
    }

//    /**
//     * 保存方法
//     */
//    public void saveBitmap(Bitmap bm) {
//        String picFolder = MainApp.getExternalStoragePicFolder();
//        if (null != picFolder) {
//            LogUtil.d(TAG, "saveBitmap debug, Pictures Folder = " + picFolder);
//            String picFullPath = picFolder + File.separator + fileName;
//            LogUtil.d(TAG, "saveBitmap debug, Pictures Full Path = " + picFullPath);
//            File file = new File(picFullPath);
////        if (file.exists())
////            return;
//            if (file.exists()) {
//                file.delete();
//            }
//            try {
//                FileOutputStream out = new FileOutputStream(file);
//                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.flush();
//                out.close();
//            } catch (FileNotFoundException e) {
//                LogUtil.e(TAG, "saveBitmap error", e);
//            } catch (IOException e) {
//                LogUtil.e(TAG, "saveBitmap error", e);
//            }
//        }
//    }
}
