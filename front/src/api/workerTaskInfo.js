import {getUrl} from "./tool";
import {getTaskIdToServer} from "./taskTypeName";

export function taskInfo(taskId, callback) {
	taskInfoFromServer(taskId, callback);
}
export function taskJoin(taskId, callback) {
	taskJoinFromServer(taskId, callback);
}
export function subTaskInfo(taskId, taskType, callback) {
	subTaskInfoFromServer(taskId, getTaskIdToServer(taskType), callback);
}
export function subTaskDetailsInfo(taskId, subTaskId, taskType, callback) {
	subTaskDetailsInfoFromServer(taskId, subTaskId, getTaskIdToServer(taskType), callback);
}
export function acceptSubTask(taskId, subTaskId, taskType, callback) {
	acceptSubTaskFromServer(taskId, subTaskId, getTaskIdToServer(taskType), callback);
}
export function submitSubTask(taskId, subTaskId, callback) {
	submitSubTaskFromServer(taskId, subTaskId, callback);
}


function submitSubTaskFromServer(taskId, subTaskId, callback) {
	let url = 'worker/task/subTask/commit.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskId: subTaskId,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function acceptSubTaskFromServer(taskId, subTaskId, taskType, callback) {
	let url = 'worker/task/subTask/accept.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskId: subTaskId,
		taskType: taskType
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function subTaskDetailsInfoFromServer(taskId, subTaskId, taskType, callback) {
	let url = 'worker/task/subTask/details.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskId: subTaskId,
		taskType: taskType
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function taskInfoFromServer(taskId, callback) {

	let url = 'worker/task/index.html';

	$.get(getUrl(url), {
	    taskId: taskId,
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function taskJoinFromServer(taskId, callback) {

	let url = 'worker/join.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function subTaskInfoFromServer(taskId, taskType, callback) {

	let url = 'worker/task/subTaskSet.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		taskType: taskType
	}, function (res) {
		callback(JSON.parse(res));
	});
}



function submitSubTaskMock(taskId, subTaskId, callback) {
	console.log("subTaskId"+subTaskId);
	callback(JSON.parse(JSON.stringify({
		result: true,
		description: "xxxx",
	})));
}
function acceptSubTaskMock(taskId, subTaskId, taskType, callback) {
	console.log("subTaskId"+subTaskId+",taskType:"+taskType);
	callback(JSON.parse(JSON.stringify({
		result: true,
		description: "xxxx",
	})));
}
function subTaskDetailsInfoMock(taskId, subTaskId, taskType, callback) {
	let detailsData = {
			"taskId": taskId,
			"subTaskId": subTaskId,
			"taskState": "Accepted",
			"taskName": "Featured Prediction Competition",
			"taskType": taskType,
			"taskDescription": "WebStorm is a powerful IDE for modern JavaScript development perfectly equipped .",
			"finishedPicList": [
			"../../../../static/background/bg1.jpg",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
		],
			"unFinishedPicList": [
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
			"../../../../static/1.png",
		],
		// 	"picList": [
		// 		"../../../../static/1.png",
		// 		"../../../../static/1.png"
		// 	],
			"endDate": "2018-5-24"
	};
	callback( JSON.parse(JSON.stringify(detailsData)));
}
function subTaskInfoMock(taskId, taskType, callback) {
	let n = 2;
	if(taskType === 400){
		n = 4;
	}
	let data = [
		{
			"subTaskId": 0,
			"taskId": taskId,
			"cover": "../../../../static/background/bg3.jpg", // 是一个URL
			"picCount": 66 // 图片数量
		},
		{
			"subTaskId": 1,
			"taskId": taskId,
			"cover": "../../../../static/background/bg2.jpg", // 是一个URL
			"picCount": 88 // 图片数量
		},
		{
			"subTaskId": 2,
			"taskId": taskId,
			"cover": "../../../../static/background/bg3.jpg", // 是一个URL
			"picCount": 100 // 图片数量
		},
	];
	for(let i=0;i<n;i++){
		data.push({
			"subTaskId": i+3,
			"taskId": taskId,
			"cover": "../../../../static/1.png", // 是一个URL
			"picCount": 99 // 图片数量
		})
	}
	callback( JSON.parse(JSON.stringify(data)));
}

function taskInfoMock(taskId, callback) {
	callback( JSON.parse(JSON.stringify({
		"name": "TrackML Particle Tracking Challenge",
		"taskId": 666,
		"taskCover": "../../../../static/1.png",
		"taskDescription": "High Energy Physics particle tracking in CERN detectors",
		"endDate": "2017-1-10",
		"state": "Finished", // 可能的值 Finished, Going
		"participated": true, // 是否已经参与了这个任务
		"requiredDivision": "Contributor", // 表示段位的一个枚举值
		"taskTags": ["nature", "history"],
		"taskTypes": ['t_401', 't_300','t_301','t_400',],

		"taskBackground": "Meet WebStorm\n" +
		"Welcome to WebStorm help!\n" +
		"WebStorm is a powerful IDE for modern JavaScript development perfectly equipped for complex client-side development and server-side development with Node.js.\n" +
		"WebStorm features advanced support for JavaScript, HTML, CSS, and their modern alternatives, as well as for frameworks such as Angular or React. WebStorm also integrates with various web development tools and version control systems. \n" +
		"Intelligent Editor with coding assistance for JavaScript, Node.js, ECMAScript 6, TypeScript, CoffeeScript, and Dart as well as for HTML, CSS, Less, Sass and Stylus. Coding assistance includes syntax highlighting, documentation lookup, and refactorings. \n" +
		"On-the-fly code analysis, error highlighting, and quick fixes.\n" +
		"Powerful navigation across the project and advanced refactorings.\n" +
		"Support for modern frameworks: React, Angular, AngularJS, Vue.js, Express, and more. \n" +
		"Built-in debugger for client-side code and Node.js.\n" +
		"Integration with build tools (Grunt, Gulp), code quality tools (JSHint, JSLint, ESLint, JSCS), test runners (Karma, Mocha, Jest) and VCS (Git, GitHub, Mercurial, SVN)." +
		"",
		"totalDollars": 500.00, // 价格
		"canAccept": false, // 用户没有接过这个任务 && 用户满足接受条件 && 正在进行中，就可以接受
		"earnedDollars": 233.00, // 如果用户参与了这个任务而且这个任务已经完成，这个字段才有效，否则就是0
		"scoreChange": 666, //如果用户参与了这个任务而且这个任务已经完成，这个字段才有效，否则就是0 这两个字段如果无效，就不显示
	})));
}

function taskJoinMock(taskId, callback) {
	callback( JSON.parse(JSON.stringify({
		"result": true,
		"reason": "some thing wrong" // 如果是true的话，那就是空的
	})));
}
