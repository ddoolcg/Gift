package com.lcg.gift.model;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcg.gift.BaseActivity;
import com.lcg.gift.BaseObservableMe;
import com.lcg.gift.R;

import xa.qwe.xz.os.df.AdExtraTaskStatus;
import xa.qwe.xz.os.df.AdTaskStatus;
import xa.qwe.xz.os.df.AppExtraTaskObject;
import xa.qwe.xz.os.df.AppExtraTaskObjectList;
import xa.qwe.xz.os.df.AppSummaryObject;
import xa.qwe.xz.os.df.DiyOfferWallManager;

/**
 * 广告
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/12/2 21:16
 */

public class AD extends BaseObservableMe {
    private AppSummaryObject mAppSummaryObject;
    private String adIconUrl;      // 获取 app 的图标地址
    private String title;      // 获取 app 的名称、大小
    private String money;//多少钱
    private String content;      // 广告词、广告步骤、追加任务列表。

    public AD(BaseActivity activity, AppSummaryObject appSummaryObject) {
        super(activity);
        mAppSummaryObject = appSummaryObject;
        adIconUrl = appSummaryObject.getIconUrl();
        title = appSummaryObject.getAppName();
        String appSize = appSummaryObject.getAppSize();
        if (!TextUtils.isEmpty(appSize)) {
            title += " " + appSize;
        }
        money = getTotalPoints(appSummaryObject) / 100f + getActivity().getString(R.string.coins);
        content = appSummaryObject.getAdSlogan();
        if (appSummaryObject.getAdTaskStatus() == AdTaskStatus.NOT_COMPLETE) {
            content += "<br><small><font color=\"#FF0000\">"
                    + appSummaryObject.getTaskSteps() + "</font></small>";
        } else if (appSummaryObject.getAdTaskStatus() == AdTaskStatus.HAS_EXTRA_TASK && !appSummaryObject.getExtraTaskList().isEmpty()) {
            AppExtraTaskObjectList list = appSummaryObject.getExtraTaskList();  // 获取追加任务列表
            int size = list.size();
            for (int i = 0; i < size; i++) {
                AppExtraTaskObject taskObject = list.get(i);
                int status = taskObject.getStatus();
                if (status == AdExtraTaskStatus.NOT_START && status == AdExtraTaskStatus.IN_PROGRESS && taskObject.getStartTimeStamp() > 0)
                    content += "<br><br><small><font color=\"#FF0000\">"
                            + taskObject.getAdText() + "</font></small>";
            }
        } else {
            content += "<br><small><font color=\"#FA5200\">暂无任务</font></small>";
        }
    }

    /**
     * 如果任务未完成就获取指定广告的所有积分（正常完成的积分+可完成的追加任务积分）
     */
    private int getTotalPoints(AppSummaryObject appSummaryObject) {
        int totalpoints = appSummaryObject.getPoints();
        AppExtraTaskObjectList tempList = appSummaryObject.getExtraTaskList();
        if (tempList != null && tempList.size() > 0) {
            for (int i = 0; i < tempList.size(); ++i) {
                AppExtraTaskObject extraTaskObject = tempList.get(i);
                if (extraTaskObject.getStatus() == AdExtraTaskStatus.NOT_START ||
                        extraTaskObject.getStatus() == AdExtraTaskStatus.IN_PROGRESS) {
                    totalpoints += extraTaskObject.getPoints();
                }
            }
        }
        return totalpoints;
    }

    /**
     * 通过传入 AppSummaryObject 对象来进行下载或者打开
     */
    public void open(View v) {
        BaseActivity activity = getActivity();
        DiyOfferWallManager.getInstance(activity).openOrDownloadApp(activity, mAppSummaryObject);
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView iv, String url) {
        Glide.with(iv.getContext()).load(url).error(R.mipmap.ic_launcher).into(iv);
    }

    @BindingAdapter({"html"})
    public static void loadHtml(TextView tv, String soure) {
        tv.setText(Html.fromHtml(soure));
    }

    public String getAdIconUrl() {
        return adIconUrl;
    }

    public void setAdIconUrl(String adIconUrl) {
        this.adIconUrl = adIconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
