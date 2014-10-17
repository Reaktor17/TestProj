package com.drg.testretrofit.app;

import com.drg.testretrofit.events.token.TokenExpiredEvent;
import com.drg.testretrofit.events.base.DataErrorEvent;
import com.drg.testretrofit.events.base.DataGotEvent;
import com.drg.testretrofit.events.base.GetDataEvent;
import com.drg.testretrofit.events.token.TokenObtainFailEvent;
import com.drg.testretrofit.events.token.TokenObtainedEvent;
import com.drg.testretrofit.events.traps.TrapLoadEventWrapper;
import com.drg.testretrofit.events.traps.TrapsLoadEventWrapper;
import com.drg.testretrofit.models.ObtainTokenModel;
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

	private final RetrofitService mRetrofitService;

	private String mToken;

	private boolean netAvailable = true;
	private int i;
	private boolean tokenInObtainingState;

	public BusService(RetrofitService retrofitService) {
		mRetrofitService = retrofitService;
	}

	@Subscribe
	public void onTokenExpired(TokenExpiredEvent event) {
		Timber.e("onTokenExpired: " + String.valueOf(event));

		Observable observable = mRetrofitService.obtainToken(new ObtainTokenModel("loginHor", "megaPass"));

		// may fall in infinite loop (if all time errorCode == tokenExpired)
		doSubscribe(observable, new TokenObtainedEvent(), new TokenObtainFailEvent());
	}

	@Subscribe
	public void onTokenObtained(TokenObtainedEvent event) {
		if(event.getEntity() != null) {

			// restore waiting tasks

		}
	}

	@Subscribe
	public void onTokenObtainingFailed(TokenObtainFailEvent event) {
		// clear waiting pool
		// hz - "exit"
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
			trapObservable = mRetrofitService.getTrap(trapLoadEvent.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		} else {
			trapObservable = Observable.just(new Trap("db", 1413194844));
		}
		i++;

		if (trapObservable != null) {
			doSubscribe(trapObservable, trapLoadEvent.getLoadedEvent(), trapLoadEvent.getErrorEvent());
		}

	}

	@Subscribe
	public void loadTraps(TrapsLoadEventWrapper.TrapsLoadEvent event) {
		Timber.e("loadTraps: " + String.valueOf(event));

		Observable observable;

		observable = mRetrofitService.getTraps().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

		doSubscribe(observable, event.getLoadedEvent(), event.getErrorEvent());
	}

	private <T extends ItemEmitter<E>, E> void doSubscribe(Observable<T> observable, final DataGotEvent<E> dataGotEvent, final DataErrorEvent dataErrorEvent) {
		observable.subscribe(new Action1<T>() {
			@Override
			public void call(T t) {
				dataGotEvent.setEntity(t.getEmitItem());
				BusProvider.getInstance().post(dataGotEvent);
			}
		}, new BaseThrowableAction(){
			@Override
			protected void onApiError(Egor egor) {
				super.onApiError(egor);

				boolean tokenExpired = egor.getCode() == 101;
				if(tokenExpired) {
					if(!tokenInObtainingState) {
						tokenInObtainingState = true;
						BusProvider.getInstance().post(new TokenExpiredEvent());
					} else {
						// put in waiting pool

					}
				} else {
					dataErrorEvent.setEgor(egor);
					BusProvider.getInstance().post(dataErrorEvent);
				}
			}

			@Override
			protected void onRetrofitError(RetrofitError retrofitError) {
				super.onRetrofitError(retrofitError);

				BusProvider.getInstance().post(dataErrorEvent);
			}

			@Override
			protected void onSmthElseError(Throwable throwable) {
				super.onSmthElseError(throwable);

				BusProvider.getInstance().post(dataErrorEvent);
			}
		});
	}

}
