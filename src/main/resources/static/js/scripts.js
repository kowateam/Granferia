var base_url = window.location.origin;

// Splash screen
$('#loading-short').delay(1300).fadeOut(300);
$('#loading-product').delay(600).fadeOut(500);
$('.loading').delay(5000).fadeOut(300);

// Cookies
$('#btnAcceptCookies').click(function () {
	$('.cookies').addClass('hidden');
});

// BUSCAR
// --------------------------------------------
$('#emp-results').text($('.emp-container .emp').length);

$("#filtroBuscar").on("keyup", function () {
	var value = this.value.trim();
	$(".emp-container .emp").show().filter(function () {
		return $(this).text().toLowerCase().trim().indexOf(value) == -1;
	}).hide();

	var filtered = $('.emp-container .emp').filter(function () {
		return $(this).css('display') == 'none';
	}
	).length;

	var results = $('.emp-container .emp').length;
	finalResults = results - filtered;
	$('#emp-results').text(finalResults);

});

$('.menu-s').click(function () {
	$('.search-container, .one-item').removeClass('hidden');
	$('#segment-main').addClass('vh');
	$('body').css('overflow-y', 'hidden');
});

$('.close-search, #btnAplicarFiltros').click(function () {
	$('body').css('overflow-y', 'auto');
});

$('.close-search, #btnAplicarFiltros').click(function () {
	$('.search-container').addClass('hidden');
	$('#segment-main').removeClass('vh');
});

$('#filtroBuscar').keydown(function () {
	if ($('#filtroBuscar').val().length >= 2) {
		$('#btnAplicarFiltros').prop('disabled', false);
	} else {
		$('#btnAplicarFiltros').prop('disabled', true);
	}

	if ($('#emp-results').text() == 0) {
		$('.no-results').removeClass('hidden');
	} else {
		$('.no-results').addClass('hidden');
	}

	$('.no-results .typed').text($(this).val());
});

// $('#btnFiltrosAvanzados, #btnFiltrosSimple').click(function () {
// 	$('.banner .undeline').toggleClass('hidden');
// 	$('.search-advanced').toggleClass('hidden');
// 	$('#btnFiltrosAvanzados, #btnFiltrosSimple').toggleClass('hidden');
// });



// NAVEGACIÓN FLOTANTE
// --------------------------------------------
var left = $('.main-container .segment').width();

$('.main-container').scrollLeft(left);

function openProfile() {
	var left = $('.main-container .segment').width();
	$('.main-container').scrollLeft(left * 2);

	$('#segment-main').addClass('vh');
	$('.floating-menu .row').addClass('hidden');
	$('.floating-menu #menu-profile').removeClass('hidden');
}

// (NF) Abrir acceder
$('.menu-a, .access-bar-overlay').click(function () {
	$('.access-bar').toggleClass('hidden');
	$('.access-bar-overlay').toggleClass('hidden');
});

// (NF) Abrir perfil
$('.menu-p').click(function () {
	$('.main-container').scrollLeft(left * 2);
	$('.floating-menu .row').addClass('hidden');
	$('.floating-menu #menu-profile').removeClass('hidden');
	$('.container33').addClass('hidden');
	setTimeout(function () {
		$('#segment-main').addClass('vh');
		$('body, html').scrollTop(0);
	}, 600);
});

// (NF) Abrir settings
$('.profile-settings').click(function () {
	$('.floating-menu .row').addClass('hidden');
	$('#section-profile, #section-profileSettings').toggleClass('hidden');
	$('#back-profile').removeClass('hidden');

	setTimeout(function () {
		$('#segment-main').addClass('vh');
		$('body, html').scrollTop(0);
	}, 600);
});

$('#back-profile').click(function () {
	$('.floating-menu .row').addClass('hidden');
	$('#section-profile, #section-profileSettings').toggleClass('hidden');
	$('#menu-profile').removeClass('hidden');
	$('body, html').scrollTop(0);
});

