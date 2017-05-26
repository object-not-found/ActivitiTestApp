package com.app.demo.ServiceTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.demo.service.ActivitiService;

public class LeaverProcess01 extends BaseTest{

	@Autowired
	private ActivitiService activiti;
	
	@Test
	public void test_01(){
		String resource = "diagrams/Leave01.bpmn";
		Deployment deployment = activiti.deployByClassPath(resource);
		String key = activiti.getByDeployId(deployment.getId()).getKey();
		
		Map<String, Object> map = new HashMap<>();
		map.put("applyUser", "employee");
		map.put("days", 3);
		activiti.startWithVar(key, map);
		
		List<Task> tasks = activiti.getTasksByUser("deptLeader");
		tasks.forEach(task -> {
			map.clear();
			map.put("approved", true);
			activiti.claim(task.getId(), "leader01");
			activiti.completeWithVar(task.getId(), map);
			
		});						
	}
	
	@Test
	public void test_02(){
		IdentityService is = activiti.getIdentityService();
		Group group = is.newGroup("HR");
		group.setName("HR组");
		group.setType("assignment");
		is.saveGroup(group);
		
		User user = is.newUser("hr01");
		user.setFirstName("yu");
		user.setLastName("zhiyou");
		user.setEmail("111@qq.com");
		is.saveUser(user);
		
		is.createMembership(user.getId(), group.getId());
		
		
	}
}
