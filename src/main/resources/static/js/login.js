$(document).ready(function() {
    //on ready
});

async function iniciarSesion(){
  let datos ={};

  datos.username = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;


  const request = await fetch('api/auth/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });

  const response = await request.text();

  console.log(response);

  if(response !=='{"jwt":"FAIL"}'){
      localStorage.token = response;
      localStorage.email = datos.email;
      window.location.href= "usuarios.html"
  }else{
    alert('Credenciales Incorrectas. Por favor, intente nuevamente');
  }
}