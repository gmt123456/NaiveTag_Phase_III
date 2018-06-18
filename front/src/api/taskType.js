export function convertTypeToString(taskType) {
  let types = getAllTypes();
  for (let i in types) {


    if (taskType.charAt(0) === 't') {
      taskType = taskType.substr(2);
    }
    if (types[i].value === taskType) {
      return types[i].label;
    }
  }
}

export function needLabel(type) {
  return type.charAt(2) !== '1' && type.indexOf('4') === -1;
}

export function getTypes() {
  let types = getAllTypes();
  let res=[];
  for (let key in types){
    res.push(types[key].label);
  }

  return res;
}


export function getAllTypes() {

  return [
    {value: '100', label: 'Global Mark'},
    {value: '101', label: 'Global Describe'},
    {value: '200', label: 'Single-Frame Mark'},
    {value: '201', label: 'Single-Frame Describe'},
    {value: '300', label: 'Multiple-Frame Mark'},
    {value: '301', label: 'Multiple-Frame Describe'},
    {value: '400', label: 'Area Mark'},
    {value: '401', label: 'Area Describe'}
  ];
}
