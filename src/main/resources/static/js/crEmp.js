// CREAR EMPRENDIMIENTO
// --------------------------------------------
var regEmpNombre = false;
var regEmpDescripcion = false;
var base_url = window.location.origin;

var regNombreTitular = false;
var regDocumentoTitular = false;

// Usar DNI o CUIT
$('#btnUsarCuit, #btnUsarDNI').click(function () {
    $('.usar-dni, .usar-cuit, #btnUsarDNI, #btnUsarCuit').toggleClass('hidden');
    $('.usar-dni, .usar-cuit').find('.form-control').val('');
    $('.usar-dni, .usar-cuit').find('.input-group').removeClass('filled');
    $('#btnAcercaEmp').prop('disabled', true);
    $(this).addClass('hidden');

    regNombreTitular = false;
    regDocumentoTitular = false;
});

// Abrir modal (beneficios de marca registrada)
$('#btnVerBeneficios').click(function () {
    $('#beneficios-marca').removeClass('hidden');
});

$('#btnAcercaEmp, #btnVolverPers').click(function () {
    $('#info-emp, #info-personal').toggleClass('hidden');
});

// Ingresar nombre
$('#regNombreTitular').keyup(function () {
    if ($(this).val() != '') {
        regNombreTitular = true;
    } else {
        regNombreTitular = false;
    }
    checkPers();
});

// Ingresar documento
$('#regDocumentoTitular').keyup(function () {
    if ($(this).val().length == 8) {
        regDocumentoTitular = true;
    } else {
        regDocumentoTitular = false;
    }
    checkPers();
});

function checkPers() {
    if (regNombreTitular && regDocumentoTitular) {
        $('#btnAcercaEmp').prop('disabled', false);
    } else {
        $('#btnAcercaEmp').prop('disabled', true);
    }
}

// Nombre del emprendimiento
$('#regEmpNombre').keyup(function () {
    var name = $(this).val();

    // Mayus/minus y acentos
    var namePattern = /^[a-zA-ZÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ\s]{3,}$/;

    if (namePattern.test(name)) {
        regEmpNombre = true;
    } else {
        regEmpNombre = false;
    }

    $('#regEmpDir').val($(this).val().replace(/ +?/g, ''));
    $('#resEmpDir').val($(this).val());
});

// Descripción del emprendimiento
$('#regEmpDescripcion').keyup(function () {
    var name = $(this).val();
    // Mayus/minus y acentos
    var namePattern = /^[a-zA-ZÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ\s]{3,}$/;

    if (namePattern.test(name)) {
        regEmpDescripcion = true;
    } else {
        regEmpDescripcion = false;
    }
});

$('#regAcerca .form-control').keyup(function () {
    if (regEmpNombre && regEmpDescripcion) {
        $('#btnRegEmp1, #btnRegEmp2').prop('disabled', false);
    } else {
        $('#btnRegEmp1, #btnRegEmp2').prop('disabled', true);
    }
});

// Validar si URL esta disponible al darle "Siguiente"
$('#btnRegEmp1').click(function () {

    const str = $('#regEmpDir').val();
    var empNameLower = str.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();
    $('#regEmpDir').val(empNameLower);

    var option = $('#regEmpDir').val();
    $('#regEmpDirPreview').val('www.granferia.online/' + option);

    $.ajax({
        method: "GET",
        url: base_url + "/sub/dispweb/" + option
    }).done(function (data) {
        if (data == false) {
            // Ocupado
            $('#dirState').removeClass('hidden');
            $('#btnRegEmp2').prop('disabled', true);
        } else {
            // Disponible
            $('#dirState').addClass('hidden');
            $('#btnRegEmp2').prop('disabled', false);
        }
    });
});

$('#regEmpDir').keyup(function(){
    const str = $('#regEmpDir').val();
    var empNameLower = str.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();
    $('#regEmpDir').val(empNameLower);

    var option = $('#regEmpDir').val();
    $('#regEmpDirPreview').val('www.granferia.online/' + option);

    $.ajax({
        method: "GET",
        url: base_url + "/sub/dispweb/" + option
    }).done(function (data) {
        if (data == false) {
            // Ocupado
            $('#dirState').removeClass('hidden');
            $('#btnRegEmp2').prop('disabled', true);
        } else {
            // Disponible
            $('#dirState').addClass('hidden');
            $('#btnRegEmp2').prop('disabled', false);
        }
    });
})

$('#btnRegEmp2').click(function () {
    $('#resEmpDirPreview').val($('#regEmpDirPreview').val());
});

