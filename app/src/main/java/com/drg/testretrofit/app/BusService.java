package com.drg.testretrofit.app;

import android.os.Handler;
import android.os.Looper;

import com.drg.testretrofit.events.base.DataErrorEvent;
import com.drg.testretrofit.events.base.DataGotEvent;
import com.drg.testretrofit.events.base.DestinationEvent;
import com.drg.testretrofit.events.base.GetDataEvent;
import com.drg.testretrofit.events.token.TokenExpiredEvent;
import com.drg.testretrofit.events.token.TokenObtainEvent;
import com.drg.testretrofit.events.traps.TrapLoadEventWrapper;
import com.drg.testretrofit.events.traps.TrapsLoadEventWrapper;
import com.drg.testretrofit.models.ObtainTokenModel;
import com.drg.testretrofit.models.Trap;
import com.drg.testretrofit.models.base.Egor;
import com.drg.testretrofit.models.base.ItemEmitter;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

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

	private String mToken = "some old token f.e.: BAo_O";

	private boolean netAvailable = true;
	private int i;
	private boolean tokenInObtainingState;

	private HashMap<String, DestinationEvent> mWaitingPool = new HashMap<String, DestinationEvent>();

	public BusService(RetrofitService retrofitService) {
		mRetrofitService = retrofitService;
	}

	@Subscribe
	public void onTokenExpired(TokenExpiredEvent event) {
		Timber.e("onTokenExpired: " + String.valueOf(event));

		Observable observable = mRetrofitService.obtainToken(new ObtainTokenModel("loginHor", "megaPass"));

		// may fall in infinite loop (if all time errorCode == tokenExpired)
		doSubscribe(observable, new TokenObtainEvent());
	}

	@Subscribe
	public void onTokenObtained(TokenObtainEvent.TokenObtainedEvent event) {
		Timber.e("onTokenObtained");
		tokenInObtainingState = false;
		if(event.getEntity() != null) {
			setToken(event.getEntity().getToken());
			// re-run waiting tasks
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					for (DestinationEvent destinationEvent : mWaitingPool.values()) {
						BusProvider.getInstance().post(destinationEvent);
					}
					mWaitingPool.clear();
				}
			});
		}
	}

	@Subscribe
	public void onTokenObtainingFailed(TokenObtainEvent.TokenObtainErrorEvent event) {
		Timber.e("onTokenObtainingFailed");
		tokenInObtainingState = false;
		// clear waiting pool
		mWaitingPool.clear();
		// hz - "exit"
	}

	@Subscribe
	public void inGate(GetDataEvent getDataEvent) {
		Timber.e("inGate: " + String.valueOf(getDataEvent));

		BusProvider.getInstance().post(getDataEvent.getDestinationEvent());
	}

	@Subscribe
	public void loadTrap(TrapLoadEventWrapper.TrapLoadEvent event) {
		Timber.e("loadTrap: " + String.valueOf(event));

		Observable trapObservable;

		if (i % 2 == 0) {
			trapObservable = mRetrofitService.getTrap(getToken(), event.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		} else {

			trapObservable = Observable.just(new Trap("db", 1413194844));



		}
		i++;

		if (trapObservable != null) {
			doSubscribe(trapObservable, event);
		}
	}

	@Subscribe
	public void loadTraps(TrapsLoadEventWrapper.TrapsLoadEvent event) {
		Timber.e("loadTraps: " + String.valueOf(event));

		Observable observable;

		observable = mRetrofitService.getTraps(getToken()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

		doSubscribe(observable, event);
	}

	private <T extends ItemEmitter<E>, E> void doSubscribe(Observable<T> observable, final DestinationEvent<E> destinationEvent) {
		observable.subscribe(new Action1<T>() {
			@Override
			public void call(T t) {
				DataGotEvent<E> dataGotEvent = destinationEvent.getLoadedEvent();
				dataGotEvent.setEntity(t.getEmitItem());
				BusProvider.getInstance().post(dataGotEvent);
			}
		}, new BaseThrowableAction(){
			@Override
			protected void onApiError(Egor egor) {
				super.onApiError(egor);

				boolean tokenExpired = egor.getCode() == 101;
				if(tokenExpired) {
					// put in waiting pool
					String eventKey = genWaitingTaskKey(destinationEvent);
					if(!mWaitingPool.containsKey(eventKey)) {
						mWaitingPool.put(eventKey, destinationEvent);
					}
					// invoke to run obtain token logic
					if(!tokenInObtainingState) {
						tokenInObtainingState = true;
						BusProvider.getInstance().post(new TokenExpiredEvent());
					}
				} else {
					DataErrorEvent dataErrorEvent = destinationEvent.getErrorEvent();
					dataErrorEvent.setEgor(egor);
					BusProvider.getInstance().post(dataErrorEvent);
				}
			}

			@Override
			protected void onRetrofitError(RetrofitError retrofitError) {
				super.onRetrofitError(retrofitError);


				BusProvider.getInstance().post(destinationEvent.getErrorEvent());
			}

			@Override
			protected void onSmthElseError(Throwable throwable) {
				super.onSmthElseError(throwable);

				BusProvider.getInstance().post(destinationEvent.getErrorEvent());
			}
		});
	}

	private String genWaitingTaskKey(DestinationEvent destinationEvent) {
		// event key + event name
		return destinationEvent.getLoadedEvent().getClass().getSimpleName() + destinationEvent.getUniqueKey();
	}

	/* Getters & Setters */

	public String getToken() {
		return mToken;
	}

	public void setToken(String token) {
		mToken = token;
	}
}
