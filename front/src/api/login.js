
import {getUrl} from "./tool";

export function login(username, password, callback) {
    validateMock(username,password,callback)
}

function validateFromServer(username, password,callback) {
   $.post(getUrl('login.html'),{
       username:username,
       password:password
   },function (result) {
       callback(JSON.parse(result))
   })
}

function validateMock(username, password,callback) {
    let res;
    if (username === '')
        res={"result": "success", "userType": "worker"};
    else
        res={"result": "success", "userType": "requester"};

    callback(res);
}
