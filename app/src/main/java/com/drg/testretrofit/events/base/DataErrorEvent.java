package com.drg.testretrofit.events.base;

import com.drg.testretrofit.models.base.Egor;

/**
 * DataErrorEvent
 */
public abstract class DataErrorEvent {

	protected Egor mEgor;

	public Egor getEgor() {
		return mEgor;
	}

	public void setEgor(Egor egor) {
		mEgor = egor;
	}
}
