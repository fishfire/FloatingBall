package com.ice.floatingball;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by asd on 4/20/2017.
 */


public class MyAccessibilityService extends AccessibilityService {

    public static final int BACK = 1;
    public static final int HOME = 2;
    private static final String TAG = "ICE";

    private static class MyScreenReceiver extends BroadcastReceiver {
        public MyScreenReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                Log.i("Screen", "screen on");
                showView(context);
//                Intent goToActivity = new Intent(context,MainActivity.class);
//                goToActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(goToActivity);
            } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                Log.i("Screen", "screen off");
            }
        }

        private void showView(Context context) {
            ViewManager.getInstance(context.getApplicationContext()).showFloatBall();
        }}

    private MyScreenReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        mReceiver = new MyScreenReceiver();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mReceiver, filter);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ViewManager.getInstance(this.getApplicationContext()).showFloatBall();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Subscribe
    public void onReceive(Integer action) {
        switch (action) {
            case BACK:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                break;
            case HOME:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }
}