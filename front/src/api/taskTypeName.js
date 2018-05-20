export function getTaskName(taskType) {
	switch (taskType){
		case 100:
			return ["Global","Input"]
			break;
		case 101:
			return ["Global","Select"]
			break;
		case 200:
			return ["Single-Frame","Input"]
			break;
		case 201:
			return ["Single-Frame","Select"]
			break;
		case 300:
			return ["Multiple-Frame","Input"]
			break;
		case 301:
			return ["Multiple-Frame","Select"]
			break;
		case 400:
			return ["Area","Only"]
			break;
		case 401:
			return ["Area","Input"]
			break;
		default:
			return;
	}
}