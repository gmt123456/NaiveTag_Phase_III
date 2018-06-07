import {getUrl} from "./tool";

export function getActiveUser(callback) {
  getActiveUserFromServer(callback);
}

function getActiveUserFromServer(callback) {
  $.get(getUrl('admin/statistic/activeUser.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getTotalUser(callback) {
  getTotalUserFromServer(callback);
}

function getTotalUserFromServer(callback) {
  $.get(getUrl('admin/statistic/signUpUser.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getTasks(callback) {
  getTasksFromServer(callback);
}

function getTasksFromServer(callback) {
  $.get(getUrl('admin/statistic/tasks.html'), res => {
    callback(JSON.parse(res));
  })
}

/*-------------------------------------------------- mock -----------------------------------------------------------*/
function mockActiveUser(callback) {
  let res = {
    time: ['2018-6-1', '2018-6-2', '2018-6-3', '2018-6-4', '2018-6-5', '2018-6-6'],
    requesterData: [11, 13, 14, 14, 15, 20],
    workerData: [30, 60, 100, 123, 153, 214],
    totalData: []
  };

  for (let i in res.time) {
    res.totalData.push(res.workerData[i] + res.requesterData[i]);
  }

  callback(res);
}

function mockSignUpUser(callback) {

  let res = {
    time: ['2018-6-1', '2018-6-2', '2018-6-3', '2018-6-4', '2018-6-5', '2018-6-6'],
    requesterData: [11, 13, 14, 14, 15, 20],
    workerData: [30, 60, 100, 123, 153, 214],
    userData: []
  };

  for (let i in res.time) {
    res.userData.push(res.workerData[i] + res.requesterData[i]);
  }

  callback(res);

}

function mockTasks(callback) {
  let res = {
    time: ['2018-6-1', '2018-6-2', '2018-6-3', '2018-6-4', '2018-6-5', '2018-6-6'],
    doneNumber: [11, 13, 14, 14, 15, 20],
    commitNumber: [30, 60, 100, 123, 153, 214],
    releaseNumber: []
  };

  for (let i in res.time) {
    res.releaseNumber.push(res.doneNumber[i] + res.commitNumber[i]);
  }

  callback(res);
}


