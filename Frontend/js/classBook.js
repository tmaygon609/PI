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
    console.log("usuarioActual", usuarioActual);

    if (!usuarioActual) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }

    let userId = usuarioActual.userInfo.id;
    console.log("userid ", usuarioActual.userInfo.id);

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
          console.log("El libro ya existe. Solo se crea la relación.");

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
        console.error("Error al procesar el libro:", error);
      }
    };

    reader.readAsArrayBuffer(imagen);
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
      return { existe: true, libro: libroEncontrado[0] };
    } else {
      return { existe: false, libro: null };
    }
  }

  async crearLibro(nuevoLibro) {
    try {
      const response = await fetch("http://localhost:8080/v1/books", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
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
        { method: "GET" }
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
      console.error("Error inesperado intentalo de nuevo:", error);
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
          // Manejar este caso según sea necesario
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

  async catalogo() {
    try {
      // Realiza la petición para obtener los detalles de los libros
      const response = await fetch("http://localhost:8080/v1/books");
      const books = await response.json();

      // Selecciona el contenedor donde se cargarán los detalles de los libros
      const bookDetailsContainer = document.getElementById(
        "book-details-container"
      );

      // Limpiar el contenedor antes de agregar nuevos libros
      bookDetailsContainer.innerHTML = "";

      // Itera sobre los libros y crea los elementos HTML correspondientes
      books.forEach((book, index) => {
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
        title.textContent = `${index + 1} - ${book.title}`;

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
              <a href="book-info.html" class="btn btn-success" title="Añadir libro"><i class="fa fa-plus"></i></a>
              <a href="book-config.html" class="btn btn-info" title="Gestionar libro"><i class="fa fa-wrench"></i></a>
          `;

        // Agrega los elementos al contenedor principal

        bookInfo.appendChild(title);
        bookInfo.appendChild(author);
        bookInfo.appendChild(genre);
        bookInfo.appendChild(actionLinks);

        bookItem.appendChild(bookCover);
        bookItem.appendChild(bookInfo);

        listItem.appendChild(bookItem);
        bookDetailsContainer.appendChild(listItem);
      });
    } catch (error) {
      console.error("Error al cargar los detalles de los libros:", error);
    }
  }

  async guardarCambios(id, status, rate, comment) {
    console.log("id", id);
    console.log("status", status);
    console.log("rate", rate);
    console.log("comment", comment);

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

  // Método que carga los generos de los libros.
  async cargarGeneros() {
    try {
      const response = await fetch("http://localhost:8080/v1/genres");
      const generos = await response.json();

      const selectGenero = document.getElementById("txtGenero");
      const selectGenero2 = document.getElementById("txtGenero2");

      generos.forEach((genero) => {
        const option = document.createElement("option");
        option.value = genero.genreName;
        option.text = genero.genreName;
        selectGenero.appendChild(option);

        const option2 = document.createElement("option");
        option2.value = genero.genreName;
        option2.text = genero.genreName;
        selectGenero2.appendChild(option2);
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
    } catch (error) {
      console.error("Error al cargar los géneros:", error);
    }
  }

  // Método para cargar libros filtrados por género
  async cargarLibrosPorGenero(genre) {
    try {
      // Realizar la petición para obtener los libros filtrados por género
      const response = await fetch(
        `http://localhost:8080/v1/books/searchByGenre?genre=${genre}`
      );
      const books = await response.json();

      // Limpiar el contenedor de detalles de libros
      const bookDetailsContainer = document.getElementById(
        "book-details-container"
      );
      bookDetailsContainer.innerHTML = "";

      // Iterar sobre los libros y crear los elementos HTML correspondientes
      books.forEach((book, index) => {
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
        bookCover.src = imgUrl; // Aquí necesitarás la portada del libro actual
        bookCover.alt = "Portada del libro";
        bookCover.classList.add("book-cover");

        const bookInfo = document.createElement("div");
        bookInfo.classList.add("book-info");

        const title = document.createElement("h4");
        title.classList.add("list-group-item-heading");
        title.textContent = `${index + 1} - ${book.title}`;

        const author = document.createElement("p");
        author.classList.add("list-group-item-text");
        author.innerHTML = `<strong>Autor: </strong>${book.author}<br/>`;

        const genreElement = document.createElement("p");
        genreElement.classList.add("list-group-item-text");
        genreElement.innerHTML = `<strong>Género: </strong>${genre}<br/><br/>`;

        // Agrega los enlaces de acción
        const actionLinks = document.createElement("p");
        actionLinks.classList.add("list-group-item-text");
        actionLinks.innerHTML = `
            <a href="book-info.html" class="btn btn-success" title="Añadir libro"><i class="fa fa-plus"></i></a>
            <a href="book-config.html" class="btn btn-info" title="Gestionar libro"><i class="fa fa-wrench"></i></a>
        `;

        // Agrega los elementos al contenedor principal
        bookInfo.appendChild(title);
        bookInfo.appendChild(author);
        bookInfo.appendChild(genreElement);
        bookInfo.appendChild(actionLinks);

        bookItem.appendChild(bookCover);
        bookItem.appendChild(bookInfo);

        listItem.appendChild(bookItem);
        bookDetailsContainer.appendChild(listItem);
      });
    } catch (error) {
      console.error("Error al cargar los libros por género:", error);
    }
  }

  // Método que carga los tipos de estados de los libros.
  async cargarEstados() {
    try {
      const response = await fetch("http://localhost:8080/v1/status");
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
