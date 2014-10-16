package com.drg.testretrofit.models.base;

import com.google.gson.annotations.SerializedName;

/**
 * Egor
 */
public class Egor {

	@SerializedName("code")
	private Integer mCode;

	@SerializedName("msg")
	private String mMsg;

	public Egor(Integer code, String msg) {
		mCode = code;
		mMsg = msg;
	}

	public Integer getCode() {
		return mCode;
	}

	public String getMsg() {
		return mMsg;
	}

	@Override
	public String toString() {
		return "Egor{" +
				"mCode=" + mCode +
				", mMsg='" + mMsg + '\'' +
				'}';
	}
}