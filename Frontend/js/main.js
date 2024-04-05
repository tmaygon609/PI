$(document).ready(function () {
  // Submenu para el menú de la barra lateral
  $(".btn-sideBar-SubMenu").on("click", function (e) {
    e.preventDefault();
    var SubMenu = $(this).next("ul");
    var iconBtn = $(this).children(".zmdi-caret-down");
    if (SubMenu.hasClass("show-sideBar-SubMenu")) {
      iconBtn.removeClass("zmdi-hc-rotate-180");
      SubMenu.removeClass("show-sideBar-SubMenu");
    } else {
      iconBtn.addClass("zmdi-hc-rotate-180");
      SubMenu.addClass("show-sideBar-SubMenu");
    }
  });

  // Menu lateral se muestra o se oculta
  $(".btn-menu-dashboard").on("click", function (e) {
    e.preventDefault();
    var body = $(".dashboard-contentPage");
    var sidebar = $(".dashboard-sideBar");
    // icono izquierda derecha
    var icon = $(this).find("i");
    if (sidebar.css("pointer-events") == "none") {
      body.removeClass("no-paddin-left");
      sidebar.removeClass("hide-sidebar").addClass("show-sidebar");
      // reemplazar icono derecha a izquierda
      icon.removeClass("fa-chevron-right").addClass("fa-chevron-left");
    } else {
      body.addClass("no-paddin-left");
      sidebar.addClass("hide-sidebar").removeClass("show-sidebar");
      //reemplazar icono izquierda a derecha
      icon.removeClass("fa-chevron-left").addClass("fa-chevron-right");
    }
  });
});
