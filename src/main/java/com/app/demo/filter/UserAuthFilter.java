package com.app.demo.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.app.demo.response.ResponseJson;
import com.app.demo.response.StatusCode;

public class UserAuthFilter extends UserFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		ResponseJson json = new ResponseJson(StatusCode.NOT_LOGGED_ON);
		sendResponse((HttpServletResponse)response, json);
		return false;
	}

	private  void sendResponse(HttpServletResponse response, ResponseJson json) throws IOException{
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(JSON.toJSONString(json));
	}
}
