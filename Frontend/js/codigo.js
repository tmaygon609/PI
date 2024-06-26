"use strict";
const listado = document.getElementById("listado");

// Se agrega un evento de escucha al listado de libros
listado.addEventListener("click", manejadorEvento);

const guardarCambiosBtn = document.getElementById("guardarCambiosBtn");

guardarCambiosBtn.addEventListener("click", guardarCambios);

// Obtener el botón de eliminar cuenta por su ID
const eliminarCuentaBtn = document.getElementById("eliminarCuentaBtn");

// Agregar un EventListener para manejar el clic en el botón de eliminar cuenta
eliminarCuentaBtn.addEventListener("click", aceptarEliminarCuenta);

// Oculta todo el contenido de la página al principio
document.body.style.display = "none";

// Llamas a la función comprobarInicioSesion al cargar tu página principal.html
window.onload = comprobarInicioSesion;

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

// Método para comprobar el inicio de sesión
function comprobarInicioSesion() {
  // Compruebas si el token JWT existe
  if (!localStorage.getItem("jwtToken")) {
    // Si no existe el token, rediriges a la página de inicio de sesión
    window.location.href = "index.html";
  } else {
    // Si existe el token, muestra el contenido de la página y carga los géneros y estados
    document.body.style.display = "block";
    let oBook = new Book();
    oBook.cargarGeneros();
    oBook.cargarEstados();
    // oBook.catalogo();

    mostrarContenidoPrincipal();
  }
}

// Método para recuperar el listado del usuario
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

// Método para actualizar el listado de libros
function actualizarListado(event) {
  if (!event) {
    console.error("El evento no está definido.");
    return;
  }

  event.preventDefault();

  let storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    const filterValue = document.getElementById("filterInput").value;
    let usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.listadoLibros(filterValue);
    document.getElementById("filterInput").value = "";
  } else {
    console.error("no se contro la informacion del usuario.");
  }
}

// Método para gestionar la visualización de formularios
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

// Método que oculta todos los formularios.
function ocultarTodosLosFormularios() {
  let oFormularios = document.querySelectorAll("form");

  for (let i = 0; i < oFormularios.length; i++) {
    oFormularios[i].style.display = "none";
  }
}

// Método que elimina y muestra el listado completo en catalogo
function mostrarListadoEnCatalogo() {
  document.getElementById("txtGenero2").selectedIndex = 0;
  let oBook = new Book();
  oBook.catalogo();
}

// Método que muestra el contenido principal
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

    // Selecciona la imagen por su ID
    let userIcon = document.getElementById("avatar");

    // Verifica el género del usuario y asigna el src de la imagen correspondiente
    if (usuarioActual.gender === "masculino") {
      userIcon.src = "./assets/avatars/StudetMaleAvatar.png";
    } else if (usuarioActual.gender === "femenino") {
      userIcon.src = "./assets/avatars/StudetFemaleAvatar.png";
    } else {
      // Si el género no está definido, puedes usar un avatar genérico o un estilo predeterminado
      userIcon.src = "./assets/avatars/TeacherFemaleAvatar2.png";
    }
  } else {
    console.error("No se encontró la información del usuario.");
  }
}

