export function getDivision(division) {

  switch (division.toLocaleString().toLocaleLowerCase()){
    case 'expert':
      return "/static/grade/expert.png";
    case 'contributor':
      return '/static/grade/contributor.png';
    case 'novice':
      return '/static/grade/novice.png';
    case 'master':
      return '/static/grade/master.png';
    case 'grandmaster':
      return '/static/grade/grandmaster.png';

  }
}
