package com.drg.testretrofit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main);

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://10.0.3.2")
				.build();

		final RetrofitService service = restAdapter.create(RetrofitService.class);

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				Log.e(TAG, "Before request");
//				Log.e(TAG, String.valueOf(service.getTrap(1)));
//				Log.e(TAG, String.valueOf(service.getTraps()));
//			}
//		}).start();

		service.getTrap(1, new ProjRetroCallback<Trap>() {
			@Override
			public void success(Trap trap, Response response) {
				Log.e(TAG, "success");
			}
		});
	}

}
