document.addEventListener("DOMContentLoaded", validarFormulario());
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
    swal({
      title: "Rellene todos los datos por favor.",
      icon: "warning",
    });
  } else {
    // Obtener el valor del género seleccionado
    let gender = document.querySelector('input[name="genero"]:checked').value;

    let oUsuario = new User(name, lastName, u, pwd, gender);
    oUsuario.registrar();
    limpiarFormulario();
    ocultarFormulario();
    limpiarValidaciones();
  }
}

function ocultarFormulario() {
  document.getElementById("loginForm").style.display = "block";
  document.getElementById("registerForm").style.display = "none";
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

function limpiarValidaciones() {
  var inputs = document.querySelectorAll(".form-control");
  inputs.forEach(function (input) {
    input.classList.remove("is-valid", "is-invalid");
    var feedback = input.parentNode.querySelector(".invalid-feedback");
    if (feedback) {
      feedback.textContent = "";
    }
  });
}

function convertirCalificacionEnEstrellas(calificacion) {
  let estrellas = "";
  for (let i = 0; i < calificacion; i++) {
    estrellas += "★";
  }
  for (let i = calificacion; i < 5; i++) {
    estrellas += "☆";
  }
  return estrellas;
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

// Validación de formulario en tiempo real
function validarFormulario() {
  window.addEventListener("load", function () {
    var form = document.querySelector(".needs-validation");
    var contrasena = document.getElementById("contrasena");
    var contrasenaFeedback =
      contrasena.parentNode.querySelector(".invalid-feedback");
    var otraContrasena = document.getElementById("otraContrasena");
    var otraContrasenaFeedback =
      otraContrasena.parentNode.querySelector(".invalid-feedback");

    form.addEventListener(
      "submit",
      function (event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add("was-validated");

        // Ocultar íconos de validación después de enviar el formulario
        var inputs = document.querySelectorAll(".form-control");
        inputs.forEach(function (input) {
          input.classList.remove("is-valid", "is-invalid");
        });
      },
      false
    );

    var inputs = document.querySelectorAll(".form-control");
    inputs.forEach(function (input) {
      input.addEventListener(
        "input",
        function (event) {
          input.classList.remove("is-invalid", "is-valid");
          if (input.checkValidity()) {
            input.classList.add("is-valid");
            input.setCustomValidity(""); // Limpiar mensaje de error personalizado
          } else {
            input.classList.add("is-invalid");
          }
          // Específicamente para las contraseñas, llamar a la función de validación personalizada
          if (
            (input.id === "contrasena" || input.id === "otraContrasena") &&
            input.value !== ""
          ) {
            var resultado = validarRequisitosContrasena(input.value);
            if (resultado.esValida) {
              input.classList.remove("is-invalid");
              input.classList.add("is-valid");
              input.setCustomValidity(""); // Limpiar mensaje de error personalizado
              if (input.id === "contrasena") {
                contrasenaFeedback.textContent = "";
              } else {
                otraContrasenaFeedback.textContent = "";
              }
            } else {
              input.classList.remove("is-valid");
              input.classList.add("is-invalid");
              input.setCustomValidity(resultado.mensaje); // Establecer mensaje de error personalizado
              if (input.id === "contrasena") {
                contrasenaFeedback.textContent = resultado.mensaje;
              } else {
                otraContrasenaFeedback.textContent = resultado.mensaje;
              }
            }
            // Comprobación adicional para verificar si las contraseñas coinciden
            if (contrasena.value !== otraContrasena.value) {
              otraContrasena.classList.add("is-invalid");
              otraContrasenaFeedback.textContent =
                "Las contraseñas no coinciden";
            } else {
              otraContrasena.classList.remove("is-invalid");
              otraContrasenaFeedback.textContent = "";
            }
          }
          // Para los campos de nombre y apellidos, verificar que no estén vacíos
          if (
            (input.id === "nombre" || input.id === "apellidos") &&
            input.value !== ""
          ) {
            input.classList.remove("is-invalid");
            input.classList.add("is-valid");
            input.setCustomValidity(""); // Limpiar mensaje de error personalizado
          }
        },
        false
      );
    });
  });
}
