package com.drg.testretrofit;

import com.google.gson.annotations.SerializedName;

/**
 * ServerResp
 */
public class ServerResp<T> {

	@SerializedName("status")
	private Integer mStatus;

	@SerializedName("response")
	private T mResp;

	@SerializedName("error")
	private Egor mEgor;

	public ServerResp(Integer status, T resp, Egor egor) {
		mStatus = status;
		mResp = resp;
		mEgor = egor;
	}

	public Integer getStatus() {
		return mStatus;
	}

	public T getResp() {
		return mResp;
	}

	public Egor getEgor() {
		return mEgor;
	}
}
