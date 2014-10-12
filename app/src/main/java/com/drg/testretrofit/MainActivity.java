package com.drg.testretrofit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	private TextView mAnswerView;
	private EditText mUrlView;
	private RetrofitService mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main);
		mAnswerView = (TextView) findViewById(R.id.answer);
		mUrlView = (EditText) findViewById(R.id.url);
		findViewById(R.id.go).setOnClickListener(this);
		findViewById(R.id.add).setOnClickListener(this);

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://10.0.3.2")
//				.setConverter(new TestConverter())
				.build();

		mService = restAdapter.create(RetrofitService.class);

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				Log.e(TAG, "Before request");
//				Log.e(TAG, String.valueOf(service.getTrap(1)));
//				Log.e(TAG, String.valueOf(service.getTraps()));
//			}
//		}).start();


	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.go:
				applyNGo();
				break;
			case R.id.add:
				add();
				break;
		}
	}

	private void add() {
		if (mUrlView.getText().toString() != null && mUrlView.getText().toString().length() > 0) {
			Trap trap = new Trap(mUrlView.getText().toString(), (int) ((new Date().getTime()) / 1000));
			mService.addTrap(trap, new Callback<String>() {
				@Override
				public void success(String response, Response response2) {
					Log.e(TAG, "success");
					if (response != null) {
						Log.e(TAG, response.toString());
					}
				}

				@Override
				public void failure(RetrofitError error) {
					Log.e(TAG, error.toString());
					try {
						InputStream in = error.getResponse().getBody().in();
						InputStreamReader is = new InputStreamReader(in);
						StringBuilder sb = new StringBuilder();
						BufferedReader br = new BufferedReader(is);
						String read = br.readLine();

						while (read != null) {
							//System.out.println(read);
							sb.append(read);
							read = br.readLine();
						}

						Log.e(TAG, "Raw: " + read);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
//			mService.addTrap(trap, new Callback<String>() {
//				@Override
//				public void success(String response, Response response2) {
//					Log.e(TAG, "success");
//					if (response != null) {
//						Log.e(TAG, response.toString());
//					}
//				}
//
//				@Override
//				public void failure(RetrofitError error) {
//					Log.e(TAG, error.toString());
//					try {
//						InputStream in = error.getResponse().getBody().in();
//						InputStreamReader is = new InputStreamReader(in);
//						StringBuilder sb = new StringBuilder();
//						BufferedReader br = new BufferedReader(is);
//						String read = br.readLine();
//
//						while (read != null) {
//							//System.out.println(read);
//							sb.append(read);
//							read = br.readLine();
//						}
//
//						Log.e(TAG, "Raw: " + read);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
		}
	}

	private void applyNGo() {
		mAnswerView.setText("");

		if (mUrlView.getText().toString() != null && mUrlView.getText().toString().length() > 0) {
			mService.getTrap(Integer.parseInt(mUrlView.getText().toString()), new ProjRetroCallback<Trap>() {
				@Override
				public void success(Trap trap, Response response) {
					Log.e(TAG, "success");
					mAnswerView.setText(String.valueOf(trap));

//					try {
//						InputStream in = response.getBody().in();
//						InputStreamReader is = new InputStreamReader(in);
//						StringBuilder sb = new StringBuilder();
//						BufferedReader br = new BufferedReader(is);
//						String read = br.readLine();
//
//						while(read != null) {
//							//System.out.println(read);
//							sb.append(read);
//							read = br.readLine();
//						}
//
//						Log.e(TAG, "Raw: "+read);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}

				}

				@Override
				public void failure(RetrofitError error) {
					Log.e(TAG, String.valueOf(error));
					mAnswerView.setText(String.valueOf(error));
				}
			});

		} else {
			mService.getTraps(new ProjRetroCallback<List<Trap>>() {
				@Override
				public void success(List<Trap> traps, Response response) {
					mAnswerView.setText(String.valueOf(traps));
				}

				@Override
				public void failure(RetrofitError error) {
					super.failure(error);
					mAnswerView.setText(String.valueOf(error));
				}
			});

		}

	}
}
