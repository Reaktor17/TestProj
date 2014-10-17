package com.drg.testretrofit.app;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * BusProvider
 */
public class BusProvider {

	private static Bus sBus;

	private BusProvider() {}

	public synchronized static Bus getInstance() {
		if(sBus == null) {
			sBus = new Bus(ThreadEnforcer.ANY);
		}
		return sBus;
	}
}
