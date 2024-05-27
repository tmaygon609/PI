"use strict";
const listadoUsuarios = document.getElementById("listadoUsuarios");

// Se agrega un evento de escucha al listado de libros
listadoUsuarios.addEventListener("click", manejadorEventoAdmin);

const listadoLibrosAdmin = document.getElementById("listadoLibrosAdmin");

// Se agrega un evento de escucha al listado de libros
listadoLibrosAdmin.addEventListener("click", manejadorEventoLibroAdmin);

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

// Función para comprobar el inicio de sesión
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

    mostrarContenidoPrincipal();
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
    case "frmListadoStudents":
      frmListadoStudents.style.display = "block";
      aceptarListadoUsuarios();
      break;
    case "frmListadoAdmin":
      frmListadoAdmin.style.display = "block";
      break;
    case "frmRegistroUsuario":
      frmRegistroUsuario.style.display = "block";
      break;
    case "frmListadoLibrosAdmin":
      frmListadoLibrosAdmin.style.display = "block";
      aceptarListadoLibrosAdmin();
      break;
    case "frmInformacionAdmin":
      frmInformacionAdmin.style.display = "block";
      const usuario = new User();
      usuario.actualizarFormularioInformacionAdmin();
      break;
    case "CerrarSesion":
      cerrarSesion();
      break;
  }

  document.getElementById("contenidoPrincipal").style.display = "none";
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
  oBook.catalogo();
}

// Recuperar el generar usuario admin y añadirle un evento de escucha
document
  .getElementById("generarUsuarioAdmin")
  .addEventListener("click", generarUsuarioAdmin);

//GENERADOR DE USUARIO.
function generarUsuarioAdmin() {
  let name = document.getElementById("nombre").value;
  let lastName = document.getElementById("apellidos").value;

  let u = name.substring(0, 1).toLowerCase();
  u += lastName.substring(0, 3).toLowerCase();
  u += lastName
    .substring(lastName.indexOf(" ") + 1, lastName.indexOf(" ") + 4)
    .toLowerCase();

  document.getElementById("usuario").value = u;
}

function aceptarRegistroUsuario() {
  let name = document.getElementById("nombre").value;
  let lastName = document.getElementById("apellidos").value;
  let user = document.getElementById("usuario").value;
  let password = document.getElementById("contrasenaAdmin").value;

  if (name == "" || lastName == "" || user == "" || password == "") {
    swal({
      title: "Campos obligatorios vacíos",
      text: "Por favor rellene correctamente todos los campos.",
      icon: "warning",
    });
    return;
  }
  // Validar la contraseña
  if (!validarContrasena(password)) {
    return; // Añadido return para detener la ejecución si la contraseña no es válida
  }

  let gender = document.querySelector('input[name="genero"]:checked').value;

  let oUser = new User(name, lastName, user, password, gender);
  oUser.registrar();

  // Limpiar los campos del formulario
  document.getElementById("nombre").value = "";
  document.getElementById("apellidos").value = "";
  document.getElementById("usuario").value = "";
  document.getElementById("contrasenaAdmin").value = "";

  // Desmarcar los radio buttons
  let radios = document.querySelectorAll('input[name="genero"]');
  radios.forEach((radio) => (radio.checked = false));
}

// Función para validar los requisitos de la contraseña en el formulario de admin
function validarContrasena() {
  const contrasenaAdmin = document.getElementById("contrasenaAdmin").value;

  // Validar los requisitos de la contraseña
  const resultadoValidacion = validarRequisitosContrasena(contrasenaAdmin);
  if (!resultadoValidacion.esValida) {
    swal({
      title: "Error de validación",
      text: resultadoValidacion.mensaje,
      icon: "warning",
    });
    return false;
  }
  return true;
}

// Función que muestra el contenido principal
function mostrarContenidoPrincipal() {
  let storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    let usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    let nombreUsuario = usuarioActual.name;

    // Selecciona el elemento figcaption
    let figcaption = document.querySelector(
      "figcaption.text-center.text-titles"
    );

    // Asigna el nombre de usuario al contenido de figcaption
    figcaption.textContent = nombreUsuario;

    // Selecciona la imagen por su ID
    let userIcon = document.getElementById("avatar");

    console.log("genero", usuarioActual.gender);
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

function actualizarListadoUsuarios(event) {
  if (!event) {
    console.error("El evento no está definido.");
    return;
  }

  event.preventDefault();

  let storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    const filterValue = document.getElementById("filterInputUsuarios").value;
    let usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.listadoUsuarios(filterValue);
    document.getElementById("filterInputUsuarios").value = "";
  } else {
    console.error("No se controló la información del usuario.");
  }
}

// Clic en la opción de menú de listar todos los usuarios.
function aceptarListadoUsuarios() {
  document.getElementById("listadoUsuarios").innerHTML = "";
  document.getElementById("listadoUsuarios").style.display = "none";

  // Recupera la información del usuario almacenada en el localStorage
  const storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.listadoUsuarios();
  } else {
    console.error("no se contro la informacion del usuario.");
  }
}

