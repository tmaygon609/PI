class Book {
  title;
  author;
  genre;
  status;

  constructor(t, a, g, s) {
    this.title = t;
    this.author = a;
    this.genre = g;
    this.status = s;
  }

  // Metodo que da de alta a un libro.
  altaLibro() {
    const storedUser = localStorage.getItem("usuarioActual");

    console.log("storedUser", storedUser);

    if (!storedUser) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }

    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    console.log("usuarioActual después de asignar:", usuarioActual);

    console.log("userid ", usuarioActual.userInfo.id);
    let userId = usuarioActual.userInfo.id;

    const nuevoLibro = {
      title: this.title,
      author: this.author,
      genre: this.genre,
      status: this.status,
    };

    this.comprobarLibro(this.title)
      .then((resultado) => {
        if (resultado.existe) {
          console.log("El libro ya existe. Solo se crea la relación.");
          // Crea la relación entre el usuario y el libro
          let miLibro = new Book();
          miLibro.crearRelacionUserBook(userId, resultado.libroId);
        } else {
          console.log("El libro no existe. Creando nuevo libro y relación.");
          const oHttpCrearLibro = new XMLHttpRequest();
          oHttpCrearLibro.open(
            "POST",
            "http://localhost:8080/building/saveBook"
          );
          oHttpCrearLibro.setRequestHeader("Content-type", "application/json");

          oHttpCrearLibro.send(JSON.stringify(nuevoLibro));

          oHttpCrearLibro.onload = function () {
            console.log(oHttpCrearLibro.status);
            console.log(oHttpCrearLibro.responseText);

            if (oHttpCrearLibro.status == 201) {
              console.log("entro en crear libro.");
              if (
                oHttpCrearLibro.responseText &&
                oHttpCrearLibro.responseText !== ""
              ) {
                const libroId = JSON.parse(oHttpCrearLibro.responseText).id;
                alert("Libro registrado correctamente");

                console.log("Nuevo libro creado con ID:", libroId);

                // Llama a la función getBookById una vez que el libro ha sido creado

                let myBook = new Book();
                myBook.crearRelacionUserBook(userId, libroId);
              } else {
                alert("La respuesta del servicdor esta vacia");
              }
            } else {
              alert(
                "Error al registrar el libro, contacte con el administrador de la app"
              );
            }
          };
        }
      })
      .catch((error) => {
        console.error("Error al comprobar el libro", error);
      });
  }

  //Método que busca un libro por su titulo.
  comprobarLibro(title) {
    return new Promise((resolve, reject) => {
      const oHttp = new XMLHttpRequest();

      oHttp.open(
        "GET",
        "http://localhost:8080/building/searchByTitle?title=" +
          encodeURIComponent(title)
      );
      oHttp.setRequestHeader("Content-type", "application/json");
      oHttp.send();

      oHttp.onload = function () {
        if (oHttp.status == "200") {
          const libroEncontrado = JSON.parse(oHttp.responseText);

          if (libroEncontrado.length > 0) {
            const libroId = libroEncontrado[0].id;
            resolve({ existe: true, libroId: libroId });
          } else {
            resolve({ existe: false, libroId: null });
          }
        } else {
          reject(new Error("Error al buscar el libro"));
        }
      };
    });
  }

  crearRelacionUserBook(userId, libroId) {
    console.log("entro en realcuion.");
    const oHttpCrearRelacion = new XMLHttpRequest();
    oHttpCrearRelacion.open(
      "POST",
      "http://localhost:8080/building/addBookToUser?userId=" +
        userId +
        "&bookId=" +
        libroId
    );
    oHttpCrearRelacion.setRequestHeader("Content-type", "application/json");

    oHttpCrearRelacion.send();

    oHttpCrearRelacion.onload = function () {
      if (oHttpCrearRelacion.status == 201) {
        alert("Relación USER_BOOK creada correctamente");
      } else {
        alert(
          "Error al crear la relación USER_BOOK, contacte con el administrador de la app"
        );
      }
    };
  }

  // Metodo para eliminar un libro.
  eliminar(id) {
    const oHttp = new XMLHttpRequest();

    oHttp.open("DELETE", "http://localhost:8080/building/" + id);
    oHttp.setRequestHeader("Content-type", "application/json");
    oHttp.setRequestHeader("Access-Control-Allow-Origin", "*");
    oHttp.send();

    oHttp.onload = function () {
      if (oHttp.status == "200") {
        alert("Libro eliminado correctamente");

        // Eliminar la fila de la tabla
        let valorBoton = "e" + id;
        let filaEliminar = document
          .querySelector("button[value='" + valorBoton + "']")
          .closest("tr");
        filaEliminar.parentNode.removeChild(filaEliminar);
      } else {
        alert(
          "Error al eliminar el libro. Detalles: " +
            oHttp.status +
            " - " +
            oHttp.statusText
        );
      }
    };
  }

  // Método que busca un libro por su titulo.
  buscarLibro(title) {
    document.getElementById("listado").innerHTML = "";

    const storedUser = localStorage.getItem("usuarioActual");
    if (!storedUser) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }
    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));
    let userId = usuarioActual.userInfo.id;

    const oHttp = new XMLHttpRequest();

    oHttp.open(
      "GET",
      "http://localhost:8080/building/searchByTitleAndUser?title=" +
        encodeURIComponent(title) +
        "&userId=" +
        userId
    );
    oHttp.setRequestHeader("Content-type", "application/json");
    oHttp.send();

    oHttp.onload = function () {
      if (oHttp.status == "200") {
        const libroEncontrado = JSON.parse(oHttp.responseText);

        if (libroEncontrado.length > 0) {
          // Libro encontrado, mostrar listado
          let tabla =
            "<h1 style='Text-align:center'>Libro encontrado:</h1><br><br>";
          tabla +=
            "<table id= 'tabla' class='table table-striped'><thead><tr><th scope='col'>Titulo</th><th scope='col'>Autor</th><th scope='col'>Genero</th><th scope='col'>Estado</th></tr></thead><tbody>";

          libroEncontrado.forEach((fila) => {
            tabla += "<tr>";
            tabla += "<td>" + fila.title + "</td>";
            tabla += "<td>" + fila.author + "</td>";
            tabla += "<td>" + fila.genre + "</td>";
            tabla += "<td>" + fila.status + "</td>";
            tabla += "</tr>";
          });

          tabla += "</tbody></table>";

          document.getElementById("listado").innerHTML += tabla;
          document.getElementById("listado").style.display = "block";
        } else {
          alert("Libro no encontrado.");
        }
      } else {
        alert(
          "Error al buscar el libro, contacte con el administrador de la app"
        );
      }
    };
  }

  //Metodo que muestra el libro recomendado por la IA
  listadoRecomendado() {
    document.getElementById("listado").innerHTML = "";

    const storedUser = localStorage.getItem("usuarioActual");
    if (!storedUser) {
      console.error("No se ha encontrado la información del usuario.");
      return;
    }
    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));
    let userId = usuarioActual.userInfo.id;

    // Crear objeto para enviar al servidor
    let requestData = {
      model: "gpt-3.5-turbo",
      messages: [{ role: "user", content: prompt }],
      temperature: 0.8,
    };

    const oHttp = new XMLHttpRequest();
    oHttp.open(
      "POST",
      "http://localhost:8080/building/getBookRecommendation?userId=" + userId
    );
    oHttp.setRequestHeader("Content-type", "application/json");
    oHttp.send(JSON.stringify(requestData));

    let tabla =
      "<h1 style='Text-align:center'>Libro recomendado: </h1><br><br>";

    oHttp.onload = function () {
      if (oHttp.status == "200") {
        const response = JSON.parse(this.responseText);

        if (Array.isArray(response.choices)) {
          const choices = response.choices;

          tabla +=
            "<table class='table'><thead><tr><th scope='col'>Título</th><th scope='col'>Autor</th></tr></thead><tbody>";

          choices.forEach((choice) => {
            const content = choice.message.content.split(", ");
            const title = content[0];
            const author = content[1];

            tabla += "<tr>";
            tabla += "<td>" + title + "</td>";
            tabla += "<td>" + author + "</td>";
            tabla += "</tr>";
          });

          tabla += "</tbody></table>";

          document.getElementById("listado").innerHTML += tabla;
          document.getElementById("listado").style.display = "block";
        } else {
          console.error(
            "La respuesta no contiene un array 'choices':",
            response
          );
          // Manejar este caso según sea necesario
        }
      } else {
        alert("Has llegado al límite de recomendaciones, prueba más tarde.");
      }
    };
  }
}
