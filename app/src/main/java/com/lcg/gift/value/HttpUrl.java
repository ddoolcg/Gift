package com.lcg.gift.value;

/**
 * 所有的HTTP请求URL
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/18 20:54
 */

public class HttpUrl {
    private static final String HOST = "http://www.31xing.com/api/s/";
    /**
     * 错误日志
     */
    public static final String APPLOGS = HOST + "log";
    /**
     * 获取APP版本信息
     */
    public static final String APPLICATION_GET = HOST + "application/get";
    /**
     * 登陆
     */
    public static final String LOGIN = HOST + "user/login";
    /**
     * 注册
     */
    public static final String REGIST = HOST + "user/register";
    /**
     * 获取单个用户信息
     */
    public static final String USERINFO_GET = HOST + "user/get";
    /**
     * 修改个人信息
     */
    public static final String USERINFO_UPDATA = HOST + "user/setuserinfo";
    /**
     * 修改密码
     */
    public static final String CHANGE_PASSWORD = HOST + "user/setpwd";
    /**
     * 获取最新最近任务列表
     */
    public static final String LEARN_TASKS = HOST + "learntasks";
    /**
     * 获取学习任务列表
     */
    public static final String LEARN_TASKS_LIST = LEARN_TASKS + "/list";
    /**
     * 获取学习任务详情及进度
     */
    public static final String LEARN_TASKS_GET = LEARN_TASKS + "/get";
    /**
     * 获取单个资源详情
     */
    public static final String RESOURCE_GET = HOST + "resource/get";
    /**
     * 获取单个试题
     */
    public static final String TESTQUESTION_GET = HOST + "testquestion/get";
    /**
     * 获取作业列表
     */
    public static final String HOMEWORK_LIST = HOST + "homework/list";
    /**
     * 获取单个作业详情
     */
    public static final String HOMEWORK_GET = HOST + "homework/get";
    /**
     * 获取评测任务列表
     */
    public static final String EVALTASK_LIST = HOST + "evaltask/list";
    /**
     * 获取单个评测任务
     */
    public static final String EVALTASK_GET = HOST + "evaltask/get";
    /**
     * 获取错题集
     */
    public static final String TESTQUESTION_GETERRORS = HOST + "testquestion/geterrors";
    /**
     * 生成评测考试及试卷(点“立即评测”时调用此API)
     */
    public static final String EXAMINATION_MAKE = HOST + "examination/make";
    /**
     * 记录学生答题情况
     */
    public static final String EXAMINATION_SUBMITANS = HOST + "examination/submitans";
    /**
     * 交卷
     */
    public static final String EXAMINATION_HANDIN = HOST + "examination/handin";

    /**
     * 获取班级列表
     */
    public static final String CLASS_LIST = HOST + "class/list";
    /**
     * 加入班级
     */
    public static final String CLASS_JOIN = HOST + "class/join";

    /**
     * 获取消息列表
     */
    public static final String MESSAGE_LIST = HOST + "message/list";

}
