class Book {
  title;
  author;
  genre;

  constructor(t, a, g, s, r, c, i) {
    this.title = t;
    this.author = a;
    this.genre = g;
    this.status = s;
    this.rate = r;
    this.comment = c;
    this.imagen = i;
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
  async altaLibro(imagen) {
    const usuarioActual = this.obtenerUsuarioActual();

    if (!usuarioActual) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }

    let userId = usuarioActual.userInfo.id;

    const reader = new FileReader();
    reader.onload = async (event) => {
      try {
        const imagenData = event.target.result;
        const bytesImagen = Array.from(new Uint8Array(imagenData));

        const nuevoLibro = {
          title: this.title,
          author: this.author,
          genre: this.genre,
          imagenBytes: bytesImagen,
        };

        const resultado = await this.comprobarLibro(this.title);

        if (resultado.existe) {
          const confirmacion = await swal({
            title: "El libro ya existe",
            text: `¿Quieres añadirlo a tu lista de libros?
            Título: ${resultado.libro.title}
            Autor: ${resultado.libro.author}
            Género: ${resultado.libro.genre}`,
            icon: "info",
            buttons: ["Cancelar", "Aceptar"],
          });

          if (confirmacion) {
            await this.crearRelacionUserBook(
              userId,
              resultado.libro.id,
              this.status,
              this.rate,
              this.comment
            );
          }
        } else {
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
        console.error("Error al procesar el libro:", error);
      }
    };

    reader.readAsArrayBuffer(imagen);
  }

  // Método para comprobar si un libro ya existe por título.
  async comprobarLibro(title) {
    const response = await fetch(
      `http://localhost:8080/v1/books/searchByTitle?title=${encodeURIComponent(
        title
      )}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`, // Aquí es donde incluyes el token en la cabecera de la petición
        },
      }
    );

    if (!response.ok) {
      throw new Error("Error al buscar el libro");
    }

    const libroEncontrado = await response.json();

