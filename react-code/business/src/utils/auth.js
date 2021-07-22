//存放信息
export function curEnt(name){
  return getCurEnt(name)
}
export function isCurEnt(name,flag){
  if (flag!==undefined) {
    setCurEnt(name,flag)
  }
}
export function clearEnt(name){
  localStorage.removeItem(name);
}

function getCurEnt(name){
  return JSON.parse(localStorage.getItem(name));
}
function setCurEnt(name,flag){
  localStorage.setItem(name,JSON.stringify(flag));
}
