import {getUrl} from "./tool";

export function getTaskSketch(taskId, callback) {
  mockSketch(taskId, callback)
}

export function getParticipants(taskId, callback) {
  mockParticipants(taskId, callback)
}

export function getReadme(taskId, callback) {
  mockReadme(taskId, callback)
}

export function editReadme(taskId, content, callback) {
  mockEdit(taskId, content, callback);
}

export function getSubTask(taskId, callback) {
  mockSubTask(taskId, callback);
}

function mockSubTask(taskId, callback) {
  let res = [
    {
      "type": "100",
      "process": 0.87,
      "participantsNum": 100,
      "description": 'Try to find the dog in these pictures ssss sss sss ss s ssss ss Try to find the dog in these pictures ssss sss sss ss s ssss ss' +
      'Try to find the dog in these pictures ssss sss sss ss s ssss ss ' +
      'Try to find the dog in these pictures ssss sss sss ss s ssss ss ' +
      'Try to find the dog in these pictures ssss sss sss ss s ssss ss' +
      ' Try to find the dog in these pictures ssss sss sss ss s ssss ss' +
      'Try to find the dog in these pictures ssss sss sss ss s ssss ss' +
      'Try to find the dog in these pictures ssss sss sss ss s ssss ss ',
      "participants": [

        {
          "name": 'frog4',
          "avatar": '/static/1.png',
          "level": 'grandmaster',
          "pictureNum": 223
        },
        {
          "name": 'frog5',
          "avatar": '/static/1.png',
          "level": 'expert',
          "pictureNum": 223
        },
        {
          "name": 'frog6',
          "avatar": '/static/1.png',
          "level": 'novice',
          "pictureNum": 223
        }
      ]
    },
    {
      "type": "200",
      "process": 0.50,
      "participantsNum": 28,
      "description":'mark the box in the picture with the box and labels',
      "participants": [
        {
          "name": 'frog0',
          "avatar": '/static/1.png',
          "level": 'novice',
          "pictureNum": 123
        },
        {
          "name": 'frog1',
          "avatar": '/static/1.png',
          "level": 'master',
          "pictureNum": 223
        },
        {
          "name": 'frog2',
          "avatar": '/static/1.png',
          "level": 'expert',
          "pictureNum": 223
        },
        {
          "name": 'frog3',
          "avatar": '/static/1.png',
          "level": 'contributor',
          "pictureNum": 223
        },
        {
          "name": 'frog4',
          "avatar": '/static/1.png',
          "level": 'grandmaster',
          "pictureNum": 223
        },
        {
          "name": 'frog5',
          "avatar": '/static/1.png',
          "level": 'expert',
          "pictureNum": 223
        },
        {
          "name": 'frog6',
          "avatar": '/static/1.png',
          "level": 'novice',
          "pictureNum": 223
        }
      ]
    }
  ];


  callback(res);

}

function mockEdit(taskId, content, callback) {

  let res1 = {
    message: 'asdasd',
    status: 'success'
  };


  let res2 = {
    message: 'disconnected',
    status: 'fail'
  }

  if (content === '1') {
    callback(res2);
  } else {
    callback(res1);
  }

}

function mockReadme(taskId, callback) {
  callback('');
}

function mockSketch(taskId, callback) {
  let res = {
    "title": "The Task",
    "taskId": 12324,
    "description": "It is just a demooo ooo oo oooo oooo ooooo oooo oooooo ooooo ooo oooo ooooo ooooo.",
    "tags": ["gaming", "education", "marketing", "animal", "fun"],
    "dollars": 30000,
    "backgroundImage": "/static/background/bg2.jpg",
    "participantsNum": 234,
    "pictureNum": 1324,
    "types": ["400", "100", "200"],
    "timeInfo": "upload 2 months ago, 30 days to go",
    "deadline": "2018-5-12 23:59",
    "process": 0.86,
    "state":'finished'
  };
  callback(res);
}

function sketchFromServer(taskId, callback) {
  $.get(getUrl('requester/task/sketch.html'), {taskId: taskId},
    res => callback(JSON.parse(res)));

}

function mockParticipants(taskId, callback) {
  let res = [
    {
      "name": 'frog0',
      "avatar": '/static/1.png',
      "level": 'novice',
      "pictureNum": 123
    },
    {
      "name": 'frog1',
      "avatar": '/static/1.png',
      "level": 'master',
      "pictureNum": 223
    },
    {
      "name": 'frog2',
      "avatar": '/static/1.png',
      "level": 'expert',
      "pictureNum": 223
    },
    {
      "name": 'frog3',
      "avatar": '/static/1.png',
      "level": 'contributor',
      "pictureNum": 223
    },
    {
      "name": 'frog4',
      "avatar": '/static/1.png',
      "level": 'grandmaster',
      "pictureNum": 223
    },
    {
      "name": 'frog5',
      "avatar": '/static/1.png',
      "level": 'expert',
      "pictureNum": 223
    },
    {
      "name": 'frog6',
      "avatar": '/static/1.png',
      "level": 'novice',
      "pictureNum": 223
    }
  ];

  callback(res);
}
