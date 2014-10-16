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
}
