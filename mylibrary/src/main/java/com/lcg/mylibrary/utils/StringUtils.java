package com.lcg.mylibrary.utils;

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
}
