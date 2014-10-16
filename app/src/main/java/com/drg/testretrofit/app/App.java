package com.drg.testretrofit.app;

import android.app.Application;
import android.os.StrictMode;

import com.drg.testretrofit.BusProvider;
import com.drg.testretrofit.BusService;
import com.squareup.otto.Bus;

import timber.log.Timber;

/**
 * App
 */
public class App extends Application {

	private BusService mBusService;
	private Bus mBus = BusProvider.getInstance();


	@Override
	public void onCreate() {
		super.onCreate();

		initStrict();
		initTimber();

//		RetrofitService retrofitService = new RestAdapter.Builder()
//				.setEndpoint("http://10.0.3.2/test")
//				.setClient(new OkClient()) // avoid bug in @post
//				.build()
//				.create(RetrofitService.class);

		mBusService = new BusService(RetrofitApi.getInstance());

		// register in bus
		mBus.register(mBusService);
	}

	private void initStrict() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
	}

	private void initTimber() {
		Timber.plant(new Timber.DebugTree());
	}
}