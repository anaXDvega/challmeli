function consultarIp(){
var valorIp = document.getElementById('inputIp').value;
console.log("consultarIp" + valorIp);
const url = 'http://localhost:8080/registryIp/190.173.136.0';
async function fetchAPI(apiURL) {
  let response = await fetch(url);
  let data = await response.json();
  return data;
}
fetchAPI(url).then(data => {
  console.log('Toda la info: ', data);
}).catch(error => {console.log(error)});//en caso de algún error
  }
function getData(){
    console.log("Funcion cargada al inicio");
    const url = '/findAll';
    async function fetchAPI(apiURL) {
      let response = await fetch(url);
      let data = await response.json();
      return data;
    }
    fetchAPI(url).then(data => {
      console.log('Toda la info: ', data);
    }).catch(error => {console.log(error)});//en caso de algún error
}

window.onload = getData();