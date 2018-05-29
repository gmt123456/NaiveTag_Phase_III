export function getUrl(url) {
    return 'http://localhost:8000/naive/'+url;
}

export function deepClone(source){
  return JSON.parse(JSON.stringify(source));
}
