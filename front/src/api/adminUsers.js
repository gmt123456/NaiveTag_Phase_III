import {getUrl} from "./tool";

export function getWorkers(page, pageSize, callback) {
  mockWorkers(page, pageSize, callback);
}

function getWorkersFromServer(page, pageSize, callback) {
  $.get(getUrl('admin/workers.html', {
    page: page,
    pageSize: pageSize
  }, res => {
    callback(JSON.parse(res));
  }))
}

export function getRequester(page, pageSize, callback) {
  mockRequester(page, pageSize, callback);
}

function getRequesterFromServer(page, pageSize, callback) {
  $.get(getUrl('admin/requester.html'), {
    page: page,
    pageSize: pageSize
  }, res => {
    callback(JSON.parse(res))
  })
}

export function changePassword(email, newPassword, callback) {
  mockChangePassword(email,newPassword,callback);
}

function changePasswordFromServer(email, newPassword, callback) {
  $.post(getUrl('admin/modify/password.html'),{
    token:localStorage.token,
    email:email,
    newPassword:newPassword
  },res=>{
    callback(JSON.parse(res));
  })
}

export function changeDollars(email, dollars, callback) {
  mockChangeDollars(email,dollars,callback);
}

function changeDollarsFromServer(email, dollars, callback) {
  $.post(getUrl('admin/modify/dollars.html'),{
    token:localStorage.token,
    email:email,
    dollars:dollars
  },res=>{
    callback(JSON.parse(res));
  })
}

export function searchUser(key, userType, page, pageSize, callback) {
  mockSearch(key, userType, page, pageSize, callback);
}

function searchUserFromServer(key, userType, page, pageSize, callback) {
  $.get(getUrl('admin/searchUser.html'), {
    key: key,
    userType: userType,
    page: page,
    pageSize: pageSize
  }, res => {
    callback(JSON.parse(res))
  })
}


/*------------------------------------------------- mock ------------------------------------------------------------*/

function mockWorkers(page, Pagesize, callback) {
  let res = [
    {
      "name": "frog",
      "email": "12341234@mail.com",
      "avatar": "/static/1.png",
      "signupTime": "2018-5-15",
      "dollars": 2000,
      "division": "master",
      "score": 1342
    },
    {
      "name": "frog",
      "email": "12341234@mail.com",
      "avatar": "/static/1.png",
      "signupTime": "2018-5-15",
      "dollars": 2000,
      "division": "novice",
      "score": 1342
    },
    {
      "name": "frog",
      "email": "12341234@mail.com",
      "avatar": "/static/1.png",
      "signupTime": "2018-5-15",
      "dollars": 2000,
      "division": "contributor",
      "score": 1342
    }
  ];

  callback(res);
}

function mockRequester(page, pageSize, callback) {
  let res = [
    {
      "name": "frog",
      "avatar": "/static/1.png",
      "signupTime": "2018-5-15",
      "dollars": 2000,
      "tasksNum": 1342,
      "email": "2134213@mail.com"
    },
    {
      "name": "frog",
      "avatar": "/static/1.png",
      "signupTime": "2018-5-15",
      "dollars": 2000,
      "tasksNum": 200,
      "email": "2134213@mail.com"
    },
    {
      "name": "frog",
      "avatar": "/static/1.png",
      "signupTime": "2018-5-15",
      "dollars": 2000,
      "tasksNum": 12,
      "email": "2134213@mail.com"
    }
  ];

  callback(res);
}


function mockSearch(key, userType, page, pageSize, callback) {
  if (userType === 'worker') {
    callback([
      {
        "name": "frog",
        "email": "12341234@mail.com",
        "avatar": "/static/1.png",
        "signupTime": "2018-5-15",
        "dollars": 2000,
        "division": "contributor",
        "score": 1342
      }
    ])
  } else {
    callback([
      {
        "name": "frog",
        "avatar": "/static/1.png",
        "signupTime": "2018-5-15",
        "dollars": 2000,
        "tasksNum": 1342,
        "email": "2134213@mail.com"
      }
    ]);

  }
}

function mockChangePassword(email, newPassword, callback) {
  callback({
    status: 'success',
    message: ''
  })
}

function mockChangeDollars(email, dollars, callback) {
  callback({
    status: 'success',
    message: ''
  })
}
