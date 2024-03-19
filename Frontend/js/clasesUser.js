class User {
  name;
  lastName;
  user;
  password;
  userInfo;

  constructor(name, lastName, user, password) {
    this.name = name;
    this.lastName = lastName;
    this.user = user;
    this.password = password;
    this.userInfo = {};
  }

  setUserInfo(userInfo) {
    this.userInfo = userInfo;

    console.log(
      "Usuario creado: " + this.name,
      this.lastName,
      this.user,
      this.password
    );
  }

  //Metodo que muestra el listado de todos los libros.
  listadoLibros() {
    const oHttp = new XMLHttpRequest();

    console.log(this.userInfo);
    console.log("Userinfo: " + this.userInfo + " user : " + this.userInfo.id);
    if (!this.userInfo || !this.userInfo.user) {
      console.log("no se ha establecido informacion del usuarioo.");
      return;
    }

    const userId = this.userInfo.id;

    oHttp.open(
      "GET",
      "http://localhost:8080/building/getBooksByUserId?userId=" + userId
    );
    oHttp.setRequestHeader("Content-type", "application/json");
    oHttp.send();

    let tabla =
      "<h1 style='Text-align:center'>Listado libros leidos:</h1><br><br>";

    oHttp.onload = function () {
      console.log(oHttp.responseText);
      const posts = JSON.parse(oHttp.responseText);

      console.log(posts);
      tabla +=
        "<table id= 'tabla' class='table table-striped'><thead><tr><th scope='col'>Titulo</th><th scope='col'>Autor</th><th scope='col'>Genero</th><th scope='col'>Estado</th></tr></thead><tbody>";

      if (posts.length === 0) {
        tabla +=
          "<tr><td colspan='5'>No hay libros leídos por este usuario.</td></tr>";
      } else {
        posts.forEach((fila) => {
          console.log("Objeto fila: ", fila);
          tabla += "<tr>";
          tabla += "<td>" + fila.title + "</td>";
          tabla += "<td>" + fila.author + "</td>";
          tabla += "<td>" + fila.genre + "</td>";
          tabla += "<td>" + fila.status + "</td>";
          tabla +=
            "<td><button value='e" +
            fila.id +
            "' type='button' class='btn btn-danger fa-regular fa-trash-can'></button></td>";
          tabla +=
            "<td><button value='a" +
            fila.id +
            "' type='button' class='btn btn-success fa-solid fa-pencil'></button></td>";
          tabla += "</tr>";
        });
      }
      tabla += "</tbody></table>";

      document.getElementById("listado").innerHTML += tabla;
      document.getElementById("listado").style.display = "block";
    };
  }

  registrar() {
    const oHttp = new XMLHttpRequest();
    oHttp.open("POST", "http://localhost:8080/user/saveUser");
    oHttp.setRequestHeader("Content-type", "application/json");

    console.log("Estoy en registrar.");

    const userToRegister = {
      name: this.name,
      lastName: this.lastName,
      user: this.user,
      password: this.password,
    };

    console.log(userToRegister);

    oHttp.send(JSON.stringify(userToRegister));

    console.log("el json: " + JSON.stringify(userToRegister));
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
