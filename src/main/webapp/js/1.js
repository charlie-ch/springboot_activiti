
$(function(){
	
})
/**
 * 查询个人待处理任务
 */
function findMyTask(){
	$.ajax({
		type:"post",
		url:"activiti/findMyPersonalTask",
		data:{assignee:$("#assignee").val()},
		success:function(res){
			$("#myTask tr:gt(0)").remove();
			for(var i in res){
				$("#myTask").append("<tr onclick='getProcessImg(&#39;"+res[i].taskProcessInstanceId+"&#39;)'><td>"+res[i].taskId+"</td><td>"+res[i].taskName+"</td><td>"+res[i].taskCreateTime+"</td><td>"+res[i].taskAssignee+"</td><td>"+res[i].taskProcessInstanceId+"</td><td>"+res[i].taskExecutionId+"</td><td><a href='javascript:dealTask(&#39;"+res[i].taskId+"&#39;)'>处理</a></td></tr>");
			}
		}
	});
}
/**
 * 启动流程
 */
function startProcess(){
	
	var startAssignee =$("#startAssignee").val();
	$.ajax({
		type:"post",
		url:"activiti/startProcess",
		data:{processDefinitionKey:"myProcess",startAssignee:startAssignee},
		success:function(res){
			alert(res);
		}
	});
}
/**
 * 处理任务
 */
function dealTask(taskId){
	$.ajax({
		type:"post",
		url:"activiti/complete",
		data:{taskId:taskId},
		success:function(res){
			alert(res);
		}
	});
}
/**
 * 查询流程图
 */
function getProcessImg(instanceId){
	var date = new Date();
	date.getTime()
	$("#imgDiv").empty().append("<img src='activiti/queryflowimage?instanceId="+instanceId+"&time="+date.getTime()+"'/>");
}