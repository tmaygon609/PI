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
        `http://localhost:8080/v1/books/getBooksByUserId?userId=${userId}`,
        {
          method: "GET",
        }
      );
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const posts = await response.json();

      let bookId = null;
      for (let post of posts) {
        bookId = post.id;
      }

      // Crea un array de promesas, para cada detalle de libro del usuario.
      const userBookDetailsPromises = posts.map(async (post) => {
        const userResponse = await fetch(
          `http://localhost:8080/v1/usersBooks/getUserDetails?userId=${userId}&bookId=${post.id}`,
          {
            method: "GET",
          }
        );
        if (!userResponse.ok) {
          throw new Error(`HTTP error! status: ${userResponse.status}`);
        }
        return userResponse.json();
      });

      // Espera a que todas las promesas se resuelvan.
      const userBookDetails = await Promise.all(userBookDetailsPromises);

      this.tablaLibros(posts, userBookDetails);
    } catch (error) {
      console.error("Error:", error);
    }
  }

  tablaLibros(posts, userBookDetails) {
    let tabla =
      "<table id= 'tabla' class='table table-striped'><thead><tr><th scope='col'>Titulo</th><th scope='col'>Autor</th><th scope='col'>Genero</th><th scope='col'>Estado</th><th scope='col'>Calificación</th><th scope='col'>Comentario</th></tr></thead><tbody>";

    if (posts.length === 0) {
      tabla +=
        "<tr><td colspan='5'>No hay libros leídos por este usuario.</td></tr>";
    } else {
      posts.forEach((fila, index) => {
        console.log("Objeto fila: ", fila);
        tabla += "<tr>";
        tabla += "<td>" + fila.title + "</td>";
        tabla += "<td>" + fila.author + "</td>";
        tabla += "<td>" + fila.genre + "</td>";
        tabla += "<td>" + userBookDetails[index].status + "</td>";
        tabla +=
          "<td>" +
          this.convertirCalificacionEnEstrellas(userBookDetails[index].rate) +
          "</td>";
        tabla += "<td>" + userBookDetails[index].comment + "</td>";
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

  convertirCalificacionEnEstrellas(calificacion) {
    let estrellas = "";
    for (let i = 0; i < calificacion; i++) {
      estrellas += "★";
    }
    for (let i = calificacion; i < 5; i++) {
      estrellas += "☆";
    }
    return estrellas;
  }

  // convertirCalificacionEnEstrellas(calificacion) {
  //   let estrellas = "";
  //   let estrellaLlena = "<img src='../img/estrellaLlena.png'>";
  //   let estrellaVacia = "<img src='../img/estrellaVacia.png'>";
  //   for (let i = 0; i < calificacion; i++) {
  //     estrellas += estrellaLlena;
  //   }
  //   for (let i = calificacion; i < 5; i++) {
  //     estrellas += estrellaVacia;
  //   }
  //   return estrellas;
  // }

  async registrar() {
    const userToRegister = {
      name: this.name,
      lastName: this.lastName,
      user: this.user,
      password: this.password,
    };

    try {
      const response = await fetch("http://localhost:8080/v1/users/saveUser", {
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
        icon: "error",
      });
      console.error("Error:", error);
    }
  }
}
