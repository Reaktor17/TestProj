package com.drg.testretrofit.events.traps;

import com.drg.testretrofit.events.base.DataGotEvent;
import com.drg.testretrofit.models.Trap;

/**
 * TrapLoadedEvent
 */
public class TrapLoadedEvent2 extends DataGotEvent<Trap> {

	public TrapLoadedEvent2(Trap entity) {
		super(entity);
	}
}
