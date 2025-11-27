const passwordInput = document.querySelector("#password");
const submitButton = document.querySelector("#submit-btn");
const errorPasswrd = document.querySelector("#password-error");

passwordInput.addEventListener("input", function(){

    const password = passwordInput.value;

    if(password.length < 5){
        errorPasswrd.textContent = "La contraseña debe tener 5 caracteres o mas.";
        errorPasswrd.classList.remove("d-none");
        submitButton.disabled = true;
    }else{
        errorPasswrd.textContent = "";
        errorPasswrd.classList.add("d-none");
        submitButton.disabled = false;
    }
});