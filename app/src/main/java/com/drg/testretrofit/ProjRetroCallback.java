package com.drg.testretrofit;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * ProjRetroCallback
 */
public abstract class ProjRetroCallback<T> implements Callback<T> {

	@Override
	public void failure(RetrofitError error) {
		Log.d("ProjRetroCallback", String.valueOf(error));
	}
}
