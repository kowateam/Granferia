// Pedidos: Usuario
// --------------------------------------------
$('.btn-usuario-problema').click(function(){
	// var thisOrder = $(this).closest('.accordion').prop('id');

	$('#pedidos-problema-usuario').removeClass('hidden');
});

$('.btn-me-arrepenti').click(function(){
	$('#pedidos-me-arrepenti').removeClass('hidden');
});

// Mostrar "unidad" / "unidades"
var prodCantidad = $('#prod-cantidad').text();
if(prodCantidad == 1){
    $('.units').text('unidad');
} else {
    $('.units').text('unidades');
}

// Formatear día y hora
$('.confirmation-total').each(function () {
	var askedDate = $(this).closest('.asked-item').find('.asked-date').text().slice(0, -5);
	$(this).closest('.asked-item').find('.asked-date').text(askedDate);
});

// Formatear precio en la página de Pedidos
$('.confirmation-total').each(function () {
	var priceDraft = $(this).closest('.price').find('.confirmation-total-draft').text();

	// Eliminar espacios en blanco
	priceConfirmacion = $.trim(priceDraft);
	$(this).text(priceConfirmacion);

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

// Me arrepentí
$('.btn-usuario-arrepentir').click(function(){
	$(this).closest('.asked-item').find('.usuario-arrepentir').removeClass('hidden');
});

$('.btnCancelarPedido').click(function(){
	$(this).closest('#pedidos-usuario').find('.asked-item').remove();
	asked = $('#pedidos-usuario .asked-item').length;
	if (asked > 0) {
		$('#pedidos-usuario .asked-count').text('(' + asked + ')');
	} else {
		$('#pedidos-usuario .asked-count').text('');
	}
});

$('.apply-changes .form-check-label').click(function () {
	var motivoRechazo = $(this).closest('.form-check').find('input').val();
	$('#motivoRechazo').val(motivoRechazo);
});

// Ver información de contacto
$('.contact-info').click(function(){
	$(this).closest('.historic-item').find('#contacto-modal').removeClass('hidden');
});

// Aceptar / Rechazar pedido (emprendedor)
$('[for^=rechazarEmp]').click(function () {
	$(this).closest('.asked-item').find('#rechEmp').removeClass('hidden');
	$(this).closest('.asked-item').find('#aplicarCambiosEmp').prop('disabled', true);
});

$('[for^=aceptarEmp]').click(function () {
	$(this).closest('.asked-item').find('#rechEmp').addClass('hidden');
	$(this).closest('.asked-item').find('#aplicarCambiosEmp').prop('disabled', false);
	$(this).closest('.asked-item').find('.form-option input').prop('checked', false);
	$(this).closest('.asked-item').find('.form-option').removeClass('checked');
});

// Tomar acción sobre un pedido (emprendedor)
$('.option-selector .option').click(function(){
	$(this).closest('.asked-item').find('.apply-changes').removeClass('hidden');
});

// Contanos que pasó
$('.apply-changes .form-check').click(function(){
	$(this).closest('.asked-item').find('#aplicarCambiosEmp').prop('disabled', false);
});