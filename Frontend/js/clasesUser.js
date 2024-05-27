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

  // Método para actualizar el formulario con los datos del usuario
  actualizarFormularioInformacionAdmin() {
    const usuarioActual = JSON.parse(localStorage.getItem("usuarioActual"));

    if (usuarioActual) {
      // Actualizar los campos del formulario con los datos del usuario
      document.getElementById("nombreAdmin").value = usuarioActual.name;
      document.getElementById("apellidosAdmin").value = usuarioActual.lastName;
      document.getElementById("usuarioAdmin").value = usuarioActual.user;

      // Deshabilitar los campos del formulario
      document.getElementById("nombreAdmin").disabled = true;
      document.getElementById("apellidosAdmin").disabled = true;
      document.getElementById("usuarioAdmin").disabled = true;
    }
  }

  async listadoLibros(filter = "") {
    if (!this.userInfo || !this.userInfo.user) {
      console.log("No se ha establecido información del usuario.");
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

      // Filtrar los libros en función del filtro proporcionado
      const filteredPosts = posts.filter(
        (post) =>
          post.title.toLowerCase().includes(filter.toLowerCase()) ||
          post.author.toLowerCase().includes(filter.toLowerCase()) ||
          post.genre.toLowerCase().includes(filter.toLowerCase())
      );

      // Obtener los detalles de los libros filtrados
      const userBookDetailsPromises = filteredPosts.map(async (post) => {
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

      const userBookDetails = await Promise.all(userBookDetailsPromises);

      // Mostrar la tabla con los libros filtrados y sus detalles
      this.tablaLibros(filteredPosts, userBookDetails);
    } catch (error) {
      console.error("Error:", error);
    }
  }

  tablaLibros(posts, userBookDetails) {
    let tabla = `<div class="table-responsive">
        <table id="tabla" class="table table-striped table-hover">
            <thead>
                <tr>
                <th scope="col" data-field="title">Titulo</th>
                <th scope="col" data-field="author">Autor</th>
                <th scope="col" data-field="genre">Genero</th>
                <th scope="col" data-field="status">Estado</th>
                <th scope="col" data-field="rate">Calificación</th>
                <th scope="col" data-field="comment">Comentario</th>
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

    // Limpiar el contenido existente antes de agregar el nuevo contenido filtrado
    document.getElementById("listado").innerHTML = tabla;
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

  async listadoUsuarios(filter = "") {
    if (!this.userInfo || !this.userInfo.user) {
      console.log("No se ha establecido información del usuario.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/v1/users", {
        method: "GET",
        headers: {
          authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const users = await response.json();

      // Filtrar los usuarios en función del filtro proporcionado
      const filteredUsers = users.filter(
        (user) =>
          user.name.toLowerCase().includes(filter.toLowerCase()) ||
          user.lastName.toLowerCase().includes(filter.toLowerCase())
      );

      // Mostrar la tabla con los usuarios filtrados
      this.mostrarTablaUsuarios(filteredUsers);
    } catch (error) {
      console.error("Error:", error);
    }
  }

  mostrarTablaUsuarios(users) {
    let tabla = `<div class="table-responsive">
        <table id="tablaUsuarios" class="table table-striped table-hover">
            <thead>
                <tr>
                <th scope="col">Nombre</th>
                <th scope="col">Apellido</th>
                <th scope="col">Usuario</th>
                <th scope="col">Rol</th>               
                </tr>
            </thead>
            <tbody>`;

    if (users.length === 0) {
      tabla += `<tr>
                    <td colspan="5">No hay usuarios registrados.</td>
                </tr>`;
    } else {
      users.forEach((user) => {
        if (user.role !== "ADMIN") {
          tabla += `<tr>`;
          tabla += `<td id="nombre${user.id}">${user.name}</td>`;
          tabla += `<td id="apellido${user.id}">${user.lastName}</td>`;
          tabla += `<td>${user.user}</td>`;
          tabla += `<td>${user.role}</td>`;

          tabla += `<td><button value="e${user.id}" type="button" class="btn btn-danger fa-regular fa-trash-can"></button></td>`;
          tabla += `<td><button value="a${user.id}" type="button" class="btn btn-info fa-solid fa-pencil"></button></button></td>`;
          // Agrega más columnas según tus datos de usuario
          tabla += `</tr>`;
        }
      });
    }
    tabla += `</tbody></table></div>`;

    // Limpiar el contenido existente antes de agregar el nuevo contenido
    document.getElementById("listadoUsuarios").innerHTML = tabla;
    document.getElementById("listadoUsuarios").style.display = "block";
  }

  async listadoLibrosAdmin(filter = "") {
    if (!this.userInfo || !this.userInfo.user) {
      console.log("No se ha establecido información del usuario.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/v1/books/all`, {
        method: "GET",
        headers: {
          authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const books = await response.json();

      // Filtrar los libros en función del filtro proporcionado
      const filteredBooks = books.filter(
        (book) =>
          book.title.toLowerCase().includes(filter.toLowerCase()) ||
          book.author.toLowerCase().includes(filter.toLowerCase()) ||
          book.genre.toLowerCase().includes(filter.toLowerCase())
      );

      // Mostrar la tabla con los libros filtrados
      this.mostrarTablaLibrosAdmin(filteredBooks);
    } catch (error) {
      console.error("Error:", error);
    }
  }

  mostrarTablaLibrosAdmin(books) {
    let tabla = `<div class="table-responsive">
        <table id="tablaLibros" class="table table-striped table-hover">
            <thead>
                <tr>
                    <th scope="col" data-field="title">Título</th>
                    <th scope="col" data-field="author">Autor</th>
                    <th scope="col" data-field="genre">Género</th>
                </tr>
            </thead>
            <tbody>`;

    if (books.length === 0) {
      tabla += `<tr>
                <td colspan="4">No hay libros registrados para este usuario.</td>
            </tr>`;
    } else {
      books.forEach((book) => {
        tabla += `<tr>`;
        tabla += `<td id="titulo${book.id}">${book.title}</td>`;
        tabla += `<td id="autor${book.id}">${book.author}</td>`;
        tabla += `<td>${book.genre}</td>`;

        tabla += `<td><button value="e${book.id}" type="button" class="btn btn-danger fa-regular fa-trash-can"></button></td>`;
        tabla += `<td><button value="a${book.id}" type="button" class="btn btn-info fa-solid fa-pencil"></button></button></td>`;

        tabla += `</tr>`;
      });
    }
    tabla += `</tbody></table></div>`;

    // Limpiar el contenido existente antes de agregar el nuevo contenido filtrado
    document.getElementById("listadoLibrosAdmin").innerHTML = tabla;
    document.getElementById("listadoLibrosAdmin").style.display = "block";
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

      // Limpiar los campos del formulario
      document.getElementById("nuevaContrasena").value = "";
      document.getElementById("confirmarContrasena").value = "";
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

  // Método para eliminar un usuario por su ID
  async eliminarUsuario(id) {
    try {
      const response = await fetch(
        `http://localhost:8080/v1/users/delete/${id}`,
        {
          method: "DELETE",
          headers: {
            "Content-type": "application/json",
            "Access-Control-Allow-Origin": "*",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );

      if (response.ok) {
        swal({
          title: "Usuario eliminado correctamente",
          icon: "success",
        });

        // Eliminar la fila de la tabla
        let valorBoton = "e" + id;
        let filaEliminar = document
          .querySelector(`button[value='${valorBoton}']`)
          .closest("tr");
        filaEliminar.parentNode.removeChild(filaEliminar);
      } else {
        throw new Error(
          `Error al eliminar el usuario. Detalles: ${response.status} - ${response.statusText}`
        );
      }
    } catch (error) {
      alert(error.message);
    }
  }

  async guardarCambiosUsuario(id, nombre, apellido) {
    console.log("id", id);
    console.log("nombre", nombre);
    console.log("apellido", apellido);

    // URL del endpoint
    const url = `http://localhost:8080/v1/users/${id}`;

    // Datos que deseas enviar. Asegúrate de que estos coincidan con los parámetros esperados por tu endpoint.
    const data = {
      name: nombre,
      lastName: apellido,
    };

    // Opciones de la solicitud
    const requestOptions = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
      body: JSON.stringify(data),
    };

    try {
      const response = await fetch(url, requestOptions); // Utilizamos requestOptions aquí en lugar de requestData
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const responseData = await response.json(); // Cambiamos el nombre de la variable para evitar conflicto con la variable 'data' anterior
      console.log(responseData);
    } catch (error) {
      console.error("Error:", error);
    }
  }

  async eliminarLibro(id) {
    try {
      const response = await fetch(
        `http://localhost:8080/v1/books/delete/${id}`,
        {
          method: "DELETE",
          headers: {
            "Content-type": "application/json",
            "Access-Control-Allow-Origin": "*",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );

      if (response.ok) {
        swal({
          title: "Libro eliminado correctamente",
          icon: "success",
        });

        // Eliminar la fila de la tabla
        let valorBoton = "e" + id;
        let filaEliminar = document
          .querySelector(`button[value='${valorBoton}']`)
          .closest("tr");
        filaEliminar.parentNode.removeChild(filaEliminar);
      } else {
        throw new Error(
          `Error al eliminar el libro. Detalles: ${response.status} - ${response.statusText}`
        );
      }
    } catch (error) {
      alert(error.message);
    }
  }

  async guardarCambiosLibro(id, titulo, autor) {
    console.log("id", id);
    console.log("titulo", titulo);
    console.log("autor", autor);

    // URL del endpoint
    const url = `http://localhost:8080/v1/books/${id}`;

    // Datos que deseas enviar. Asegúrate de que estos coincidan con los parámetros esperados por tu endpoint.
    const data = {
      title: titulo,
      author: autor,
    };

    // Opciones de la solicitud
    const requestOptions = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
      body: JSON.stringify(data),
    };

    try {
      const response = await fetch(url, requestOptions);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const responseData = await response.json();
      console.log(responseData);
    } catch (error) {
      console.error("Error:", error);
    }
  }
}
