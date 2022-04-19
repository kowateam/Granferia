var base_url = window.location.origin;
let puntitos=document.querySelectorAll("#puntos")
let modalSeguir=document.querySelectorAll("#modalSeguir")
let moveBack=document.querySelectorAll("#moveBack")
let nombreEmprendimientos=document.querySelectorAll("#nombreEmprendimiento")
let irEmprendimiento=document.querySelectorAll(".irEmprendimiento")
let idEmprendimiento=document.querySelectorAll(".idEmprendimiento")
let seguirEmprendimiento=document.querySelectorAll(".seguirEmprendimiento")

puntitos.forEach((element,key)=>{
    element.addEventListener("click",function(e){
        e.preventDefault()
        modalSeguir[0].classList.remove("hidden");
        irEmprendimiento.forEach(element=>{element.addEventListener("click",function(){window.location.href=base_url+"/"+nombreEmprendimientos[key].textContent})})
        seguirEmprendimiento.forEach(element=>{element.addEventListener("click",function(){
            $(this).closest('.slide-card').find('.idSaveUsuario')   
            var idUser =$('.idUsuario').text();
            var idEmprendimientoo=idEmprendimiento[key].textContent
            $.ajax({
                method: "GET",
                url: base_url + "/rest/seguir/" +idEmprendimientoo+"/"+idUser,
            });
            window.location.href=base_url+"/"+nombreEmprendimientos[key].textContent
              
        })})
    })
})

