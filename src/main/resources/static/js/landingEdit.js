// (NF) Abrir apariencia de emprendimiento
$('.menu-ap').click(function () {
    $('.floating-menu .row').addClass('hidden');
    $('.floating-menu #menu-close-ap').removeClass('hidden');

    $('#landingEdit, #nuevaCarga').addClass('hidden');
    $('#emp-apariencia').removeClass('hidden');
    showClose();
});

// Navegación (bottom) por página
function showClose() {
    $('.floating-menu .row').addClass('hidden');
    $('.floating-menu #menu-close-ap').removeClass('hidden');
    $('body, html').scrollTop(0);
}

// Subir logo
var $imagenLogo = $("#uploadLogo");

function logoLoad() {
    $(this).attr("value", "");
    $('#subirLogo').click();
}

$imagenLogo.change(logoLoad);

// Ajustar scroll cuando se cierra un modal
$('.close-modal').click(function(){
    $('body, html').removeAttr('style');
});

// Cargar fotos de productos
// --------------------------------------------
var $fotoProducto = $("#uploadFotoProductos");

function fotoLoad() {
    $(this).attr("value", "");
    $('#subirFoto').click();
    console.log('fotoLooooooad');
}

$fotoProducto.change(fotoLoad);

// Categorias (selección)
$('#btnCategorias').click(function () {
    $('#elegirCategorias').removeClass('hidden');
    $('#elegirFotos').addClass('hidden');
});

$('#btnBackFotos').click(function () {
    $('#elegirCategorias').addClass('hidden');
    $('#elegirFotos').removeClass('hidden');
});

$('#elegirCategorias .form-check').click(function(){
    $('#btnPublicarPremium').prop('disabled', false);
});

// LANDING EDIT
// --------------------------------------------
// Validar nombre categoría
$('#nuevaCategoria').keyup(function () {
    if ($(this).val().length > 3) {
        $('#btnCrearCategoriaModal').prop('disabled', false);
    } else {
        $('#btnCrearCategoriaModal').prop('disabled', true);
    }
});

// Crear por primera vez
$('#nuevaCategoriaFirst').keyup(function () {
    if ($(this).val().length > 3) {
        $('#btnCrearCategoria').prop('disabled', false);
    } else {
        $('#btnCrearCategoria').prop('disabled', true);
    }
});

// Stats
$('.stat').click(function(){
    $('.stat-description').removeClass('hidden');
    $('.stat').removeClass('active');
    $(this).addClass('active');
});

$('#stat-vistas').click(function(){
    $('.stat-description').text('Estas es la cantidad de vistas que tiene el landing del emprendimiento.');
});

$('#stat-visitantes').click(function(){
    $('.stat-description').text('Esta es la cantidad de usuarios únicos que visitaron tu landing.');
});

$('#stat-calificacion').click(function(){
    $('.stat-description').text('Calificación promedio de los usuarios.');
});

// PRODUCTOS
// --------------------------------------------

// Crear producto firstime (tutorial)
$('#btnCrearProducto').click(function () {
    $('#nuevoProducto, #nuevaCarga').toggleClass('hidden');
    $('header #header-logo, header #emp-details').toggleClass('hidden');
});

// Mostrar / ocultar contenido (Firstime)
var productCounter = $('.productos-counter').length;
var categoryCounter = $('.categorias-counter').length;

console.log('Categorias: ' + categoryCounter + ' / Productos: ' + productCounter);

if (productCounter > 0) {
    $('#empNotFirstTime').removeClass('hidden');
    $('#empFirstTime').addClass('hidden');
    $('.menu-cr-firsttime').addClass('hidden');
} else {
    $('#empFirstTime').removeClass('hidden');
    $('#empNotFirstTime').addClass('hidden');
    $('.menu-cr-firsttime').removeClass('hidden');
}

// Elegir que crear en el modal (producto o servicio)
$('#cargar-nuevo-modal .form-check').click(function () {
    $('#btnCargarPModal').prop('disabled', false);

    if ($(this).attr('id') == 'createCategoria') {
        $('#nombreCategoria').removeClass('hidden');
        $('#btnCargarPModal').prop('disabled', true);
        $('#btnCargarPModal').addClass('hidden');
        $('#btnCrearCategoriaModal').removeClass('hidden');
    } else {
        $('#nombreCategoria').addClass('hidden');
        $('#btnCargarPModal').prop('disabled', false);
        $('#btnCargarPModal').removeClass('hidden');
        $('#btnCrearCategoriaModal').addClass('hidden');
    }
});

// CARGAR FOTOS
// --------------------------------------------
// Carga de fotos (productos o servicios)
$('.upload-photos .photo').click(function () {
    $('.upload-photos .photo').removeClass('active');
    $(this).addClass('active');
});

// CARGAR PRODUCTO O SERVICIO (FORMULARIO)
// --------------------------------------------

// Tipos de validaciones
var prodOServ = false;
var fisiODigital = false;
var camposLlenos = false;
var medioDePago = false;
var tipoStock = false;

$('#optionProdServ').click(function () {
    $(this).removeClass('bottom-divider');
    $('#optionFisDigi').removeClass('hidden');
    prodOServ = true;
    cargaForm();
});