function aceptarListadoLibrosAdmin() {
  document.getElementById("listadoLibrosAdmin").innerHTML = "";
  document.getElementById("listadoLibrosAdmin").style.display = "none";

  // Recupera la información del usuario almacenada en el localStorage
  const storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.listadoLibrosAdmin();
  } else {
    console.error("no se contro la informacion del libro.");
  }
}

function actualizarListadoLibros(event) {
  if (!event) {
    console.error("El evento no está definido.");
    return;
  }

  event.preventDefault();

  let storedUser = localStorage.getItem("usuarioActual");

  if (storedUser) {
    const filterValue = document.getElementById("filterInputLibros").value;
    const usuarioActual = new User();
    Object.assign(usuarioActual, JSON.parse(storedUser));

    usuarioActual.listadoLibrosAdmin(filterValue);

    document.getElementById("filterInputLibros").value = "";
  } else {
    console.error("No se controló la información del usuario.");
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
        console.log("Imagen comprimida:", imagenComprimida);

        console.log("Imagen original:", imagen);

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

// Primeras letras en mayúscula para el campo género y estado del libro
function capitalizeFirstLetter(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

// Función para comprimir la imagen
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
      localStorage.removeItem("jwtToken");
      window.open("index.html", "_self");
    }
  });
}

// Función para guardar los cambios en la contraseña
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

// Maneja el evento de los botones de la tabla de usuarios
function manejadorEventoAdmin(event) {
  if (event.target.tagName == "BUTTON") {
    let btn = event.target.value;

    if (btn.substring(0, 1) === "e") {
      // Si se presiona el botón de eliminar
      swal({
        title: "¿Quieres eliminar este usuario?",
        text: "Esta acción no se puede deshacer",
        icon: "warning",
        buttons: ["Cancelar", "Eliminar"],
        dangerMode: true,
      }).then((eliminar) => {
        if (eliminar) {
          let id = String(btn).substring(1, btn.length);

          let usuario = new User();
          usuario.eliminarUsuario(id);
        } else {
          swal({
            title: "Se ha cancelado la operación",
            icon: "error",
          });
        }
      });
    } else if (btn.substring(0, 1) === "a") {
      let id = String(btn).substring(1);
      convertirCamposAInputsAdmin(id);
    } else if (String(btn).substring(0, 1) === "g") {
      let id = String(btn).substring(1);

      const { nombre, apellido } = obtenerValores(id);

      console.log("Valores obtenidos:", nombre, apellido);

      // Verificar si el nombre o apellido están vacíos
      if (!nombre || !apellido) {
        swal({
          title: "Los campos nombre y apellido son obligatorios",
          text: "Por favor, completa todos los campos antes de guardar los cambios.",
          icon: "warning",
          button: "OK",
        });
        return; // Retornar temprano para evitar guardar los cambios
      }

      // Realizar la acción de guardar cambios
      let oUsuario = new User();
      oUsuario.guardarCambiosUsuario(id, nombre, apellido);
      revertirCamposAInputsAdmin(id);
    }
  }
}

function obtenerValores(id) {
  const nombreInput = document.getElementById("nombreInput" + id);
  const nombre = nombreInput.value;

  const apellidoInput = document.getElementById("apellidoInput" + id);
  const apellido = apellidoInput.value;

  return { nombre, apellido };
}

// Convierte los campos de nombre y apellidos en inputs editables
function convertirCamposAInputsAdmin(id) {
  let nombreElement = document.getElementById("nombre" + id);
  let apellidoElement = document.getElementById("apellido" + id);

  // Obtener el valor actual del comentario
  let nombreValue = nombreElement.textContent || nombreElement.innerText;

  let apellidoValue = apellidoElement.textContent || apellidoElement.innerText;

  // Crear el input para el nombre
  let nombreInput = document.createElement("input");
  nombreInput.type = "text";
  nombreInput.id = "nombreInput" + id;
  nombreInput.value = nombreValue;

  // Reemplazar el contenido del elemento nombreElement con el input de nombre
  nombreElement.innerHTML = "";
  nombreElement.appendChild(nombreInput);

  // Crear el input para el apellido
  let apellidoInput = document.createElement("input");
  apellidoInput.type = "text";
  apellidoInput.id = "apellidoInput" + id;
  apellidoInput.value = apellidoElement.textContent;

  // Reemplazar el contenido del elemento apellidoElement con el input de apellido
  apellidoElement.innerHTML = "";
  apellidoElement.appendChild(apellidoInput);

  /// Cambiar el botón de lápiz por el de guardar cambios
  let btn = document.querySelector("button[value='a" + id + "']");
  btn.innerHTML = ""; // Limpiamos el contenido del botón

  // Cambiamos el valor del botón para que sea reconocido como un botón de guardar cambios
  btn.setAttribute("value", "g" + id);

  // Cambiar las clases del botón para reflejar el cambio a un botón de guardar
  btn.classList.remove("btn-info", "fa-pencil");
  btn.classList.add("btn-success", "fa-save");
}

