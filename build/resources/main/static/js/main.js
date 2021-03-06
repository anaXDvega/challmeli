function consultarIp(){
var valorIp = $("#inputIp").val();
validateIp(valorIp);
if(validateIp(valorIp)){
const url = '/registryIp/'+ valorIp;
//190.173.136.0 argentina
async function fetchAPI(apiURL) {
  let response = await fetch(url);
  let data = await response.json();
  return data;
}
fetchAPI(url).then(data => {
    recargo();
    buildDataCountry(data);
    findAverage();
  console.log('Toda la info: ', data);
}).catch(error => {
alert(error)});//en caso de algún error
  }
else{
alert("ip incorrecta")//en caso de algún error
}
}  function recargo(){
    $("#resultadoIp").empty();
    $("#statistics").empty();
  	$("#tbody").empty();
  	$("#thead").empty();
  	getData();
  }
  function findAverage(){
  const url = '/generateStadistics';
      async function fetchAPI(apiURL) {
        let response = await fetch(url);
        let data = await response.json();
        return data;
      }
      fetchAPI(url).then(data => {
        console.log('Toda la info: ', data);
        buildAverague(data);
      }).catch(error => {alert(error)});//en caso de algún error

  }
  function buildAverague(data){
  console.log(data)
 $('#statistics').append("<p>Cantidad de invocaciones en total: "+data[0].cantInvocations+"<br/>Distancia mas lejana a buenos aires la cual se ha consultado el servicio: "
 +data[0].max +"<br/> Distancia mas cercana a buenos aires la cual se ha consultado el servicio: "+data[0].min+"<br/>Distancia promedio de todas las ejecuciones: "+
       data[0].average+"</p>");
  }

    function buildDataCountry(data){
    console.log(data);
    if(data.success=="true"){
    const tiempoTranscurrido = Date.now();
    const hoy = new Date(tiempoTranscurrido);
    var hora = hoy.getHours() + ':' + hoy.getMinutes() + ':' + hoy.getSeconds();
       $('#resultadoIp').append("<p>IP:"+data.ip+", fecha actual: "+hoy.toLocaleDateString() +" " + hora+"<br/> Pais: "+data.country+"<br/>ISO code: "+
       data.isoCode+"<br/> Idiomas: "+buildIdiomas(data.languages)+ " <br/> Moneda: "+ data.countryCurrencyCode +
       " (1 "+  getCoinToConvert(data.coinToConvert) +" = "+data.coin + getCountryCurrencyCode(data.countryCurrencyCode) +")<br/> Distancia estimada: "
       + data.distanceToBA + "kms (-34.687, -58.563) a ("+data.latitude+","+ data.longitude+ ")</p>");
       }else{
        $('#resultadoIp').append("<p>Codigo de error:"+data.code+"<br/> Info del error: "+data.info +"<br/> Modulo:" + data.module+"<br/> Tipo: "+data.type+"</p>");
       }
    }
 function getCoinToConvert(data){
 if(data=="USD"){return "EUR";}else{return data}
 }
 function getCountryCurrencyCode(data){
  if(data=="EUR"){return "USD";}else{return data}
  }
function buildIdiomas(languages){
let  retorna = "";
if(languages.length>0){
  for (var i = 0; i < languages.length; i++) {
        retorna += " "+languages[i].name+ "("+languages[i].code+") ";
           }
}
return retorna;
}
function getData(){
 $("#textOutData").hide();
    console.log("Funcion cargada al inicio");
    const url = '/findAll';
    async function fetchAPI(apiURL) {
      let response = await fetch(url);
      let data = await response.json();
      return data;
    }
    fetchAPI(url).then(data => {
       generarTabla(data);
      console.log('Toda la info: ', data);
    }).catch(error => {console.log(error)});//en caso de algún error
}
function generarTabla(data) {
console.log(data)
if(data.length==0){
      $("#textOutData").show();
}else{

    $("#textOutData").hide();
    var tr = document.createElement('tr');
    tr.setAttribute("placeholder", "Address Line " + data);
    var thead = document.getElementById("thead");
    thead.insertAdjacentElement("beforeend", tr);
       $('#thead').append("<tr><th>Pais</th><th>Distancia</th><th>Invocaciones</th></tr>");
       for (var i = 0; i < data.length; i++) {
            $('#tbody').append("<tr id="+data[i].ip+"><td>"+data[i].country+"-"+data[i].regionName+"</td><td>"+data[i].distance+"Kms</td><td>"+data[i].invocations+"</td></tr>");
           }
}
}
window.onload = getData();