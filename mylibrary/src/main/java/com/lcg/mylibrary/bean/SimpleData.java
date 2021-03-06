package com.lcg.mylibrary.bean;

import android.text.TextUtils;

/**
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2014-10-28
 * @version 1.0
 */
public class SimpleData {
	private int code = -1;
	private String detail;
	private String msg;

	public String getDetail() {
		if (TextUtils.isEmpty(detail)) {
			return msg;
		} else {
			return detail;
		}
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
