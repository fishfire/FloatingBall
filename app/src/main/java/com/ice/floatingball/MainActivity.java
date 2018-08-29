package com.ice.floatingball;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    Button btn_openSetting, btn_openFloatingBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableDeviceAdmin();

        btn_openSetting = (Button) findViewById(R.id.btn_openSetting);
        btn_openFloatingBall = (Button) findViewById(R.id.btn_openFloatingBall);
        Intent intent = new Intent(this, MyAccessibilityService.class);
        startService(intent);
        btn_openSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开设置  打开服务才能实现返回功能
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });

        btn_openFloatingBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewManager.getInstance(MainActivity.this).showFloatBall();
            }
        });
    }

    private void enableDeviceAdmin() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName mAdminName = new ComponentName(this, MyDeviceAdmin.class);
        if (devicePolicyManager != null) {
            //先判断是否已经激活
            if (!devicePolicyManager.isAdminActive(mAdminName)) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
                startActivity(intent);
            }

        }


    }
}
