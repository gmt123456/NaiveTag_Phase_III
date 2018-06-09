import {getUrl} from "./tool";
import {getTaskIdToServer} from "./taskTypeName";

export function allTasks(callback) {

  // workerUnfinishMock(callback);

  $.ajax({
    url: getUrl('staff/task/index.html'),
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
    url: getUrl('staff/task/myParticipation.html'),
    type: 'GET',
    data: {
      'token': localStorage.token,
    },
    success: function(result){
      callback(JSON.parse(result));
    },
  })
}


export function staffSubTaskDetailsInfo(taskId, subTaskId, taskType, callback) {
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

export function staffAcceptSubTask(taskId, subTaskId, taskType, callback) {
  // acceptSubTaskMock(taskId, subTaskId, getTaskIdToServer(taskType), callback);

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

export function staffCommitSubTask(taskId, subTaskId, taskType, callback) {

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