// (NF) Abrir menu
$('.menu-m').click(function () {
	$('.main-container').scrollLeft(0);
	$('.floating-menu .row').addClass('hidden');
	$('.floating-menu #menu-close').removeClass('hidden');

	setTimeout(function () {
		$('#segment-main').addClass('vh');
		$('body, html').scrollTop(0);
	}, 300);
});

// (NF) Ir al home
$('.menu-h, .m-close, header .logo').click(function () {
	$('.floating-menu .row').addClass('hidden');
	$('.floating-menu #main-menu').removeClass('hidden');
	$('#segment-main').removeClass('vh');
	$('.main-container').scrollLeft(left);
	$('.container33').removeClass('hidden');
});

// Ocultar banner / Guardar crear emp
$('.btnEmpMasTarde').click(function () {
	$(this).closest('#main-banner').addClass('hidden');
	var id = $('#iduser').text();
	$.ajax({
		method: "GET",
		url: base_url + "/categoria/empmastarde/" + id
	});

	$.ajax({
		method: "GET",
		url: base_url + "/sub/guardarcrearemp/" + id
	});
});

// CARDS
// --------------------------------------------
// Mostrar / Ocultar detalles de cards
$('.slide-card .show-details').click(function () {
	console.log("toco")
	$(this).addClass('hidden');
	$(this).parent('.slide-card').find('.slide-details').removeClass('hidden');
});

$('.slide-card .close').click(function () {
	$(this).closest('.slide-card').find('.slide-details').addClass('hidden');
	$(this).closest('.slide-card').find('.show-details').removeClass('hidden');
});


$('.slide-card .close').click(function () {
	$(this).closest('.slide-card').find('.slide-details').addClass('hidden');
	$(this).closest('.slide-card').find('.show-details').removeClass('hidden');
});

// Guardar
$('.slide-details .save').click(function () {
	$(this).find('img').toggleClass('hidden');
});

// FORM-CONTROL (INPUTS)
// --------------------------------------------
$('.form-control').focus(function () {
	$(this).closest('.input-group').addClass('focused');
});

$('.form-control').focusout(function () {
	$(this).closest('.input-group').removeClass('focused');
	$(this).closest('.input-group').removeClass('filled');

	if ($(this).val()) {
		$(this).closest('.input-group').addClass('filled');
	}
});

$('#step1 .form-control').keydown(function (event) {
	if (event.keyCode == 13) {
		event.preventDefault();
		return false;
	}
});

// FORM-CONTROL (RADIO)
// --------------------------------------------
$('.form-check').click(function () {
	$('.form-option').removeClass('checked');
	$(this).find('.form-option').addClass('checked');
});

// Step by step (Siguiente)
$('.btn-step').click(function () {
	$('body, html').scrollTop(0);
	$(this).closest('.step-container').addClass('hidden');
	$(this).closest('.step-container').next().removeClass('hidden');
});

// Step by step (Volver)
$('.step-container .btn-back').click(function () {
	$('body, html').scrollTop(0);
	$(this).closest('.step-container').addClass('hidden');
	$(this).closest('.step-container').prev().removeClass('hidden');
	$('.input-message').addClass('hidden');
});

// Mostrar/Ocultar pass
$('.icon-view-show').click(function () {
	$(this).addClass('hidden');
	$(this).closest('.input-group').find('.icon-view-hide').removeClass('hidden');
	$(this).closest('.input-group').find('.form-control').attr('type', 'text');
	$(this).closest('.input-group').find('.form-control').focus();
});

$('.icon-view-hide').click(function () {
	$(this).addClass('hidden');
	$(this).closest('.input-group').find('.icon-view-show').removeClass('hidden');
	$(this).closest('.input-group').find('.form-control').attr('type', 'password');
	$(this).closest('.input-group').find('.form-control').focus();
});

