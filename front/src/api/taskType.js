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
    {value:'100', label:'Global Mark'},
    {value:'101', label:'Global Describe'},
    {value:'200', label:'Single-Frame Mark'},
    {value:'201', label:'Single-Frame Describe'},
    {value:'300', label:'Multiple-Frame Mark'},
    {value:'301', label:'Multiple-Frame Describe'},
    {value:'400', label:'Area Mark'},
    {value:'401', label:'Area Describe'}
  ];
}
