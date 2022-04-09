function consultarIp(){
var valorIp = document.getElementById('inputIp');
console.log("consultarIp" + valorIp);
fetch('http://localhost:8080/registryIp/', valorIp)
      .then(response => response.json())
      .then(json => console.log(json))
}
function getData(){
    console.log("Funcion cargada al inicio");
       fetch('http://localhost:8080/findAll')
      .then(response => response.json())
      .then(json => console.log(json))
}

window.onload = getData();