export function utc2bj(utcTime) {
  var T_pos = utcTime.indexOf('T');
  var Z_pos = utcTime.indexOf('Z');
  var year_month_day = utcTime.substr(0,T_pos);
  var hour_minute_second = utcTime.substr(T_pos+1,Z_pos-T_pos-1);
  var new_datetime = year_month_day+" "+hour_minute_second; // 2017-03-31 08:02:06

  // 处理成为时间戳
  var timestamp = new Date(Date.parse(new_datetime));
  timestamp = timestamp.getTime();
  timestamp = timestamp/1000;
  
  // 增加8个小时，北京时间比utc时间多八个时区
  timestamp = timestamp+8*60*60;
  
  // 时间戳转为时间
  const bjTime = timestampToTime(timestamp)
  
  return bjTime; //2021-5-9 8:00:00
}

function timestampToTime(timestamp) {
  var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
  var Y = date.getFullYear() + '-';
  var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
  var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
  var h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
  var m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
  var s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
  return Y+M+D+h+m+s;
}