// Producto o servicio
$('#optionProdServ .option').click(function () {
    if ($(this).prop('for') == 'itemproducto') {
        $('#type').text('producto');
    } else {
        $('#type').text('servicio');
    }
});

// Digital o físico
$('#optionFisDigi').click(function () {
    fisiODigital = true;
    cargaForm();
});

$('.option').click(function () {
    if (prodOServ && fisiODigital) {
        $('#prodType').addClass('passed');
    }
});

// Validar todos los campos llenos
var nombreProducto = false;
var descProducto = false;
var precioProducto = true;

$('#nombreProducto').keyup(function () {
    if ($(this).val().length != 0) {
        nombreProducto = true;
    } else {
        nombreProducto = false;
    }
});

$('#descProducto').keyup(function () {
    if ($(this).val().length != 0) {
        descProducto = true;
    } else {
        descProducto = false;
    }
});

// Precio

// Oferta vs Cotización
$('#aCotizar').change(function () {
    if (this.checked) {
        $(this).prop("checked");
        $('#esOferta').prop('disabled', true).prop('checked', false);
        $('#precioProducto').prop('disabled', true);
        $('#prodPrice').addClass('passed');
        precioProducto = true;
    } else {
        $('#esOferta').prop('disabled', false);
        $('#precioProducto').prop('disabled', false);
        $('#prodPrice').removeClass('passed');
        precioProducto = false;
    }

    cargaForm();
});

$('#esOferta').change(function () {
    if (this.checked) {
        $(this).prop("checked");
        $('#aCotizar').prop('disabled', true).prop('checked', false);
        $('#precio-oferta').removeClass('hidden');
        $('#precioProducto').closest('.input-group').find('.form-label').text('Precio de la oferta');
    } else {
        $('#aCotizar').prop('disabled', false);
        $('#precio-oferta').addClass('hidden');
        $('#precio-oferta').removeClass('filled');
        $('#precio-oferta .form-control').val('');
        $('#precioProducto').closest('.input-group').find('.form-label').text('Precio');
    }
});

$('#precioProducto').keyup(function () {
    if ($(this).val() != '') {
        precioProducto = true;
        $('#prodPrice').addClass('passed');
    } else {
        precioProducto = false;
        $('#prodPrice').removeClass('passed');
    }
});

$('#subirItem .form-control').keyup(function () {
    if (nombreProducto && descProducto && precioProducto) {
        camposLlenos = true;
    } else {
        camposLlenos = false;
    }
    cargaForm();
});

$('#subirItem .form-control').keyup(function () {
    if (nombreProducto && descProducto) {
        $('#prodTitle').addClass('passed');
    } else {
        $('#prodTitle').removeClass('passed');
    }
});

// Validar si hay 1 medio de pago
$('#medios-de-pago .form-check').change(function () {
    if ($(this).find('.form-check-input').is(':checked')) {
        medioDePago = true;
    } else {
        medioDePago = false;
    }

    if ($('#medios-de-pago .form-check .form-check-input').is(':checked')) {
        $('#prodPayment').addClass('passed');
    } else {
        $('#prodPayment').removeClass('passed');
    }

    cargaForm();
});

// Validar precio
$('#precioProducto, #precioOferta').keyup(function () {
    if ($(this).prop('id') == 'precioProducto') {
        const number = $(this).val();

        var formatedPrice = new Intl.NumberFormat('es-AR', {
            style: 'currency',
            currency: 'ARS',
        }).format(number);

        $('#preview-precio').text(formatedPrice);
        var text = $('#preview-precio').html();
        text = text.slice(0, -3);
        $('#preview-precio').html('Así lo ven los usuarios: ' + text);
    } else {
        const number = $(this).val();

        var formatedPrice = new Intl.NumberFormat('es-AR', {
            style: 'currency',
            currency: 'ARS',
        }).format(number);

        $('#preview-precio').addClass('hidden');
        $('#preview-oferta').text(formatedPrice);
        var text = $('#preview-oferta').html();
        text = text.slice(0, -3);
        $('#preview-oferta').html(text);
    }

    if ($('#preview-precio').html() == 'Así lo ven los usuarios: $&nbsp;0') {
        $('#preview-precio').addClass('hidden');
        $('#priceHelp').removeClass('hidden');
    } else {
        $('#preview-precio').removeClass('hidden');
        $('#priceHelp').addClass('hidden');
    }
});

// Máximo de unidades (Stock = máx. 30 unidades)
$.fn.inputFilter = function (inputFilter) {
    return this.on("input keydown keyup mousedown mouseup select contextmenu drop", function () {
        if (inputFilter(this.value)) {
            this.oldValue = this.value;
            this.oldSelectionStart = this.selectionStart;
            this.oldSelectionEnd = this.selectionEnd;
        } else if (this.hasOwnProperty("oldValue")) {
            this.value = this.oldValue;
            this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
        } else {
            this.value = "";
        }
    });
};

$("#unidProducto").inputFilter(function (value) {
    return /^\d*$/.test(value) && (value === "" || parseInt(value) <= 30);
});

