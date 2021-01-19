package com.edu.videomediademo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.edu.videomediademo.logger.JLog;

/**
 * @name: WelcomeActivity
 * @date: 2021-01-19 14:29
 * @comment:
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String TAG = "WelComeActivity";
    protected boolean isDestroy = false;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };

    /**
     * 请求权限的回调
     *
     * @param requestCode  请求码
     * @param isAllGranted 是否全部被同意
     */
    private void afterRequestPermission(int requestCode, boolean isAllGranted) {
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            if (isAllGranted) {
                startApp();
            } else {
                Toast.makeText(this, "未获取权限", Toast.LENGTH_SHORT).show();
               finish();

            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
        //防止安装页面点击打开后点击home键后重新进入返回不到之前的界面问题
        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action == Intent.ACTION_MAIN) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }
        isDestroy = false;
        init();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }


    private void init() {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            startApp();
        }
    }

    private void startApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    /**
     * 权限检查
     *
     * @param neededPermissions 需要的权限
     * @return 是否全部被允许
     */
    protected boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            JLog.d(TAG, "checkPermissions: " + ContextCompat.checkSelfPermission(this, neededPermission));
            allGranted = allGranted && ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isAllGranted = true;
        for (int grantResult : grantResults) {
            isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
        }
        afterRequestPermission(requestCode, isAllGranted);
    }

}
