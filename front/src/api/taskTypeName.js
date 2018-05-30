export function getTaskName(taskType) {
	switch (taskType){
		case 't_100':
			return ["Global","Input"]
			break;
		case 't_101':
			return ["Global","Select"]
			break;
		case 't_200':
			return ["Single-Frame","Input"]
			break;
		case 't_201':
			return ["Single-Frame","Select"]
			break;
		case 't_300':
			return ["Multiple-Frame","Input"]
			break;
		case 't_301':
			return ["Multiple-Frame","Select"]
			break;
		case 't_400':
			return ["Area","Only"]
			break;
		case 't_401':
			return ["Area","Input"]
			break;
		default:
			return;
	}
}

export function getTaskIdToServer(taskStringId) {
  return parseInt(taskStringId.split('_')[1]);

}
