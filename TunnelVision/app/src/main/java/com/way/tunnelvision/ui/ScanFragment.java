package com.way.tunnelvision.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.way.tunnelvision.R;
import com.way.tunnelvision.util.RGBLuminanceSource;
import com.way.tunnelvision.util.ToastUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by pc on 2015/12/6.
 */
public class ScanFragment extends Fragment {
    private final static String TAG = ScanFragment.class.getName();

    private Button generateBtn;
    private Button decodeQRCodeBtn;
    private ImageView imageView;
    private TextView textView;
    private EditText contentText;
    private static int QR_WIDTH = 400;
    private static int QR_HEIGHT = 400;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.content_scan, container, false);
            generateBtn = (Button) rootView.findViewById(R.id.btn_scan_qrcode_generate);
            decodeQRCodeBtn = (Button) rootView.findViewById(R.id.btn_scan_qrcode_decode);
            imageView = (ImageView) rootView.findViewById(R.id.iv_scan_qrcode_bitmap);
            textView = (TextView) rootView.findViewById(R.id.tv_scan_qrcode_info);
            contentText = (EditText) rootView.findViewById(R.id.et_scan_qrcode_info);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView error", e);
        }
        Log.d(TAG, "onCreateView debug, end");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated debug, start");
        try {
            generateBtn.setOnClickListener(buttonOnClickListener);
            decodeQRCodeBtn.setOnClickListener(buttonOnClickListener);
        } catch (Exception e) {
            Log.e(TAG, "onActivityCreated error", e);
        }
        Log.d(TAG, "onActivityCreated debug, end");
    }

    View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.btn_scan_qrcode_generate){
                String text = contentText.getText().toString();
                if ("".equals(text) || null == text) {
                    ToastUtil.show(getActivity(), "请输入内容");
                    return;
                }
                Bitmap bitmap = createBitmap(text);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            } else if(id==R.id.btn_scan_qrcode_decode) {
                String content = readImage(imageView);
                textView.setText(content);
            }
        }
    };

    /**
     * 生成二维码图片
     *
     * @return
     */
    private Bitmap createBitmap(String text) {
        Bitmap bitmap = null;
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);

            // QRCodeWriter writer = new QRCodeWriter();
            // // 把输入的文本转为二维码
            // BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE,
            // QR_WIDTH, QR_HEIGHT);

            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 解析QR图内容
     *
     * @param imageView
     * @return
     */
    private String readImage(ImageView imageView) {
        String content = null;
        Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

        // 获得待解析的图片
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            Result result = reader.decode(bitmap1, hints);
            // 得到解析后的文字
            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //listData = null;
    }
}
