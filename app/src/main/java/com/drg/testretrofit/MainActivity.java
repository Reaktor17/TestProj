package com.drg.testretrofit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import retrofit.RestAdapter;
import rx.Scheduler;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


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
				.setEndpoint("http://10.0.3.2/test")
//				.setEndpoint("http://requestb.in/16m7phw1")
//				.setConverter(new TestConverter())
				.build();

		mService = restAdapter.create(RetrofitService.class);
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
//		if (mUrlView.getText().toString() != null && mUrlView.getText().toString().length() > 0) {
//			Trap trap = new Trap(mUrlView.getText().toString(), (int) ((new Date().getTime()) / 1000));
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
//		}
	}

	private void applyNGo() {
		mAnswerView.setText("");

		Integer id = (mUrlView.getText().toString().length() > 0) ? Integer.parseInt(mUrlView.getText().toString()) : null;

		getTrap(id);
	}

	private void getTrap(Integer id) {

		mService.getTrap(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Trap>() {
					@Override
					public void call(Trap trap) {
						if(trap != null) {
							Log.e(TAG, trap.toString());
							mAnswerView.setText(trap.toString());
						}
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						Log.e(TAG, "getTrap throwable", throwable);
					}
				});
	}
}
