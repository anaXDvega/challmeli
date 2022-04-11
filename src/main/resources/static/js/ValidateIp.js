function validateIp(ip) {
  var patronIp = new RegExp("^([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3})$");
  var valores;
  if(ip.search(patronIp) !== 0) {
    return false
  }

  valores = ip.split(".");

  return valores[0] <= 255 && valores[1] <= 255 && valores[2] <= 255 && valores[3] <= 255
}