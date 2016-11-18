package com.lcg.gift;

import android.databinding.BaseObservable;

import com.alibaba.fastjson.annotation.JSONType;

import okhttp3.Call;

/**
 * databinding 的 BaseObservable基类
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/20 20:30
 */
@JSONType(ignores = {"activity"})
public class BaseObservableMe extends BaseObservable {
    private BaseActivity activity;

    public BaseObservableMe(BaseActivity activity) {
        this.activity = activity;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * 显示进度对话框
     *
     * @param msg  你想要显示的消息
     * @param call 网络请求的call
     */
    protected void notifyProgressDialogShow(String msg, Call call) {
        activity.showProgressDialog(msg, call);
    }

    /**
     * 关闭进度对话框
     *
     * @param msg 如果不为空，则只会关闭与之匹配的进度对话框。
     */
    protected void notifyProgressDialogdismiss(String msg) {
        activity.dismissProgressDialog(msg);
    }

    /**
     * 关闭进度对话框
     */
    protected void notifyProgressDialogdismiss() {
        notifyProgressDialogdismiss(null);
    }

    /**
     * 开启一个activity
     */
    protected void startActivity(Class<? extends BaseActivity> clazz) {
        activity.startActivity(clazz);
    }
}
