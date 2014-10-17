package com.drg.testretrofit.events.token;

import com.drg.testretrofit.events.base.DataErrorEvent;
import com.drg.testretrofit.events.base.DataGotEvent;
import com.drg.testretrofit.events.base.DestinationEvent;
import com.drg.testretrofit.models.Token;

/**
 * TokenObtainEvent
 */
public class TokenObtainEvent implements DestinationEvent<Token> {

	public TokenObtainEvent() {}

	@Override
	public DataGotEvent<Token> getLoadedEvent() {
		return new TokenObtainedEvent();
	}

	@Override
	public DataErrorEvent getErrorEvent() {
		return new TokenObtainErrorEvent();
	}

	@Override
	public String getUniqueKey() {
		return String.valueOf(TokenObtainEvent.class.getSimpleName());
	}

	public static class TokenObtainedEvent extends DataGotEvent<Token> {

	}

	public static class TokenObtainErrorEvent extends DataErrorEvent {

	}
}

