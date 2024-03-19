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
    alert("Todos los campos deben estar rellenos.");
  }
}

// Abre página de registro usuario
function abrirRegistro() {
  window.open("html/registro.html", "_self");
}

// Consulta en base de datos si el usuario existe
async function consultar(user, password) {
  try {
    const response = await fetch(
      `http://localhost:8080/user/login?user=${user}&password=${password}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    if (response.ok) {
      const userInfo = await response.json();

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
  } catch (error) {
    console.error("Error:", error.message);
  }
}
