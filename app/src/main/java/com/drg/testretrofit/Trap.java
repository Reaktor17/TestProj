package com.drg.testretrofit;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Trap
 */
public class Trap {

	@SerializedName("title")
	private String mTitle;

	@SerializedName("sDate")
	private Integer mTimestamp;

	public Trap(String title, Integer timestamp) {
		mTitle = title;
		mTimestamp = timestamp;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Integer getTimestamp() {
		return mTimestamp;
	}

	public void setTimestamp(Integer timestamp) {
		mTimestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Title: "+mTitle+" | util date: "+new Date(mTimestamp*1000L);
	}
}
