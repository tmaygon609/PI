"use strict";
const listado = document.getElementById("listado");

// Se agrega un evento de escucha al listado de libros
listado.addEventListener("click", manejadorEvento);

// Se cargan al iniciar los generos y estados en los select y radio buttons
document.addEventListener("DOMContentLoaded", () => {
  let oBook = new Book();
  oBook.cargarGeneros();
  oBook.cargarEstados();
  // oBook.listadoLibrosCompleto();

  mostrarContenidoPrincipal();
});

// Selecciona el modal por su id
let modalBook = document.getElementById("bookModal");
let modalIA = document.getElementById("iaModal");

// Agrega un evento de escucha para cuando el modal se oculta
$(modalBook).on("hidden.bs.modal", function () {
  recuperarListadoUsuario();
});

$(modalIA).on("hidden.bs.modal", function () {
  recuperarListadoUsuario();
});

function recuperarListadoUsuario() {
  let storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    let usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.listadoLibros();
  } else {
    console.error("no se contro la informacion del usuario.");
  }
}

// Gestión de formularios
function gestionFormularios(sFormularioVisible) {
  ocultarTodosLosFormularios();

  // Hacemos visible el formulario que llega como parámetro
  switch (sFormularioVisible) {
    case "frmAltaLibro":
      frmAltaLibro.style.display = "block";
      break;
    case "frmListadoLibros":
      frmListadoLibros.style.display = "block";
      aceptarListadoLibros();
      break;
    case "frmListadoAdmin":
      frmListadoAdmin.style.display = "block";
      break;
    case "frmListadoStudents":
      frmListadoStudents.style.display = "block";
      break;
    case "frmCatalogo":
      frmCatalogo.style.display = "block";
      mostrarListadoEnCatalogo();
      break;
    case "frmInformacionUsuario":
      frmInformacionUsuario.style.display = "block";
      const usuario = new User();
      usuario.actualizarFormularioInformacion();
      break;
    case "CerrarSesion":
      cerrarSesion();
      break;
  }

  document.getElementById("contenidoPrincipal").style.display = "none";
  document.getElementById("listado").style.display = "none";
}

//Función que oculta todos los formularios.
function ocultarTodosLosFormularios() {
  let oFormularios = document.querySelectorAll("form");

  for (let i = 0; i < oFormularios.length; i++) {
    oFormularios[i].style.display = "none";
  }
}

//Función que elimina y muestra el listado completo en catalogo
function mostrarListadoEnCatalogo() {
  document.getElementById("txtGenero2").selectedIndex = 0;
  let oBook = new Book();
  oBook.listadoLibrosCompleto();
}

// Función que muestra el contenido principal
function mostrarContenidoPrincipal() {
  let storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    let usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    let nombreUsuario = usuarioActual.name;
    let divUsuario = document.getElementById("nombreUsuario");
    divUsuario.innerHTML = `${nombreUsuario}`;

    // Selecciona el elemento figcaption
    let figcaption = document.querySelector(
      "figcaption.text-center.text-titles"
    );

    // Asigna el nombre de usuario al contenido de figcaption
    figcaption.textContent = nombreUsuario;
  } else {
    console.error("No se encontró la información del usuario.");
  }
}

