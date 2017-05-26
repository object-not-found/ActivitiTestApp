package com.app.demo.service;

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

public interface ActivitiService {
	public RepositoryService getRepositoryService();
	public RuntimeService getRuntimeService();
	public TaskService getTaskService();
	public HistoryService getHistoryService();
	public IdentityService getIdentityService();
	//部署流程定义图
	/**
	 * 根据输入流部署
	 * @param resourceName
	 * @param inputStream
	 * @return
	 */
	public Deployment deployByStream(String resourceName, InputStream inputStream);
	
	/**
	 * 根据资源路径部署
	 * @param resource
	 * @return
	 */
	public Deployment deployByClassPath(String resource);
	
	/**
	 * 根据zip文件部署
	 * @param zipInputStream
	 * @return
	 */
	public Deployment deployByZip(ZipInputStream zipInputStream);
	
	/**
	 * 根据xml字符串部署
	 * @param resourceName
	 * @param xmlStr
	 * @return
	 */
	public Deployment deployByXmlStr(String resourceName, String xmlStr);
	
	/**
	 * 根据流程部署ID查询流程定义
	 * @param deployId
	 * @return
	 */
	public ProcessDefinition getByDeployId(String deployId);
	
	//启动流程实例
	/**
	 * 根据流程定义key启动流程实例
	 * @param key
	 * @return
	 */
	public ProcessInstance startByKey(String key);
	
	/**
	 * 根据流程定义key和流程变量启动流程实例
	 * @param key
	 * @param var
	 * @return
	 */
	public ProcessInstance startWithVar(String key, Map<String, Object> var);
	
	//查询代办任务
	/**
	 * 根据代办人查询任务列表
	 * @param assignee
	 * @return
	 */
	public List<Task> getTasksByAssignee(String assignee);
	
	/**
	 * 根据角色查询任务列表
	 * @param user
	 * @return
	 */
	public List<Task> getTasksByUser(String user);
	
	/**
	 * 根据用户组查询任务列表
	 * @param group
	 * @return
	 */
	public List<Task> getTasksByGroup(String group);
	
	/**
	 * 认领任务
	 * @param taskId
	 * @param userId
	 */
	public void claim(String taskId, String userId);
	
	/**
	 * 完成任务
	 * @param taskId
	 */
	public void complete(String taskId);
	
	/**
	 * 完成任务，带流程变量
	 * @param taskId
	 * @param var
	 */
	public void completeWithVar(String taskId, Map<String, Object> var);
	
	
	
	
	
	
	
	
	
	
}
