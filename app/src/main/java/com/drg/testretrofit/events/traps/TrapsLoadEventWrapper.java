package com.drg.testretrofit.events.traps;

import com.drg.testretrofit.events.base.DataErrorEvent;
import com.drg.testretrofit.events.base.DataGotEvent;
import com.drg.testretrofit.events.base.DestinationEvent;
import com.drg.testretrofit.events.base.GetDataEvent;
import com.drg.testretrofit.models.Trap;

import java.util.List;

/**
 * TrapsLoadEventWrapper
 */
public class TrapsLoadEventWrapper extends GetDataEvent {

	private DestinationEvent mEvent;

	public TrapsLoadEventWrapper() {
		mEvent = new TrapsLoadEvent();
	}

	@Override
	public DestinationEvent getDestinationEvent() {
		return mEvent;
	}

	/**
	 * TrapsLoadEvent
	 */
	public static class TrapsLoadEvent implements DestinationEvent {

		public TrapsLoadEvent() {}

		@Override
		public DataGotEvent<List<Trap>> getLoadedEvent() {
			return new TrapsLoadedEvent();
		}

		@Override
		public DataErrorEvent getErrorEvent() {
			return new TrapsErrorLoadedEvent();
		}

		@Override
		public String getUniqueKey() {
			return TrapsLoadEvent.class.getSimpleName();
		}
	}

	/**
	 * TrapsLoadedEvent
	 */
	public static class TrapsLoadedEvent extends DataGotEvent<List<Trap>> {

	}

	/**
	 * TrapsErrorLoadedEvent
	 */
	public static class TrapsErrorLoadedEvent extends DataErrorEvent {

	}
}
