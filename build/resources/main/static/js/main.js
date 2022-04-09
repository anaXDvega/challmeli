function ola(){
console.log("consultarIp");
fetch('/registryIp/190.173.136.0')
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