// aceptarAltaLibro. Clic en boton del formulario de registrar libro.
function aceptarAltaLibro() {
  if (
    document.getElementById("txtTitulo").value == "" ||
    document.getElementById("txtAutor").value == "" ||
    document.getElementById("txtGenero").value == ""
  ) {
    swal({
      title: "Campos vacíos",
      text: "por favor rellene correctamente los campos",
      icon: "warning",
    });
  } else {
    let title = document.getElementById("txtTitulo").value;
    let author = document.getElementById("txtAutor").value;
    let genreBook = document.getElementById("txtGenero");
    let selectedGenre = genreBook.options[genreBook.selectedIndex].text;

    let status;

    if (document.getElementById("rbtSinempezar").checked) {
      status = capitalizeFirstLetter(
        document.getElementById("rbtSinempezar").value
      );
    } else if (document.getElementById("rbtEncurso").checked) {
      status = capitalizeFirstLetter(
        document.getElementById("rbtEncurso").value
      );
    } else {
      status = capitalizeFirstLetter(document.getElementById("rbtLeido").value);
    }

    let rate;
    if (document.getElementById("radio1").checked) {
      rate = document.getElementById("radio1").value;
    } else if (document.getElementById("radio2").checked) {
      rate = document.getElementById("radio2").value;
    } else if (document.getElementById("radio3").checked) {
      rate = document.getElementById("radio3").value;
    } else if (document.getElementById("radio4").checked) {
      rate = document.getElementById("radio4").value;
    } else if (document.getElementById("radio5").checked) {
      rate = document.getElementById("radio5").value;
    }

    let comments = document.getElementById("txtComments").value;

    let oBook = new Book(title, author, selectedGenre, status, rate, comments);

    oBook.altaLibro();

    frmAltaLibro.reset();
  }
}

// Primeras letras en mayúscula para el campo género y estado del libro
function capitalizeFirstLetter(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

// aceptarBuscarLibro. Clic en boton del formulario de buscar libro por titulo.
async function aceptarBuscarLibro() {
  if (document.getElementById("txtTituloLibro").value == "") {
    swal({
      title: "Campos vacío",
      text: "por favor rellene el campo correctamente.",
      icon: "warning",
    });
  } else {
    let title = document.getElementById("txtTituloLibro").value;

    let oBook = new Book();

    oBook.buscarLibro(title);
    const tabla = await oBook.buscarLibro(title);
    if (tabla) {
      // Agregar la tabla al cuerpo del modal
      document.getElementById("bookModalBody").innerHTML = tabla;

      // Abrir el modal
      $("#bookModal").modal("show");

      document.getElementById("txtTituloLibro").value = "";
    }
  }
}

// Clic en la opción de menú de listar todos los libros.
function aceptarListadoLibros() {
  document.getElementById("listado").innerHTML = "";
  document.getElementById("listado").style.display = "none";

  // Recupera la información del usuario almacenada en el localStorage
  const storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.listadoLibros();
  } else {
    console.error("no se contro la informacion del usuario.");
  }
}

// Clic en la opción de recomendar un libro a través de la IA.
async function recomendarLibro() {
  document.getElementById("listado").innerHTML = "";
  document.getElementById("listado").style.display = "none";

  let oBook = new Book();
  oBook.listadoRecomendado();

  const tabla = await oBook.listadoRecomendado();
  if (tabla) {
    // Add the table to the body of the modal
    document.getElementById("iaModalBody").innerHTML = tabla;

    // Open the modal
    $("#iaModal").modal("show");
  }
}

// Volvemos a la página de login
function cerrarSesion() {
  swal({
    title: "Cerrar sesión",
    text: "¿Estás seguro de que quieres cerrar sesión?",
    icon: "warning",
    buttons: ["Cancelar", "Cerrar sesión"],
    dangerMode: true,
  }).then((cerrar) => {
    if (cerrar) {
      window.open("index.html", "_self");
    }
  });
}

