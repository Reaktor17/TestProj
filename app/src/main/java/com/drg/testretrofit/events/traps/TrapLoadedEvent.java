package com.drg.testretrofit.events.traps;

import com.drg.testretrofit.events.DataGotEvent;
import com.drg.testretrofit.models.Trap;

/**
 * TrapLoadedEvent
 */
public class TrapLoadedEvent extends DataGotEvent<Trap> {

	public TrapLoadedEvent(Trap entity) {
		super(entity);
	}

	//	private Trap mTrap;
//
//	public TrapLoadedEvent(Trap trap) {
//		mTrap = trap;
//	}
//
//	public Trap getTrap() {
//		return mTrap;
//	}
}
