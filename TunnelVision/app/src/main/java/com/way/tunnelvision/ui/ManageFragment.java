package com.way.tunnelvision.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.util.ToastUtil;

/**
 * Created by pc on 2015/12/6.
 */
public class ManageFragment extends Fragment {
    private final static String TAG = ManageFragment.class.getName();

    private TextView textView_accountmanagement;
    private TextView textView_notification;
    private TextView textView_privacysafety;
    private TextView textView_generalsettings;
    private TextView textView_feedback;
    private TextView textView_about;
    private TextView textView_clearcache;
    private TextView textView_signout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.content_manage, container, false);
            textView_accountmanagement = (TextView) rootView.findViewById(R.id.tv_manage_accountmanagement);
            textView_notification = (TextView) rootView.findViewById(R.id.tv_manage_notification);
            textView_privacysafety = (TextView) rootView.findViewById(R.id.tv_manage_privacysafety);
            textView_generalsettings = (TextView) rootView.findViewById(R.id.tv_manage_generalsettings);
            textView_feedback = (TextView) rootView.findViewById(R.id.tv_manage_feedback);
            textView_about = (TextView) rootView.findViewById(R.id.tv_manage_about);
            textView_clearcache = (TextView) rootView.findViewById(R.id.tv_manage_clearcache);
            textView_signout = (TextView) rootView.findViewById(R.id.tv_manage_signout);

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
        initView();
        Log.d(TAG, "onActivityCreated debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            textView_accountmanagement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(getContext(), "textView_accountmanagement");
                }
            });
            textView_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(getContext(), "textView_notification");
                }
            });
            textView_privacysafety.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(getContext(), "textView_privacysafety");
                }
            });
            textView_generalsettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(getContext(), "textView_generalsettings");
                }
            });
            textView_feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(getContext(), "textView_feedback");
                }
            });
            textView_about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_about = new Intent(getContext(), AboutActivity.class);
                    getContext().startActivity(intent_about);
                }
            });
            textView_clearcache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(getContext(), "textView_clearcache");
                }
            });
            textView_signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtil.show(getContext(), "Sign Out");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are You Sure To Sign Out?");
                    builder.setTitle("Notification");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent_about = new Intent(getContext(), SigninActivity.class);
                            getContext().startActivity(intent_about);
                            //getActivity().finish();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //listData = null;
    }
}
