
export function getUrl(url) {
    let baseUrl='http://localhost:8000/naive/';
    return baseUrl+url;
}

export function deepClone(source){
  return JSON.parse(JSON.stringify(source));
}
