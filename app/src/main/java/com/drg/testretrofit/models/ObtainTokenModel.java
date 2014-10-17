package com.drg.testretrofit.models;

import com.google.gson.annotations.SerializedName;

/**
 * ObtainTokenModel
 */
public class ObtainTokenModel {

	@SerializedName("login")
	private String mLogin;

	@SerializedName("pass")
	private String mPassword;

	public ObtainTokenModel(String login, String password) {
		mLogin = login;
		mPassword = password;
	}

	public String getLogin() {
		return mLogin;
	}

	public String getPassword() {
		return mPassword;
	}
}
