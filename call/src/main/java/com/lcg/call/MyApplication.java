package com.lcg.call;

import com.lcg.mylibrary.BaseApplication;
import com.lcg.mylibrary.utils.L;

/**
 * TODO
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2017/1/11 14:15
 */
public class MyApplication extends BaseApplication {
    @Override
    protected void onInitMainProcesses() {
        L.DEBUG = BuildConfig.DEBUG;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void gotoLoin() {

    }
}
