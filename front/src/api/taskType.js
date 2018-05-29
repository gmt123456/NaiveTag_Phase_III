export function convertTypeToString(taskType) {
  switch (taskType) {
    case 100:
      return "整体标"
      break;
    case 101:
      return "整体注"
      break;
    case 200:
      return "单框标"
      break;
    case 201:
      return "单框注"
      break;
    case 300:
      return "多框标"
      break;
    case 301:
      return "多框注"
      break;
    case 400:
      return "边界注"
      break;
    case 401:
      return "边界标注"
      break;
    default:
      break;
  }
}

export function needLabel(type) {
  return type.charAt(2) !== '1' && type.indexOf('4') === -1;
}


export function getAllTypes() {

  return [
    {value:'100', label:'aaa'},
    {value:'101', label:'bbb'},
    {value:'200', label:'ccc'},
    {value:'201', label:'ddd'},
    {value:'300', label:'eee'},
    {value:'301', label:'fff'},
    {value:'400', label:'ggg'},
    {value:'401', label:'hhh'}
  ];
}
