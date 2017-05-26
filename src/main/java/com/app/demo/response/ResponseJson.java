package com.app.demo.response;

import com.alibaba.fastjson.JSON;

public class ResponseJson {
	private int code = StatusCode.SUCCESS.getCode();
	private String msg = StatusCode.SUCCESS.getMsg();
	private Object data = new Object();
	
	private transient static ResponseJson successResponse = new ResponseJson(StatusCode.SUCCESS, true);
	private transient static ResponseJson failResponse = new ResponseJson(StatusCode.SYSTEM_ERROR, false);
	
	public ResponseJson(){}
	
	public ResponseJson(int code){
		super();
		this.code = code;
	}
	
	public ResponseJson(int code, String msg){
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public ResponseJson(StatusCode statusCode, boolean success){
		this.code  = statusCode.getCode();
		this.msg = statusCode.getMsg();
	}
	
	public ResponseJson(StatusCode statusCode){
		this.code = statusCode.getCode();
		this.msg = statusCode.getMsg();
	}
	
	public ResponseJson(Object data){
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString(){
		return "ResponseJson [code=" + code + ", msg=" + msg + ", data=" + JSON.toJSONString(data) + "]]";
	}
	
	public static ResponseJson getSuccessResponse(){
		return ResponseJson.successResponse;
	}
	
	public static ResponseJson getFailedResponse(){
		return ResponseJson.failResponse;
	}
	
	public static ResponseJson getFailedResponse(String msg){
		ResponseJson response = ResponseJson.failResponse;
		response.setMsg(msg);
		return response;
	}
	
	
}
