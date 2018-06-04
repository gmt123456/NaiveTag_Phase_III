import {getUrl} from "./tool";
import {getTaskIdToServer} from "./taskTypeName";

//访问某个check小任务的下一张图片
export function checkTaskNextPicUrl(subPartId, callback) {
	let url = 'staff/subCheck/more.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		subPartId: subPartId,
	}, function (res) {
		callback(JSON.parse(res));
	});
}

//获取图片的标注信息
export function checkTaskLabelInfo(subPartId, picUrl, callback) {
	let url = 'staff/subCheck/label.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		subPartId: subPartId,
		url: picUrl,
	}, function (res) {
		callback(JSON.parse(res));
	});
}

//给标注结果评分
export function checkTaskMark(subPartId, picUrl, accept, callback) {
	let url = 'staff/subCheck/mark.html';

	$.get(getUrl(url), {
		token: localStorage.token,
		subPartId: subPartId,
		url: picUrl,
		accept: accept,
	}, function (res) {
		callback(JSON.parse(res));
	});
}