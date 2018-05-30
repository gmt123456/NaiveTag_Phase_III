export function getAvatar(token, callback) {
  mock(token, callback)
}

function mock(token, callback) {
  callback('https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526712386018&di=73764149c4900086732f6c1d53c0e9f1&imgtype=0&src=http%3A%2F%2Fp3.music.126.net%2FX6bNTBFo7JPqlJVi1y0g5w%3D%3D%2F3261151496795031.jpg%3Fparam%3D180y180');
}

function getAvatarFromServer(token,callback) {

}
