package com.drg.testretrofit.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.drg.testretrofit.R;
import com.drg.testretrofit.app.BusProvider;
import com.drg.testretrofit.events.traps.TrapLoadEventWrapper;
import com.drg.testretrofit.events.traps.TrapsLoadEventWrapper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import timber.log.Timber;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

	private TextView mAnswerView;
	private EditText mEntityIdView;

	private Bus mBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		mBus = BusProvider.getInstance();

		setContentView(R.layout.ac_main);
		mAnswerView = (TextView) findViewById(R.id.answer);
		mEntityIdView = (EditText) findViewById(R.id.entityId);
		findViewById(R.id.findOne).setOnClickListener(this);
		findViewById(R.id.findMany).setOnClickListener(this);
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
			case R.id.findOne:
				findOne();
				break;
			case R.id.findMany:
				findMany();
				break;
		}
	}

	private void findOne() {
		mAnswerView.setText("");

		Integer id = (mEntityIdView.getText().toString().length() > 0) ? Integer.parseInt(mEntityIdView.getText().toString()) : null;

		if (id != null) {
			setSupportProgressBarIndeterminateVisibility(true);
			mBus.post(new TrapLoadEventWrapper(id));
		}
	}

	private void findMany() {
		setSupportProgressBarIndeterminateVisibility(true);
		mBus.post(new TrapsLoadEventWrapper());
	}

	@Subscribe
	public void trapLoaded(TrapLoadEventWrapper.TrapLoadedEvent event) {
		setSupportProgressBarIndeterminateVisibility(false);
		Timber.e("trapLoaded: " + String.valueOf(event));
		mAnswerView.setText(String.valueOf(event.getEntity()));
	}

	@Subscribe
	public void trapFailLoad(TrapLoadEventWrapper.TrapErrorLoadedEvent event) {
		setSupportProgressBarIndeterminateVisibility(false);
		if (event.getEgor() != null) {
			Timber.e("trapFailLoad - api error: " + String.valueOf(event));
		} else {
			Timber.e("trapFailLoad - HZ error: " + String.valueOf(event));
		}
	}

	@Subscribe
	public void trapsLoaded(TrapsLoadEventWrapper.TrapsLoadedEvent event) {
		setSupportProgressBarIndeterminateVisibility(false);
		Timber.e("trapsLoaded: " + String.valueOf(event));
		mAnswerView.setText(String.valueOf(event.getEntity()));
	}

	@Subscribe
	public void trapsFailLoad(TrapsLoadEventWrapper.TrapsErrorLoadedEvent event) {
		setSupportProgressBarIndeterminateVisibility(false);
		if (event.getEgor() != null) {
			Timber.e("trapsFailLoad - api error: " + String.valueOf(event));
		} else {
			Timber.e("trapsFailLoad - HZ error: " + String.valueOf(event));
		}
	}

}
