package com.app.demo.exception;

public class AppServiceException extends AppException {

	private static final long serialVersionUID = 2567014123915309564L;

	public AppServiceException(){}
	
	public AppServiceException(String errorCode){
		super(errorCode);
	}
	
	public AppServiceException(String errorCode, Object... args){
		super(errorCode, args);
	}
}
