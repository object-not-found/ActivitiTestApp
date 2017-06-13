package com.app.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.domain.User;
import com.app.demo.response.ResponseJson;
import com.app.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseJson add(@RequestBody User user){
		ResponseJson json = new ResponseJson();
		userService.insert(user);
		
		return json;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ResponseJson list(){
		ResponseJson json = new ResponseJson();
		List<User> users = userService.list();
		json.setData(users);
		return json;
	}
	
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public ResponseJson view(@PathVariable Long id){
		ResponseJson json = new ResponseJson();
		User user = userService.select(id);
		json.setData(user);
		return json;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	public ResponseJson update(@RequestBody User user){
		ResponseJson json = new ResponseJson();
		userService.update(user);
		return json;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseJson delete(@PathVariable Long id){
		ResponseJson json = new ResponseJson();
		userService.delete(id);
		return json;
	}
}
