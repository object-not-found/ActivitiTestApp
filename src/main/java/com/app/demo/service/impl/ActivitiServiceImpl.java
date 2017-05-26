package com.app.demo.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.service.ActivitiService;

@Service
public class ActivitiServiceImpl implements ActivitiService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private IdentityService identityService;
	
	@Override
	public Deployment deployByStream(String resourceName, InputStream inputStream) {
		Deployment deployment = repositoryService.createDeployment()
				.addInputStream(resourceName, inputStream)
				.deploy();
		return deployment;
	}
	
	@Override
	public Deployment deployByClassPath(String resource) {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource(resource)
				.deploy();
		return deployment;
	}
	
	@Override
	public Deployment deployByZip(ZipInputStream zipInputStream) {
		Deployment deployment = repositoryService.createDeployment()
				.addZipInputStream(zipInputStream)
				.deploy();
		return deployment;
	}
	
	@Override
	public Deployment deployByXmlStr(String resourceName, String xmlStr) {
		Deployment deployment = repositoryService.createDeployment()
				.addString(resourceName, xmlStr)
				.deploy();
		return deployment;
	}
	
	@Override
	public ProcessInstance startByKey(String key){
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(key);
		return instance;
	}
	
	@Override
	public ProcessInstance startWithVar(String key, Map<String, Object> var){
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(key, var);
		return instance;
	}

	@Override
	public ProcessDefinition getByDeployId(String deployId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployId).singleResult();
		return processDefinition;
	}

	@Override
	public List<Task> getTasksByAssignee(String assignee) {
		List<Task> tasks = taskService.createTaskQuery()
				.taskAssigneeLike(assignee).list();
		return tasks;
	}

	@Override
	public List<Task> getTasksByUser(String user) {
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateUser(user).list();
		return tasks;
	}

	@Override
	public List<Task> getTasksByGroup(String group) {
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateGroup(group).list();
		return tasks;
	}

	@Override
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	@Override
	public void complete(String taskId) {
		taskService.complete(taskId);
	}

	@Override
	public void completeWithVar(String taskId, Map<String, Object> var) {
		taskService.complete(taskId, var);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public IdentityService getIdentityService() {
		return identityService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}
}
