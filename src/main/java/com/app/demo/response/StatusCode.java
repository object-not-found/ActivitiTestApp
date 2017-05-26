package com.app.demo.response;

public enum StatusCode {
	SUCCESS(0, "OK"),                    //success
	ILLEGAL_OPERATION(10001, "illegal operation"),   //非法操作
	NOT_FOUND(10404, "Not Found"),       //request not found
	SYSTEM_ERROR(10500, "System error"), //system error
	REQUEST_REDIRECT(10302, "Redirect"),  //重定向请求
	VALIDATION_FAILED(10999, "Validation failed"),  //校验字段信息错误
	
	TYPE_MISMATH(11001, "Parameter type mismath:[%s]"),  //参数类型匹配错误
	MISSING_REQUEST_PATAMETER(11002, "Missing request parametere:[%s]"),  //缺失必要的字段
	HTTP_MESSAGE_NOT_READABLE(11003, "Check Parameter");  //参考HttpMessageNotReadableException请求错误
	
	private int code;
	private String msg;
	
	private StatusCode(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode(){
		return this.code;
	}
	
	public String getMsg(){
		return this.msg;
	}
}
