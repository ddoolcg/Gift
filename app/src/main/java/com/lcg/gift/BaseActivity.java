package com.lcg.gift;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.lcg.gift.dialog.ProgressDialog;

import okhttp3.Call;

/**
 * 所有activity的基类
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/13 11:03
 */

public class BaseActivity extends FragmentActivity {
    private ProgressDialog mProgressDialog;

    /**
     * 显示进度对话框
     *
     * @param msg  你想要显示的消息
     * @param call 网络请求的call
     */
    public void showProgressDialog(String msg, Call call) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCall(call);
        mProgressDialog.show(msg);
    }

    /**
     * 关闭进度对话框
     *
     * @param msg 如果不为空，则只会关闭与之匹配的进度对话框。
     */
    public void dismissProgressDialog(String msg) {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.dismiss(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**去另外一个页面*/
    public void startActivity(Class<? extends BaseActivity> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
