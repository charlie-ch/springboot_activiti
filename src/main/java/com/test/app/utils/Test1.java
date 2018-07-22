/*package com.test.app.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.app.App;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class Test1 {

	@Autowired
	RuntimeService runtimeService;

	
	 * @Test public void TestStartProcess() { Map<String, Object> variables =
	 * new HashMap<String, Object>(); variables.put("applicantName",
	 * "John Doe"); variables.put("email", "john.doe@activiti.com");
	 * variables.put("phoneNumber", "123456789");
	 * runtimeService.startProcessInstanceByKey("myProcess", variables); }
	 
	*//**
	 * ???
	 *//*
	@Test
	public void deploy() {
		// 1.�???��?�?????�????????�??�???��?�??�?epositoryService对象�??�??象�?
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// ?��?�?????对象产�?�?��?�署对象??��对象�???��?�??署�?�???��???��??
		Deployment deploy = processEngine.getRepositoryService().createDeployment().name("请�?�??2")
				.addClasspathResource("processes/myProcess.bpmn").addClasspathResource("processes/myProcess.png")
				.deploy();// �?????
		System.out.println(deploy.getId() + "----" + deploy.getName());

	}

	*//**
	 * ???�??
	 * 
	 *//*
	@Test
	public void startProcess() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("a", "aaa");
		variables.put("b", "bbb");
		variables.put("c", "ccc");
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("myProcess",
				variables);
		System.out.println("完成" + processInstance.getId() + "--" + processInstance.getProcessDefinitionId());

	}

	*//**
	 * ?��?�?��任�?
	 *//*
	@Test
	public void queryMyTask() {
		String assignee = "�??";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> list = processEngine.getTaskService().createTaskQuery()// ??��?��?
				.taskAssignee(assignee)// ?��????人�?�?
				.orderByTaskCreateTime().desc()// ???
				.list();// ?��????

		System.out.println("-------------�?��任�???��-------------");
		Map<String, Object> variables = processEngine.getTaskService().getVariables("37507");
		
		System.out.println("???�?+variables.get("a"));
		System.out.println("???�?+variables.get("b"));
		System.out.println("???�?+variables.get("c"));
		for (Task task : list) {
			System.out.println("任�?ID:" + task.getId());
			System.out.println("任�???��:" + task.getName());
			System.out.println("任�????建�???" + task.getCreateTime());
			System.out.println("任�??????��:" + task.getAssignee());
			System.out.println("�??�??ID�? + task.getProcessInstanceId());
			System.out.println("?��?对象ID:" + task.getExecutionId());
			System.out.println("�??�??ID:" + task.getProcessDefinitionId());
			System.out.println("########################################################");
		}
	}

	
	*//**
	 * ?��?�?��任�?
	 *//*
	@Test
	public void findMyPersonalTask() {
		String assignee = "???";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> list = processEngine.getTaskService()// �???��?�??任�?管�??��???ervice
				.createTaskQuery()// ??��任�??��?对象
				*//** ?��??�件�?here?��?�?*//*
				.taskAssignee(assignee)// ???�?��任�??��?�??�????��
				// .taskCandidateUser(candidateUser)//�?��?��????人�?�?
				// .processDefinitionId(processDefinitionId)//使�?�??�??ID?��?
				// .processInstanceId(processInstanceId)//使�?�??�??ID?��?
				// .executionId(executionId)//使�??��?对象ID?��?
				*//** ??? *//*
				.orderByTaskCreateTime().asc()// 使�???��?��????�????
				*//** �??�????*//*
				// .singleResult()//�?????�????
				// .count()//�??�??????��?
				// .listPage(firstResult, maxResults);//??��?��?
				.list();// �????��
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任�?ID:" + task.getId());
				System.out.println("任�???��:" + task.getName());
				System.out.println("任�????建�???" + task.getCreateTime());
				System.out.println("任�??????��:" + task.getAssignee());
				System.out.println("�??�??ID�? + task.getProcessInstanceId());
				System.out.println("?��?对象ID:" + task.getExecutionId());
				System.out.println("�??�??ID:" + task.getProcessDefinitionId());
				System.out.println("########################################################");
			}
		}
	}
	*//**
	 * �??任�?�?��?��?�?��????????askId
	 *//*
	@Test
	public void complete(){
		String taskId = "20016";//?��?�?���????��?��?�??
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService().complete(taskId);
	}
	
	*//**
	 * ?��?�???��?
	 *//*
	@Test
    public void isProcessEnd() {
        String processInstanceId = "30004545";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance pi = processEngine.getRuntimeService()// 表示正�??��????�??�???��?对象
                .createProcessInstanceQuery()// ??���??�???��?
                .processInstanceId(processInstanceId)// 使�?�??�??ID?��?
                .singleResult();
        if (pi == null) {
            System.out.println("�??已�?�??");
        } else {
            System.out.println("�??没�?�??");
        }
    }
	
	*//**
	 * ?��????任�?
	 *//*
	@Test
    public void findHistoryTask(){
        String taskAssignee = "�??";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<HistoricTaskInstance> list = processEngine.getHistoryService()//�???��???????�???��???ervice
                        .createHistoricTaskInstanceQuery()//??��???任�?�???��?
                        .taskAssignee(taskAssignee)//??????任�??????��
                        .list();
        if(list!=null && list.size()>0){
            for(HistoricTaskInstance hti:list){
                System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());
                System.out.println("################################");
            }
        }
    }
	
}
*/