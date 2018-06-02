import {getUrl} from "./tool";

export function taskInfo(taskId,subTaskId,taskType,callback) {

    $.ajax({
        url: getUrl('tag/taskInfo.html'),
        type: 'GET',
        // xhrFields:{withCredentials:true},
        data: {
            'token': localStorage.token,
            "taskId" : taskId,
	        "subTaskId": subTaskId,
	        "taskType": taskType,
        },
        success: function(result){
            callback(JSON.parse(result));
        },
        // contentType:"application/x-www-form-urlencoded; charset=utf-8"
    })
}
export function getLabelInfo(taskId,subTaskId,taskType, picUrl, callback) {

    $.ajax({
        url: getUrl('tag/getLabelInfo.html'),
        type: 'GET',
        // xhrFields:{withCredentials:true},
        data: {
            'token': localStorage.token,
            "taskId" : taskId,
	        "subTaskId": subTaskId,
	        "taskType": taskType,
            "picURL" : picUrl
        },
        success: function(result){
            callback(JSON.parse(result));
        },
        // contentType:"application/x-www-form-urlencoded; charset=utf-8"
    })
}