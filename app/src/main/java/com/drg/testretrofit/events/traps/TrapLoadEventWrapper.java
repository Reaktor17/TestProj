package com.drg.testretrofit.events.traps;

import com.drg.testretrofit.events.base.DataErrorEvent;
import com.drg.testretrofit.events.base.DataGotEvent;
import com.drg.testretrofit.events.base.DestinationEvent;
import com.drg.testretrofit.events.base.GetDataEvent;
import com.drg.testretrofit.models.Trap;

/**
 * TrapLoadEventWrapper
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

	/**
	 * TrapLoadEvent
	 */
	public static class TrapLoadEvent implements DestinationEvent<Trap> {

		private Integer mId;

		public TrapLoadEvent(Integer id) {
			mId = id;
		}

		public Integer getId() {
			return mId;
		}

		@Override
		public DataGotEvent<Trap> getLoadedEvent() {
			return new TrapLoadedEvent();
		}

		@Override
		public DataErrorEvent getErrorEvent() {
			return new TrapErrorLoadedEvent();
		}

		@Override
		public String getUniqueKey() {
			return String.valueOf(mId);
		}
	}

	public static class TrapLoadedEvent extends DataGotEvent<Trap> {

	}

	public static class TrapErrorLoadedEvent extends DataErrorEvent {

	}
}
