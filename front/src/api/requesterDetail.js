import {getUrl} from "./tool";


export function getRequesterMain(token, callback) {
  RequesterMainFromServer(token, callback)
}

function RequesterMainFromServer(token, callback) {
  $.get(getUrl('requester/userInfo/main.html'), {token: token}, res => {
    callback(JSON.parse(res));
  })
}

export function getAccountInfo(token, page, pageSize, callback) {
  AccountInfoFromServer(token, page, pageSize, callback);
}

function AccountInfoFromServer(token, page, pageSize, callback) {
  $.get(getUrl("requester/userInfo/accountInfo.html"), {
      token: token,
      page: page,
      pageSize: 20,
    },
    res => {
      callback(JSON.parse(res))
    })
}

export function changeName(name, callback) {
  changeNameToServer(name, callback);
}

function changeNameToServer(name, callback) {
  $.post(getUrl('requester/userInfo/changeName.html'), {
      name: name,
      token: localStorage.token
    },
    res => {
      callback(JSON.parse(res));
    })
}

export function changePassword(oldPassword, newPassword, callback) {
  changePasswordToServer(oldPassword, newPassword, callback);
}

function changePasswordToServer(oldPassword, newPassword, callback) {
  $.post(getUrl('requester/userInfo/changePassword.html'), {
      oldPassword: oldPassword,
      newPassword: newPassword,
      token: localStorage.token
    },
    res => {
      callback(JSON.parse(res));
    })
}

export function recharge(dollars, callback) {
  rechargeToServer(dollars, callback);
}

function rechargeToServer(dollars, callback) {
  $.post(getUrl('requester/userInfo/recharge.html'), {
      dollars: Number(dollars),
      token: localStorage.token
    },
    res => {
      callback(JSON.parse(res));
    })
}

export function changeAvatar(url, callback) {
  changeAvatarToServer(url, callback);
}

function changeAvatarToServer(url, callback) {
  $.post(getUrl('requester/userInfo/changeAvatar.html'), {
      avatar: url,
      token: localStorage.token
    },
    res => {
      callback(JSON.parse(res));
    })
}


/*------------------------------------------------ mock分割线 --------------------------------------------------------*/

function mockMain(token, callback) {
  let res = {
    "name": "frog0",
    "email": "12312421@naive.com",
    "signMessage": "Joined 3 days ago last seen in 1 day",
    "dollars": "9,999.20",
    "avatar": "/static/1.png",
  }

  callback(res);

}

function mockAccountInfo(token, page, pageSize, callback) {
  let res = [
    ["+1,000.2", "Recharge", "2,000.2", "2018-5-15 12:00"],
    ["-2,000,000.2", "TaskPay", "0", "2018-4-30 11:00"],
    ["+1,000.2", "Recharge", "2,000.2", "2018-5-15 12:00"],
    ["-2,000,000.2", "TaskPay", "0", "2018-4-30 11:00"],
    ["+1,000.2", "Recharge", "2,000.2", "2018-5-15 12:00"],
    ["-2,000,000.2", "TaskPay", "0", "2018-4-30 11:00"],
    ["+1,000.2", "Recharge", "2,000.2", "2018-5-15 12:00"],
    ["-2,000,000.2", "TaskPay", "0", "2018-4-30 11:00"],
    ["+1,000.2", "Recharge", "2,000.2", "2018-5-15 12:00"],
    ["-2,000,000.2", "TaskPay", "0", "2018-4-30 11:00"],
    ["+1,000.2", "Recharge", "2,000.2", "2018-5-15 12:00"],
    ["-2,000,000.2", "TaskPay", "0", "2018-4-30 11:00"],
  ];
  console.log('list');
  if (page < 3)
    callback(res);
  else
    callback([["+0", "TaskPay", "0", "2018-4-30 11:00"]]);
}


function mockChangeName(name, callback) {
  callback({
    status: 'success',
    message: null
  });
}

function mockChangePassword(old, newP, callback) {
  callback({
    status: 'success',
    message: null
  });
}
