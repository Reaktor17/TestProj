package com.drg.testretrofit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.drg.testretrofit.events.RetrofitErrorEvent;
import com.drg.testretrofit.events.traps.TrapLoadEvent;
import com.drg.testretrofit.events.traps.TrapLoadedEvent;
import com.drg.testretrofit.events.traps.TrapSaveEvent;
import com.drg.testretrofit.models.Trap;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Date;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	private TextView mAnswerView;
	private EditText mUrlView;

	private Bus mBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBus = BusProvider.getInstance();

		setContentView(R.layout.ac_main);
		mAnswerView = (TextView) findViewById(R.id.answer);
		mUrlView = (EditText) findViewById(R.id.url);
		findViewById(R.id.go).setOnClickListener(this);
		findViewById(R.id.add).setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		mBus.register(this);
	}

	@Override
	protected void onStop() {
		mBus.unregister(this);

		super.onStop();
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
			mBus.post(new TrapSaveEvent(trap));
		}
	}

	private void applyNGo() {
		mAnswerView.setText("");

		Integer id = (mUrlView.getText().toString().length() > 0) ? Integer.parseInt(mUrlView.getText().toString()) : null;

		if(id != null) {
			mBus.post(new TrapLoadEvent(id));
		}
	}

	@Subscribe
	public void trapLoaded(TrapLoadedEvent trapLoadedEvent) {
		Log.e(TAG, "trapLoaded");
		mAnswerView.setText(String.valueOf(trapLoadedEvent.getTrap()));
	}

//	@Subscribe
//	public void trapSaved(TrapSaEvent trapLoadedEvent) {
//		Log.e(TAG, "trapLoaded");
//		mAnswerView.setText(String.valueOf(trapLoadedEvent.getTrap()));
//	}

	@Subscribe
	public void eGor(RetrofitErrorEvent retrofitErrorEvent) {
		Log.e(TAG, "eGor");
		Log.e(TAG, String.valueOf(retrofitErrorEvent));
	}
}
