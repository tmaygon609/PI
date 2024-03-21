class Book {
  title;
  author;
  genre;

  constructor(t, a, g, s, r, c) {
    this.title = t;
    this.author = a;
    this.genre = g;
    this.status = s;
    this.rate = r;
    this.comment = c;
  }

  // Método para obtener el usuario actual desde el localStorage
  obtenerUsuarioActual() {
    const storedUser = localStorage.getItem("usuarioActual");

    if (!storedUser) {
      console.error("No se ha encontrado la información del usuario.");
      return null;
    }

    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));
    return usuarioActual;
  }

  // Metodo que da de alta a un libro.
  async altaLibro() {
    const usuarioActual = this.obtenerUsuarioActual();
    console.log("usuarioActual", usuarioActual);

    if (!usuarioActual) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }

    let userId = usuarioActual.userInfo.id;
    console.log("userid ", usuarioActual.userInfo.id);

    const nuevoLibro = {
      title: this.title,
      author: this.author,
      genre: this.genre,
    };

    try {
      const resultado = await this.comprobarLibro(this.title);

      if (resultado.existe) {
        console.log("El libro ya existe. Solo se crea la relación.");

        //Confirmar por el usuario si quiere añadir el libro
        const confirmacion = confirm(
          `El libro ya existe, ¿quieres añadirlo a tu lista de libros?
          Título: ${this.title}
          Autor: ${this.author}
          Género: ${this.genre}`
        );

        if (confirmacion) {
          await this.crearRelacionUserBook(
            userId,
            resultado.libroId,
            this.status,
            this.rate,
            this.comment
          );
        }
      } else {
        console.log("El libro no existe. Creando nuevo libro y relación.");
        const libroId = await this.crearLibro(nuevoLibro);
        await this.crearRelacionUserBook(
          userId,
          libroId,
          this.status,
          this.rate,
          this.comment
        );

        swal({
          title: "Libro añadido correctamente",
          icon: "success",
        });
      }
    } catch (error) {
      console.error("Error al comprobar el libro", error);
    }
  }

  async comprobarLibro(title) {
    const response = await fetch(
      `http://localhost:8080/v1/books/searchByTitle?title=${encodeURIComponent(
        title
      )}`,
      { method: "GET" }
    );

    if (!response.ok) {
      throw new Error("Error al buscar el libro");
    }

    const libroEncontrado = await response.json();

    if (libroEncontrado.length > 0) {
      return { existe: true, libroId: libroEncontrado[0].id };
    } else {
      return { existe: false, libroId: null };
    }
  }

  async crearLibro(libro) {
    const response = await fetch("http://localhost:8080/v1/books/saveBook", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(libro),
    });

    if (!response.ok) {
      throw new Error("Error al crear el libro");
    }

    const libroCreado = await response.json();
    return libroCreado.id;
  }

  async crearRelacionUserBook(userId, libroId, status, rate, comment) {
    const response = await fetch(
      `http://localhost:8080/v1/books/addBookToUser?userId=${userId}&bookId=${libroId}&status=${status}&rate=${rate}&comment=${comment}`,
      {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
      }
    );

    if (!response.ok) {
      throw new Error("Error al crear la relación USER_BOOK");
    }
  }

  // Metodo para eliminar un libro.
  async eliminar(id) {
    try {
      const response = await fetch(`http://localhost:8080/v1/books/${id}`, {
        method: "DELETE",
        headers: {
          "Content-type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      });

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

  // Método que busca un libro por su titulo.
  async buscarLibro(title) {
    document.getElementById("listado").innerHTML = "";

    // const usuarioActual = this.obtenerUsuarioActual();
    // if (!usuarioActual) {
    //   console.error("No se ha encontrado la información del usuario.");
    //   return;
    // }
    // let userId = usuarioActual.userInfo.id;

    try {
      const response = await fetch(
        `http://localhost:8080/v1/books/searchByTitle?title=${encodeURIComponent(
          title
        )}`,
        { method: "GET" }
      );

      if (response.ok) {
        const libroEncontrado = await response.json();

        if (libroEncontrado.length > 0) {
          // Libro encontrado, mostrar listado
          let tabla =
            "<h1 style='Text-align:center'>Libro encontrado:</h1><br><br>";
          tabla +=
            "<table id= 'tabla' class='table table-striped'><thead><tr><th scope='col'>Titulo</th><th scope='col'>Autor</th><th scope='col'>Genero</th></tr></thead><tbody>";

          libroEncontrado.forEach((fila) => {
            tabla += "<tr>";
            tabla += "<td>" + fila.title + "</td>";
            tabla += "<td>" + fila.author + "</td>";
            tabla += "<td>" + fila.genre + "</td>";
            tabla += "</tr>";
          });

          tabla += "</tbody></table>";

          document.getElementById("listado").innerHTML += tabla;
          document.getElementById("listado").style.display = "block";
        } else {
          // No se encontró el libro
          swal({
            title: "Libro no encontrado.",
            icon: "error",
          });
        }
      } else {
        // Manejar otros casos de respuesta HTTP no exitosa
        console.error("Error al buscar el libro:", response.statusText);
        // Opcionalmente, mostrar un mensaje de error al usuario
        swal({
          title: "Error al buscar el libro.",
          text: "Por favor, inténtelo de nuevo más tarde.",
          icon: "error",
        });
      }
    } catch (error) {
      console.error("Error inesperado:", error);
      alert(
        "Ocurrio un error inesperado. Por favor, inténtelo de nuevo más tarde."
      );
    }
  }

  // Método que muestra el libro recomendado por la IA
  async listadoRecomendado() {
    document.getElementById("listado").innerHTML = "";

    const usuarioActual = this.obtenerUsuarioActual();
    if (!usuarioActual) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }

    let userId = usuarioActual.userInfo.id;

    // Crear objeto para enviar al servidor
    let requestData = {
      model: "gpt-3.5-turbo",
      messages: [{ role: "user", content: prompt }],
      temperature: 0.8,
    };

    try {
      const response = await fetch(
        `http://localhost:8080/v1/books/getBookRecommendation?userId=${userId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestData),
        }
      );

      if (response.ok) {
        const data = await response.json();

        if (Array.isArray(data.choices)) {
          const choices = data.choices;

          let tabla = `
              <h1 style='Text-align:center'>Libro recomendado: </h1><br><br>
              <table class='table'>
                <thead>
                  <tr>
                    <th scope='col'>Título</th>
                    <th scope='col'>Autor</th>
                  </tr>
                </thead>
                <tbody>
            `;

          choices.forEach((choice) => {
            const [title, author] = choice.message.content.split(", ");

            tabla += `
                <tr>
                  <td>${title}</td>
                  <td>${author}</td>
                </tr>
              `;
          });

          tabla += "</tbody></table>";

          document.getElementById("listado").innerHTML += tabla;
          document.getElementById("listado").style.display = "block";
        } else {
          console.error("La respuesta no contiene un array 'choices':", data);
          // Manejar este caso según sea necesario
        }
      } else {
        alert("Has llegado al límite de recomendaciones, prueba más tarde.");
        swal({
          title: "Has llegado al límite de recomendaciones",
          text: "Prueba más tarde",
          icon: "warning",
        });
      }
    } catch (error) {
      console.error("Error al obtener la recomendación:", error);
    }
  }
}
