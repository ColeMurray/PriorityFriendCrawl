package com.utility;

public class PriorityUserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ALL_TWEETS = "Received All Tweets";
	public static final String INACCESSIBLE_USER = "User cannot be looked up";
	
	
	public PriorityUserException(){
		super();
	}
	public PriorityUserException(String message){
		super(message);
	}
	public PriorityUserException(String message, Throwable cause){
		super (message,cause);
	}
	public PriorityUserException (Throwable cause){
		super(cause);
	}

}
