package com.drg.testretrofit.events.traps;

/**
 * TrapLoadEvent
 */
public class TrapLoadEvent {

	private Integer mId;

	public TrapLoadEvent(Integer id) {
		mId = id;
	}

	public Integer getId() {
		return mId;
	}
}
