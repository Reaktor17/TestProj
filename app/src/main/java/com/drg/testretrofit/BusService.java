package com.drg.testretrofit;

import com.drg.testretrofit.events.RetrofitErrorEvent;
import com.drg.testretrofit.events.traps.TrapLoadEvent;
import com.drg.testretrofit.events.traps.TrapLoadedEvent;
import com.drg.testretrofit.events.traps.TrapSaveEvent;
import com.drg.testretrofit.models.Trap;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * BusService
 */
public class BusService {

	private static final String TAG = BusProvider.class.getSimpleName();

	private final RetrofitService mRetrofitService;
	private final Bus mBus;

	public BusService(RetrofitService retrofitService, Bus bus) {
		mRetrofitService = retrofitService;
		mBus = bus;
	}

	@Subscribe
	public void loadTrap(TrapLoadEvent trapLoadEvent) {
		mRetrofitService.getTrap(trapLoadEvent.getId())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Trap>() {
					@Override
					public void call(Trap trap) {
						mBus.post(new TrapLoadedEvent(trap));
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						if (throwable instanceof RetrofitError) {
							mBus.post(new RetrofitErrorEvent((RetrofitError) throwable));
						}
					}
				});
	}

	@Subscribe
	public void saveTrap(TrapSaveEvent trapSaveEvent) {
		mRetrofitService.addTrap(trapSaveEvent.getTrap())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Trap>() {
					@Override
					public void call(Trap trap) {
						mBus.post(new TrapLoadedEvent(trap));
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						if (throwable instanceof RetrofitError) {
							mBus.post(new RetrofitErrorEvent((RetrofitError) throwable));
						}
					}
				});
	}
}
