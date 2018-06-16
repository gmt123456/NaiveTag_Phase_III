import {getUrl} from "./tool";
import {getTaskIdToServer} from "./taskTypeName";

export function taskInfo(taskId,subTaskId,taskType,callback) {

    $.ajax({
        url: getUrl('worker/tag/taskInfo.html'),
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
export function getLabelInfo(taskId,subTaskId,taskType, picUrl, callback) {

    $.ajax({
        url: getUrl('worker/tag/getLabelInfo.html'),
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
export function getRecommendation(taskId, picUrl, callback) {

	$.ajax({
		url: getUrl('worker/tag/helper.html'),
		type: 'GET',
		// xhrFields:{withCredentials:true},
		data: {
			'token': localStorage.token,
			"taskId" : taskId,
			"url" : picUrl
		},
		success: function(result){
			callback(JSON.parse(result));
		},
		// contentType:"application/x-www-form-urlencoded; charset=utf-8"
	})
}
