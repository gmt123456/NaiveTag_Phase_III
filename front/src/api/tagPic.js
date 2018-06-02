import {getUrl} from "./tool";

export function save(taskId,subTaskId,taskType,picUrl,JSONObject,callback) {

    $.ajax({
        url: getUrl('tag/save.html'),
        type: 'POST',
        // xhrFields:{withCredentials:true},
        data: {
            'token': localStorage.token,
            "taskId": taskId,
	        "subTaskId": subTaskId,
	        "taskType": taskType,
            "url": picUrl, // 图片的url
            "data": JSONObject //迭代一中已经定义好的图片标注信息
        },
        success: function(result){
            callback(result);
        },
        // contentType:"application/x-www-form-urlencoded; charset=utf-8"
    })
}
export function next(taskId,subTaskId,taskType, picUrl, callback) {

    $.ajax({
        url: getUrl('tag/next.html'),
        type: 'GET',
        // xhrFields:{withCredentials:true},
        data: {
            'username': localStorage.username,
            "taskId" : taskId,
	        "subTaskId": subTaskId,
	        "taskType": taskType,
            "url": picUrl // 当前图片的URL
        },
        success: function(result){
            callback(JSON.parse(result));
        },
        // contentType:"application/x-www-form-urlencoded; charset=utf-8"
    })
}
export function previous(taskId,subTaskId,taskType, picUrl, callback) {

    $.ajax({
        url: getUrl('tag/previous.html'),
        type: 'GET',
        // xhrFields:{withCredentials:true},
        data: {
            'username': localStorage.username,
            "taskId" : taskId,
	        "subTaskId": subTaskId,
	        "taskType": taskType,
            "url": picUrl // 当前图片的URL
        },
        success: function(result){
            callback(JSON.parse(result));
        },
        // contentType:"application/x-www-form-urlencoded; charset=utf-8"
    })
}