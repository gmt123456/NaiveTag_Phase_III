import {getUrl} from "./tool";


export function getRequesterMain(token, callback) {
  mockMain(token, callback)
}

function RequesterMainFromServer(token, callback) {
  $.get(getUrl('requester/userInfo/main.html'), {token: token}, res => {
    callback(JSON.parse(res));
  })
}

export function getAccountInfo(token, page, pageSize,callback) {
  mockAccountInfo(token,page,pageSize,callback);
}

function AccountInfoFromServer(token, page,pageSize,callback) {
  $.get(getUrl("requester/userInfo/accountInfo.html"), {
      token: token,
      page: page,
      size: 20,
    },
    res => {
      callback(JSON.parse(res))
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

function mockAccountInfo(token, page, pageSize,callback) {
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
  if(page<3)
    callback(res);
  else
    callback([["+0", "TaskPay", "0", "2018-4-30 11:00"]]);
}
