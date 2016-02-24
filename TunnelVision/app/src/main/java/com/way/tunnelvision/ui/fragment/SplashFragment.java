package com.way.tunnelvision.ui.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.activity.AdDisplayActivity;

/**
 * Created by pc on 2016/2/23.
 */
public class SplashFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button btn_enter;
    private ImageView iv_image;
    private TextView tv_tip, tv_detail;

    public SplashFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SplashFragment newInstance(int sectionNumber) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        btn_enter = (Button) rootView.findViewById(R.id.btn_splash_enter);
        iv_image = (ImageView) rootView.findViewById(R.id.iv_splash_introduce_image);
        tv_tip = (TextView) rootView.findViewById(R.id.tv_splash_introduce_tip);
        tv_detail = (TextView) rootView.findViewById(R.id.tv_splash_introduce_detail);

        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface typeFace_xingshu =Typeface.createFromAsset(getActivity().getAssets(),"fonts/YuWeiXingShuJianTi.ttf");
        //Typeface typeFace_rmedium =Typeface.createFromAsset(getActivity().getAssets(),"fonts/rmedium.ttf");
        Typeface typeFace_ritalic =Typeface.createFromAsset(getActivity().getAssets(),"fonts/ritalic.ttf");
        //使用字体
        btn_enter.setTypeface(typeFace_xingshu);
        tv_tip.setTypeface(typeFace_xingshu);
        tv_detail.setTypeface(typeFace_ritalic);

        int sectionIndex = getArguments().getInt(ARG_SECTION_NUMBER);
        //btn_enter.setText(getString(R.string.section_format, sectionIndex));
        switch (sectionIndex) {
            case 1:
                rootView.setBackgroundResource(R.drawable.bg_splash_background_1);
                iv_image.setBackgroundResource(R.drawable.ic_splash_introduce_image_1);
                tv_tip.setText("Free");
                tv_detail.setText("Tunnel Vision is free forever. Few ads. No subscription fees.");
                btn_enter.setVisibility(View.INVISIBLE);
                break;
            case 2:
                rootView.setBackgroundResource(R.drawable.bg_splash_background_2);
                iv_image.setBackgroundResource(R.drawable.ic_splash_introduce_image_2);
                tv_tip.setText("Fast");
                tv_detail.setText("Tunnel Vision always delivers the latest news faster than any other application.");
                btn_enter.setVisibility(View.INVISIBLE);
                break;
            default:
                rootView.setBackgroundResource(R.drawable.bg_splash_background_3);
                iv_image.setBackgroundResource(R.drawable.ic_splash_introduce_image_3);
                tv_tip.setText("Powerful");
                tv_detail.setText("Tunnel Vision has no limits to your reading.");
                btn_enter.setVisibility(View.VISIBLE);
                break;
        }
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AdDisplayActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.create_zoomin, R.anim.create_zoomout);
            }
        });
        return rootView;
    }
}