// Elegir categoría
$('#categoriaEmp').change(function () {
    var option = $(this).find('option:selected').val();
    var optionDefault = $(this).find('option').first().val();
    var optionSelected = $("#categoriaEmp option:selected").text();
    $('#resCategoriaEmp').val(optionSelected);
    $('#subCategoriaEmp').empty();
    $('#subCategoriaEmp').addClass('default');

    $.ajax({
        method: "GET",
        url: base_url + "/sub/sub/" + option
    }).done(function (data) {
        $.each(data, function (index, item) {
            $('#subCategoriaEmp').append($("<option></option>").attr("value", item.sub).text(item.sub));
        });
    });

    if ($(this).val() == optionDefault) {
        $('#btnRegEmp5').prop('disabled', true);
        $('#subCategoriaEmp').addClass('hidden');
    } else {
        $('#btnRegEmp5').prop('disabled', false);
        $('#subCategoriaEmp').removeClass('hidden');
    }

    $('#subCategoriaEmp').append('<option selected>Sub categoría (opcional)</option>');
});

var empEmpAddress = false;
var empEmpProvincia = false;
var empEmpLocalidad = false;

// ¿Tiene logo?
$('#regTieneLogo .option-selector input').click(function () {
    if ($(this).prop('id') == 'logoNo') {
        $('#noTieneLogoText').removeClass('hidden');
    } else {
        $('#noTieneLogoText').addClass('hidden');
    }

    if ($(this).prop('id') == 'logoSi') {
        $('#siTieneLogoText').removeClass('hidden');
    } else {
        $('#siTieneLogoText').addClass('hidden');
    }
});

// Elegir provincia y localidad
$('#provinciaEmp').change(function () {
    var option = $('#provinciaEmp option:selected').text().toLowerCase();
    $('#localidadEmp').empty();

    $.ajax({
        method: "GET",
        url: "https://apis.datos.gob.ar/georef/api/departamentos?provincia=" + option + "&max=30"
    }).done(function (data) {
        $.each(data.departamentos, function (index, item) {
            $('#localidadEmp').append($("<option></option>").attr("value", item.nombre).text(item.nombre));
        });
    });

    if ($('#provinciaEmp option:selected').text() == 'Provincia') {
        empEmpProvincia = false;
        $('#localidadEmp').addClass('hidden');
        $(this).addClass('default');
    } else {
        empEmpProvincia = true;
        $('#localidadEmp').removeClass('hidden');
        $(this).removeClass('default');
    }
    $('#localidadEmp').append('<option selected>Localidad</option>');
    checkRegAddress();
});

$('#localidadEmp').change(function () {
    var optionDefault = $(this).find('option').first().val();
    if ($(this).val() == optionDefault) {
        empEmpLocalidad = false;
    } else {
        empEmpLocalidad = true;
    }
    checkRegAddress();
});

// Dirección y código postal
$('#regDireccion .form-control').keyup(function () {
    var dirCalle = $('#dirCalle').val();
    var namePattern = /^[a-zA-ZÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ\s]{4,}$/;

    if (namePattern.test(dirCalle) && $('#dirNum').val().length >= 1 && $('#codPostal').val().length >= 4) {
        empEmpAddress = true;
    } else {
        empEmpAddress = false;
    }
    checkRegAddress();
});

function checkRegAddress() {
    if (empEmpProvincia && empEmpLocalidad && empEmpAddress) {
        $('#btnRegEmp8').prop('disabled', false);
    } else {
        $('#btnRegEmp8').prop('disabled', true);
    }
}

// Elegir privacidad
$('#dirPrivacidad').change(function () {
    if ($('#dirPrivacidad option:selected').text() == 'Visibilidad pública') {
        $('#dirPrivadaHelp').addClass('hidden');
        $('#dirPublicaHelp').removeClass('hidden');
    } else {
        $('#dirPublicaHelp').addClass('hidden');
        $('#dirPrivadaHelp').removeClass('hidden');
    }
});

// Información de contacto
$('#empTel').keyup(function () {
    if ($(this).val().length > 9) {
        $('#btnRegEmp9').prop('disabled', false);
    } else {
        $('#btnRegEmp9').prop('disabled', true);
    }
});

// Saber más
$('#btnSaberMas').click(function(){
    $('#saber-mas, #saber-confirmacion').toggleClass('hidden');
    const id = $('#idUsuario').text();
    $.ajax({
		method: "GET",
		url: base_url + "/rest/sabermas/"+id
	})
});

// Finalizar
$('#btnCrearEmp').click(function(){
    $('.loading-spinner').removeClass('hidden');
});