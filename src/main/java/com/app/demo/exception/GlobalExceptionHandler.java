package com.app.demo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.demo.response.ResponseJson;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ResponseJson handlerException(HttpServletRequest req, Exception e){
		ResponseJson res = new ResponseJson();
		res.setCode(0000);
		res.setMsg(e.getMessage());
		return res;
	}
}
