package com.app.demo.tmp;

import com.alibaba.fastjson.JSON;
import com.gomeplus.meipro.common.response.ResponseJson;
import com.gomeplus.meipro.common.response.StatusCode;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by barry on 2017/3/4.
 */
public class RolesAuthFilter extends RolesAuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);

        ResponseJson json ;
        if (subject.getPrincipal() == null) {
            json = new ResponseJson(StatusCode.NOT_LOGGED_ON);
        } else {
            json = new ResponseJson(StatusCode.NO_PERMISSION);
        }

        sendResponse((HttpServletResponse) response, json);
        // return super.onAccessDenied(request, response);
        return false;
    }

    private void sendResponse(HttpServletResponse response, ResponseJson responseJson) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JSON.toJSONString(responseJson));
    }
}
