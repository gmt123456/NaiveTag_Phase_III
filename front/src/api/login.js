import {getUrl} from "./tool";

export function login(email, password, userType, callback) {
  validateFromServer(email, password, userType, callback)
}

function validateFromServer(email, password, userType, callback) {
  $.get(getUrl('login.html'), {
    email: email,
    password: password,
    userType: userType
  }, function (result) {
    callback(JSON.parse(result))
  })
}

export function insiderLogin(email, password, callback) {
  insiderLoginFromServer(email, password, callback)
}

function insiderLoginFromServer(username,password,callback) {
  $.get(getUrl('inside/login.html'), {
    username: username,
    password: password
  }, function (result) {
    callback(JSON.parse(result))
  })
}


/*------------------------------------------------------ mock -------------------------------------------------------*/


function validateMock(username, password, userType, callback) {
  let res;
  res = {
    "message": null,
    "status": "success",
    "token": "asdasdasdas",
  };

  callback(res);
}

function mockInsider(email, password, callback) {
  let res = {
    status: 'success',
    message: '',
    token:'sadasds'
  }
  callback(res);
}
