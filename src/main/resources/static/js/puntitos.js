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
/* 
$.ajax({
    method: "GET",
    url: base_url + "url",
}).done(function(data) {
    let result= JSON.parse(data) 
    renderCarru(result)
  });
function renderCarru(obj){
    
//let arrayDeObjetos=[{nombre:"pancho",img:"/foto/load/4281ac29-d9ab-4cc5-a708-d86446be9f9c"},{nombre:"pancho",img:"/foto/load/4281ac29-d9ab-4cc5-a708-d86446be9f9c"},{nombre:"pancho",img:"/foto/load/4281ac29-d9ab-4cc5-a708-d86446be9f9c"},{nombre:"pedro",img:"/foto/load/4281ac29-d9ab-4cc5-a708-d86446be9f9c"}]
let arrayDeObjetos=obj;
let prodDestacados=document.querySelector("#prodDestacados")
for(let i=0;i<arrayDeObjetos.length;i++){
    prodDestacados.innerHTML+=`<span >
    <div class="slide-card prod" style="background:url(${arrayDeObjetos[i].img}); background-size: auto 100%" >
       

        <span class="show-details "></span>

        <div class="slide-details  hidden ">
            <span  class="icon save">
                <p class="idSaveProducto hidden" ></p>
                <p class="idSaveUsuario hidden" ></p>
                
                <img  class="saveProducto" src="/img/gt/card/save.svg" alt="Guardar">
                <img  class="saveProducto hidden" src="/img/gt/card/saved.svg" alt="Guardado">
            </span>

            <span class="icon close">
                <img src="/img/gt/card/close.png" alt="Cerrar">
            </span>

            <!-- Detalles -->
            <span  class="emp-logo" ></span>
            <span  class="emp-logo default" ></span>

            <p class="name" >${arrayDeObjetos[i].nombre}</p>

            <span >
                <p class="price offer" >$<span class="ammount" >Precio oferta</span></p>
                <p class="price">$<span class="ammount" >Precio</span></p>
            </span>

            <span >
                <button class="btn btn-secondary quote" type="button">A cotizar</button>
            </span>

            <a class="btn-details" target="_self">Ver detalles</a>
        </div>
    </div>
</span>`
    
}}
 */

let idFotoProdValorado= document.querySelectorAll("#idFotoProdValorado")
let idEmprendimientoValorado=document.querySelectorAll("#idEmprendimientoValorado")
let idProductoValorado=document.querySelectorAll("#idProductoValorado")
let btnIr=document.querySelectorAll("#btn-ir")

let logoValorado=document.querySelectorAll(".logoValorado")
logoValorado.forEach((element,index)=>{
    const base="url("
    const adicional="/foto/load/"
    let linkEmp=base_url+"/producto/"+idProductoValorado[index].textContent+"/"+idEmprendimientoValorado[index].textContent
    if(idFotoProdValorado[index].textContent){
        element.style.background=base+base_url+adicional+idFotoProdValorado[index].textContent
        element.style.backgroundSize="cover"
        
    }
    else{
        element.classList.add("default")
    }
    btnIr[index].setAttribute("href",linkEmp)
})
//<a class="btn-details" target="_self"
//									th:href="@{/producto/} + ${prod.id} +'/'+ ${empren.id}">Ver detalles</a> 