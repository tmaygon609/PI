// Método para validar si la contraseña cumple con los requisitos establecidos
function validarRequisitosContrasena(pwd) {
  let resultado = {
    esValida: false,
    mensaje: "",
  };

  if (pwd.length <= 6 || pwd.length > 12) {
    resultado.mensaje =
      "La longitud de la contraseña debe estar entre 6 y 12 caracteres";
  } else if (!buscarAbc(pwd)) {
    resultado.mensaje =
      "La contraseña debe tener al menos una letra mayúscula o minúscula";
  } else if (!buscarSimbolo(pwd)) {
    resultado.mensaje =
      "La contraseña debe tener al menos uno de estos símbolos: !#$%&().:;";
  } else if (!buscarNumero(pwd)) {
    resultado.mensaje = "La contraseña debe tener al menos un número";
  } else {
    resultado.esValida = true;
  }

  return resultado;
}

// Método auxiliar para buscar si la contraseña contiene alguna letra del alfabeto
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

// Método auxiliar para buscar si la contraseña contiene algún símbolo especial
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

// Método auxiliar para buscar si la contraseña contiene algún número
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
