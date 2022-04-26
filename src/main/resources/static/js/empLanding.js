

let clickHidden=document.querySelector(".clickHidden")
let clickHidden2=document.querySelector(".clickHidden2")
let idClickHidden= document.querySelector("#idClickHidden")
let idClickHidden2= document.querySelector("#idClickHidden2")
let footer= document.querySelector("footer")
clickHidden.addEventListener("click", function(){
    idClickHidden.classList.toggle("hidden")
    idClickHidden2.classList.toggle("hidden")
    footer.style.display="none"
})
clickHidden2.addEventListener("click",function(){
    idClickHidden.classList.toggle("hidden")
    idClickHidden2.classList.toggle("hidden")
    footer.style.display="block"
})
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
    setTimeout('location.reload()', 300);
})

guargarvariable[0].addEventListener("click",function(){
	var idEmprendimiento = $('.idEmprendimiento').text();
	var idUser =$('.idUsuario').text();
    $.ajax({
        method: "GET",
        url: base_url + "/rest/seguir/" +idEmprendimiento+"/"+idUser,
        //data:idUser
    });
    setTimeout('location.reload()', 300);
})

