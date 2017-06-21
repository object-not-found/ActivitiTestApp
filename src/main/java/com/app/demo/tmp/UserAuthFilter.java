package com.app.demo.tmp;

import com.alibaba.fastjson.JSON;
import com.gomeplus.meipro.common.response.ResponseJson;
import com.gomeplus.meipro.common.response.StatusCode;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限filter
 * 
 * @author qupeng
 *
 */
public class UserAuthFilter extends UserFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return super.isAccessAllowed(request, response, mappedValue);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		ResponseJson json = new ResponseJson(StatusCode.NOT_LOGGED_ON);
		sendResponse((HttpServletResponse) response, json);
		// return super.onAccessDenied(request, response);
		return false;
	}

	private void sendResponse(HttpServletResponse response, ResponseJson responseJson) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(JSON.toJSONString(responseJson));

	}

}