$('#unidProducto').keyup(function () {
    if ($(this).val() != '') {
        $('#prodStock').addClass('passed');
        tipoStock = true;
    } else {
        $('#prodStock').removeClass('passed');
        tipoStock = false;
    }
});

// Elegir tipo de stock
$('#stock-type').change(function () {

    if ($(this).val() == 'disponible' || $(this).val() == 'aPedido') {
        $('#campoUnidades').removeClass('hidden');
    } else {
        $('#campoUnidades').addClass('hidden');
        $('#unidProducto').val('');
        $('#campoUnidades').removeClass('filled');
    }

    if ($(this).val() == 'aPedido') {
        $('#product-units .col-9').removeClass('hidden');
        $('#product-units .col-3').removeClass('col-12');
        $('#unidProducto').closest('.input-group').find('.form-label').text('Ud.');
    } else {
        $('#product-units .col-9').addClass('hidden');
        $('#product-units .col-3').addClass('col-12');
        $('#unidProducto').closest('.input-group').find('.form-label').text('Unidades');
    }

    if ($(this).val() == 'sinStock') {
        $('#prodStock').addClass('passed');
        tipoStock = true;
    } else {
        $('#prodStock').removeClass('passed');
        tipoStock = false;
    }

    cargaForm();
});

// Validar la carga de producto o servicio
var formCarga = false;
$('#nuevoProducto .form-control').each(function () {
    $(this).keyup(function () {
        if ($(this).val != '') {
            formCarga = true;
        } else {
            formCarga = false;
        }
    })
});

// Validar toda la carga de items
function cargaForm() {
    if (prodOServ && fisiODigital && camposLlenos && medioDePago && tipoStock && precioProducto) {
        $('#btnCargarItem').prop('disabled', false);
    } else {
        $('#btnCargarItem').prop('disabled', true);
    }
}

// Botón "Pedidos"
/* var cantidadPedidos = $('#btnEmpPedidos .pedidos-count').text();

if (cantidadPedidos == 0) {
    $('#btnEmpPedidos').addClass('disabled');
} else {
    $('#btnEmpPedidos').removeClass('disabled');
} */

// Cantidad de productos en categorias
$('.emp').each(function () {
    var cdadItems = $(this).find('.slide-card').length;
    $(this).find('.ps-created').text(cdadItems);
});

// Elegir theme
$('.theme-selector label').click(function () {
    var colorSelected = $(this).attr('id');
    var colorTheme = $(this).attr('class');

    $('body').prop('class', '');
    $('body').addClass(colorTheme + ' ' + colorSelected);
});

// VARIABLES 
// --------------------------------------------

// Crear, editar grupo de items
$('#btnCrearVarTitle').click(function () {
    var varTitle = $('#prodVariableTitulo').val();
    $('.var-title span').text(varTitle);
    $('#crear-var-title, .item-variables, #crear-var-item').toggleClass('hidden');
    $('#btnCrearFoto').prop('disabled', true);
});

// Borrar de la session
$('#btnCrearFoto ,#btnBorrarCatego').click(function () {
    $.ajax({
        method: "GET",
        url: base_url + "/categoria/quitardelasession/"
    });

    $('.editar-var').removeClass('hidden');

});

// Crear Valores (para variables)
$('#btnCrearVarItem').click(function () {
    var varItem = $('#prodVariableItem').val();
    var variableId = $('#variable-id').text();

    $.ajax({
        method: "GET",
        url: base_url + "/categoria/cargarvalor/" + variableId + "/" + varItem
    });

    $('#prodVariableItem').val('');
    $('#prodVariableItem').closest('.input-group').removeClass('filled');

    // Crear listado de items
    $('.var-item').append('<li class="x1">' + varItem + '</li>');
    $('.var-item').removeClass('hidden');
});


// Validar título de variables
$('#prodVariableTitulo').keyup(function () {
    if ($(this).val() != '') {
        $('#btnCrearVarTitle').prop('disabled', false);
    } else {
        $('#btnCrearVarTitle').prop('disabled', true);
    }
});

// Validar opciones de variables
$('#prodVariableItem').keyup(function () {
    if ($(this).val() != '') {
        $('#btnCrearVarItem').prop('disabled', false);
    } else {
        $('#btnCrearVarItem').prop('disabled', true);
    }
});

// Borrar Foto
$('.delete-file').click(function () {
    var id = $('#fotoid').text();
    $.ajax({
        method: "GET",
        url: base_url + "/rest/borrarfoto/" + id
    });

    setTimeout('location.reload()', 200);
});

// (Admin) Contactar al emprendimiento
$('#btnAdminContactar').click(function(){
    $('#contacto-modal').removeClass('hidden');
});

// Borrar producto o servicio
$('#btnBorrarItem').click(function () {
    $('#eliminar-item').removeClass('hidden');
});

$('#borrarItem').click(function () {
    var id = $('#idProducto').val();
     var idEmprendimiento = $('#idEmprendimiento').val();
    $.ajax({
        method: "GET",
        url: base_url + "/rest/borrarproducto/" + id + "/" + idEmprendimiento
    });

    window.location.replace(base_url);
});