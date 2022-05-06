
var base_url = window.location.origin;



let guargarvariable=document.querySelectorAll(".btn-seguir-emprendimiento");


let noseguir=document.querySelector(".btn-noseguir-emprendimiento");
noseguir.addEventListener("click",function(){
	var idEmprendimiento = $('.idEmprendimiento').text();
	var idUser =$('.idUsuario').text();
    console.log(idEmprendimiento)
    console.log(idUser)
    $.ajax({
        method: "GET",
        url: base_url + "/rest/seguir/" +idEmprendimiento+"/"+idUser,
    });
    setTimeout('location.reload()', 1000);
})

guargarvariable[0].addEventListener("click",function(){
	var idEmprendimiento = $('.idEmprendimiento').text();
	var idUser =$('.idUsuario').text();
    console.log(idEmprendimiento)
    console.log(idUser)
    $.ajax({
        method: "GET",
        url: base_url + "/rest/seguir/" +idEmprendimiento+"/"+idUser,
        //data:idUser
    });
    setTimeout('location.reload()', 1000);
})

