package com.drg.testretrofit.events.base;

/**
 * DestinationEvent
 */
public interface DestinationEvent<T> {

	DataGotEvent<T> getLoadedEvent();

	DataErrorEvent getErrorEvent();

	String getUniqueKey();
}
