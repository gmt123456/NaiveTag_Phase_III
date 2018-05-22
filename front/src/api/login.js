import {getUrl} from "./tool";

export function login(email, password, userType, callback) {
	validateFromServer(email, password,userType, callback)
}

function validateFromServer(email, password, userType, callback) {
  $.post(getUrl('login.html'), {
    email: email,
    password: password,
    userType: userType
  }, function (result) {
    callback(JSON.parse(result))
  })
}

function validateMock(username, password, userType, callback) {
  let res;
  res = {
    "message": null,
    "status": "success",
    "token": "asdasdasdas",
  };

  callback(res);
}
