package com.drg.testretrofit.events.traps;

import com.drg.testretrofit.models.Trap;

/**
 * TrapSaveEvent
 */
public class TrapSaveEvent {

	private Trap mTrap;

	public TrapSaveEvent(Trap trap) {
		mTrap = trap;
	}

	public Trap getTrap() {
		return mTrap;
	}
}
