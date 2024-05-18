class User {
  name;
  lastName;
  user;
  password;
  gender;
  userInfo;

  constructor(name, lastName, user, password, gender) {
    this.name = name;
    this.lastName = lastName;
    this.user = user;
    this.password = password;
    this.gender = gender;
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

  // Método para actualizar el formulario con los datos del usuario
  actualizarFormularioInformacion() {
    const usuarioActual = JSON.parse(localStorage.getItem("usuarioActual"));

    if (usuarioActual) {
      // Actualizar los campos del formulario con los datos del usuario
      document.getElementById("nombre").value = usuarioActual.name;
      document.getElementById("apellidos").value = usuarioActual.lastName;
      document.getElementById("usuario").value = usuarioActual.user;

      // Deshabilitar los campos del formulario si es necesario
      // Puedes habilitar o deshabilitar según tus necesidades
      document.getElementById("nombre").disabled = true;
      document.getElementById("apellidos").disabled = true;
      document.getElementById("usuario").disabled = true;
    }
  }

  async listadoLibros() {
    if (!this.userInfo || !this.userInfo.user) {
      console.log("no se ha establecido informacion del usuarioo.");
      return;
    }

    const userId = this.userInfo.id;

    try {
      const response = await fetch(
        `http://localhost:8080/v1/books/users/${userId}`,

        {
          method: "GET",
          headers: {
            authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
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
            headers: {
              authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
            },
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
    let tabla = `<div class="table-responsive">
        <table id="tabla" class="table table-striped table-hover">
          <thead>
            <tr>
              <th scope="col">Titulo</th>
              <th scope="col">Autor</th>
              <th scope="col">Genero</th>
              <th scope="col">Estado</th>
              <th scope="col">Calificación</th>
              <th scope="col">Comentario</th>
            </tr>
          </thead>
          <tbody>`;

    if (posts.length === 0) {
      tabla += `<tr>
          <td colspan="5">No hay libros registrados para este usuario.</td>
        </tr>`;
    } else {
      posts.forEach((fila, index) => {
        console.log("Objeto fila: ", fila);
        tabla += `<tr>`;
        tabla += `<td>${fila.title}</td>`;
        tabla += `<td>${fila.author}</td>`;
        tabla += `<td>${fila.genre}</td>`;
        tabla += `<td id="estado${userBookDetails[index].id}">${userBookDetails[index].status}</td>`;
        tabla += `<td id="calificacion${
          userBookDetails[index].id
        }">${this.convertirCalificacionEnEstrellas(
          userBookDetails[index].rate
        )}</td>`;
        tabla += `<td id="comentario${userBookDetails[index].id}">${userBookDetails[index].comment}</td>`;
        tabla += `<td><button value="e${userBookDetails[index].id}" type="button" class="btn btn-danger fa-regular fa-trash-can"></button></td>`;
        tabla += `<td><button value="a${userBookDetails[index].id}" type="button" class="btn btn-info fa-solid fa-pencil"></button></td>`;
        tabla += `</tr>`;
      });
    }
    tabla += `</tbody></table></div>`;

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

  async registrar() {
    const userToRegister = {
      name: this.name,
      lastName: this.lastName,
      user: this.user,
      password: this.password,
      gender: this.gender,
    };

    console.log("gender", this.gender);

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

  // Función para enviar la solicitud PUT para cambiar la contraseña
  async cambiarContrasena(nuevaContrasena) {
    if (!this.userInfo || !this.userInfo.user) {
      console.log("No se ha establecido la información del usuario.");
      return;
    }

    const userId = this.userInfo.id;

    const changePasswordDTO = {
      newPassword: nuevaContrasena,
    };

    console.log(localStorage.getItem("jwtToken"));

    try {
      // Realizar la solicitud PUT al backend para cambiar la contraseña
      const response = await fetch(
        `http://localhost:8080/v1/users/changePassword/${userId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
          body: JSON.stringify(changePasswordDTO),
        }
      );

      if (!response.ok) {
        throw new Error("Error al cambiar la contraseña.");
      }

      // Mostrar mensaje de éxito
      swal({
        title: "Contraseña actualizada",
        text: "La contraseña ha sido cambiada correctamente.",
        icon: "success",
      });
    } catch (error) {
      console.error("Error al cambiar la contraseña:", error);
      swal({
        title: "Error",
        text: "Hubo un error al cambiar la contraseña. Por favor, inténtalo de nuevo más tarde.",
        icon: "error",
      });
    }
  }

  async eliminarCuenta() {
    if (!this.userInfo || !this.userInfo.user) {
      console.log("No se ha establecido la información del usuario.");
      return;
    }

    const userId = this.userInfo.id;

    try {
      // Realizar la solicitud PUT al backend para cambiar la contraseña
      const response = await fetch(
        `http://localhost:8080/v1/users/delete/${userId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Error al eliminar el usuario.");
      }

      // Mostrar mensaje de éxito
      swal({
        title: "Cuenta eliminada",
        text: "La cuenta ha sido eliminada correctamente.",
        icon: "success",
      });
    } catch (error) {
      console.error("Error al borrar el usuario", error);
      swal({
        title: "Error",
        text: "Hubo un error al eliminar el usuario. Por favor, inténtalo de nuevo más tarde.",
        icon: "error",
      });
    }
  }
}
