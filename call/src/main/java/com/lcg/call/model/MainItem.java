package com.lcg.call.model;

import android.view.View;

import com.lcg.call.BaseObservableMe;

/**
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2017/6/20 20:11
 */
public class MainItem extends BaseObservableMe {
    private Main mMain;
    private String content;

    public MainItem(Main main, String content) {
        super(main.getActivity());
        mMain = main;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void click(View v) {
        mMain.setCall(content);
        mMain.commit(null);
    }
}
