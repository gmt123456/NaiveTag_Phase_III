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

export function getWorkerDistribution(callback) {
  getWorkerDistributionFromServer(callback);
}

function getWorkerDistributionFromServer(callback) {
  $.get(getUrl('admin/statistic/workerDistribution.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getWorkerSpeed(callback) {
  getWorkerSpeedFromServer(callback);
}

function getWorkerSpeedFromServer(callback) {
  $.get(getUrl('admin/statistic/workerSpeed.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getWorkerQuality(callback) {
  getWorkerQualityFromServer(callback);
}

function getWorkerQualityFromServer(callback) {
  $.get(getUrl('admin/statistic/workerDistribution.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getWorkerEvaluation(callback) {
  getWorkerEvaluationFromServer(callback);
}

function getWorkerEvaluationFromServer(callback) {
  $.get(getUrl('admin/statistic/workerEvaluation.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getTaskEvaluation(callback) {
  getTaskEvaluationFromServer(callback);
}

function getTaskEvaluationFromServer(callback) {
  $.get(getUrl('admin/statistic/taskEvaluation.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getDollars(callback) {
  getDollarsFromServer(callback);
}

function getDollarsFromServer(callback) {
  $.get(getUrl('admin/statistic/dollars.html'), res => {
    callback(JSON.parse(res));
  })
}

export function getTaskPie(callback) {
  getTaskPieFromServer(callback);
}

function getTaskPieFromServer(callback) {
  $.get(getUrl('admin/statistic/taskPieChart.html'), res => {
    callback(JSON.parse(res));
  })
}


/*-------------------------------------------------- mock -----------------------------------------------------------*/

function mockTaskPie(callback) {
  callback({
    "t_100":0.1,
    "t_101":0.1,
    "t_200":0.1,
    "t_201":0.1,
    "t_300":0.2,
    "t_301":0.1,
    "t_400":0.1,
    "t_401":0.2
  })
}

function mockDollars(callback) {
  callback({
    date:['2018-06-01','2018-06-02','2018-06-03','2018-06-04'],
    recharge:[122,230,253,346],
    prize:[123,255,346,467],
    payWorker:[124,125,266,436],
    advertisement:[124,245,457,568],
    expectedPrize:300,
    expectedPayWorker:124,
    expectedAdRate:0.32,
    expectedPayBackRate:0.45
  })
}

function mockTaskEvaluation(callback) {
  callback({
    tagError:[0.12,0.42,0.32,0.23,0.25,0.23,0.36,0.33],
    speed:[21,25,32,23,64,23,25,26],
    participantsNum:[123,152,235,326,234,123,235,263]
  })
}


function mockWorker(callback) {
  callback({
    tagError:[0.4,0.24,0.35,0.22,0.12],
    speed:[34,25,64,36,40],
    ability:[35,23,63,74,83],
    acceptedTasks:[
      [12,14,25,23,15],
      [6,14,15,21,15],
      [12,7,25,13,17],
      [13,14,23,22,15],
      [5,17,11,23,16],
      [10,8,20,24,15],
      [11,4,22,19,13],
      [12,13,25,23,12]
    ]
  })
}


function mockWorkerSpeed(callback) {
  callback([
    [11,21,32,43,12,23,12,14],
    [20,13,24,12,24,53,32,12],
    [31,42,12,25,34,25,12,53],
    [12,14,21,53,23,52,25,12]
  ])
}



function mockWorkerDistribution(callback) {
  callback({
    "Novice": 0.1,
    "Contributor": 0.1,
    "Expert": 0.2,
    "Master": 0.2,
    "GrandMaster": 0.4
  });
}

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


