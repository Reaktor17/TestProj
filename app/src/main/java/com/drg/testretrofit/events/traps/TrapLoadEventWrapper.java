package com.drg.testretrofit.events.traps;

import com.drg.testretrofit.events.base.DestinationEvent;
import com.drg.testretrofit.events.base.GetDataEvent;

/**
 * TrapLoadEvent
 */
public class TrapLoadEventWrapper extends GetDataEvent {

	private DestinationEvent mEvent;

	public TrapLoadEventWrapper(Integer id) {
		mEvent = new TrapLoadEvent(id);
	}

	@Override
	public DestinationEvent getDestinationEvent() {
		return mEvent;
	}

	public static class TrapLoadEvent implements DestinationEvent {
		private Integer mId;

		public TrapLoadEvent(Integer id) {
			mId = id;
		}

		public Integer getId() {
			return mId;
		}
	}
}
