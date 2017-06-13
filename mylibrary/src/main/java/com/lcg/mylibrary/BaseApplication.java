package com.lcg.mylibrary;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Process;

import java.util.List;

/**
 * BaseApplication
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/13 11:07
 */

public abstract class BaseApplication extends Application {
    public long mThreadId;
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
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
                    onInitMainProcesses();
                }
                break;
            }
        }
    }

    /**
     * 被主进程初始化调用
     */
    protected abstract void onInitMainProcesses();

    /**
     * 认证的token
     */
    public abstract String getToken();

    /**
     * 去登陆
     */
    public abstract void gotoLoin();
}