// OPTION SELECTOR
// --------------------------------------------
$('.option-selector .option').click(function () {

	$(this).closest('.step-container, .option-group').find('.option').removeClass('selected');
	$(this).addClass('selected');

	var validateOption = $(this).closest('.step-container').find('.option-selector input');
	var validatedBtn = $(this).closest('.step-container').find('.btn-step');

	if (validateOption.is(':checked')) {
		validatedBtn.prop('disabled', false);
	} else {
		validatedBtn.prop('disabled', true);
	}
});

// Seleccionar option
$('.form-check').click(function () {
	$(this).closest('.option-group').find('.form-option').removeClass('checked');
	$(this).find('.form-option').addClass('checked');
});

// SELECT DROPDOWN
// --------------------------------------------
$('.form-select').change(function () {
	var optionDefault = $(this).find('option').first().val();

	if ($(this).val() == optionDefault) {
		$(this).addClass('default');
	} else {
		$(this).removeClass('default');
	}
});

// PRODUCTOS
// --------------------------------------------
$('.head-details').click(function () {
	$(this).closest('.accordion').toggleClass('open');
});

$('.emp-product-page .slide-card').click(function () {
	var photoOpened = $(this).css('background-image').replace(/^url\(['"](.+)['"]\)/, '$1');;
	$('.modal-image .photo').attr('src', photoOpened);
	$('.modal-image').removeClass('hidden');
	$('body').css({
		'overflow-y': 'auto',
		'overflow-x': 'hidden'
	});
});

$('.modal-image .close-photo').click(function () {
	$('.modal-image').addClass('hidden');
	$('body').css({
		'overflow-y': 'auto',
		'overflow-x': 'hidden'
	});
});

var commentsLength = $('.row-container .section').length;
$('#section-length').text(commentsLength);

// Mostrar cantidad de guardados
var saved = $('.grid-column').length;

if (saved > 0) {
	$('.saved-count').text('(' + saved + ')');
} else {
	$('.saved-count').text('');
}

// USUARIO
// --------------------------------------------
// Mostrar cantidad de pedidos (usuarios)
var askedUser = $('#pedidos-usuario .asked-item').length;

if (askedUser > 0) {
	$('#pedidos-usuario .asked-count').text('(' + askedUser + ')');
} else {
	$('#pedidos-usuario .asked-count').text('');
}

// Mostrar cantidad de historial (usuarios)
var histoUser = $('#pedidos-usuario .historic-item').length;

if (histoUser > 0) {
	$('#pedidos-usuario .historic-count').text('(' + histoUser + ')');
} else {
	$('#pedidos-usuario .historic-count').text('');
}

// EMPRENDIMIENTO
// --------------------------------------------

// Mostrar cantidad de pedidos (emprendimientos)
var askedEmp = $('#pedidos-emprendedor .asked-item').length;

if (askedEmp > 0) {
	$('#pedidos-emprendedor .asked-count').text('(' + askedEmp + ')');
} else {
	$('#pedidos-emprendedor .asked-count').text('');
}

// Mostrar cantidad de historial (usuarios)
var histoEmp = $('#pedidos-emprendedor .historic-item').length;

if (histoEmp > 0) {
	$('#pedidos-emprendedor .historic-count').text('(' + histoEmp + ')');
} else {
	$('#pedidos-emprendedor .historic-count').text('');
}

// ADMIN
// --------------------------------------------

// Mostrar cantidad de historial
var historialAdmin = $('.emp.historic').length;

if (historialAdmin > 0) {
	$('.hist-count').text('(' + historialAdmin + ')');
} else {
	$('.hist-count').text('');
}

// MODALES
// --------------------------------------------
// Abrir avartars
$('.profile-page .avatar.editable').click(function () {
	$('#select-avatar').removeClass('hidden');
	$('body').css({
		'overflow-y': 'auto',
		'overflow-x': 'hidden'
	});
});

// Cerrar todos los modales
$('.close-modal').click(function () {
	$(this).closest('.modal-container').addClass('hidden');
	$('body').css({
		'overflow-y': 'auto',
		'overflow-x': 'hidden'
	});
	$('#step-thanks').addClass('hidden');
	$('#step-welcome').removeClass('hidden');
});

// Abrir motivo de rechazo en el modal
$('.emp .modal-details').click(function () {
	$('#motivo-rechazo').removeClass('hidden');
	$('body').css({
		'overflow-y': 'auto',
		'overflow-x': 'hidden'
	});

	var reason = $(this).closest('.emp').find('.motivo-rechazo').text();
	$('.modal-content .reason').text(reason);
});

// Mostar solo emprendimientos con productos
$('.emp-container .emp').each(function () {
	var empConProduct = $(this).find('.prod').length;

	if (empConProduct == 0) {
		$(this).remove();
	}

	$('#emp-results').text($('.emp-container .emp').length);
});

// AJUSTES DE USUARIO
// --------------------------------------------

var empAvailable = $('#section-profile .emp-section .emp-resume').length;
var empValidating = $('#section-profile .emp-section .emp-resume.validating').length;
var empSize = empAvailable - empValidating;

if(empSize >= 2) {
	$('.crear-emp-first').addClass('hidden');
	$('.crear-no-emp').removeClass('hidden');
} 

if(empSize < 2) {
	$('.crear-emp-first').removeClass('hidden');
	$('.crear-no-emp').addClass('hidden');
}

var telWappUser;

if ($('#section-profileSettings #telWappUser').prop('checked') == 'checked') {
	telWappUser = true;
} else {
	telWappUser = false;
}

$('#section-profileSettings #telWappUser').change(function () {
	if ($(this).prop('checked') != telWappUser) {
		$('#btnGuardarAjustes').prop('disabled', false);
	} else {
		$('#btnGuardarAjustes').prop('disabled', true);
	}
});

$('#section-profileSettings .editable').each(function () {
	var defaultVal = $(this).val();

	$(this).keyup(function () {
		if ($(this).val() != defaultVal) {
			$('#btnGuardarAjustes').prop('disabled', false);
		} else {
			$('#btnGuardarAjustes').prop('disabled', true);
		}
	});
});

function hasUpperCase(str) {
	return (/[A-Z]/.test(str));
}

function hasLowerCase(str) {
	return (/[a-z]/.test(str));
}

function hasNumber(str) {
	return /\d/.test(str);
}

// Contraseña cumple requisitos
$('#changePassword, #regPassword').keyup(function () {
	// Validar cantidad
	if ($(this).val().length >= 10) {
		$('#passCaract').addClass('passed');
	} else {
		$('#passCaract').removeClass('passed');
	}

	var passVal = $(this).val();

	// Validar uso de mayuscula
	if (hasUpperCase(passVal) == true) {
		$('#passMayus').addClass('passed');
	} else {
		$('#passMayus').removeClass('passed');
	}

	// Validar uso de mayuscula
	if (hasLowerCase(passVal) == true) {
		$('#passMinus').addClass('passed');
	} else {
		$('#passMinus').removeClass('passed');
	}

	// Validar uso de número
	if (hasNumber(passVal) == true) {
		$('#passNumber').addClass('passed');
	} else {
		$('#passNumber').removeClass('passed');
	}
});

// Validar cambio de contraseña
$('#changePassword').keyup(function () {
	if ($('#passCaract').hasClass('passed') && $('#passMayus').hasClass('passed') && $('#passMinus').hasClass('passed') && $('#passNumber').hasClass('passed')) {
		$('#btnGuardarAjustes').prop('disabled', false);
	} else {
		$('#btnGuardarAjustes').prop('disabled', true);
	}
});

// AVATAR
// --------------------------------------------
$('.avatars-container .av').click(function () {
	$('.avatars-container .av').removeClass('selected');
	$(this).addClass('selected');
});

// Seleccionar avatar actual
var avatarSmall = $('.profile-page .avatar').css('background-image');
$('#select-avatar .av').each(function () {
	if ($(this).css('background-image') === avatarSmall) {
		$('.avatars-container .av').removeClass('selected');
		$(this).addClass('selected');

		return false;
	}
});

$('#avatarConfirmar').click(function () {
	window.location.href = base_url + "/emp/devolverperfil";
});

// ADMIN: Autorizar - Rechazar Emprendimiento
// --------------------------------------------
$('#rechazarEmp').click(function () {
	$('#rechEmp').removeClass('hidden');
	$('#aplicarCambiosEmp').prop('disabled', true);
});

$('#reachazoText').keydown(function () {
	if ($('#reachazoText').val().length >= 6) {
		$('#aplicarCambiosEmp').prop('disabled', false);
	} else {
		$('#aplicarCambiosEmp').prop('disabled', true);
	}
});

$('#aceptarEmp').click(function () {
	$('#rechEmp').addClass('hidden');
	$('#reachazoText').val('');
	$(this).closest('form').find('#rechEmp').removeClass('filled');
	$('#aplicarCambiosEmp').prop('disabled', false);
});

// Limpiar "Volver al perfil"
$('.profile-clear').click(function () {
	$.ajax({
		method: "GET",
		url: base_url + "/sub/cambiar"
	})
});

// Elegir membresia
$('.btn-open-details, .membership-details .btn-close-details').click(function () {
	$('.floating-menu').toggleClass('hidden');
	$('.membership-details .text-content').addClass('hidden');
	$('.membership-selector, .membership-details').toggleClass('hidden');
});

$('#btnDetallesGratis').click(function () {
	$('.membership-details #detalles-gratis').removeClass('hidden');
});

$('#btnPremium').click(function () {
	$('.membership-details #detalles-premium').removeClass('hidden');
});

$('#btnAvanzado').click(function () {
	$('.membership-details #detalles-avanzado').removeClass('hidden');
});

// Mostrar estados de los pedidos
$('#btnListadoEstados').click(function () {
	$('#estados-pedidos').toggleClass('hidden');
});

$('#estados-pedidos .close-modal').click(function () {
	$('#estados-pedidos').addClass('hidden');
});

// Evaluar emprendimiento (1)
$('#btnQuestionEmpezar, #btnBackWelcome').click(function () {
	$('#step-welcome, #step-1').toggleClass('hidden');
});

$('#step-1 .rate-item input').click(function () {
	$('#step-1 .rate-item label').removeClass('active');
	$(this).closest('.rate-item').find('label').addClass('active');
	$('#btnQuestion1').prop('disabled', false);

	// Mostrar pregunta 1
	if ($(this).prop('id') == 'q1rate1') {
		$('#q1rate1').closest('.question-container').find('.input-group').removeClass('hidden');

	} else {
		$('#q1rate1').closest('.question-container').find('.input-group').addClass('hidden');
		$('#q1rate1').closest('.question-container').find('.input-group').removeClass('filled');
		$('#btnQuestion1').prop('disabled', false);
		$('#q1Razones').val('');
	}

	if ($(this).prop('id') == 'q1rate2') {
		$('#q1rate1, #q1rate2').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q1rate3') {
		$('#q1rate1, #q1rate2, #q1rate3').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q1rate4') {
		$('#q1rate1, #q1rate2, #q1rate3, #q1rate4').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q1rate5') {
		$('#q1rate1, #q1rate2, #q1rate3, #q1rate4, #q1rate5').closest('.rate-item').find('label').addClass('active');
	}
});

/* // Validar la razón de la pregunta 1
$('#q1Razones').keyup(function () {
	if ($(this).val().length >= 4) {
		$('#btnQuestion1').prop('disabled', false);
	} else {
		$('#btnQuestion1').prop('disabled', true);
	}
}); */

$('#btnQuestion1, #btnBackQuestion1').click(function () {
	$('#step-1, #step-2').toggleClass('hidden');
});
$('#btnQuestion2, #btnBackQuestion2').click(function () {
	$('#step-2, #step-3').toggleClass('hidden');
});

$('#btnQuestion3').click(function () {
	$('#step-3, #step-thanks').toggleClass('hidden');
});

// Evaluar emprendimiento (2)
$('#step-2 .rate-item input').click(function () {
	$('#step-2 .rate-item label').removeClass('active');
	$(this).closest('.rate-item').find('label').addClass('active');
	$('#btnQuestion2').prop('disabled', false);

	if ($(this).prop('id') == 'q2rate1') {
		$('#q2rate1').closest('.question-container').find('.input-group').removeClass('hidden');
		
	} else {
		$('#q2rate1').closest('.question-container').find('.input-group').addClass('hidden');
		$('#q2rate1').closest('.question-container').find('.input-group').removeClass('filled');
		$('#btnQuestion2').prop('disabled', false);
		$('#q2Razones').val('');
	}

	if ($(this).prop('id') == 'q2rate2') {
		$('#q2rate1, #q2rate2').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q2rate3') {
		$('#q2rate1, #q2rate2, #q2rate3').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q2rate4') {
		$('#q2rate1, #q2rate2, #q2rate3, #q2rate4').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q2rate5') {
		$('#q2rate1, #q2rate2, #q2rate3, #q2rate4, #q2rate5').closest('.rate-item').find('label').addClass('active');
	}
});
// Evaluar emprendimiento (3)
$('#step-3 .rate-item input').click(function () {
	$('#step-3 .rate-item label').removeClass('active');
	$(this).closest('.rate-item').find('label').addClass('active');
	$('#btnQuestion3').prop('disabled', false);

	if ($(this).prop('id') == 'q3rate1') {
		$('#q3rate1').closest('.question-container').find('.input-group').removeClass('hidden');
	
	} else {
		$('#q3rate1').closest('.question-container').find('.input-group').addClass('hidden');
		$('#q3rate1').closest('.question-container').find('.input-group').removeClass('filled');
		$('#btnQuestion3').prop('disabled', false);
		$('#q3Razones').val('');
	}

	if ($(this).prop('id') == 'q3rate2') {
		$('#q3rate1, #q3rate2').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q3rate3') {
		$('#q3rate1, #q3rate2, #q3rate3').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q3rate4') {
		$('#q3rate1, #q3rate2, #q3rate3, #q3rate4').closest('.rate-item').find('label').addClass('active');
	}
	if ($(this).prop('id') == 'q3rate5') {
		$('#q3rate1, #q3rate2, #q3rate3, #q3rate4, #q3rate5').closest('.rate-item').find('label').addClass('active');
	}
});



// Borrar productos sin fotos
$('#borrarProductos').click(function () {
	var varItem = $('#idEmprendimiento').text();
	$.ajax({
		method: "GET",
		url: base_url + "/rest/borrarproductosinfoto/" + varItem
	});
});

// Formatear precios en cards
$('.slide-details .price').each(function () {
	var priceDraft = $(this).find('.ammount').html();
	var trimmed = $.trim(priceDraft);
	const number = trimmed;

	var formatedPrice = new Intl.NumberFormat('es-AR', {
		style: 'currency',
		currency: 'ARS',
	}).format(number);

	$(this).find('.ammount').text(formatedPrice);
	var text = $(this).find('.ammount').html();
	text = text.slice(1, -3);
	$(this).find('.ammount').html(text);
});

//guardarProducto
$('.saveProducto').click(function () {
	var idusuario = $(this).closest('.slide-card').find('.idSaveUsuario').text();
	var idproducto = $(this).closest('.slide-card').find('.idSaveProducto').text();
	console.log('idusuario:     '+idusuario);
	console.log('idproducto:   '+idproducto);


	$.ajax({
		method: "GET",
		url: base_url + "/rest/guardarproducto/" + idusuario + "/" + idproducto
	});

	setTimeout('location.reload()', 300);

});





// Eliminar emprendimiento modal
$('#btnEliminarEmp').click(function () {
    $('#eliminar-emprendimiento').removeClass('hidden');
})


//guardado home
let idSaveProducto=document.querySelectorAll(".idSaveProducto");
let saveProducto=document.querySelectorAll(".saveProducto");
 for(let i=0; i<idSaveProducto.length;i++){
	for(let j=i+1; j<idSaveProducto.length;j++)
	if(idSaveProducto[i].textContent==idSaveProducto[j].textContent){
			saveProducto[i*2].classList.toggle("hidden");
			saveProducto[i*2+1].classList.toggle("hidden");
	}
}
//mostrar Evaluar emprendimiento
let preguntaConsumidor= document.querySelectorAll("#pregunta-consumidor")
let entregado= document.querySelectorAll(".btn-usuario-entregado")

let order= {
	id:"",
	firstQuestion:"",
	secondQuestion:"",
	thirdQuestion:"",
	average:"",
	comment:""
}
let nombreEmprendimiento=document.querySelectorAll(".contact-info")
let ponerNombreEmprendimiento=document.querySelectorAll("#pregunta-consumidor .emp-info h2")
let ponerProvinciaEmprendimiento=document.querySelectorAll("#pregunta-consumidor .emp-info p")
let ponerFotoEmprendimiento=document.querySelectorAll("#pregunta-consumidor .emp-header-min img")
let provinciaEmprendimiento=document.querySelectorAll(".provinciaEmprendimiento")
let localidadEmprendimiento=document.querySelectorAll(".localidadEmprendimiento")
let idFotoEmprendimiento=document.querySelectorAll(".idFotoEmprendimiento")

entregado.forEach((element,key)=>{element.addEventListener("click",function(){
		ponerNombreEmprendimiento[0].innerHTML=nombreEmprendimiento[key].textContent
		ponerProvinciaEmprendimiento[0].innerHTML=`${provinciaEmprendimiento[key].textContent}, ${localidadEmprendimiento[key].textContent}`
		ponerProvinciaEmprendimiento[0].style.color="white"
		
		if(idFotoEmprendimiento[key]){
			ponerFotoEmprendimiento[0].src="/foto/load/"+idFotoEmprendimiento[key].textContent 
		}else{
			ponerFotoEmprendimiento[0].src=""
		}
		
		preguntaConsumidor[0].classList.toggle("hidden")
		order.id=tomarIdPedidos[key].textContent
})})
let tomarIdPedidos=document.querySelectorAll(".tomarIdPedidos")
let step1=document.querySelectorAll("#step-1");
step1[0].addEventListener("submit",function(e){
	e.preventDefault()
	let inputs=document.querySelectorAll("#step-1 .active")
	order.firstQuestion=inputs.length
})
let step2=document.querySelectorAll("#step-2");
step2[0].addEventListener("submit",function(e){
	e.preventDefault()
	let inputs=document.querySelectorAll("#step-2 .active")
	order.secondQuestion=inputs.length
})

let stepThanks=document.querySelectorAll("#step-thanks");
stepThanks[0].addEventListener("submit",function(e){
	e.preventDefault()
	let saveProducto=document.querySelectorAll("#estados-pedidos");
	let inputsComentario=document.querySelector("#step-thanks .form-control").value
	order.average=(order.firstQuestion+order.secondQuestion+order.thirdQuestion)/3
	order.comment=inputsComentario
	let idproducto = $('.tomarIdProducto');
	idproducto=idproducto[0].innerText;
	order.id=idproducto;
	console.log(tomarIdPedidos)
	$.ajax({
        method: "GET",
         url: base_url + "/rest/valorar/"+order.id+"/"+order.thirdQuestion+"/"+order.secondQuestion+"/"+order.firstQuestion+"/"+order.comment
         //+"/"+idproducto[0].innerText
    });
	
})
let step3=document.querySelectorAll("#step-3");
step3[0].addEventListener("submit",function(e){
	e.preventDefault()
	let inputs=document.querySelectorAll("#step-3 .active")
	order.thirdQuestion=inputs.length
})


