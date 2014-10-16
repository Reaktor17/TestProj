package com.drg.testretrofit.events;

/**
 * DataGotEvent
 */
public class DataGotEvent<T> {

	private T mEntity;

	public DataGotEvent(T entity) {
		mEntity = entity;
	}

	public T getEntity() {
		return mEntity;
	}
}
