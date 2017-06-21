package com.app.demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.domain.User;
import com.app.demo.response.ResponseJson;

@RestController
public class LoginControllere {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseJson login(@RequestBody User user){
		ResponseJson json = new ResponseJson();
		
		return json;
	}
}