// Método aceptarAltaLibro. Clic en botón del formulario de registrar libro.
function aceptarAltaLibro() {
  if (
    document.getElementById("txtTitulo").value == "" ||
    document.getElementById("txtAutor").value == "" ||
    document.getElementById("txtGenero").value == ""
  ) {
    swal({
      title: "Campos obligatorios vacíos",
      text: "Por favor rellene correctamente los campos Titulo, Autor y Género.",
      icon: "warning",
    });
  } else {
    let title = document.getElementById("txtTitulo").value;
    let author = document.getElementById("txtAutor").value;
    let genreBook = document.getElementById("txtGenero");
    let selectedGenre = genreBook.options[genreBook.selectedIndex].text;

    const fileInput = document.querySelector('input[type="file"]');
    const imagen = fileInput.files[0];

    if (!imagen) {
      swal({
        title: "Imagen no seleccionada",
        text: "Por favor, seleccione una imagen para el libro",
        icon: "warning",
      });
      return;
    }

    // Comprimir la imagen antes de enviarla
    compressImage(imagen, 0.6) // Llama a la función con los parámetros adecuados
      .then((imagenComprimida) => {
        let status;

        if (document.getElementById("rbtSinempezar").checked) {
          status = capitalizeFirstLetter(
            document.getElementById("rbtSinempezar").value
          );
        } else if (document.getElementById("rbtEncurso").checked) {
          status = capitalizeFirstLetter(
            document.getElementById("rbtEncurso").value
          );
        } else if (document.getElementById("rbtLeido").checked) {
          status = capitalizeFirstLetter(
            document.getElementById("rbtLeido").value
          );
        } else {
          status = "Sin empezar";
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

        let oBook = new Book(
          title,
          author,
          selectedGenre,
          status,
          rate,
          comments,
          imagenComprimida // Utiliza la imagen comprimida aquí
        );

        oBook.altaLibro(imagenComprimida); // Pasa la imagen comprimida a altaLibro

        frmAltaLibro.reset();
      })
      .catch((error) => {
        console.error("Error al comprimir la imagen:", error);
      });
  }
}

// Método para convertir la primera letra en mayúscula para el campo género y estado del libro
function capitalizeFirstLetter(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

// Método para comprimir la imagen
function compressImage(image, quality) {
  const maxWidth = 167;
  const maxHeight = 250;

  return new Promise((resolve, reject) => {
    const canvas = document.createElement("canvas");
    const context = canvas.getContext("2d");

    const img = new Image();
    img.src = URL.createObjectURL(image);
    img.onload = () => {
      let width = img.width;
      let height = img.height;

      const ratio = Math.min(maxWidth / width, maxHeight / height);
      width *= ratio;
      height *= ratio;

      canvas.width = width;
      canvas.height = height;

      context.drawImage(img, 0, 0, width, height);

      canvas.toBlob(
        (blob) => {
          resolve(blob);
        },
        "image/jpeg",
        quality
      );
    };

    img.onerror = (error) => {
      reject(error);
    };
  });
}

// Método aceptarBuscarLibro. Clic en botón del formulario de buscar libro por título.
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

// Método aceptarListadoLibros. Clic en la opción de menú de listar todos los libros.
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

// Método recomendarLibro. Clic en la opción de recomendar un libro a través de la IA.
async function recomendarLibro() {
  // document.getElementById("listado").innerHTML = "";
  // document.getElementById("listado").style.display = "none";

  let oBook = new Book();
  oBook.listadoRecomendado();

  const tabla = await oBook.listadoRecomendado();
  if (tabla) {
    document.getElementById("iaModalBody").innerHTML = tabla;

    // Abre el modal
    $("#iaModal").modal("show");
  }
}

// Método para cerrar sesión y volver a la página de inicio
function cerrarSesion() {
  swal({
    title: "Cerrar sesión",
    text: "¿Estás seguro de que quieres cerrar sesión?",
    icon: "warning",
    buttons: ["Cancelar", "Cerrar sesión"],
    dangerMode: true,
  }).then((cerrar) => {
    if (cerrar) {
      localStorage.removeItem("jwtToken");
      window.open("index.html", "_self");
    }
  });
}

// Método para guardar los cambios en la contraseña
function guardarCambios() {
  // Recupera la información del usuario almacenada en el localStorage
  const storedUser = localStorage.getItem("usuarioActual");

  const nuevaContrasena = document.getElementById("nuevaContrasena").value;
  const confirmarContrasena = document.getElementById(
    "confirmarContrasena"
  ).value;

  // Validar que las contraseñas coincidan
  if (nuevaContrasena !== confirmarContrasena) {
    swal({
      title: "Error",
      text: "Las contraseñas no coinciden.",
      icon: "error",
    });
    return;
  }

  // Validar los requisitos de la nueva contraseña
  const resultadoValidacion = validarRequisitosContrasena(nuevaContrasena);
  if (!resultadoValidacion.esValida) {
    swal({
      title: "Error validación",
      text: resultadoValidacion.mensaje,
      icon: "warning",
    });
    return;
  }

  if (storedUser) {
    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.cambiarContrasena(nuevaContrasena);

    document.getElementById("nuevaContrasena").value = "";
    document.getElementById("confirmarContrasena").value = "";
  } else {
    console.error("no se contro la informacion del usuario.");
  }
}

// Método para ir a eliminar cuenta
function aceptarEliminarCuenta() {
  // Recupera la información del usuario almacenada en el localStorage
  const storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    // Mostrar un SweetAlert de confirmación
    swal({
      title: "¿Estás seguro?",
      text: "¿Seguro que quieres eliminar tu cuenta?",
      icon: "warning",
      buttons: ["Cancelar", "Eliminar"],
      dangerMode: true,
    }).then((willDelete) => {
      if (willDelete) {
        // Si el usuario confirma la eliminación, continuar con la operación
        const usuarioActual = new User();
        Object.assign(usuarioActual, JSON.parse(storedUser));

        usuarioActual.eliminarCuenta();

        // Eliminar el token JWT del localStorage después de 4 segundos
        setTimeout(function () {
          localStorage.removeItem("jwtToken");
          window.open("index.html", "_self");
        }, 4000);
      } else {
        // Si el usuario cancela, no hacer nada
        console.log("Eliminación cancelada.");
      }
    });
  } else {
    console.error(
      "No se encontró la información del usuario en el localStorage."
    );
  }
}

// Maneja el evento de los botones borrar del listado de todos los libros.
function manejadorEvento(event) {
  if (event.target.tagName == "BUTTON") {
    let btn = event.target.value;

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

  // Método para obterner valores de estado, calificación y comentario
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

  // Método que convierte los campos de estado, calificación y comentario en inputs editables
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

  // Método que revierte los campos de estado, calificación y comentario a texto
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
