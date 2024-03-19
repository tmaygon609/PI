class User {
  name;
  lastName;
  user;
  password;

  constructor(name, lastName, user, password) {
    this.name = name;
    this.lastName = lastName;
    this.user = user;
    this.password = password;
  }

  registrar() {
    const oHttp = new XMLHttpRequest();
    oHttp.open("PUT", "http://localhost:8080/user/saveUser");
    oHttp.setRequestHeader("Content-type", "application/json");
    oHttp.send(JSON.stringify(this));
    oHttp.onload = function () {
      if (oHttp.status == "200") {
        alert("Usuario registrado correctamente");
      } else {
        alert(
          "Error al registrar el usuario, contacte con el administrador de la app"
        );
      }
    };
  }
}
