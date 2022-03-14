// CREAR NUEVO
// --------------------------------------------

// Permitir eliminar si hay más de 1
if(categoryCounter == '1') {
    $('.category-delete').addClass('hidden');
} else {
    $('.category-delete').removeClass('hidden');
}

// "Crear nuevo" por primera vez
$('#btnCargarFirst, .menu-cr-firsttime').click(function () {
    $('#landingEdit').addClass('hidden');
    $('#nuevaCarga').removeClass('hidden');
    showClose();
    $('body, html').scrollTop(0);
});

// PREMIUM Cargar nuevo
$('.menu-cr').click(function () {
    $('#cargar-nuevo-modal').removeClass('hidden');
});

// "Crear nuevo" PREMIUM > Modal para elegir producto o categoria
// PREMIUM "Cargar foto" > Modal para crear categoria nueva
$('#btnCargarP, #btnNuevaCategoria').click(function () {
    $('#cargar-nuevo-modal').removeClass('hidden');
});

// "Crear nuevo" > Pagina de producto
$('#btnCargarF, #btnCargarPModal, .menu-cr-free').click(function () {
    $('#landingEdit').addClass('hidden');
    $('#nuevaCarga').addClass('hidden');
    $('#nuevoProducto').removeClass('hidden');
    $('header #header-logo, header #emp-details').toggleClass('hidden');
    showClose();
    $('body, html').scrollTop(0);
});

// CATEGORIAS
// --------------------------------------------

var catIndex;

$('.category-name .icon').click(function () {
    // Obtiene el número index
    catIndex = $(this).closest('.emp').find('.number-index').text();
    // console.log('icon clicked: ' + catIndex);

    $(this).closest('.emp').find('.modal-container').removeClass('hidden');
    $('#editar-categoría').removeClass('hidden');
    var catName = $(this).closest('.category-name').find('.name').text();
    $('#nombreCategoriaEditar').val(catName);
});

// Asignar categoria a producto - Modificar categoria
$('.btnGuardarCategoria').click(function () {
    var nombre = $('#nombreCategoriaEditar' + catIndex).val();
    var idCategoria = $('#categoria-id' + catIndex).text();
    $.ajax({
        method: "GET",
        url: base_url + "/categoria/editar/" + idCategoria + "/" + nombre
    });

    setTimeout('location.reload()', 100);
});


// Modificar categoria
// $('#btnGuardarCategoria').click(function () {
//     var nombre = $('#nombreCategoriaEditar').val();
//     var idCategoria = $('#categoria-id').text();
//     $.ajax({
//         method: "GET",
//         url: base_url + "/categoria/editar/" + idCategoria + "/" + nombre
//     });

//     setTimeout('location.reload()', 100);
// });

// Crear categoria desde modal
var nuevaCateValor;
$('#nuevaCategoria').keyup(function () {
    nuevaCateValor = $(this).val();
});

$('#btnCrearCategoriaModal').click(function () {
    var id = $('#idEmprendimiento').val();
    $.ajax({
        method: "GET",
        url: base_url + "/categoria/subir/" + nuevaCateValor + "/" + id
    });

    setTimeout('location.reload()', 100);
});

// Eliminar categoria (checkbox)

$('.eliminarCategoria').click(function () {
    var eliminarCategoria = '#eliminarCategoria' + catIndex;
    
    if ($(eliminarCategoria).change()) {
        if (this.checked) {
            $('.btnEliminarCategoria, .btnGuardarCategoria').toggleClass('hidden');
        } else {
            $('.btnEliminarCategoria, .btnGuardarCategoria').toggleClass('hidden');
        }
    }
});

$('.btnEliminarCategoria').click(function () {
    var id = $('#categoria-id'+ catIndex).text();
    console.log('id de la categoria: ' + id);

    $.ajax({
        method: "GET",
        url: base_url + "/categoria/eliminarcategoria/" + id
    });
    
    setTimeout('location.reload()', 100);
});









// Eliminar todas las categorias y asignar productos a 1 (una)
$('#eliminarCategorias').click(function () {
    var varItem = $('#idEmprendimiento').val();
    $.ajax({
        method: "GET",
        url: base_url + "/categoria/eliminarcategoria/" + varItem
    });

    setTimeout('location.reload()', 100);
});