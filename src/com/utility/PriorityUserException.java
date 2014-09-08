package com.utility;

public class PriorityUserException extends Exception {
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