// Maneja el evento de los botones borrar del listado de todos los libros.
function manejadorEvento(event) {
  if (event.target.tagName == "BUTTON") {
    let btn = event.target.value;

    console.log("Botón presionado:", event.target);
    console.log(btn);

    if (String(btn).substring(0, 1) == "e") {
      swal({
        title: "¿Quieres eliminar este libro?",
        text: "Esta acción no se puede deshacer",
        icon: "warning",
        buttons: ["Cancelar", "Eliminar"],
        dangerMode: true,
      }).then((eliminar) => {
        if (eliminar) {
          let id = String(btn).substring(1, btn.length);

          let oBook = new Book();
          oBook.eliminar(id);
        } else {
          swal({
            title: "Se ha cancelado la operación",
            icon: "error",
          });
        }
      });
    } else if (String(btn).substring(0, 1) == "a") {
      let id = String(btn).substring(1);

      convertirCamposAInputs(id);
    } else if (String(btn).substring(0, 1) === "g") {
      let id = String(btn).substring(1);

      const { status, rate, comment } = obtenerValores(id);

      console.log("Valores obtenidos:", status, rate, comment);

      // Verificar si el rate es 0 y mostrar un swal diferente
      if (rate === 0) {
        swal({
          title: "No tiene calificación",
          text: "¿Estás seguro de introducir un libro sin calificación?",
          icon: "info",
          buttons: ["Cancelar", "Guardar cambios"],
          dangerMode: false,
        }).then((guardar) => {
          if (guardar) {
            let oBook = new Book();
            oBook.guardarCambios(id, status, rate, comment);
            revertirCamposAInputs(id);
          }
        });
        return; // Retornar temprano para evitar mostrar el swal de guardar cambios
      } else {
        let oBook = new Book();
        oBook.guardarCambios(id, status, rate, comment);
        revertirCamposAInputs(id);
      }
    }
  }

  function obtenerValores(id) {
    const estadoSelect = document.getElementById("estadoSelect" + id);
    const status = estadoSelect.options[estadoSelect.selectedIndex].value;

    const calificacionInputs = document.querySelectorAll(
      'input[name="estrellas-' + id + '"]:checked'
    );
    let rate = 0;
    calificacionInputs.forEach((input) => {
      if (input.checked) {
        rate = input.value;
      }
    });

    const comentarioInput = document.getElementById("comentarioInput" + id);
    const comment = comentarioInput.value;

    return { status, rate, comment };
  }

  // Convierte los campos de estado, calificación y comentario en inputs editables
  function convertirCamposAInputs(id) {
    let estadoElement = document.getElementById("estado" + id);
    let calificacionElement = document.getElementById("calificacion" + id);
    let comentarioElement = document.getElementById("comentario" + id);

    // Create the <select> element
    let selectElement = document.createElement("select");
    selectElement.id = "estadoSelect" + id;
    selectElement.classList.add("form-control", "form-control-sm");

    // Create the three <option> elements
    let opcion1 = document.createElement("option");
    opcion1.value = "Leido";
    opcion1.text = "Leído";
    let opcion2 = document.createElement("option");
    opcion2.value = "En curso";
    opcion2.text = "En curso";
    let opcion3 = document.createElement("option");
    opcion3.value = "Sin empezar";
    opcion3.text = "Sin empezar";

    // Add the <option> elements to the <select> element
    selectElement.appendChild(opcion1);
    selectElement.appendChild(opcion2);
    selectElement.appendChild(opcion3);

    // Set the initial value of the <select> element based on the current value of the `estado` field
    for (let i = 0; i < selectElement.options.length; i++) {
      if (selectElement.options[i].value === estadoElement.innerHTML) {
        selectElement.selectedIndex = i;
        break;
      }
    }
    // Reemplazar el contenido del elemento estadoElement con el select
    let selectContainer = document.createElement("div");
    selectContainer.classList.add("col-xs-6");
    selectContainer.style.minWidth = "15vh";
    selectContainer.appendChild(selectElement);
    estadoElement.innerHTML = "";
    estadoElement.appendChild(selectContainer);

    // Create the rating elements
    let calificacionContainer = document.createElement("div");
    calificacionContainer.classList.add("form-group");

    let calificacionLabel = document.createElement("label");
    calificacionLabel.classList.add("control-label", "col-xs-3");

    calificacionContainer.appendChild(calificacionLabel);

    let calificacionRating = document.createElement("div");
    calificacionRating.classList.add("col-xs-6", "rating");
    calificacionRating.id = "star-rating";
    calificacionRating.setAttribute("data-id", id); // Añade un atributo data-id para identificar de manera única
    calificacionRating.style.display = "flex"; // Utilizamos flexbox para alinear los elementos horizontalmente

    for (let i = 5; i >= 1; i--) {
      let radioInput = document.createElement("input");
      radioInput.type = "radio";
      radioInput.id = "radio" + i + "-" + id;
      radioInput.name = "estrellas-" + id;
      radioInput.value = i;
      calificacionRating.appendChild(radioInput);

      let labelElement = document.createElement("label");
      labelElement.htmlFor = "radio" + i + "-" + id;
      labelElement.textContent = "★";
      calificacionRating.appendChild(labelElement);
    }

    calificacionContainer.appendChild(calificacionRating);

    // Replace the contents of the `calificacion` field with the rating elements
    let ratingContainer = document.createElement("div");
    ratingContainer.classList.add("col-xs-6");
    ratingContainer.style.display = "flex"; // Utilizamos flexbox para alinear los elementos horizontalmente
    ratingContainer.appendChild(calificacionContainer);
    calificacionElement.innerHTML = "";
    calificacionElement.appendChild(ratingContainer);

    // Obtener el valor actual del comentario
    let comentarioValue =
      comentarioElement.textContent || comentarioElement.innerText;

    // Crear el elemento de input de comentarios
    let comentarioInput = document.createElement("input");
    comentarioInput.type = "text";
    comentarioInput.id = "comentarioInput" + id;
    comentarioInput.value = comentarioValue;

    // Reemplazar el contenido del elemento comentarioElement con el input de comentarios
    comentarioElement.innerHTML = "";
    comentarioElement.appendChild(comentarioInput);

    // Cambiar el botón de lápiz por el de guardar cambios
    let btn = document.querySelector("button[value='a" + id + "']");
    btn.innerHTML = ""; // Limpiamos el contenido del botón

    // Cambiamos el valor del botón para que sea reconocido como un botón de guardar cambios
    btn.setAttribute("value", "g" + id);

    // Cambiar las clases del botón para reflejar el cambio a un botón de guardar
    btn.classList.remove("btn-info", "fa-pencil");
    btn.classList.add("btn-success", "fa-save");
  }

  // Revierte los campos de estado, calificación y comentario a texto
  function revertirCamposAInputs(id) {
    // Recuperar los valores actuales
    const estadoSelect = document.getElementById("estadoSelect" + id);
    const status = estadoSelect.options[estadoSelect.selectedIndex].text;

    const calificacionInputs = document.querySelectorAll(
      'input[name="estrellas-' + id + '"]:checked'
    );
    let rate = null;
    calificacionInputs.forEach((input) => {
      if (input.checked) {
        rate = input.value;
      }
    });

    const comentarioInput = document.getElementById("comentarioInput" + id);
    const comment = comentarioInput.value;

    // Convertir los elementos editables de nuevo a texto
    let estadoElement = document.getElementById("estado" + id);
    estadoElement.innerHTML = status;

    let calificacionElement = document.getElementById("calificacion" + id);
    let estrellas = "";
    for (let i = 0; i < rate; i++) {
      estrellas += "★";
    }
    for (let i = rate; i < 5; i++) {
      estrellas += "☆";
    }
    calificacionElement.innerHTML = estrellas;

    let comentarioElement = document.getElementById("comentario" + id);
    comentarioElement.innerHTML = comment;

    // Actualizar el botón
    let btn = document.querySelector("button[value='g" + id + "']");
    if (btn) {
      // Limpiar el contenido del botón
      btn.innerHTML = "";

      // Agregar el icono de lápiz y cambiar el valor del botón
      btn.setAttribute("value", "a" + id);

      // Cambiar las clases del botón
      btn.classList.remove("btn-success", "fa-save");
      btn.classList.add("btn-info", "fa-pencil");
    }
  }
}
