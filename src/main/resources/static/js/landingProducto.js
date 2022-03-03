$('#btnInfoContacto').click(function(){
    $('#contacto-usuario').removeClass('hidden');
});

// Mostrar la cantidad de productos disponibles (dropdown)
var x = 1;
var stockCantidad = $('#stock-cantidad').text();

setInterval(function () {
    var lengthOption = $('#prodCantidad option').length;

    if (lengthOption < stockCantidad) {
        $('<option value="' + x + '">' + x + '</option>').appendTo('#prodCantidad');
        x++;
    }
}, 1);

setTimeout(function () {
    $('#prodCantidad option:first-child').attr('selected', true);
}, stockCantidad * 100);

// Calcular el total en la página de producto
var cantidadProductos = 1;
$('#precio-total').text($('#precio-subtotal').text());
$('#precio-total-draft, #precio-subtotal-draft').val($('#precio-subtotal').text());
var precioSubtotal = $('#precio-subtotal').text().replace(/\./g, "");

$('#prodCantidad').change(function () {
    $('#prodCantidad option').attr('selected', false);
    $(this).find(':selected').attr('selected', true);
    cantidadProductos = $('#prodCantidad').find(':selected').val();
    $('#precio-total').text(precioSubtotal * cantidadProductos);

    $('#precio-total').each(function () {
        const number = $(this).html();

        var formatedPrice = new Intl.NumberFormat('es-AR', {
            style: 'currency',
            currency: 'ARS',
        }).format(number);

        $(this).text(formatedPrice);
        var text = $(this).html();
        text = text.slice(1, -3);
        $(this).html(text);
    });

    $('#precio-subtotal-draft').val($('#precio-subtotal').text().replace(/\./g, ""));
    $('#precio-total-draft').val($('#precio-total').text().replace(/\./g, ""));
});

var priceConfirmacion = $('#confirmation-total-draft').text();
$('.price-confirmation').text(priceConfirmacion);

// Mostrar "unidad" / "unidades"
var prodCantidad = $('#prod-cantidad').text();
if (prodCantidad == '1') {
    $('.units').text('unidad');
} else {
    $('.units').text('unidades');
}

// Formatear precio en la página de producto
$('.price').each(function () {
    const number = $(this).html();

    var formatedPrice = new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
    }).format(number);

    $(this).text(formatedPrice);
    var text = $(this).html();
    text = text.slice(1, -3);
    $(this).html(text);
});

$('.price-confirmation').each(function () {
    const number = $(this).text();

    var formatedPrice = new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
    }).format(number);

    $(this).text(formatedPrice);
    var text = $(this).text();
    text = text.slice(1, -3);
    $(this).text(text);
});

// Lo quiero: Información de contacto
$('#btnLoQuieroModal, #btnMostrarPago').click(function () {
    $('#pago-entrega').removeClass('hidden');
});

// Guardar Telefono
$('#btnMostrarPago').click(function () {
    var id = $('#idusuario').text();
    var celular = $('#celular').val();
    $.ajax({
        method: "GET",
        url: base_url + "/rest/guardarcelular/" + id + "/" + celular
    })
});

// Una sola foto de producto
var cantidadFotos = $('.emp-product-page .slide-row .slide-card').length;
console.log(cantidadFotos);

if (cantidadFotos == 1) {
    $('.emp-product-page .slide-row .slide-card').addClass('full--width');
} else {
    $('.emp-product-page .slide-row .slide-card').removeClass('full--width');
}

if (cantidadFotos == 3) {
    $('.emp-product-page .slide-row .content').addClass('photos-3');
}

if (cantidadFotos == 4) {
    $('.emp-product-page .slide-row .content').addClass('photos-4');
}

if (cantidadFotos == 5) {
    $('.emp-product-page .slide-row .content').addClass('photos-5');
}

if (cantidadFotos == 6) {
    $('.emp-product-page .slide-row .content').addClass('photos-6');
}