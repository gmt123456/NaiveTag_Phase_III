import {getUrl} from "./tool";

export function getTaskSketch(taskId, callback) {
  mockSketch(taskId, callback)
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
  };
  callback(res);
}

function sketchFromServer(taskId, callback) {
  $.get(getUrl('requester/task/sketch.html'), {taskId: taskId},
    res => callback(JSON.parse(res)));

}
