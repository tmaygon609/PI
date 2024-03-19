document.getElementById("btn-entrar").addEventListener("click", abrirPrincipal);
document
  .getElementById("enlaceRegistrar")
  .addEventListener("click", function () {
    // Aquí puedes agregar cualquier lógica adicional que necesites para manejar el registro
    document.getElementById("registerForm").style.display = "block";
    document.getElementById("loginForm").style.display = "none";
  });

document.getElementById("volverLogin").addEventListener("click", function () {
  // Aquí puedes agregar cualquier lógica adicional que necesites para manejar el retorno al inicio de sesión
  document.getElementById("loginForm").style.display = "block";
  document.getElementById("registerForm").style.display = "none";
});

// Abre la página principal
function abrirPrincipal() {
  const user = document.getElementById("user").value;
  const password = document.getElementById("pwd").value;

  if (user && password) {
    consultar(user, password);
  } else {
    swal({
      title: "Todos los campos deben estar rellenos.",
      icon: "warning",
    });
  }
}

// Abre página de registro usuario
function abrirRegistro() {
  window.open("html/registro.html", "_self");
}

// Consulta en base de datos si el usuario existe
async function consultar(user, password) {
  const oHttp = new XMLHttpRequest();

  oHttp.open(
    "GET",
    "http://localhost:8080/user/login?user=" + user + "&password=" + password
  );
  oHttp.send();

  oHttp.onload = () => {
    if (oHttp.status == "200" && oHttp.responseText != "") {
      const userInfo = JSON.parse(oHttp.responseText);

      console.log("Usuario obtenido:", userInfo);

      // Crear una instancia de User solo con el nombre de usuario
      const usuarioActual = new User(
        userInfo.name,
        userInfo.lastName,
        userInfo.user,
        userInfo.password
      );
      usuarioActual.setUserInfo(userInfo);

      // Almacena la información del usuario en el localStorage
      localStorage.setItem("usuarioActual", JSON.stringify(usuarioActual));

      window.open("html/principal.html", "_self");
    } else {
      swal({
        title: "Usuario no registrado.",
        icon: "error",
      });
      document.getElementById("user").value = "";
      document.getElementById("pwd").value = "";
    }
  };
}
