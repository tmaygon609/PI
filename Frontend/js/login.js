document.getElementById("btn-entrar").addEventListener("click", abrirPrincipal);
document
  .getElementById("enlaceRegistrar")
  .addEventListener("click", abrirRegistro);

// Abre la página principal
function abrirPrincipal() {
  let user = document.getElementById("user").value;
  let password = document.getElementById("pwd").value;

  if (user && password) {
    consultar(user, password);
  } else {
    alert("Todos los campos deben estar rellenos.");
  }
}

// Abre página de registro usuario
function abrirRegistro() {
  window.open("html/registro.html", "_self");
}

// Consulta en base de datos si el usuario existe
function consultar(user, password) {
  const oHttp = new XMLHttpRequest();

  oHttp.open(
    "GET",
    "http://localhost:8080/user/login?user=" + user + "&password=" + password
  );
  oHttp.setRequestHeader("Content-type", "application/json");
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
      alert("Usuario no registrado.");
      document.getElementById("user").value = "";
      document.getElementById("pwd").value = "";
    }
  };
}
