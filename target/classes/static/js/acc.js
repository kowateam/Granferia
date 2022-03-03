// INGRESAR
// --------------------------------------------
function checkLogin() {
    var email = $('#logMail').val();
    var emailPattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var pass = $('#logPassword').val();
    var passPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{10,}$/;

    if (emailPattern.test(email) && email !== '' && passPattern.test(pass)) {
        $('#btnIngresar').prop('disabled', false);
    } else {
        $('#btnIngresar').prop('disabled', true);
    }
};

$('#form-ingresar .form-control').keyup(function () {
    checkLogin();
});

// Recuperar contraseña
$('#recMail').keyup(function () {
    var email = $(this).val();
    var emailPattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    if (emailPattern.test(email) && email !== '') {
        $('#btnRecuperar').prop('disabled', false);
    } else {
        $('#btnRecuperar').prop('disabled', true);
    }
});

$('#recPassword').keyup(function () {
    var pass = $(this).val();
    var passPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{10,}$/;

    if (passPattern.test(pass)) {
        $('#btnRestablecer').prop('disabled', false);
    } else {
        $('#btnRestablecer').prop('disabled', true);
    }
});

$('#cta-recuperar, #cta-ingresar').click(function () {
    $('#segment-ingresar, #segment-recuperar').toggleClass('hidden');
});

$('#btnRecuperar').click(function () {
    $('.loading-spinner').removeClass('hidden');
});

var acceptTerms = false;

// CREAR CUENTA
// --------------------------------------------
function checkReg() {
    var email = $('#regMail').val();
    var emailPattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var pass = $('#regPassword').val();
    var passPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{10,}$/;

    if (emailPattern.test(email) && email !== '' && passPattern.test(pass) && acceptTerms) {
        $('#btnReg1').prop('disabled', false);
    } else {
        $('#btnReg1').prop('disabled', true);
    }
};

$('#acceptTerms').change(function () {
    if (this.checked) {
        $(this).prop("checked");
        acceptTerms = true;
    } else {
        acceptTerms = false;
    }

    console.log(acceptTerms);

    checkReg();
});

$('#regPassword').focus(function () {
    $(this).closest('.step-container').find('#passHelp').removeClass('hidden');
    $(this).addClass('x1');
});

$('#step1 .form-control').keyup(function () {
    checkReg();
});

$('#regNombre').keyup(function () {
    var name = $(this).val();
    var namePattern = /^[a-zA-ZÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ\s]{2,}$/;

    if (namePattern.test(name)) {
        $('#btnReg3').prop('disabled', false);
    } else {
        $('#btnReg3').prop('disabled', true);
    }
});

// Pasos (registro)
$('#btnReg1').click(function () {
    setTimeout(function () {
        $('body, html').scrollTop(0);
    }, 600);
});

// Seleccionar avatar
$('.avatars-container .av').click(function () {
    $('.avatars-container .av').removeClass('selected');
    $(this).addClass('selected');
});

$('#regFinalizar').click(function () {
    $('.loading-spinner').removeClass('hidden');
});