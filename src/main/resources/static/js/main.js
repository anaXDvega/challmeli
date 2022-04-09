function consultarIp(){
var valorIp = $("#inputIp").val();
console.log("consultarIp" + valorIp);
const url = 'http://localhost:8080/registryIp/190.173.136.0';
async function fetchAPI(apiURL) {
  let response = await fetch(url);
  let data = await response.json();
  return data;
}
fetchAPI(url).then(data => {
    recargo();
    buildDataCountr(data);
  console.log('Toda la info: ', data);
}).catch(error => {console.log(error)});//en caso de algún error
  }
  function recargo(){
    $("#resultadoIp").empty();
  	$("#tbody").empty();
  	$("#thead").empty();
  	getData();
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
            $('#tbody').append("<tr id="+data[i].codCountry+"><td>"+data[i].country+"</td><td>"+data[i].distance+"</td><td>"+data[i].invocations+"</td></tr>");
           }
}
}
window.onload = getData();