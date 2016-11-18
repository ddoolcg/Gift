package com.lcg.gift.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    public static Boolean isPhone(String str) {
        Boolean isPhone = false;
        String expr = "^[1][3-8]\\d{9}$";
        if (str != null && str.matches(expr)) {
            isPhone = true;
        }
        return isPhone;
    }

    /**
     * 是否是验证码
     */
    public static boolean isInvitCode(String str) {
        String regEx = "^\\d{6}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 是否是QQ
     */
    public static boolean isQQ(String str) {
        String regEx = "^[1-9][0-9]{4,9}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 是否包含特殊字符
     */
    public static Boolean findHardChar(String str) {
        String regEx = "[`~!@#$%^&*\"()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断当前网络是否可以
     */
    public static boolean isConnect(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return 以下之一： {@link ConnectivityManager#TYPE_MOBILE},
     * {@link ConnectivityManager#TYPE_WIFI},
     * {@link ConnectivityManager#TYPE_WIMAX},
     * {@link ConnectivityManager#TYPE_ETHERNET},
     * {@link ConnectivityManager#TYPE_BLUETOOTH}, 或者被
     * {@link ConnectivityManager}定义的其他类型
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

}
