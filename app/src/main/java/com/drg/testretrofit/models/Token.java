package com.drg.testretrofit.models;

import com.drg.testretrofit.models.base.ItemEmitter;
import com.google.gson.annotations.SerializedName;

/**
 * Token
 */
public class Token implements ItemEmitter<Token> {

	@SerializedName("token")
	private String mToken;

	public Token(String token) {
		mToken = token;
	}

	public String getToken() {
		return mToken;
	}

	@Override
	public Token getEmitItem() {
		return this;
	}
}
