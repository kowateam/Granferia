


let idUsuario=document.querySelectorAll(".idUsuario")
let idEmprendimiento=document.querySelectorAll(".idEmprendimiento")
let guargarvariable=document.querySelectorAll(".btn-seguir-emprendimiento");


let noseguir=document.querySelector(".btn-noseguir-emprendimiento");
noseguir.addEventListener("click",function(){
	var idEmprendimiento = $('.idEmprendimiento').text();
	var idUser =$('.idUsuario').text();
    $.ajax({
        method: "GET",
        url: base_url + "/rest/seguir/" +idEmprendimiento+"/"+idUser,
    });
    setTimeout('location.reload()', 5000);
})

guargarvariable[0].addEventListener("click",function(){
	var idEmprendimiento = $('.idEmprendimiento').text();
	var idUser =$('.idUsuario').text();
    $.ajax({
        method: "GET",
        url: base_url + "/rest/seguir/" +idEmprendimiento+"/"+idUser,
        //data:idUser
    });
    setTimeout('location.reload()', 5000);
})

