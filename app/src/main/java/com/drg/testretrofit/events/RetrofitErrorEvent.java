package com.drg.testretrofit.events;

import retrofit.RetrofitError;

/**
 * RetrofitErrorEvent
 */
public class RetrofitErrorEvent {

	private RetrofitError mRetrofitError;

	public RetrofitErrorEvent(RetrofitError retrofitError) {
		mRetrofitError = retrofitError;
	}

	public RetrofitError getRetrofitError() {
		return mRetrofitError;
	}
}
