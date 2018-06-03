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
export function submitSubTask(taskId, subTaskId, taskType, callback) {
	submitSubTaskFromServer(taskId, subTaskId, taskType, callback);
}
export function myParticipation(taskId, taskState, callback) {
	myParticipationFromServer(taskId, taskState, callback);
}
export function searchResult(taskType, taskTag, rankType, begin, step, key, canAccept, callback) {
	searchResultFromServer(taskType, taskTag, rankType, begin, step, key, canAccept, callback);
}


function searchResultFromServer(taskType, taskTag, rankType, begin, step, key, canAccept, callback) {
	let url = 'worker/task/search.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskType: taskType, // 指的是401，402之类的，如果用户没选，那就是传过来0
		taskTag: taskTag,
			// military,
			// nature,
			// sports,
			// humanity,
			// science,
			// politics,
			// others,
			// all; // all 表示全部都返回，这个要传回来一个字符串
		rankType: rankType,// 排序方式，提供默认排序，按照金钱数量降序，按照金钱升序
			// --> DEFAULT,
			// 	MONEY_DESCEND,
			// 	MONEY_ASCEND; // 传回来一个字符串
		begin: begin,// 起始位置，从0开始
		step: step,// 请求的推荐任务的数量，迭代三应该实现下拉刷新，所以就不设置页了
		key: key, // 字符串，用户为搜索提供的关键字
		canAccept: canAccept, // 筛选条件，指的是用户是否可以接受，如果是false就返回所有的，如果是true就只返回可以接受的
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function myParticipationFromServer(taskId, taskState, callback) {
	let url = 'worker/task/myParticipation.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskState: taskState,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function submitSubTaskFromServer(taskId, subTaskId, taskType, callback) {
	let url = 'worker/task/subTask/commit.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		taskId: taskId,
		subTaskId: subTaskId,
    taskType: getTaskIdToServer(taskType),
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




function searchResultMock(taskType, taskTag, rankType, begin, step, key, canAccept, callback) {
	console.log("begin " + begin);
	console.log("step "+step);
	callback(JSON.parse(JSON.stringify(getFinishDataMock(10))));
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
function myParticipationMock(taskId, taskState, callback) {
	if(taskState === 'doing'){
		callback(JSON.parse(JSON.stringify([
			{
				"cover": "../../../../static/1.png", // 封面
				"expiredDate": "2016-3-10",
				"subTaskId": 11,
				"taskType": 't_301', // 任务类型
				"process": 67, //之间的整数
				"picAmount": 666,// 图片数量
			},
			{
				"cover": "../../../../static/1.png", // 封面
				"expiredDate": "2016-3-10",
				"subTaskId": 12,
				"taskType": 't_400', // 任务类型
				"process": 67, //之间的整数
				"picAmount": 233,// 图片数量
			},{
				"cover": "../../../../static/1.png", // 封面
				"expiredDate": "2016-3-10",
				"commiteDate": "2018-5-30", // 可能是空的，如果没提交就是空的
				"subTaskId": 13,
				"taskType": 't_400', // 任务类型
				"process": 100, //之间的整数
				"picAmount": 555,// 图片数量
			},
		])));
	}else{
		callback(JSON.parse(JSON.stringify([])));
	}

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
			"taskType": "t_301",
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
