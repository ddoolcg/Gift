package com.lcg.gift.value;

/**
 * 所有的HTTP请求URL
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/18 20:54
 */

public class HttpUrl {
    private static final String HOST = "http://bddoo.tunnel.qydev.com/";
    /**
     * 错误日志
     */
    public static final String APPLOGS = HOST + "log";
    /**
     * 获取APP版本信息
     */
    public static final String APPLICATION_GET = HOST + "application/get";
    /**
     * 登陆（post）与注册(put)以及登出(get)
     */
    public static final String AUTH = HOST + "auth";
    /**
     * 密码服务（post修改密码，get获取忘记密码验证码，put重置密码）
     */
    public static final String PASSWORD = HOST + "password";

}
