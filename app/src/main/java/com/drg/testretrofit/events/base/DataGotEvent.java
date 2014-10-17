package com.drg.testretrofit.events.base;

/**
 * DataGotEvent
 */
public abstract class DataGotEvent<T> {

	private T mEntity;

	public T getEntity() {
		return mEntity;
	}

	public void setEntity(T entity) {
		mEntity = entity;
	}
}