// Revierte los campos de nombre y apellidos a texto
function revertirCamposAInputsAdmin(id) {
  // Recuperar los valores actuales de nombre y apellidos
  const nombreInput = document.getElementById("nombreInput" + id);
  const apellidoInput = document.getElementById("apellidoInput" + id);

  const nombre = nombreInput.value;
  const apellido = apellidoInput.value;

  // Convertir los elementos editables de nuevo a texto
  let nombreElement = document.getElementById("nombre" + id);
  nombreElement.textContent = nombre;

  let apellidoElement = document.getElementById("apellido" + id);
  apellidoElement.textContent = apellido;

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

function manejadorEventoLibroAdmin(event) {
  if (event.target.tagName === "BUTTON") {
    let btn = event.target.value;

    if (btn.substring(0, 1) === "e") {
      // Si se presiona el botón de eliminar
      swal({
        title: "¿Quieres eliminar este libro?",
        text: "Esta acción no se puede deshacer",
        icon: "warning",
        buttons: ["Cancelar", "Eliminar"],
        dangerMode: true,
      }).then((eliminar) => {
        if (eliminar) {
          let id = String(btn).substring(1, btn.length);

          let usuario = new User();
          usuario.eliminarLibro(id);
        } else {
          swal({
            title: "Se ha cancelado la operación",
            icon: "error",
          });
        }
      });
    } else if (btn.substring(0, 1) === "a") {
      let id = String(btn).substring(1);
      convertirCamposAInputsLibroAdmin(id);
    } else if (String(btn).substring(0, 1) === "g") {
      let id = String(btn).substring(1);

      const { titulo, autor, genero } = obtenerValoresLibro(id);

      // Verificar si el título, autor o género están vacíos
      if (!titulo || !autor) {
        swal({
          title: "Los campos Titulo y Autor son obligatorios",
          text: "Por favor, completa todos los campos antes de guardar los cambios.",
          icon: "warning",
          button: "OK",
        });
        return; // Retornar temprano para evitar guardar los cambios
      }

      // Realizar la acción de guardar cambios
      let oUsuario = new User();
      oUsuario.guardarCambiosLibro(id, titulo, autor, genero);
      revertirCamposAInputsLibroAdmin(id);
    }
  }
}

function obtenerValoresLibro(id) {
  const tituloInput = document.getElementById("tituloInput" + id);
  const titulo = tituloInput.value;

  const autorInput = document.getElementById("autorInput" + id);
  const autor = autorInput.value;

  return { titulo, autor };
}

function convertirCamposAInputsLibroAdmin(id) {
  let tituloElement = document.getElementById("titulo" + id);
  let autorElement = document.getElementById("autor" + id);

  // Obtener el valor actual del título, autor y género
  let tituloValue = tituloElement.textContent || tituloElement.innerText;
  let autorValue = autorElement.textContent || autorElement.innerText;

  // Crear el input para el título
  let tituloInput = document.createElement("input");
  tituloInput.type = "text";
  tituloInput.id = "tituloInput" + id;
  tituloInput.value = tituloValue;

  // Reemplazar el contenido del elemento tituloElement con el input de título
  tituloElement.innerHTML = "";
  tituloElement.appendChild(tituloInput);

  // Crear el input para el autor
  let autorInput = document.createElement("input");
  autorInput.type = "text";
  autorInput.id = "autorInput" + id;
  autorInput.value = autorValue;

  // Reemplazar el contenido del elemento autorElement con el input de autor
  autorElement.innerHTML = "";
  autorElement.appendChild(autorInput);

  // Cambiar el botón de lápiz por el de guardar cambios
  let btn = document.querySelector("button[value='a" + id + "']");
  btn.innerHTML = ""; // Limpiamos el contenido del botón

  // Cambiamos el valor del botón para que sea reconocido como un botón de guardar cambios
  btn.setAttribute("value", "g" + id);

  // Cambiar las clases del botón para reflejar el cambio a un botón de guardar
  btn.classList.remove("btn-info", "fa-pencil");
  btn.classList.add("btn-success", "fa-save");
}

function revertirCamposAInputsLibroAdmin(id) {
  // Recuperar los valores actuales de título, autor y género
  const tituloInput = document.getElementById("tituloInput" + id);
  const autorInput = document.getElementById("autorInput" + id);

  const titulo = tituloInput.value;
  const autor = autorInput.value;

  // Convertir los elementos editables de nuevo a texto
  let tituloElement = document.getElementById("titulo" + id);
  tituloElement.textContent = titulo;

  let autorElement = document.getElementById("autor" + id);
  autorElement.textContent = autor;

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
