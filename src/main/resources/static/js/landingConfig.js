// LANDING CONFIG (Editar info y Apariencia)
// --------------------------------------------

// Cancelar plan modal
$('#btnCancelarPlan').click(function () {
    $('#cambiar-plan').removeClass('hidden');
})

// Editar dirección en landingConfig
$('#btnEditDireccion').click(function () {
    $('body, html').scrollTop(0);
    $('#landingConfig').addClass('hidden');
    $('#editDireccion').removeClass('hidden');
});

// Checkear si existe Piso y Depto
$('.address-items .input-group').each(function () {
    var addressInput = $(this).find('.form-control');

    if (addressInput.val() == '') {
        $(this).closest('.input-group').removeClass('filled');
    } else {
        $(this).closest('.input-group').addClass('filled');
    }
});

// Mostrar subcategorias base en la seleccion existente
var catExistente = $('#categoria-seleccionada').text();
var subCatExistente = $('#subcategoria-seleccionada').text();

$.ajax({
    method: "GET",
    url: base_url + "/sub/sub/" + catExistente
}).done(function (data) {
    $.each(data, function (index, item) {
        $('#subCategoriaEmp').append($("<option></option>").attr("value", item.sub).text(item.sub));

        $(function () {
            $('#subCategoriaEmp').val(subCatExistente);
        });

    });
});

// Obtener la localidad seleccionada en el registro
var provinciaExistente = $('#provincia-seleccionada').text();
var localExistente = $('#localidad-seleccionada').text();
var optionLocal = provinciaExistente.toLowerCase();

// Cargar Localidades cuando se carga la pantalla
$.ajax({
    method: "GET",
    url: "https://apis.datos.gob.ar/georef/api/departamentos?provincia=" + optionLocal + "&max=30"
}).done(function (data) {
    $.each(data.departamentos, function (index, item) {
        $('#localidadEmp').append($("<option></option>").attr("value", item.nombre).text(item.nombre));
    });
});

// Cargar la provincia seleccionada
$('#provinciaEmp option').each(function () {
    var copy = $(this).text();

    if (copy == provinciaExistente) {
        $(this).attr('selected', true);
    }
});

// Cargar la localidad seleccionada una vez carga el listado de localidades
setTimeout(function () {
    $('#localidadEmp option').each(function () {
        var copy = $(this).text();
        if (copy == localExistente) {
            $(this).attr('selected', true);
        }
    });
}, 400);

// Cambiar de provincia
$('#provinciaEmp').change(function () {
    $('#localidadEmp').empty();
    xxx = $('#provinciaEmp option:selected').text().toLowerCase();
    console.log(xxx);

    $.ajax({
        method: "GET",
        url: "https://apis.datos.gob.ar/georef/api/departamentos?provincia=" + xxx + "&max=30"
    }).done(function (data) {
        $.each(data.departamentos, function (index, item) {
            $('#localidadEmp').append($("<option></option>").attr("value", item.nombre).text(item.nombre));
        });
    });

    $('#localidadEmp').append('<option selected>Localidad</option>');
});

// Placeholder inputs vacios
$('#landingConfig .form-control').each(function(){
    if($(this).val() == ''){
        $(this).closest('.input-group').removeClass('filled');
    }
});

// Eliminar emprendimiento
$('#eliminarEmprendimiento').change(function(){
    if($(this).prop('checked') == true){
        $('#btnConfirmarEliminar').prop('disabled', false);
    } else {
        $('#btnConfirmarEliminar').prop('disabled', true);
    }
});