    if (libroEncontrado.length > 0) {
      return { existe: true, libro: libroEncontrado[0] };
    } else {
      return { existe: false, libro: null };
    }
  }

  // Método para crear un nuevo libro en la base de datos.
  async crearLibro(nuevoLibro) {
    try {
      const response = await fetch("http://localhost:8080/v1/books", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
        body: JSON.stringify(nuevoLibro),
      });

      if (!response.ok) {
        const errorDetails = await response.text();
        throw new Error("Error al crear el libro: " + errorDetails);
      }

      const libroCreado = await response.json();
      return libroCreado.id;
    } catch (error) {
      console.error("Error al crear el libro:", error);
      throw new Error("Error al crear el libro: " + error.message);
    }
  }

  // Método para crear una relación entre usuario y libro.
  async crearRelacionUserBook(userId, libroId, status, rate, comment) {
    const userBook = {
      user: { id: userId },
      book: { id: libroId },
      status: status,
      rate: rate,
      comment: comment,
    };

    const response = await fetch(
      `http://localhost:8080/v1/books/addBookToUser`,
      {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
        body: JSON.stringify(userBook),
      }
    );

    if (!response.ok) {
      throw new Error("Error al crear la relación USER_BOOK");
    }
  }

  // Metodo para eliminar un libro.
  async eliminar(id) {
    try {
      const response = await fetch(
        `http://localhost:8080/v1/usersBooks/${id}`,
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

  // Método que busca un libro por su titulo.
  async buscarLibro(title) {
    const usuarioActual = this.obtenerUsuarioActual();
    if (!usuarioActual) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }
    // let userId = usuarioActual.userInfo.id;

    try {
      const response = await fetch(
        `http://localhost:8080/v1/books/searchByTitle?title=${encodeURIComponent(
          title
        )}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`, // Aquí es donde incluyes el token en la cabecera de la petición
          },
        }
      );

      if (response.ok) {
        const libroEncontrado = await response.json();

        if (libroEncontrado.length > 0) {
          // Libro encontrado, mostrar listado
          let contenido = "<div class='container'>"; // Envuelve el contenido en un contenedor

          libroEncontrado.forEach((fila) => {
            contenido += "<div class='row mb-4'>"; // Crea una fila con margen para espaciado
            contenido += "<div class='col-md-2'>"; // Columna de imagen (4 columnas en dispositivos medianos)
            contenido +=
              "<img src='data:image/jpeg;base64," +
              fila.imagenBytes +
              "' class='img-fluid'>";
            contenido += "</div>";

            contenido +=
              "<div class='col-md-10 d-flex flex-column justify-content-center'>"; // Columna de texto (8 columnas en dispositivos medianos)
            contenido += "<p><strong>Título:</strong> " + fila.title + "</p>";
            contenido += "<p><strong>Autor:</strong> " + fila.author + "</p>";
            contenido += "<p><strong>Género:</strong> " + fila.genre + "</p>";
            contenido += "</div>";

            contenido += "</div><br>"; // Cierra la fila
          });

          contenido += "</div>"; // Cierra el contenedor

          return contenido;
        } else {
          // No se encontró el libro
          swal({
            title: "Libro no encontrado.",
            icon: "error",
          }).then(() => {
            usuarioActual.listadoLibros();
          });
          document.getElementById("txtTituloLibro").value = "";
        }
      } else {
        console.error("Error al buscar el libro:", response.statusText);
        // Opcionalmente, mostrar un mensaje de error al usuario
        swal({
          title: "Error al buscar el libro.",
          text: "Por favor, inténtelo de nuevo más tarde.",
          icon: "error",
        });
      }
    } catch (error) {
      console.error("Error inesperado intentalo de nuevo:", error);
    }
  }

  // Método que muestra el libro recomendado por la IA
  async listadoRecomendado() {
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
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
          body: JSON.stringify(requestData),
        }
      );

      if (response.ok) {
        const data = await response.json();

        if (Array.isArray(data.choices)) {
          const choices = data.choices;

          let tabla = `
              
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

          return tabla;
        } else {
          console.error("La respuesta no contiene un array 'choices':", data);
        }
      } else {
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

  // Método que carga el catálogo de libros.
  async catalogo() {
    try {
      // Realizar la petición para obtener los detalles de los libros
      const response = await fetch("http://localhost:8080/v1/books", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      const booksPage = await response.json(); // Obtener la información de paginación y los libros

      // Mostrar los libros de la primera página
      await this.mostrarLibros(booksPage);
    } catch (error) {
      console.error("Error al cargar los detalles de los libros:", error);
    }
  }

  // Método para cargar libros filtrados por género
  async cargarLibrosPorGenero(genre) {
    try {
      // Realizar la petición para obtener los libros filtrados por género
      const response = await fetch(
        `http://localhost:8080/v1/books/searchByGenre?genre=${genre}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`, // Incluir el token en la cabecera de la petición
          },
        }
      );

      const booksPage = await response.json(); // Obtener la información de paginación y los libros

      // Mostrar los libros filtrados por género utilizando el método mostrarLibros existente
      await this.mostrarLibros(booksPage);
    } catch (error) {
      console.error("Error al cargar los libros por género:", error);
    }
  }

  // Método para mostrar los detalles de los libros.
  async mostrarLibros(booksPage) {
    try {
      const books = booksPage.content; // Obtener la lista de libros

      // Selecciona el contenedor donde se cargarán los detalles de los libros
      const bookDetailsContainer = document.getElementById(
        "book-details-container"
      );

      // Limpia el contenedor antes de agregar nuevos libros
      bookDetailsContainer.innerHTML = "";

      // Itera sobre los libros y crear los elementos HTML correspondientes
      books.forEach((book) => {
        const listItem = document.createElement("div");
        listItem.classList.add("list-group-item");

        const bookItem = document.createElement("div");
        bookItem.classList.add("book-item");

        let base64String = book.imagenBytes;
        let binaryString = window.atob(base64String);
        let len = binaryString.length;
        let bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
          bytes[i] = binaryString.charCodeAt(i);
        }

        let blob = new Blob([bytes.buffer]);

        let imgUrl = URL.createObjectURL(blob);

        const bookCover = document.createElement("img");
        bookCover.src = imgUrl;
        bookCover.alt = "Portada del libro";
        bookCover.classList.add("book-cover");

        const bookInfo = document.createElement("div");
        bookInfo.classList.add("book-info");

        const title = document.createElement("h4");
        title.classList.add("list-group-item-heading");
        title.textContent = `${book.title}`;

        const author = document.createElement("p");
        author.classList.add("list-group-item-text");
        author.innerHTML = `<strong>Autor: </strong>${book.author}<br/>`;

        const genre = document.createElement("p");
        genre.classList.add("list-group-item-text");
        genre.innerHTML = `<strong>Género: </strong>${book.genre}<br/><br/>`;

        // Agrega los enlaces de acción
        const actionLinks = document.createElement("p");
        actionLinks.classList.add("list-group-item-text");
        actionLinks.innerHTML = `
          <a value="n${book.id}" class="btn btn-success add-book-button" title="Añadir libro"><i class="fa fa-plus"></i></a>
        `;

        // Agregar el evento click al botón de añadir libro
        const addButton = actionLinks.querySelector(".add-book-button");
        addButton.addEventListener("click", async () => {
          // Obtener el ID del libro
          const libroId = book.id;

          const usuarioActual = this.obtenerUsuarioActual();
          if (!usuarioActual) {
            console.error("No se ha encontrado la información del usuario.");
            return;
          }

          let userId = usuarioActual.userInfo.id;

          try {
            const libroExistente = await this.comprobarLibroUsuario(
              book.id,
              userId
            );

            if (libroExistente.existe) {
              swal({
                title: `El libro ${book.title} ya está en tu lista.`,
                icon: "warning",
              });
            } else {
              this.crearRelacionUserBook(
                userId,
                libroId,
                "Sin empezar",
                "",
                ""
              );
              swal({
                title: "Libro añadido a tu lista.",
                text: "",
                icon: "success",
              });
            }
          } catch (error) {
            console.error("Error al crear la relación usuario-libro:", error);
            swal("Error al añadir el libro", "", "error");
          }
        });

        // Agregar los elementos al contenedor principal
        bookInfo.appendChild(title);
        bookInfo.appendChild(author);
        bookInfo.appendChild(genre);
        bookInfo.appendChild(actionLinks); // Aquí se agrega el botón de añadir libro

        bookItem.appendChild(bookCover);
        bookItem.appendChild(bookInfo);

        listItem.appendChild(bookItem);
        bookDetailsContainer.appendChild(listItem);
      });

      // Código para manejar la paginación
      const paginationContainer = document.getElementById("pagination");
      paginationContainer.innerHTML = ""; // Limpiar el contenedor antes de agregar los elementos de paginación

      for (let i = 0; i < booksPage.totalPages; i++) {
        const pageItem = document.createElement("li");
        pageItem.classList.add("pagination-item");
        if (i === booksPage.number) {
          pageItem.classList.add("active");
        }

        const pageLink = document.createElement("a");
        pageLink.href = "javascript:void(0)";
        pageLink.textContent = i + 1;

        pageLink.addEventListener("click", async () => {
          await this.cargarLibros(i); // Función para cargar libros de la página seleccionada
        });

        pageItem.appendChild(pageLink);
        paginationContainer.appendChild(pageItem);
      }
    } catch (error) {
      console.error("Error al mostrar los detalles de los libros:", error);
    }
  }

  // Método para cargar los libros de una página específica.
  async cargarLibros(pageNumber, genre = null) {
    try {
      let url;
      if (genre) {
        url = `http://localhost:8080/v1/books/searchByGenre?genre=${genre}&page=${pageNumber}&size=5`;
      } else {
        url = `http://localhost:8080/v1/books?page=${pageNumber}&size=5`;
      }

      const response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });

      const booksPage = await response.json();

      // Identificar si la llamada fue para libros por género
      const fromGenreSearch = !!genre;

      // Mostrar los libros de acuerdo al origen de la búsqueda
      await this.mostrarLibros(booksPage, fromGenreSearch);
    } catch (error) {
      console.error("Error al cargar los libros:", error);
    }
  }

  // Método para comprobar si un usuario ya tiene un libro en su lista.
  async comprobarLibroUsuario(bookId, userId) {
    try {
      // Realizar una petición para buscar el libro por título y usuario
      const response = await fetch(
        `http://localhost:8080/v1/usersBooks/searchByBookIdAndUserId?bookId=${bookId}&userId=${userId}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );

      // Verificar si la respuesta es exitosa
      if (!response.ok) {
        throw new Error("Error al buscar el libro");
      }

      // Convertir la respuesta a formato JSON
      const libroEncontrado = await response.json();

      // Verificar si se encontró algún libro
      if (libroEncontrado.length > 0) {
        // Si se encontró al menos un libro, devuelve que existe y el primer libro encontrado
        return { existe: true, libro: libroEncontrado[0] };
      } else {
        // Si no se encontró ningún libro, devuelve que no existe y null
        return { existe: false, libro: null };
      }
    } catch (error) {
      // Manejar cualquier error que ocurra durante la ejecución de la función
      console.error("Error en comprobarLibro:", error);
      throw new Error("Error al comprobar el libro");
    }
  }

  // Método para guardar cambios en la relación usuario-libro.
  async guardarCambios(id, status, rate, comment) {
    // URL del endpoint
    const url = `http://localhost:8080/v1/usersBooks/${id}`;

    // Datos que deseas enviar. Asegúrate de que estos coincidan con los parámetros esperados por tu endpoint.
    const data = {
      status: status,
      rate: rate,
      comment: comment,
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
    } catch (error) {
      console.error("Error:", error);
    }
  }

  // Método que carga los generos de los libros.
  async cargarGeneros() {
    try {
      const response = await fetch("http://localhost:8080/v1/genres", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`, // Aquí es donde incluyes el token en la cabecera de la petición
        },
      });

      const generos = await response.json();

      const selectGenero = document.getElementById("txtGenero");
      const selectGenero2 = document.getElementById("txtGenero2");

      if (selectGenero) {
        generos.forEach((genero) => {
          const option = document.createElement("option");
          option.value = genero.genreName;
          option.text = genero.genreName;
          selectGenero.appendChild(option);
        });
      }

      if (selectGenero2) {
        generos.forEach((genero) => {
          const option = document.createElement("option");
          option.value = genero.genreName;
          option.text = genero.genreName;
          selectGenero2.appendChild(option);
        });

        // Agregar un evento para cargar los libros filtrados por género
        selectGenero2.addEventListener("change", () => {
          const selectedGenre = selectGenero2.value;
          if (!selectedGenre) {
            // Si no se ha seleccionado un género, cargar todos los libros
            this.catalogo();
          } else {
            // Realizar una nueva consulta y cargar los libros filtrados por género
            this.cargarLibrosPorGenero(selectedGenre);
          }
        });
      }
    } catch (error) {
      console.error("Error al cargar los géneros:", error);
    }
  }

  // Método que carga los tipos de estados de los libros.
  async cargarEstados() {
    try {
      const response = await fetch("http://localhost:8080/v1/status", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`, // Aquí es donde incluyes el token en la cabecera de la petición
        },
      });

      const estados = await response.json();

      const divEstados = document.getElementById("divEstados");

      estados.forEach((estado) => {
        const divFormCheck = document.createElement("div");
        divFormCheck.classList.add("form-check");

        const radio = document.createElement("input");
        radio.type = "radio";
        radio.classList.add("form-check-input");
        radio.name = "rbtEstado";
        radio.id = "rbt" + estado.statusName.replace(/\s+/g, "");
        radio.value = estado.statusName.toLowerCase();

        const label = document.createElement("label");
        label.classList.add("form-check-label");
        label.htmlFor = "rbt" + estado.statusName.replace(/\s+/g, "");
        label.textContent = estado.statusName;

        const space = document.createElement("span");
        space.innerHTML = "&nbsp;&nbsp;"; // Añade espacio entre el radio y el texto

        divFormCheck.appendChild(radio);
        divFormCheck.appendChild(space);
        divFormCheck.appendChild(label);

        divEstados.appendChild(divFormCheck);
      });
    } catch (error) {
      console.error("Error al cargar los estados:", error);
    }
  }
}
