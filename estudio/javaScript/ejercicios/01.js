//construir una funcion que devuelve
//el parametro mas grande

function cualEsMajor(a, b) {
    if(a>b){
        return  a + " es major que "+  b 
    }else if(a < b){
        return b +  "es major que "+ a
    }
    return "Son iguals"
}
let a = 5
let b = 5

console.log(cualEsMajor(a, b))