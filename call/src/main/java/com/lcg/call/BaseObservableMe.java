package com.lcg.call;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.annotation.JSONType;
import com.bumptech.glide.Glide;
import com.lcg.mylibrary.BaseActivity;
import com.lcg.mylibrary.utils.L;

import java.io.Serializable;

import okhttp3.Call;

/**
 * databinding 的 BaseObservable基类
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/20 20:30
 */
@JSONType(ignores = {"activity", "leftText", "titleText", "rightText", "showBack"})
public class BaseObservableMe extends BaseObservable implements Serializable {
    private String leftText, titleText, rightText;
    private boolean showBack;
    private BaseActivity activity;

    public BaseObservableMe(BaseActivity activity) {
        titleText = "";
        rightText = "";
        leftText = "";
        showBack = true;
        this.activity = activity;
    }

    public void clickLeft(View v) {
        L.i("左键被点击");
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
    public void notifyProgressDialogShow(String msg, Call call) {
        activity.showProgressDialog(msg, call);
    }

    /**
     * 关闭进度对话框
     *
     * @param msg 如果不为空，则只会关闭与之匹配的进度对话框。
     */
    public void notifyProgressDialogdismiss(String msg) {
        activity.dismissProgressDialog(msg);
    }

    /**
     * 关闭进度对话框
     */
    public void notifyProgressDialogdismiss() {
        notifyProgressDialogdismiss(null);
    }

    /**
     * 开启一个activity
     */
    protected void startActivity(Class<? extends Activity> clazz) {
        activity.startActivity(clazz);
    }

    @BindingAdapter({"html"})
    public static void parseHtml(TextView tv, String content) {
        tv.setText(Html.fromHtml(content));
    }

    @BindingAdapter({"img"})
    public static void loadIcon(final ImageView iv, final String icon) {
        Glide.with(iv.getContext()).load(icon).error(android.R.drawable.ic_menu_gallery).into(iv);
    }

    @Bindable
    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        notifyPropertyChanged(com.lcg.call.BR.leftText);
    }

    @Bindable
    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        notifyPropertyChanged(com.lcg.call.BR.titleText);
    }

    @Bindable
    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        notifyPropertyChanged(com.lcg.call.BR.rightText);
    }
    @Bindable
    public boolean isShowBack() {
        return showBack;
    }

    public void setShowBack(boolean showBack) {
        this.showBack = showBack;
        notifyPropertyChanged(com.lcg.call.BR.showBack);
    }

}
