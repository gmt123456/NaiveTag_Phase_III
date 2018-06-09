import {getUrl} from "./tool";
import {getTaskIdToServer} from "./taskTypeName";

export function allTasks(callback) {

	// workerUnfinishMock(callback);

	$.ajax({
		url: getUrl('staff/check/index.html'),
		type: 'GET',
		data: {
			'token': localStorage.token,
		},
		success: function(result){
			callback(JSON.parse(result));
		},
	})
}

export function myTasks(callback) {

	// workerUnfinishMock(callback);

	$.ajax({
		url: getUrl('staff/check/myParticipation.html'),
		type: 'GET',
		data: {
			'token': localStorage.token,
		},
		success: function(result){
			callback(JSON.parse(result));
		},
	})
}

//点开具体一级tag任务
export function tagTaskInfo(taskId, callback) {

	// taskInfoMock(taskId, callback);

	let url = 'staff/task/main.html';

	$.get(getUrl(url), {
		taskId: taskId,
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//接受一级tag任务
export function tagTaskJoin(taskId, callback) {

	// taskJoinMock(taskId, callback);

	let url = 'staff/task/join.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//查看一级tag任务下属小任务
export function tagSubTaskInfo(taskId, taskType, callback) {

	// subTaskInfoMock(taskId, taskType, callback);

	let url = 'staff/task/subTaskSet.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		taskType: getTaskIdToServer(taskType),
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//查看参与的tag小任务
export function tagMyParticipation(taskId, taskState, callback) {

	// myParticipationMock(taskId, taskState, callback);

	let url = 'staff/task/myParticipation.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskState: taskState,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//接受某个tag小任务
export function tagAcceptSubTask(taskId, subTaskId, taskType, callback) {

	// acceptSubTaskMock(taskId, subTaskId, taskType, callback);

	let url = 'staff/subTask/accept.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskId: subTaskId,
		taskType: getTaskIdToServer(taskType),
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//某个tag小任务的详细信息
export function tagSubTaskDetailsInfo(taskId, subTaskId, taskType, callback) {
	// subTaskDetailsInfoMock(taskId, subTaskId, getTaskIdToServer(taskType), callback);

	let url = 'staff/subTask/details.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskId: subTaskId,
		taskType: getTaskIdToServer(taskType),
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//提交某个tag小任务
export function tagCommitSubTask(taskId, subTaskId, taskType, callback) {

	// callback(JSON.parse(JSON.stringify({
	// 	result: true,
	// 	description: "xxxx",
	// })));

	let url = 'staff/subTask/commit.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskId: subTaskId,
		taskType: getTaskIdToServer(taskType),
	}, function (res) {
		callback(JSON.parse(res));
	});
}



export function staffTaskInfo(taskId,subTaskId,taskType,callback) {

	$.ajax({
		url: getUrl('staff/tag/taskInfo.html'),
		type: 'GET',
		// xhrFields:{withCredentials:true},
		data: {
			'token': localStorage.token,
			"taskId" : taskId,
			"subTaskId": subTaskId,
			"taskType": getTaskIdToServer(taskType),
		},
		success: function(result){
			callback(JSON.parse(result));
		},
		// contentType:"application/x-www-form-urlencoded; charset=utf-8"
	})
}

export function staffLabelInfo(taskId,subTaskId,taskType, picUrl, callback) {

	$.ajax({
		url: getUrl('staff/tag/getLabelInfo.html'),
		type: 'GET',
		// xhrFields:{withCredentials:true},
		data: {
			'token': localStorage.token,
			"taskId" : taskId,
			"subTaskId": subTaskId,
			"taskType": getTaskIdToServer(taskType),
			"url" : picUrl
		},
		success: function(result){
			callback(JSON.parse(result));
		},
		// contentType:"application/x-www-form-urlencoded; charset=utf-8"
	})
}

export function staffSave(taskId,subTaskId,taskType,picUrl,JSONObject,callback) {

	$.ajax({
		url: getUrl('staff/tag/save.html'),
		type: 'POST',
		// xhrFields:{withCredentials:true},
		data: {
			'token': localStorage.token,
			"taskId": taskId,
			"subTaskId": subTaskId,
			"taskType": getTaskIdToServer(taskType),
			"url": picUrl, // 图片的url
			"data": JSONObject //迭代一中已经定义好的图片标注信息
		},
		success: function(result){
			callback(result);
		},
		// contentType:"application/x-www-form-urlencoded; charset=utf-8"
	})
}

export function staffNext(taskId,subTaskId,taskType, picUrl, callback) {

	$.ajax({
		url: getUrl('staff/tag/next.html'),
		type: 'GET',
		// xhrFields:{withCredentials:true},
		data: {
			'username': localStorage.username,
			"taskId" : taskId,
			"subTaskId": subTaskId,
			"taskType": getTaskIdToServer(taskType),
			"url": picUrl // 当前图片的URL
		},
		success: function(result){
			callback(JSON.parse(result));
		},
		// contentType:"application/x-www-form-urlencoded; charset=utf-8"
	})
}

export function staffPrevious(taskId,subTaskId,taskType, picUrl, callback) {

	$.ajax({
		url: getUrl('staff/tag/previous.html'),
		type: 'GET',
		// xhrFields:{withCredentials:true},
		data: {
			'username': localStorage.username,
			"taskId" : taskId,
			"subTaskId": subTaskId,
			"taskType": getTaskIdToServer(taskType),
			"url": picUrl // 当前图片的URL
		},
		success: function(result){
			callback(JSON.parse(result));
		},
		// contentType:"application/x-www-form-urlencoded; charset=utf-8"
	})
}

