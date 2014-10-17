package com.drg.testretrofit.models;

/**
 * ObtainTokenModel
 */
public class ObtainTokenModel {

	private String mLogin;

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
