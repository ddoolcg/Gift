package com.lcg.gift;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.os.Process;

import com.lcg.gift.activity.LoginActivity;
import com.lcg.gift.bean.UserInfor;

import java.util.List;

/**
 * MyApplication
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/13 11:07
 */

public class MyApplication extends Application {
    public long mThreadId;
    private static MyApplication instance;
    private UserInfor userInfor;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        initCrashHandler();
        super.onCreate();
        instance = this;
        mThreadId = Thread.currentThread().getId();
        initMainProcesses();
    }

    /**
     * 异常奔溃的信息处理器初始化
     */
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler
                .getInstance(getApplicationContext());
        // 注册crashHandler
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        // 发送上一次没有发送的异常
        crashHandler.sendPreviousReportsToServer();
    }

    /**
     * 主进程初始化
     */
    public void initMainProcesses() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am
                .getRunningAppProcesses();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
            if (info.pid == myPid) {
                if (!info.processName.contains(":")) {
                    PreferenceHandler.getInstance().init(this);
                    //TODO
                }
                break;
            }
        }
    }

    /**
     * 获取当前用户信息
     */
    public UserInfor getUserInfor() {
        if (userInfor == null)
            userInfor = PreferenceHandler.getInstance().getConfigFull(UserInfor.class);
        return userInfor;
    }

    /**
     * 设置当前用户信息
     */
    public void setUserInfor(UserInfor userInfor) {
        if (this.userInfor != null && userInfor != null && !this.userInfor.equals(userInfor)) {
            PreferenceHandler.getInstance().setConfigFull(userInfor);
        }
        this.userInfor = userInfor;
    }

    public void gotoLoin() {
        for (BaseActivity activity : BaseActivity.activities) {
            activity.finish();
        }
        startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
