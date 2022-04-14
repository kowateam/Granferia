

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
console.log(idUsuario[0].textContent)
console.log(idEmprendimiento[0].textContent)
let guargarvariable=document.querySelector(".btn-seguir-emprendimiento");
guargarvariable.addEventListener("click",function(){
    $.ajax({
        method: "GET",
        url: base_url + "/seguir" +idUsuario[0].textContent+idEmprendimiento[0].textContent
    });
})