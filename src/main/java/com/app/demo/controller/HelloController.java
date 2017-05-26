package com.app.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.response.ResponseJson;

@RestController
public class HelloController {
	
	@RequestMapping("/")
	public ResponseJson hello(){
		ResponseJson response = new ResponseJson();
		List<String> list = new ArrayList<>();
		list.add("hello");
		list.add("java");
		
		response.setData(list);
		return response;
	}
}
