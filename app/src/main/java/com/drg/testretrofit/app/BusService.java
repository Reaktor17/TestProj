package com.drg.testretrofit.app;

import com.drg.testretrofit.events.TokenExpiredEvent;
import com.drg.testretrofit.events.base.DataGotEvent;
import com.drg.testretrofit.events.base.GetDataEvent;
import com.drg.testretrofit.events.traps.TrapLoadEventWrapper;
import com.drg.testretrofit.models.base.Egor;
import com.drg.testretrofit.models.base.ItemEmitter;
import com.drg.testretrofit.models.Trap;
import com.squareup.otto.Subscribe;

import retrofit.RetrofitError;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * BusService
 */
public class BusService {

	private final RetrofitService mRetrofitService1;

	private boolean netAvailable = true;
	private int i;

	public BusService(RetrofitService retrofitService1) {
		mRetrofitService1 = retrofitService1;
	}

	@Subscribe
	public void onTokenExpired(TokenExpiredEvent event) {
		Timber.e("onTokenExpired: " + String.valueOf(event));

//			BusProvider.getInstance().post();

	}

	@Subscribe
	public void inGate(GetDataEvent getDataEvent) {
		Timber.e("inGate: " + String.valueOf(getDataEvent));

		BusProvider.getInstance().post(getDataEvent.getDestinationEvent());
	}


	@Subscribe
	public void loadTrap(TrapLoadEventWrapper.TrapLoadEvent trapLoadEvent) {
		Timber.e("loadTrap: " + String.valueOf(trapLoadEvent));

		Observable trapObservable;

		if (i % 2 == 0) {
			trapObservable = mRetrofitService1.getTrap(trapLoadEvent.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		} else {
			trapObservable = Observable.just(new Trap("db", 1413194844));
		}
		i++;

//		Observable<ServerResp<Trap>> trapObservable1 = mRetrofitService1.getTrap(trapLoadEvent.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//		Observable<Trap> trapObservable2 = Observable.just(new Trap("db", 1413194844));


		if (trapObservable != null) {

			doSubscribe(trapObservable);
		}

	}

	private <T extends ItemEmitter<E>, E> void doSubscribe(Observable<T> observable) {
		observable.subscribe(new Action1<T>() {
			@Override
			public void call(T t) {
				BusProvider.getInstance().post(new DataGotEvent<E>(t.getEmitItem()));
			}
		}, new BaseThrowableAction(){
			@Override
			protected void onApiError(Egor egor) {
				super.onApiError(egor);

				boolean tokenExpired = false;
				if(tokenExpired) {
					BusProvider.getInstance().post(new TokenExpiredEvent());
				} else {
					BusProvider.getInstance().post(egor);
				}
			}

			@Override
			protected void onRetrofitError(RetrofitError retrofitError) {
				super.onRetrofitError(retrofitError);

				BusProvider.getInstance().post(new DataGotEvent<E>(null));
			}

			@Override
			protected void onSmthElseError(Throwable throwable) {
				super.onSmthElseError(throwable);

				BusProvider.getInstance().post(new DataGotEvent<E>(null));
			}
		});
	}

}
