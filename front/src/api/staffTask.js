import {getUrl} from "./tool";
import {getTaskIdToServer} from "./taskTypeName";

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

//点开具体一级check任务
export function checkTaskInfo(taskId, callback) {
	let url = 'staff/check/main.html';

	$.get(getUrl(url), {
		taskId: taskId,
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//接受一级check任务
export function checkTaskJoin(taskId, callback) {
	let url = 'staff/check/join.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//查看一级check任务下属小任务
export function checkSubTaskInfo(taskId, taskType, callback) {
	let url = 'staff/check/subTasks.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		taskType: taskType
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//查看参与的check小任务
export function checkMyParticipation(taskId, taskState, callback) {
	let url = 'staff/check/subTask/ongoing.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskState: taskState,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//接受某个check小任务
export function checkAcceptSubTask(taskId, subPartId, taskType, callback) {
	let url = 'staff/check/subTask/accept.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subPartId: subPartId,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
//访问某个check小任务的第一张图片
export function checkFirstPicUrl(subPartId, callback) {
	let url = 'staff/subCheck/first.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		subPartId: subPartId,
	}, function (res) {
		callback(JSON.parse(res));
	});
}

function workerUnfinishMock(callback) {
	callback( JSON.parse(JSON.stringify(getFinishDataMock(6))));
}
function getFinishDataMock(num) {
	let data = [];
	for (let index = 0; index < num; index++) {
		data.push({
			"name": "TrackML Particle Tracking Challenge", // 任务名
			"taskId": 2333, // 任务ID
			"taskDescription": "High Energy Physics particle tracking in CERN detectors", // 任务描述
			"taskCover": "../../../static/1.png", // 一个url，表示任务的封面，以固定的尺寸显示！
			"earnedDollors": 250.00, // 2位小数，表示挣来的钱数
			"changeOfScore": 360, // 做的这个任务引起的积分变化，可正可负
			"taskType": ['t_401', 't_300'], // 这个一级任务中包含的任务的类型
			"endDate": "2017-10-1", // 任务截止的时间
			"taskTag": ["nature", "history"], // 任务的一些标签信息，就是任务的主题
		})
	}
	return data;
}
