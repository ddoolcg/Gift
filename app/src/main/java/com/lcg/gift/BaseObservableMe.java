package com.lcg.gift;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.alibaba.fastjson.annotation.JSONType;
import com.lcg.gift.utils.L;

import okhttp3.Call;

/**
 * databinding 的 BaseObservable基类
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/20 20:30
 */
@JSONType(ignores = {"activity", "titleText", "rightText"})
public class BaseObservableMe extends BaseObservable {
    private String titleText, rightText;

    private BaseActivity activity;

    public BaseObservableMe(BaseActivity activity) {
        titleText = "";
        rightText = "";
        this.activity = activity;
    }


    public void clickRight(View v) {
        L.i("右键被点击");
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
    protected void startActivity(Class<? extends Activity> clazz) {
        activity.startActivity(clazz);
    }

    @Bindable
    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        notifyPropertyChanged(BR.titleText);
    }

    @Bindable
    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        notifyPropertyChanged(BR.rightText);
    }
}
