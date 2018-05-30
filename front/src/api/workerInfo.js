import {getUrl} from "./tool";


export function workerInfo(callback) {
	getWorkerInfoFromServer(callback);
}
export function workerChanges(callback) {
	getWorkerChangesFromServer(callback);
}
export function workerEditUserName(userName, callback) {
	editUserNameFromServer(userName, callback);
}
export function workerEditAvatar(avatar, callback) {
	editAvatarFromServer(avatar, callback);
}
export function workerEditPassword(oldPassword, newPassword, callback) {
	editPasswordFromServer(oldPassword, newPassword, callback);
}
export function workerUnfinish(callback) {
	getUnfinishFromServer(callback);
}
export function workerFinish(callback) {
	getFinishFromServer(callback);
}
export function workerRank(callback) {
	getRankFromServer(callback);
}

function getWorkerInfoFromServer(callback) {

	let url = 'worker/userInfo/main.html';

	$.get(getUrl(url), {
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function getWorkerChangesFromServer(callback) {

	let url = 'worker/userInfo/recent.html';

	$.get(getUrl(url), {
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function editUserNameFromServer(userName, callback) {

	let url = 'worker/userInfo/editBasicInfo.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		userName: userName,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function editAvatarFromServer(avatar, callback) {

	let url = 'worker/userInfo/editBasicAvatar.html';

	$.post(getUrl(url), {
		token: localStorage.token,
		avatar: avatar,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function editPasswordFromServer(oldPassword, newPassword, callback) {

	let url = 'worker/userInfo/editPassword.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		oldPassword: oldPassword,
		newPassword: newPassword,
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function getUnfinishFromServer(callback) {
	let url = 'worker/userInfo/doing.html';

	$.get(getUrl(url), {
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function getFinishFromServer(callback) {
	let url = 'worker/userInfo/finished.html';

	$.get(getUrl(url), {
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}
function getRankFromServer(callback) {
	let url = 'worker/userInfo/rank.html';

	$.get(getUrl(url), {
		token: localStorage.token
	}, function (res) {
		callback(JSON.parse(res));
	});
}



function workerRankMock(callback) {
	callback( JSON.parse(JSON.stringify(getRankDataList(6))));
}
function getRankDataList(num) {
	let data = [];
	for(let index = 0;index < num;index++){
		data.push({
			"userName": "Junda",
			"score": 999.99,
			"division": "Grandmaster",//(可能的取值：Novice, Contributor, Expert, Master, Grandmaster)
			"avatar": "../../../../static/1.png"
		})
	}
	return data;
}

function workerFinishMock(callback) {
	callback( JSON.parse(JSON.stringify(getFinishDataMock(6))));
}

function workerUnfinishMock(callback) {
	callback( JSON.parse(JSON.stringify(getUnfinishDataMock(6))));
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
			"taskType": [401, 300], // 这个一级任务中包含的任务的类型
			"endDate": "2017-10-1", // 任务截止的时间
			"taskTag": ["nature", "history"], // 任务的一些标签信息，就是任务的主题
		})
	}
	return data;
}
function getUnfinishDataMock(num) {
	let data = [];
	for (let index = 0; index < num; index++) {
		data.push({
			"name": "TrackML Particle Tracking Challenge", // 任务名
			"taskId": 2333, // 任务ID
			"taskDescription": "High Energy Physics particle tracking in CERN detectors", // 任务描述
			"taskCover": "../../../static/1.png", // 一个url，表示任务的封面，以固定的尺寸显示！
			"totalDollars": 250.00, // 2位小数，表示挣来的钱数
			"taskType": [401, 300], // 这个一级任务中包含的任务的类型
			"endDate": "2017-10-1", // 任务截止的时间
			"taskTag": ["nature", "history"], // 任务的一些标签信息，就是任务的主题
		})
	}
	return data;
}

function workerEditPasswordMock(oldPassword, newPassword, callback) {
	let result = {state: "success"};
	if(oldPassword === newPassword){
		result = {state: "invalid password"};
	}
	callback( JSON.parse(JSON.stringify(result)));
}

function workerEditAvatarMock(avatar, callback) {
	callback( JSON.parse(JSON.stringify({state: "success"})));
}

function workerEditUserNameMock(userName, callback) {
	callback( JSON.parse(JSON.stringify({state: "success"})));
}

function workerInfoMock(callback) {
	let userInfo= {
		"avatar": "../../../static/1.png", // url
		"userName": "Junda",
		"email": "123456@qq.com",
		"lastVisit": "last seen in the past day",
		"rank": 8048000,
		"joint": "joined a minute ago",
		"dollars": 6.66,
		"division": "Expert", //(可能的取值：Novice, Contributor, Expert, Master, Grandmaster)
		"score": 88.9,
	};
	callback( JSON.parse(JSON.stringify(userInfo)));
}
function workerChangesMock(callback) {
	let userChanges= {
		"dollarChanges": changeData([["2000-06-05",116],["2000-06-06",129]]),
		//"dollarChanges": changeData([["2000-06-05",116],["2000-06-06",129],["2000-06-07",135],["2000-06-08",86],["2000-06-09",73],["2000-06-10",85],["2000-06-11",73],["2000-06-12",68],["2000-06-13",92],["2000-06-14",130],["2000-06-16",139],["2000-06-17",115],["2000-06-18",111],["2000-06-20",206],["2000-06-21",137],["2000-06-22",128],["2000-06-23",85],["2000-06-24",94],["2000-06-25",71],["2000-06-26",106],["2000-06-27",84],["2000-06-28",93],["2000-06-29",85],["2000-06-30",73],["2000-07-01",83],["2000-07-02",125],["2000-07-03",107],["2000-07-04",82],["2000-07-05",44],["2000-07-06",72],["2000-07-07",106],["2000-07-08",107],["2000-07-09",66],["2000-07-10",91],["2000-07-11",92],["2000-07-12",113],["2000-07-13",107],["2000-07-14",131],["2000-07-15",111],["2000-07-16",64],["2000-07-17",69],["2000-07-18",88],["2000-07-19",77],["2000-07-20",83],["2000-07-21",111],["2000-07-22",57],["2000-07-23",55],["2000-07-24",60]]),
		"scoreChanges": changeData([["2000-06-05",56],["2000-06-06",31.8],["2000-06-07",88],["2000-06-08",10],["2000-06-09",73],["2000-06-10",85],["2000-06-11",73],["2000-06-12",68],["2000-06-13",92],["2000-06-14",130],["2000-06-16",139],["2000-06-17",115],["2000-06-18",111],["2000-06-19",309],["2000-06-20",206],["2000-06-21",137],["2000-06-22",128],["2000-06-23",85],["2000-06-24",94],["2000-06-25",71],["2000-06-26",106],["2000-06-27",84],["2000-06-28",93],["2000-06-29",85],["2000-06-30",73],["2000-07-01",83],["2000-07-02",125],["2000-07-03",107],["2000-07-04",82],["2000-07-05",44],["2000-07-06",72],["2000-07-07",106],["2000-07-08",107],["2000-07-09",66],["2000-07-10",91],["2000-07-11",92],["2000-07-12",113],["2000-07-13",107],["2000-07-14",131],["2000-07-15",111],["2000-07-16",64],["2000-07-17",69],["2000-07-18",88],["2000-07-19",77],["2000-07-20",83],["2000-07-21",111],["2000-07-22",57],["2000-07-23",55],["2000-07-24",60]]),
		"activity": changeData(getVirtulData()), // 365个数字，表示活跃度，瑞年要不要考虑回头再议，反正2018年不是瑞年
	};
	callback( JSON.parse(JSON.stringify(userChanges)));
}

function changeData(array) {
	let arr = [];
	for(let index in array){
		arr.push({
			date: array[index][0],
			value: array[index][1],
		})
	}
	return arr;
}

function getVirtulData() {
	let mydate = new Date();
	let data = [];

	for (let index=0;index<365;index++) {
		data.push([
			mydate.toLocaleDateString(),
			Math.random()*200
		]);
		mydate = new Date((mydate/1000+86400)*1000);
	}
	return data;
}
