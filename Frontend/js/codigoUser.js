const formulario = document.getElementsByClassName("principal")[0];
const botonRegistrar = document.getElementById("registrar");

let id;

document.getElementById("registrar").addEventListener("click", registraUsuario);
document
  .getElementById("generarUsuario")
  .addEventListener("click", generarUsuario);

function registraUsuario() {
  let name = document.getElementById("nombre").value;
  let lastName = document.getElementById("apellidos").value;
  let u = document.getElementById("usuario").value;
  let pwd = document.getElementById("contrasena").value;
  let otraPwd = document.getElementById("otraContrasena").value;

  if (name == "" || lastName == "" || u == "" || pwd == "" || otraPwd == "") {
    alert("Rellene todos los datos por favor.");
  } else {
    if (validarContrasena(pwd, otraPwd) == true) {
      let oUsuario = new User(name, lastName, u, pwd);
      oUsuario.registrar();
      limpiarFormulario();
    }
  }
}

function mostrarFormulario() {
  formulario.style.display = "block";
  listado.style.display = "none";
  listado.removeChild(listado.lastChild);
}

function limpiarFormulario() {
  document.getElementById("usuario").value = "";
  document.getElementById("contrasena").value = "";
  document.getElementById("otraContrasena").value = "";
  document.getElementById("apellidos").value = "";
  document.getElementById("nombre").value = "";
}

//GENERADOR DE USUARIO.
function generarUsuario() {
  let name = document.getElementById("nombre").value;
  let lastName = document.getElementById("apellidos").value;

  let u = name.substring(0, 1).toLowerCase();
  u += lastName.substring(0, 3).toLowerCase();
  u += lastName
    .substring(lastName.indexOf(" ") + 1, lastName.indexOf(" ") + 4)
    .toLowerCase();

  document.getElementById("usuario").value = u;
}

// Comprueba que las dos contraseñas cumplen los requisitos establecidos.
function validarContrasena(pwd, otraPwd) {
  let valida;

  if (otraPwd === pwd) {
    if (pwd.length >= 6 && pwd.length <= 12) {
      let encontradoAbc = buscarAbc(pwd);
      if (encontradoAbc == true) {
        let encontradoSimbolo = buscarSimbolo(pwd);
        if (encontradoSimbolo == true) {
          let encontradoNumero = buscarNumero(pwd);
          if (encontradoNumero == true) {
            valida = true;
          } else {
            valida = false;
            alert("Contraseña no válida, debe tener al menos un número");
          }
        } else {
          valida = false;
          alert(
            "Contraseña no válida, debe tener al menos uno de estos símbolos: !#$%&().:;"
          );
        }
      } else {
        valida = false;
        alert(
          "Contraseña no válida debe tener al menos una letra mayúscula o minúscula"
        );
      }
    } else {
      valida = false;
      alert(
        "Longitud contraseñas no cumple requisitos, debe estar entre 6 y 12 caracteres"
      );
    }
  } else {
    valida = false;
    alert("Contraseñas distintas");
  }
  return valida;
}

//FUNCIONES VALIDACION CONTRASEÑA
function buscarAbc(pwd) {
  let abc = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
  let i = 0;
  let encontrado = false;

  while (i < pwd.length && !encontrado) {
    encontrado = abc.includes(pwd[i]);
    i += 1;
  }

  return encontrado;
}

function buscarSimbolo(pwd) {
  let simbolos = "!#$%&().:;";
  let i = 0;
  let encontrado = false;

  while (i < pwd.length && !encontrado) {
    encontrado = simbolos.includes(pwd[i]);
    i += 1;
  }

  return encontrado;
}

function buscarNumero(pwd) {
  let numeros = "0123456789";
  let i = 0;
  let encontrado = false;

  while (i < pwd.length && !encontrado) {
    encontrado = numeros.includes(pwd[i]);
    i += 1;
  }

  return encontrado;
}
