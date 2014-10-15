package com.drg.testretrofit.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.drg.testretrofit.Egor;
import com.drg.testretrofit.R;
import com.drg.testretrofit.ServerResp;
import com.drg.testretrofit.app.RetrofitApi;
import com.drg.testretrofit.models.Trap;

import retrofit.RetrofitError;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import timber.log.Timber;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	private TextView mAnswerView;
	private EditText mUrlView;

	private Subscription mSubscription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ac_main);
		mAnswerView = (TextView) findViewById(R.id.answer);
		mUrlView = (EditText) findViewById(R.id.url);
		findViewById(R.id.go).setOnClickListener(this);
		findViewById(R.id.add).setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		if (mSubscription != null && !mSubscription.isUnsubscribed()) {
			mSubscription.unsubscribe();
			mSubscription = null;
		}

		super.onDestroy();
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
//			mBus.post(new TrapSaveEvent(trap));
//		}
	}

	private void applyNGo() {
		mAnswerView.setText("");

		final Integer id = (mUrlView.getText().toString().length() > 0) ? Integer.parseInt(mUrlView.getText().toString()) : null;

		if (id != null) {

			if (mSubscription == null || mSubscription.isUnsubscribed()) {
				mSubscription =	RetrofitApi.getInstance().getTrap(id)
						.observeOn(AndroidSchedulers.mainThread())
						.doOnSubscribe(new Action0() {
							@Override
							public void call() {
								Timber.e("doOnSubscribe");
							}
						})
						.subscribe(new Action1<ServerResp<Trap>>() {
							@Override
							public void call(ServerResp<Trap> trapServerResp) {
								Timber.e("good "+trapServerResp.getResp());
								mAnswerView.setText(trapServerResp.getResp().toString());
							}
						}, new BaseThrowableAction() {
							@Override
							protected void onApiError(Egor egor) {
								Timber.e(egor.toString());
							}

							@Override
							protected void onAfterAll(RetrofitError retrofitError) {
								Timber.e("Un Subscribe");
							}
						});
		}

	}
}

/**
 * BaseThrowableAction
 */
static class BaseThrowableAction implements Action1<Throwable> {

	@Override
	public void call(Throwable throwable) {
		if (throwable instanceof RetrofitError) {
			switch (((RetrofitError) throwable).getKind()) {
				case CONVERSION:
					if (throwable.getCause() instanceof RetrofitApi.ApiException) {
						Timber.e("API");
						onApiError(((RetrofitApi.ApiException) throwable.getCause()).getEgor());
					} else {
						Timber.e("CONVERSION");
						onConversionError((RetrofitError) throwable);
					}
					break;
				case HTTP:
					Timber.e("HTTP");
					onHttpError((RetrofitError) throwable);
					break;
				case NETWORK:
					Timber.e("NETWORK");
					onNetworkError((RetrofitError) throwable);
					break;
				case UNEXPECTED:
					Timber.e("UNEXPECTED");
					onUnexpectedError((RetrofitError) throwable);
					break;
			}
			onAfterAll((RetrofitError) throwable);
		}
	}

	protected void onApiError(Egor egor) {
	}

	protected void onConversionError(RetrofitError retrofitError) {
	}

	protected void onHttpError(RetrofitError retrofitError) {
	}

	protected void onNetworkError(RetrofitError retrofitError) {
	}

	protected void onUnexpectedError(RetrofitError retrofitError) {
	}

	protected void onAfterAll(RetrofitError retrofitError) {
	}
}
}
