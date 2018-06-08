import {getUrl} from "./tool";
import {getTaskIdToServer} from "./taskTypeName";

import {workerUnfinishMock} from "./workerInfo";
import {subTaskDetailsInfoMock, taskInfoMock} from "./workerTaskInfo";
import {taskJoinMock} from "./workerTaskInfo";
import {subTaskInfoMock} from "./workerTaskInfo";
import {myParticipationMock} from "./workerTaskInfo";
import {acceptSubTaskMock} from "./workerTaskInfo";
import {submitSubTaskMock} from "./workerTaskInfo";

//未接受的所有审核任务
export function allCheck(callback) {

	workerUnfinishMock(callback);

	// $.ajax({
	// 	url: getUrl('staff/check/index.html'),
	// 	type: 'GET',
	// 	data: {
	// 		'token': localStorage.token,
	// 	},
	// 	success: function(result){
	// 		callback(JSON.parse(result));
	// 	},
	// })
}

//已接受的所有审核任务
export function myCheck(callback) {

	workerUnfinishMock(callback);
	// $.ajax({
	// 	url: getUrl('staff/check/myParticipation.html'),
	// 	type: 'GET',
	// 	data: {
	// 		'token': localStorage.token,
	// 	},
	// 	success: function(result){
	// 		callback(JSON.parse(result));
	// 	},
	// })
}

export function allTasks(callback) {

	workerUnfinishMock(callback);

	// $.ajax({
	// 	url: getUrl('staff/check/index.html'),
	// 	type: 'GET',
	// 	data: {
	// 		'token': localStorage.token,
	// 	},
	// 	success: function(result){
	// 		callback(JSON.parse(result));
	// 	},
	// })
}

export function myTasks(callback) {

	workerUnfinishMock(callback);

	// $.ajax({
	// 	url: getUrl('staff/check/myParticipation.html'),
	// 	type: 'GET',
	// 	data: {
	// 		'token': localStorage.token,
	// 	},
	// 	success: function(result){
	// 		callback(JSON.parse(result));
	// 	},
	// })
}

//点开具体一级check任务
export function checkTaskInfo(taskId, callback) {

	taskInfoMock(taskId, callback);

	// let url = 'staff/check/main.html';
	//
	// $.get(getUrl(url), {
	// 	taskId: taskId,
	// 	token: localStorage.token
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}
//接受一级check任务
export function checkTaskJoin(taskId, callback) {

	taskJoinMock(taskId, callback);

	// let url = 'staff/check/join.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	taskId: taskId
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}
//查看一级check任务下属小任务
export function checkSubTaskInfo(taskId, taskType, callback) {

	subTaskInfoMock(taskId, taskType, callback);

	// let url = 'staff/check/subTasks.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	taskId: taskId,
	// 	taskType: getTaskIdToServer(taskType),
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}
//查看参与的check小任务
export function checkMyParticipation(taskId, taskState, callback) {

	myParticipationMock(taskId, taskState, callback);

	// let url = 'staff/check/subTask/ongoing.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	taskId: taskId,
	// 	subTaskState: taskState,
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}
//接受某个check小任务
export function checkAcceptSubTask(taskId, subPartId, taskType, callback) {

	acceptSubTaskMock(taskId, subPartId, taskType, callback);

	// let url = 'staff/check/subTask/accept.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	taskId: taskId,
	// 	subPartId: subPartId,
     //    taskType: getTaskIdToServer(taskType),
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}

//访问某个check小任务的第一张图片
export function checkFirstPicUrl(subPartId, callback) {

	callback("../../../../static/1.png");

	// let url = 'staff/subCheck/first.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	subPartId: subPartId,
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}


export function staffSubTaskDetailsInfo(taskId, subTaskId, taskType, callback) {
	subTaskDetailsInfoMock(taskId, subTaskId, getTaskIdToServer(taskType), callback);

	// let url = 'staff/subTask/details.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	taskId: taskId,
	// 	subTaskId: subTaskId,
	// 	taskType: getTaskIdToServer(taskType),
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}

export function staffAcceptSubTask(taskId, subTaskId, taskType, callback) {
	acceptSubTaskMock(taskId, subTaskId, getTaskIdToServer(taskType), callback);

	// let url = 'staff/subTask/accept.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	taskId: taskId,
	// 	subTaskId: subTaskId,
	// 	taskType: getTaskIdToServer(taskType),
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}

export function staffCommitSubTask(taskId, subTaskId, taskType, callback) {

	callback(JSON.parse(JSON.stringify({
		result: true,
		description: "xxxx",
	})));

	// let url = 'staff/subTask/commit.html';
	//
	// $.get(getUrl(url), {
	// 	token: localStorage.token,
	// 	taskId: taskId,
	// 	subTaskId: subTaskId,
	// 	taskType: getTaskIdToServer(taskType),
	// }, function (res) {
	// 	callback(JSON.parse(res));
	// });
}