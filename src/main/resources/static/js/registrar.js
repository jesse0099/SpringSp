$(document).ready(function() {
    //on ready
});

async function registrarUsuario(){
  let datos ={};

  datos.id = null;
  datos.nombre = document.getElementById('txtNombre').value;
  datos.apellido = document.getElementById('txtApellido').value;
  datos.telefono = null;
  datos.email = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;
  datos.roles = 'ROLE_SADMIN';
  datos.active = true;

  let repeatedPassword = document.getElementById('txtRepeatPassword').value;

  if(repeatedPassword !== datos.password){
     alert('Las contraseñas no coinciden');
     return;
  }

  const request = await fetch('api/usuarios', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
  const usuario = await request.json();

  console.log(usuario);

  window.location.href = "login.html";
}