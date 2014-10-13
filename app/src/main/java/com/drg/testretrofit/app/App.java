package com.drg.testretrofit.app;

import android.app.Application;
import android.os.StrictMode;

import com.drg.testretrofit.BusProvider;
import com.drg.testretrofit.BusService;
import com.drg.testretrofit.RetrofitService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * App
 */
public class App extends Application {

	private BusService mBusService;
	private Bus mBus = BusProvider.getInstance();


	@Override
	public void onCreate() {
		super.onCreate();

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

		RetrofitService retrofitService = new RestAdapter.Builder()
				.setEndpoint("http://10.0.3.2/test")
				.setClient(new OkClient()) // avoid bug in @post
				.build()
				.create(RetrofitService.class);

		mBusService = new BusService(retrofitService, mBus);
		mBus.register(mBusService);
	}
}