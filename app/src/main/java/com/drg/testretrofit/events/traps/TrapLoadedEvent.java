package com.drg.testretrofit.events.traps;

import com.drg.testretrofit.models.Trap;

/**
 * TrapLoadedEvent
 */
public class TrapLoadedEvent {

	private Trap mTrap;

	public TrapLoadedEvent(Trap trap) {
		mTrap = trap;
	}

	public Trap getTrap() {
		return mTrap;
	}
}