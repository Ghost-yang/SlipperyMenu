package com.goodview.gvmenu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.ButterKnife;

/**
 * Created by goodview on 2019/6/25.
 */

public class MenuService extends Service {

    public static final int CANCEL_MENU = 1;

    public static final int SHOW_MENU = 2;

    private View mMenuView;

    private ImageView mImgView;

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mMenuParams;

    private WindowManager.LayoutParams mTouchParams;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case CANCEL_MENU:

                    break;
                case SHOW_MENU:
                    mWindowManager.updateViewLayout(mMenuView, mMenuParams);
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        addTouchView();
    }

    private void addTouchView() {
        mMenuView = View.inflate(this, R.layout.view_channel, null);
        mMenuView.setLayoutParams(new ViewGroup.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, mMenuView);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mMenuParams = new WindowManager.LayoutParams();
        mTouchParams = new WindowManager.LayoutParams();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mMenuParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mMenuParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        mMenuParams.alpha = 1.0f;
        mMenuParams.width = 1125;
        mMenuParams.height = 500;
        mMenuParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH|
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        //mMenuParams.flags = 8651304;
        mMenuParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        mMenuParams.format = PixelFormat.RGBA_8888;
        mMenuParams.x = 0;
        mMenuParams.y = -670;
        mWindowManager.addView(mMenuView, mMenuParams);

        mImgView = new ImageView(getApplicationContext());
        mImgView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mTouchParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        //mTouchParams.alpha = 0.0f;
        mTouchParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mTouchParams.height = 20;
        mTouchParams.flags = 8651304;
        /*mTouchParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH|
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;*/
        mTouchParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        mTouchParams.format = PixelFormat.RGBA_8888;
        mTouchParams.x = 0;
        mTouchParams.y = 0;

        mWindowManager.addView(mImgView, mTouchParams);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                LogUtils.D("-----menuview--" + action);
                LogUtils.D("-----x-->>" + motionEvent.getX() + "----y--->>" + motionEvent.getY());
                switch (action) {
                    default:
                        return false;
                    case MotionEvent.ACTION_OUTSIDE:
                        if (mMenuParams.y > 0) {
                            int maxdistance = 40;
                            while (true) {
                                if(maxdistance < -670) {
                                    break;
                                }
                                mMenuParams.y = maxdistance;
                                mWindowManager.updateViewLayout(mMenuView, mMenuParams);
                                maxdistance--;
                            }
                        }
                        break;
                }
                return false;
            }
        });
        mImgView.setOnTouchListener(new View.OnTouchListener() {

            private float pointY;
            private float mvDistance;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                LogUtils.D("-----mImgView--" + action);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        pointY = motionEvent.getY();
                        LogUtils.D("down pointY------->>" + pointY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float mvpointY = motionEvent.getY();
                        LogUtils.D("move  pointY------->>" + mvpointY);
                        mvDistance = this.pointY - mvpointY;
                        LogUtils.D("move  distance------->>" + mvDistance);
                        if (mvDistance > 60.0F && mvDistance <= 710.0F) {
                            mMenuParams.y = (int) (-670.0F + mvDistance);
                            mWindowManager.updateViewLayout(mMenuView, mMenuParams);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (mvDistance > 60.0F && mvDistance <= 710.0F) {
                            while (true) {
                                if (mvDistance > 710) {
                                    break;
                                }
                                mMenuParams.y = (int) (-670.0F + mvDistance);
                                mWindowManager.updateViewLayout(mMenuView, mMenuParams);
                                mvDistance++;
                            }
                        }
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
