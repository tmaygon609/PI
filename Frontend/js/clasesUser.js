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

  async listadoLibros() {
    if (!this.userInfo || !this.userInfo.user) {
      console.log("no se ha establecido informacion del usuarioo.");
      return;
    }

    const userId = this.userInfo.id;

    try {
      const response = await fetch(
        `http://localhost:8080/building/getBooksByUserId?userId=${userId}`,
        {
          method: "GET",
        }
      );
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const posts = await response.json();

      this.tablaLibros(posts);
    } catch (error) {
      console.error("Error:", error);
    }
  }

  tablaLibros(posts) {
    let tabla =
      "<h1 style='Text-align:center'>Listado libros leidos:</h1><br><br>";
    tabla +=
      "<table id= 'tabla' class='table table-striped'><thead><tr><th scope='col'>Titulo</th><th scope='col'>Autor</th><th scope='col'>Genero</th></tr></thead><tbody>";

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
  }

  async registrar() {
    const userToRegister = {
      name: this.name,
      lastName: this.lastName,
      user: this.user,
      password: this.password,
    };

    try {
      const response = await fetch("http://localhost:8080/user/saveUser", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(userToRegister),
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      swal({
        title: "Usuario registrado correctamente",
        icon: "success",
      });
    } catch (error) {
      swal({
        title: "Error al registrar el usuario",
        text: "Contacte con el administrador de la app",
        icon: "success",
      });
      console.error("Error:", error);
    }
  }
}
