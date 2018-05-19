import {getUrl} from "./tool";


export function signUp(signUpForm,callback) {
    mock(signUpForm,callback);
}

function signUpFromSever(signUpForm,callback) {
  let url;
  if (signUpForm.userType === 'worker') {
    url = 'join/worker.html'
  } else {
    url = 'join/requester.html'
  }

  console.log(signUpForm);
  $.post(getUrl(url),{
    username: signUpForm.username,
    password: signUpForm.password,
    name: signUpForm.nickname,
    email: signUpForm.email
  },function (res) {
    callback(JSON.parse(res));
  });
}

function mock(signUpForm,callback) {
   callback( JSON.parse(JSON.stringify({'result': 'success'})));
}
