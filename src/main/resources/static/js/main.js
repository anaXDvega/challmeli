function consultarIp(){
var valorIp = $("#inputIp").val();
console.log("consultarIp" + valorIp);
const url = 'http://localhost:8080/registryIp/'+ valorIp;
//190.173.136.0 argentina
async function fetchAPI(apiURL) {
  let response = await fetch(url);
  let data = await response.json();
  return data;
}
fetchAPI(url).then(data => {
    recargo();
    buildDataCountry(data);
  console.log('Toda la info: ', data);
}).catch(error => {console.log(error)});//en caso de algún error
  }
  function recargo(){
    $("#resultadoIp").empty();
  	$("#tbody").empty();
  	$("#thead").empty();
  	getData();
  }
  function buscoPromedios(){
  const url = '/generateStadistics';
      async function fetchAPI(apiURL) {
        let response = await fetch(url);
        let data = await response.json();
        return data;
      }
      fetchAPI(url).then(data => {
        console.log('Toda la info: ', data);
      }).catch(error => {console.log(error)});//en caso de algún error

  }
    function buildDataCountry(data){
    const tiempoTranscurrido = Date.now();
    const hoy = new Date(tiempoTranscurrido);
    var hora = hoy.getHours() + ':' + hoy.getMinutes() + ':' + hoy.getSeconds();
       $('#resultadoIp').append("<p>IP:"+data.ip+", fecha actual: "+hoy.toLocaleDateString() +" " + hora+"<br/> Pais: "+data.country+"<br/>ISO code: "+
       data.isoCode+"<br/> Idiomas: "+buildIdiomas(data.languages)+ " <br/> Moneda: "+ data.countryCurrencyCode +
       " (1 "+  getCoinToConvert(data.coinToConvert) +" = "+data.coin + getCountryCurrencyCode(data.countryCurrencyCode) +")<br/> Distancia estimada: "
       + data.distanceToBA + "kms (-34.687400817871094, -58.56330108642578) a ("+data.latitude+","+ data.longitude+ ")</p>");
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
console.log(languages.length);
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
            $('#tbody').append("<tr id="+data[i].ip+"><td>"+data[i].country+"-"+data[i].city+"</td><td>"+data[i].distance+"Kms</td><td>"+data[i].invocations+"</td></tr>");
           }
}
}
window.onload = getData();