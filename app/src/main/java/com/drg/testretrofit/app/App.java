package com.drg.testretrofit.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.StrictMode;

import timber.log.Timber;

/**
 * App
 */
public class App extends Application {


	@Override
	public void onCreate() {
		super.onCreate();

		initStrictMode();
		initTimber();
	}

	private void initStrictMode() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
	}

	private void initTimber() {
		Timber.plant(new Timber.DebugTree());
	}
}