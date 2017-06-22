package com.app.demo.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.app.demo.response.ResponseJson;
import com.app.demo.response.StatusCode;

public class RolesAuthFilter extends RolesAuthorizationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		Subject subject = getSubject(request, response);
		ResponseJson json;
		if(subject.getPrincipal() == null){
			json = new ResponseJson(StatusCode.NOT_LOGGED_ON);
		}else{
			json = new ResponseJson(StatusCode.NO_PERMISSION);
		}
		sendResponse((HttpServletResponse) response, json);
		return super.onAccessDenied(request, response);
	}

	private void sendResponse(HttpServletResponse response, ResponseJson json) throws IOException{
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(JSON.toJSONString(json));
	}
}
