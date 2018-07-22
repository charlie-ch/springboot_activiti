package com.test.app.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.entity.MyTask;
import com.test.app.utils.ProcessDiagramGenerator;

@RestController
@RequestMapping("activiti")
public class ActivitiController {
	@Autowired
	HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 发布
	 */
	@RequestMapping("/deploy")
	public Object deploy() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		Deployment deploy = processEngine.getRepositoryService().createDeployment()// 创建发布对象
				.name("请假流程")// 发布的流程的名称
				.addClasspathResource("processes/myProcess.bpmn")// 规则文件
				.addClasspathResource("processes/myProcess.png")// 规则图片
				.deploy();// 发布
		String msg = deploy.getId() + "--" + deploy.getName();
		return msg;
	}

	/**
	 * 启动流程
	 */
	@RequestMapping("startProcess")
	public Object startProcess(String processDefinitionKey,String startAssignee) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 定义启动变量
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("a", "aa");
		variables.put("b", "bb");
		variables.put("c", "cc");
		// 启动流程
		ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey,
				variables);
		String res = "启动完成：" + pi.getId() + "--" + pi.getActivityId() + "--" + pi.getProcessDefinitionId();
		return res;
	}

	/**
	 * 查询个人待处理任务
	 */
	@RequestMapping("findMyPersonalTask")
	@ResponseBody
	public Object findMyPersonalTask(String assignee) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> list = processEngine.getTaskService()// 与正在执行的任务管理相关的Service
				.createTaskQuery()// 创建任务查询对象
				/** 查询条件（where部分） */
				.taskAssignee(assignee)// 指定个人任务查询，指定办理人
				// .taskCandidateUser(candidateUser)//组任务的办理人查询
				// .processDefinitionId(processDefinitionId)//使用流程定义ID查询
				// .processInstanceId(processInstanceId)//使用流程实例ID查询
				// .executionId(executionId)//使用执行对象ID查询
				/** 排序 */
				.orderByTaskCreateTime().asc()// 使用创建时间的升序排列
				/** 返回结果集 */
				// .singleResult()//返回惟一结果集
				// .count()//返回结果集的数量
				// .listPage(firstResult, maxResults);//分页查询
				.list();// 返回列表

		ArrayList<Object> todoList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			MyTask t = null;
			for (Task task : list) {
				t = new MyTask();
				t.setTaskId(task.getId());
				// System.out.println("任务名称:" + task.getName());
				t.setTaskName(task.getName());
				// System.out.println("任务的创建时间:" + task.getCreateTime());
				t.setTaskCreateTime(task.getCreateTime());
				// System.out.println("任务的办理人:" + task.getAssignee());
				t.setTaskAssignee(task.getAssignee());
				// System.out.println("流程实例ID：" + task.getProcessInstanceId());
				t.setTaskProcessInstanceId(task.getProcessInstanceId());
				// System.out.println("执行对象ID:" + task.getExecutionId());
				t.setTaskExecutionId(task.getExecutionId());
				// System.out.println("流程定义ID:" +
				// task.getProcessDefinitionId());
				t.setTaskProcessDefinitionId(task.getProcessDefinitionId());
				// System.out.println("########################################################");
				todoList.add(t);
			}
		}
		return todoList;
	}

	/**
	 * 处理任务
	 */
	@RequestMapping("complete")
	public Object complete(String taskId) {
		ProcessEngines.getDefaultProcessEngine().getTaskService().complete(taskId);
		return "处理完成";
	}

	/**
	 * 查询流程图
	 */

	@RequestMapping("/queryflowimage")
	@ResponseBody
	public void queryFlowImage(String instanceId, HttpServletResponse response) throws Exception {
		// ?��?�??�??
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(instanceId).singleResult();
		if (historicProcessInstance == null) {
			throw new Exception();
		} else {

			// ?��?�??�??
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

			// ?��?�?????�?��?��????�?��???????��?�?��?��????顺�????
			List<HistoricActivityInstance> historicActivityInstanceList = historyService
					.createHistoricActivityInstanceQuery().processInstanceId(instanceId)
					.orderByHistoricActivityInstanceId().asc().list();

			// 已完成的集合
			List<String> executedActivityIdList = new ArrayList<String>();
			@SuppressWarnings("unused")
			int index = 1;
			for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
				executedActivityIdList.add(activityInstance.getActivityId());
				index++;
			}
			// ?��?�???��????�??
			InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png",
					executedActivityIdList);
			response.setContentType("image/png");
			OutputStream os = response.getOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			imageStream.close();
		}
	}
}
