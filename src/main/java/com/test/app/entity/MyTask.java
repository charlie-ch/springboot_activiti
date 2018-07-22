package com.test.app.entity;

import java.io.Serializable;
import java.util.Date;

public class MyTask implements Serializable {

	private String taskId;
	private String taskName;
	private Date taskCreateTime;
	private String taskProcessInstanceId;
	private String taskAssignee;
	private String taskExecutionId;
	private String taskProcessDefinitionId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getTaskCreateTime() {
		return taskCreateTime;
	}
	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}
	public String getTaskProcessInstanceId() {
		return taskProcessInstanceId;
	}
	public void setTaskProcessInstanceId(String taskProcessInstanceId) {
		this.taskProcessInstanceId = taskProcessInstanceId;
	}
	public String getTaskAssignee() {
		return taskAssignee;
	}
	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}
	public String getTaskExecutionId() {
		return taskExecutionId;
	}
	public void setTaskExecutionId(String taskExecutionId) {
		this.taskExecutionId = taskExecutionId;
	}
	public String getTaskProcessDefinitionId() {
		return taskProcessDefinitionId;
	}
	public void setTaskProcessDefinitionId(String taskProcessDefinitionId) {
		this.taskProcessDefinitionId = taskProcessDefinitionId;
	}
	
	
}
