let inputName = document.querySelector(".saluda");
let buttonSaluda = document.querySelector(".boton");

buttonSaluda.addEventListener("click", function(event){
    if (inputName.value.trim() != ""){
        alert("Hola " + inputName.value);
        inputName.value = "";
    }
});