package com.app.demo.exception;

public class AppException extends Exception{

	private static final long serialVersionUID = -4855265259120901079L;

	private String errorCode;
	private Object[] args;
	
	protected AppException(){}
	
	protected AppException(String errorCode){
		super("ERROR CODE:" + errorCode);
		this.errorCode = errorCode;
	}
	
	protected AppException(String errorCode, Object[] args){
		super("ERROR CODE:" + errorCode + "  ARGS:" + args);
		this.errorCode = errorCode;
		this.args = args;
	}
	
	@Override
	public synchronized Throwable fillInStackTrace(){
		return this;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object[] getArgs() {
		return args;
	}
	
}
