import {getUrl} from "./tool";

export function getDefaultInfo(callback) {
  mockDefaultInfo(callback);
}

export function submitTaskOrder(taskForm, callback) {
  mockSubmitTask(taskForm, callback);
}

export function payOrder(order, callback) {
payToMock(order,callback);
}

function payToServer(Order, callback) {
  let formData = new FormData();
  formData.append('token', localStorage.token);
  formData.append('status', order.status);
  formData.append('orderId', order.order);
  formData.append('dollars', order.dollars);
  formData.append('advertisementDollars', String(order.adFee));

  $.ajax({
    url: getUrl('requester/new/pay.html'),
    type: 'GET',
    data: formData,
    processData: false,
    contentType: false,
    success: function (res) {
      callback();
    }
  })

}

function payToMock(order, callback) {
  callback();
}

function submitTaskToServer(taskForm, callback) {
  let formData = new FormData();
  formData.append('token', localStorage.token);
  formData.append('cover', taskForm.cover);
  formData.append('backgroundImage', '');
  formData.append('title', taskForm.title);
  formData.append('tags', JSON.stringify(taskForm.tags));
  formData.append('description', taskForm.description);
  formData.append('tasks', JSON.stringify(taskForm.tasks));
  formData.append('dataset', taskForm.file);
  formData.append('readme', '');
  formData.append('deadline', taskForm.deadLine);
  formData.append('lowestDivision', taskForm.lowestDivision);

  $.ajax({
    url: getUrl('requester/new/taskOrder.html'),
    type: 'POST',
    data: formData,
    processData: false,
    contentType: false,
    success: function (res) {
      callback((JSON).parse(res));
    }
  })

}

function mockSubmitTask(taskForm, callback) {
  let res = {
    "status": "success",
    "message": "balabala",
    // 如果返回的 status 是成功则有下面两个字段，否则没有
    "orderId": "dsfdsf",  // 订单编号
    "pictureNum": 1234,
    "payLowerBound": 21412342.11,  // 最少付多少
  };
  callback(res);
}


function mockDefaultInfo(callback) {
  let res = {
    tags: ["education", "animal", "gaming", "computer", "software", "teaching", "career", "sport", "animate", "movie"],
    divisions: ["Novice", "Contributor", "Expert", "Master", "Grandmaster"]
  };
  callback(res);
}
