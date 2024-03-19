"use strict";
const listado = document.getElementById("listado");

listado.addEventListener("click", manejadorEvento);

// Gestión de formularios
function gestionFormularios(sFormularioVisible) {
  ocultarTodosLosFormularios();

  // Hacemos visible el formulario que llega como parámetro
  switch (sFormularioVisible) {
    case "frmAltaLibro":
      frmAltaLibro.style.display = "block";
      break;
    case "frmBuscarLibro":
      frmBuscarLibro.style.display = "block";
      break;
    case "frmListadoLibros":
      frmListadoLibros.style.display = "block";
      aceptarListadoLibros();
      break;
    case "frmRecomendador":
      frmRecomendador.style.display = "block";
      break;
    case "CerrarSesion":
      cerrarSesion();
      break;
  }
  document.getElementById("listado").style.display = "none";
}

//Función que oculta todos los formularios.
function ocultarTodosLosFormularios() {
  let oFormularios = document.querySelectorAll("form");

  for (let i = 0; i < oFormularios.length; i++) {
    oFormularios[i].style.display = "none";
  }
}

// aceptarAltaLibro. Clic en boton del formulario de registrar libro.
function aceptarAltaLibro() {
  if (
    document.getElementById("txtTitulo").value == "" ||
    document.getElementById("txtAutor").value == "" ||
    document.getElementById("txtGenero").value == ""
  ) {
    alert("Campos vacíos, por favor rellene correctamente los campos");
  } else {
    let title = document.getElementById("txtTitulo").value;
    let author = document.getElementById("txtAutor").value;
    let genreBook = document.getElementById("txtGenero");
    let selectedGenre = genreBook.options[genreBook.selectedIndex].text;

    let status;

    if (document.getElementById("rbtSinEmpezar").checked == true) {
      status = "Sin empezar";
    } else if (document.getElementById("rbtEnCurso").checked == true) {
      status = "En curso";
    } else {
      status = "Leido";
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

// aceptarBuscarLibro. Clic en boton del formulario de buscar libro por titulo.
function aceptarBuscarLibro() {
  if (document.getElementById("txtTituloLibro").value == "") {
    alert("Campo vacío, por favor rellene el campo correctamente.");
  } else {
    let title = document.getElementById("txtTituloLibro").value;

    let oBook = new Book();

    oBook.buscarLibro(title);
    frmBuscarLibro.reset();
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

  // let oUser = new User();

  // oUser.listadoLibros();
}

// Clic en la opción de recomendar un libro a través de la IA.
function recomendarLibro() {
  document.getElementById("listado").innerHTML = "";
  document.getElementById("listado").style.display = "none";

  let oBook = new Book();
  oBook.listadoRecomendado();
}

// Volvemos a la página de login
function cerrarSesion() {
  window.open("../index.html", "_self");
}

// Maneja el evento de los botones borrar del listado de todos los libros.
function manejadorEvento(event) {
  if (event.target.tagName == "BUTTON") {
    let btn = event.target.value;

    console.log("Botón presionado:", event.target);
    console.log(btn);

    if (String(btn).substring(0, 1) == "e") {
      let eliminar = confirm("¿Quieres eliminar?");

      if (eliminar) {
        let id = String(btn).substring(1, btn.length);

        let oBook = new Book();
        oBook.eliminar(id);
      } else {
        alert("Se ha cancelado la operación.");
      }
    } else if (String(btn).substring(0, 1) == "a") {
      let id = String(btn).substring(1, btn.length);
      let nuevoEstado = prompt("Ingrese el nuevo estado del libro");
      if (nuevoEstado) {
        let oBook = new Book();

        oBook.actualizarEstado(id, nuevoEstado);
      }
    }
  }
}
