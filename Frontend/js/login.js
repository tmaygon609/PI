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
  limpiarFormulario();
  limpiarValidaciones();
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

  oHttp.open("POST", "http://localhost:8080/api/v1/auth/signin");
  oHttp.setRequestHeader("Content-Type", "application/json");

  const data = {
    user: user,
    password: password,
  };

  oHttp.send(JSON.stringify(data));

  oHttp.onload = () => {
    if (oHttp.status == "200" && oHttp.responseText != "") {
      const response = JSON.parse(oHttp.responseText);
      const jwtToken = response.accessToken;
      const userInfo = response.user;

      console.log("token:", jwtToken);
      console.log("Usuario obtenido:", userInfo);

      localStorage.setItem("jwtToken", jwtToken);

      // Crear una instancia de User con la información actualizada
      const usuarioActual = new User(
        userInfo.name,
        userInfo.lastName,
        userInfo.user,
        userInfo.password
      );

      usuarioActual.setUserInfo(userInfo);

      // Almacena la información del usuario en el localStorage
      localStorage.setItem("usuarioActual", JSON.stringify(usuarioActual));

      window.location.href = "principal.html";
    } else {
      swal({
        title: "Usuario no registrado.",
        icon: "error",
      });
      document.getElementById("user").value = "";
      document.getElementById("pwd").value = "";
      limpiarValidaciones();
    }
  };
}
