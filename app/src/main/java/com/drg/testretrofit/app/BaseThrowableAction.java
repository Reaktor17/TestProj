package com.drg.testretrofit.app;

import com.drg.testretrofit.models.base.Egor;

import retrofit.RetrofitError;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * BaseThrowableAction
 */
public class BaseThrowableAction implements Action1<Throwable> {

	@Override
	public void call(Throwable throwable) {
		if (throwable instanceof RetrofitError) {

			if (((RetrofitError) throwable).getKind() == RetrofitError.Kind.CONVERSION) {
				if (throwable.getCause() instanceof RetrofitApi.ApiException) {
					Timber.e("API");
					onApiError(((RetrofitApi.ApiException) throwable.getCause()).getEgor());
					return;
				}
			}

			onRetrofitError((RetrofitError) throwable);

			switch (((RetrofitError) throwable).getKind()) {
				case CONVERSION:
					Timber.e("CONVERSION");
					onConversionError((RetrofitError) throwable);
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

		} else {
			onSmthElseError(throwable);
		}
	}

	protected void onApiError(Egor egor) {
		Timber.e(String.valueOf(egor));
	}

	protected void onConversionError(RetrofitError retrofitError) {
		Timber.e("onConversionError: " + String.valueOf(retrofitError));
	}

	protected void onHttpError(RetrofitError retrofitError) {
		Timber.e("onHttpError: " + String.valueOf(retrofitError));
	}

	protected void onNetworkError(RetrofitError retrofitError) {
		Timber.e("onNetworkError: " + String.valueOf(retrofitError));
	}

	protected void onUnexpectedError(RetrofitError retrofitError) {
		Timber.e("onUnexpectedError: " + String.valueOf(retrofitError));
	}

	protected void onRetrofitError(RetrofitError retrofitError) {
		Timber.e("onRetrofitError: " + String.valueOf(retrofitError));
	}

	protected void onSmthElseError(Throwable throwable) {
		Timber.e("onSmthElseError: " + String.valueOf(throwable));
	}
}