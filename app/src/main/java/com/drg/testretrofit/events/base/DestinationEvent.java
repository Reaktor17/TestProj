package com.drg.testretrofit.events.base;

/**
 * DestinationEvent
 */
public interface DestinationEvent {

	DataGotEvent getLoadedEvent();

	DataErrorEvent getErrorEvent();
}
