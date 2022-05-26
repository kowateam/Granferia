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

 
let idFotoProdValorado= document.querySelectorAll("#idFotoProdValorado")
let idEmprendimientoValorado=document.querySelectorAll("#idEmprendimientoValorado")
let idProductoValorado=document.querySelectorAll("#idProductoValorado")
let btnIr=document.querySelectorAll("#btn-ir")
let nomEmp=document.querySelectorAll("#NomEmp")
let ponerNomEmp=document.querySelectorAll("#ponerNomEmp")
let logoValorado=document.querySelectorAll(".logoValorado")
console.log(nomEmp)
ponerNomEmp.forEach((element,index)=>{
    element.innerHTML=nomEmp[index].textContent
})
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

let destacadosGranferia=document.querySelector("#destacadosGranferia") 
var destacados= document.querySelector('#agarraDestacados')

var promiseValorados=$.ajax({
	method: "GET",
	url: base_url+"/rest/masvalorados",
	success: function(respuesta){
		
	}	,
	error:()=>{let cercanos=document.querySelector("#cercanos")
					cercanos.classList.remove("hidden")}
})
promiseValorados.then((respuesta)=>{
	
	setTimeout(()=>{	destacados.innerHTML="";
					let cercanos=document.querySelector("#cercanos")
					if(cercanos){
						cercanos.classList.remove("hidden")
					}
			
			for(let i=0;i<respuesta.length;i++){	
				let first=`<div class="slide-card lazyload" data-bg="/foto/load/${respuesta[i][2]}"
				style="background:url(${base_url}/foto/load/${respuesta[i][2]});background-size: cover">
				<span class="show-details"></span>
				<div class="slide-details hidden">
					<span class="icon save">
						<p class="idSaveProducto hidden">${respuesta[i][7]}</p>
						<p class="idSaveUsuario hidden">s</p>
						<img class="saveProducto" src="/img/gt/card/save.svg" alt="Guardar">
						<img class="saveProducto hidden" src="/img/gt/card/saved.svg" alt="Guardado">
					</span>
					<span class="icon close">
						<img src="/img/gt/card/close.png" alt="Cerrar">
					</span>`
				let tieneFotoEmp=`
					<span class="emp-logo" style="background:url('${base_url}/foto/load/${respuesta[i][1]}'); background-size: cover"></span>`
				  let noTieneFotoEmp=`<span  class="emp-logo default"></span>`
				let second=`<p class="name">${respuesta[i][3]}</p>`
				let tieneOferta=`<span>
						<p class="price offer">$<span class="ammount">${respuesta[i][6]}</span></p>
						<p class="price">$<span class="ammount">${respuesta[i][5]}</span></p>
					</span>`
				  let sinOferta=`<span>
                           <p class="price">$<span class="ammount">${respuesta[i][5]}</span></p>
                       </span>`
				  let cotizar=`<span>
						<button class="btn btn-secondary quote" type="button">A cotizar</button>
							</span>`
				   
				let end=`<a class="btn-details" target="_self"
						href="${base_url}/producto/${respuesta[i][7]}/${respuesta[i][0]}">Ver detalles</a>
				</div>
			</div>` 
				
			

				if(respuesta[i][4]=="null" &&  respuesta[i][1]!="null" && respuesta[i][6]!="null"){
			 	destacados.innerHTML+=first+tieneFotoEmp+second+tieneOferta+end 
				}
				if(respuesta[i][4]=="null" &&  respuesta[i][1]=="null" && respuesta[i][6]!="null"){
					destacados.innerHTML+=first+noTieneFotoEmp+second+tieneOferta+end
				   }
                   if(respuesta[i][4]=="null" &&  respuesta[i][1]!="null" && respuesta[i][6]=="null"){
                    destacados.innerHTML+=first+tieneFotoEmp+second+sinOferta+end
				   }
                   if(respuesta[i][4]=="null" &&  respuesta[i][1]=="null" && respuesta[i][6]=="null"){
					destacados.innerHTML+=first+noTieneFotoEmp+second+sinOferta+end
				   }
				   
                  
				if(respuesta[i][4]!="null" &&  respuesta[i][1]=="null"){
					destacados.innerHTML+=first+noTieneFotoEmp+second+cotizar+end
				   
				   }
				   if(respuesta[i][4]!="null" &&  respuesta[i][1]!="null" ){
					destacados.innerHTML+=first+tieneFotoEmp+second+cotizar+end
				   }
		}
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
				})
					
				  var destacadosSlide= document.querySelectorAll('#agarraDestacados .slide-card') ;
    				if(destacadosSlide[0]==undefined){
     			   destacadosGranferia.style.display="none"
    }
				   }
			   ,7000)

				
  
})